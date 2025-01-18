package uk.co.oliverdelange.memory

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import org.jetbrains.compose.ui.tooling.preview.Preview
import uk.co.oliverdelange.memory.ui.MemoryViewModel
import uk.co.oliverdelange.memory.ui.NumberMemory

@Composable
@Preview
fun App(
    viewModel: MemoryViewModel = viewModel { MemoryViewModel() },
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    MaterialTheme {
        NumberMemory(state = state, onEvent = { viewModel.onEvent(it) })
    }
}
