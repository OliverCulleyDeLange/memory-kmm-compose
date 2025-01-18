package uk.co.oliverdelange.memory.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import uk.co.oliverdelange.memory.event.NumberMemoryEvent
import uk.co.oliverdelange.memory.event.UiEvent
import uk.co.oliverdelange.memory.math.generateRandomNumberString
import uk.co.oliverdelange.memory.model.NumberMemoryState
import uk.co.oliverdelange.memory.model.Score

class MemoryViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(NumberMemoryState())
    val uiState: StateFlow<NumberMemoryState> = _uiState.asStateFlow()

    fun onEvent(event: UiEvent) {
        when (event) {
            NumberMemoryEvent.Go -> startNewRound()
            NumberMemoryEvent.Reveal -> reveal()
            NumberMemoryEvent.Backspace -> backspace()
            is NumberMemoryEvent.Key -> onKeyPress(event.key)
            is NumberMemoryEvent.SetAdditionalTimePerDigit -> setAdditionalTimePerDigit(event.time)
        }
    }

    private fun setAdditionalTimePerDigit(time: Long) {
        _uiState.update { it.copy(config = it.config.copy(memorisingTimeAdditionalMs = time)) }
    }

    private fun backspace() {
        if (!_uiState.value.memorising) {
            _uiState.update { it.copy(attemptText = it.attemptText.dropLast(1)) }
        }
    }

    private fun reveal() {
        if (!_uiState.value.memorising) {
            _uiState.update {
                it.copy(
                    memorising = true,
                    reveals = it.reveals + 1
                )
            }
            startMemorisingTimer()
        }
    }

    private fun onKeyPress(key: String) {
        if (!_uiState.value.memorising) {
            _uiState.update {
                it.copy(attemptText = it.attemptText + key)
            }
            checkAttempt(_uiState.value.attemptText)
        }
    }

    private fun checkAttempt(newAttempt: String) {
        val state = _uiState.value
        if (newAttempt == state.numberToMemorise.toString()) {
            val newScore = Score(state.digits, state.reveals)
            val scores = state.scores.plus(newScore)
            val maxScore = if (newScore.digits > state.score.digits) newScore else state.score
            _uiState.update {
                it.copy(
                    attemptText = newAttempt,
                    digits = it.digits + 1,
                    celebrating = true,
                    scores = scores,
                    score = maxScore,
                )
            }

            viewModelScope.launch {
                delay(500L)
                startNewRound()
            }
        }
    }

    private fun startNewRound() {
        _uiState.update {
            it.copy(
                started = true,
                celebrating = false,
                memorising = true,
                numberToMemorise = generateRandomNumberString(it.digits),
                attemptText = "",
                reveals = 0
            )
        }
        startMemorisingTimer()
    }

    private fun startMemorisingTimer() {
        viewModelScope.launch {
            val state = _uiState.value
            val config = state.config
            delay(config.memorisingTimeMs + (state.digits * config.memorisingTimeAdditionalMs))
            _uiState.update { it.copy(memorising = false) }
        }
    }
}
