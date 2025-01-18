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
import uk.co.oliverdelange.memory.math.generateRandomNumber
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
        }
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
                val newAttempt = it.attemptText + key
                if (newAttempt == it.numberToMemorise.toString()) {
                    viewModelScope.launch {
                        delay(500L)
                        startNewRound()
                    }
                    val newScore = Score(it.digits, it.reveals)
                    val scores = it.scores.plus(newScore)
                    val maxScore = if (newScore.digits > it.score.digits) newScore else it.score
                    it.copy(
                        attemptText = newAttempt,
                        digits = it.digits + 1,
                        celebrating = true,
                        scores = scores,
                        score = maxScore,
                    )
                } else {
                    it.copy(attemptText = newAttempt)
                }
            }
        }
    }

    private fun startNewRound() {
        _uiState.update {
            it.copy(
                started = true,
                celebrating = false,
                memorising = true,
                numberToMemorise = generateRandomNumber(it.digits),
                attemptText = "",
                reveals = 0
            )
        }
        startMemorisingTimer()
    }

    private fun startMemorisingTimer() {
        viewModelScope.launch {
            delay(_uiState.value.memorisingTimeMs + (_uiState.value.digits * _uiState.value.memorisingTimeAdditionalMs))
            _uiState.update { it.copy(memorising = false) }
        }
    }
}
