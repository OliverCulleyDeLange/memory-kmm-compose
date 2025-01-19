package uk.co.oliverdelange.memory.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Slider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import uk.co.oliverdelange.memory.event.NumberMemoryEvent
import uk.co.oliverdelange.memory.event.UiEvent
import uk.co.oliverdelange.memory.math.coerceToNearestMultiple
import uk.co.oliverdelange.memory.model.NumberMemoryConfig
import uk.co.oliverdelange.memory.model.NumberMemoryState

@Composable
fun NumberMemory(state: NumberMemoryState, onEvent: (UiEvent) -> Unit) {
    Column {
        MainContent(
            state,
            onGo = { onEvent(NumberMemoryEvent.Go) }
        )

        Configuration(state.config) {
            onEvent(NumberMemoryEvent.SetAdditionalTimePerDigit(it))
        }

        Keypad(
            onKeypadPress = { pressedKeyString -> onEvent(NumberMemoryEvent.Key(pressedKeyString.toString())) },
            onSkip = { onEvent(NumberMemoryEvent.Skip) },
            onBackspace = { onEvent(NumberMemoryEvent.Backspace) },
        )
    }
}

@Composable
fun Configuration(config: NumberMemoryConfig, onChange: (Long) -> Unit) {
    Column(modifier = Modifier.padding(horizontal = 16.dp)){
        var valueL by remember { mutableLongStateOf(config.memorisingTimeAdditionalMs) }
        var valueF by remember { mutableFloatStateOf(config.memorisingTimeAdditionalMs.toFloat()) }
        Text("Extra milliseconds per digit: ${config.memorisingTimeAdditionalMs}")
        Slider(
            valueF,
            onValueChange = {
                val coerced = coerceToNearestMultiple(it.toInt(), 100)
                valueF = it
                valueL = coerced.toLong()
            },
            onValueChangeFinished = { onChange(valueL) },
            valueRange = 0f..500f,
            steps = 4
        )
    }
}
