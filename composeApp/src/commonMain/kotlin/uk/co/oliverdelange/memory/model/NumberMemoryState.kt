package uk.co.oliverdelange.memory.model

import uk.co.oliverdelange.memory.math.generateRandomNumber

data class NumberMemoryState(
    // Config
    val celebrationTimeMs: Long = 500L,
    val memorisingTimeMs: Long = 1000L,
    val memorisingTimeAdditionalMs: Long = 250L,

    val started: Boolean = false,
    val celebrating: Boolean = false,
    val memorising: Boolean = false,
    val digits: Int = 1,
    val numberToMemorise: Int = 0,
    val attemptText: String = "",
    val reveals: Int = 0,
    val score: Score = Score(0,0),
    val scores: List<Score> = emptyList()
)