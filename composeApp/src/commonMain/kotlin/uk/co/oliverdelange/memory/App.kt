package uk.co.oliverdelange.memory

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jetbrains.compose.ui.tooling.preview.Preview
import kotlin.math.pow
import kotlin.random.Random

@Composable
@Preview
fun App() {
    MaterialTheme {
        NumberMemory()
    }
}

@Composable
fun NumberMemory() {
    var numberDigits by remember { mutableStateOf(1) }
    var numberToMemorise by remember { mutableStateOf(generateRandomNumber(numberDigits)) }
    var enteredNumber by remember { mutableStateOf("") }

    var memorising by remember { mutableStateOf(true) }
    val memorisingTimer = rememberCoroutineScope()
    LaunchedEffect(memorising) {
        if (memorising) {
            memorisingTimer.launch {
                delay(1000L + (numberDigits * 100))
                memorising = false
            }
        }
    }

    fun onKeypadPress(number: Int) {
        enteredNumber += number
        if (enteredNumber == numberToMemorise.toString()){
            numberDigits++
            numberToMemorise = generateRandomNumber(numberDigits)
            enteredNumber = ""
            memorising = true
        }
    }

    fun onBackspace() {
        enteredNumber = enteredNumber.dropLast(1)
    }

    Column(modifier = Modifier.padding(16.dp)) {
        Box(
            modifier = Modifier.weight(1f).fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            androidx.compose.animation.AnimatedVisibility(visible = memorising) {
                Text("$numberToMemorise", fontSize = 40.sp)
            }
        }

        Text(
            enteredNumber,
            fontSize = 40.sp,
            modifier = Modifier.padding(vertical = 16.dp).fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        LazyVerticalGrid(columns = GridCells.Fixed(3)) {
            items(9) { i ->
                val number = i + 1
                OutlinedButton(onClick = { onKeypadPress(number) }, modifier = Modifier.size(40.dp).padding(2.dp)) {
                    Text("$number")
                }
            }
            item {
                OutlinedButton(onClick = { memorising = true }, modifier = Modifier.size(40.dp).padding(2.dp)) {
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
                    Image(BackspaceIcon, contentDescription = null, colorFilter = ColorFilter.tint(Gray))
                }
            }
        }
    }
}

fun generateRandomNumber(digits: Int): Int {
    if (digits <= 0) throw IllegalArgumentException("Digits must be greater than 0")
    val start = 10.0.pow(digits - 1).toInt()
    val end = 10.0.pow(digits).toInt() - 1
    return Random.nextInt(start, end + 1)
}