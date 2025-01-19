package uk.co.oliverdelange.memory.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import uk.co.oliverdelange.memory.model.NumberMemoryState
import uk.co.oliverdelange.memory.icon.Check
import uk.co.oliverdelange.memory.icon.Cross
import uk.co.oliverdelange.memory.model.Result
import uk.co.oliverdelange.memory.model.Score

@Composable
fun ColumnScope.MainContent(
    state: NumberMemoryState,
    onGo: () -> Unit
) {
    Box(
        modifier = Modifier
            .weight(1f)
            .fillMaxSize()
            .padding(16.dp)
            .border(2.dp, Color.LightGray, RoundedCornerShape(32.dp)),
        contentAlignment = Alignment.Center
    ) {
        Score(state.score, modifier = Modifier.align(Alignment.TopEnd))

        BottomRightCornerContent(state, onGo)

        CenterContent(state)
    }
}

@Composable
fun CenterContent(state: NumberMemoryState) {
    when {
        state.memorising -> {
            Text(
                state.numberToMemorise,
                fontSize = 40.sp,
                color = Color.Black
            )
        }

        !state.memorising -> {
            Text(
                state.attemptText,
                fontSize = 40.sp,
                color = when (state.result) {
                    Result.Pass -> Color.Green
                    Result.Fail -> Color.Red
                    else -> Color.LightGray
                }
            )
        }
    }
}

@Composable
fun BoxScope.BottomRightCornerContent(state: NumberMemoryState, onGo: () -> Unit) {
    Box(
        modifier = Modifier
            .padding(16.dp)
            .align(Alignment.BottomEnd)
    ) {
        when {
            !state.started -> {
                Box(modifier = Modifier
                    .border(5.dp, Color.Green, CircleShape)
                    .padding(16.dp)
                    .clickable { onGo() }) {
                    Text("GO")
                }
            }
        }
    }
}

@Composable
fun Score(score: Score, modifier: Modifier) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.padding(16.dp)
    ) {
        Text("Score:")
        Text(score.digits.toString(), fontWeight = FontWeight.Bold)
    }
}