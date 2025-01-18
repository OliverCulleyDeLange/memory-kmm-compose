package uk.co.oliverdelange.memory.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp
import uk.co.oliverdelange.memory.icon.BackspaceIcon

@Composable
fun Keypad(
    onKeypadPress: (Int) -> Unit,
    onReveal: () -> Unit,
    onBackspace: () -> Unit,
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = Modifier.padding(16.dp)
    ) {
        items(9) { i ->
            val number = i + 1
            OutlinedButton(onClick = { onKeypadPress(number) }, modifier = Modifier.size(40.dp).padding(2.dp)) {
                Text("$number")
            }
        }
        item {
            OutlinedButton(onClick = { onReveal() }, modifier = Modifier.size(40.dp).padding(2.dp)) {
                Text("Reveal", color = Color.DarkGray)
            }
        }
        item {
            OutlinedButton(onClick = { onKeypadPress(0) }, modifier = Modifier.size(40.dp).padding(2.dp)) {
                Text("0")
            }
        }
        item {
            OutlinedButton(onClick = { onBackspace() }, modifier = Modifier.size(40.dp).padding(2.dp)) {
                Image(BackspaceIcon, contentDescription = null, colorFilter = ColorFilter.tint(Color.Gray))
            }
        }
    }
}