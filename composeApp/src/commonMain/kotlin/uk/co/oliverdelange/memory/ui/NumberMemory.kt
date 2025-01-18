package uk.co.oliverdelange.memory.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import uk.co.oliverdelange.memory.event.NumberMemoryEvent
import uk.co.oliverdelange.memory.event.UiEvent
import uk.co.oliverdelange.memory.model.NumberMemoryState

@Composable
fun NumberMemory(state: NumberMemoryState, onEvent: (UiEvent) -> Unit) {
    Column {
        MainContent(
            state,
            onGo = { onEvent(NumberMemoryEvent.Go) }
        )

        Keypad(
            onKeypadPress = { pressedKeyString -> onEvent(NumberMemoryEvent.Key(pressedKeyString.toString())) },
            onReveal = { onEvent(NumberMemoryEvent.Reveal) },
            onBackspace = { onEvent(NumberMemoryEvent.Backspace) },
        )
    }
}