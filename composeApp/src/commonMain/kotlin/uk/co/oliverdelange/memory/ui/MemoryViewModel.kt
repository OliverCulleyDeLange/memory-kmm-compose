package uk.co.oliverdelange.memory.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import uk.co.oliverdelange.memory.event.NumberMemoryEvent
import uk.co.oliverdelange.memory.event.UiEvent
import uk.co.oliverdelange.memory.math.generateRandomNumberString
import uk.co.oliverdelange.memory.math.median
import uk.co.oliverdelange.memory.model.NumberMemoryConfig
import uk.co.oliverdelange.memory.model.NumberMemoryState
import uk.co.oliverdelange.memory.model.Result

class MemoryViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(NumberMemoryState())
    val uiState: StateFlow<NumberMemoryState> = _uiState.asStateFlow()

    private var memorisingTimer: Job? = null

    fun onEvent(event: UiEvent) {
        when (event) {
            NumberMemoryEvent.Go -> startNewRound()
            NumberMemoryEvent.Skip -> skip()
            NumberMemoryEvent.Backspace -> backspace()
            is NumberMemoryEvent.Key -> onKeyPress(event.key)
            is NumberMemoryEvent.SetAdditionalTimePerDigit -> setAdditionalTimePerDigit(event.time)
        }
    }

    private fun setAdditionalTimePerDigit(time: Long) {
        _uiState.update { it.copy(config = it.config.copy(memorisingTimeAdditionalMs = time)) }
    }

    private fun backspace() {
        if (_uiState.value.started && !_uiState.value.memorising) {
            _uiState.update { it.copy(attemptText = it.attemptText.dropLast(1)) }
        }
    }

    private fun skip() {
        if (_uiState.value.started) {
            startNewRound()
        }
    }

    private fun onKeyPress(key: String) {
        if (_uiState.value.started) {
            memorisingTimer?.cancel()
            _uiState.update {
                it.copy(
                    attemptText = it.attemptText + key,
                    started = true,
                    memorising = false
                )
            }
            checkAttempt(_uiState.value.attemptText)
        }
    }

    private fun checkAttempt(newAttempt: String) {
        val state = _uiState.value
        if (newAttempt.length == state.numberToMemorise.length) {
            if (newAttempt == state.numberToMemorise) {
                val newScore = state.currentDigits
                val attempts = state.passedDigits.plus(newScore)
                val maxScore = if (newScore > state.maxDigits) newScore else state.maxDigits
                val avgDigits = median(attempts)
                _uiState.update {
                    it.copy(
                        attemptText = newAttempt,
                        currentDigits = it.currentDigits + 1,
                        result = Result.Pass,
                        passedDigits = attempts,
                        maxDigits = maxScore,
                        avgDigits = avgDigits,
                    )
                }
                viewModelScope.launch {
                    delay(500L)
                    startNewRound()
                }
            } else {
                _uiState.update {
                    it.copy(
                        attemptText = newAttempt,
                        currentDigits = it.currentDigits - 1,
                        result = Result.Fail,
                    )
                }
                viewModelScope.launch {
                    val timer = getMemorisingTimerMs(state)
                    delay(timer.coerceAtLeast(3000))
                    startNewRound()
                }
            }
        }
    }

    private fun startNewRound() {
        _uiState.update {
            it.copy(
                started = true,
                result = null,
                memorising = true,
                numberToMemorise = generateRandomNumberString(it.currentDigits),
                attemptText = "",
            )
        }
        startMemorisingTimer()
    }

    private fun startMemorisingTimer() {
        memorisingTimer = viewModelScope.launch {
            val state = _uiState.value
            delay(getMemorisingTimerMs(state))
            _uiState.update { it.copy(memorising = false) }
        }
    }

    private fun getMemorisingTimerMs(state: NumberMemoryState) =
        state.config.memorisingTimeMs + (state.currentDigits * state.config.memorisingTimeAdditionalMs)
}
