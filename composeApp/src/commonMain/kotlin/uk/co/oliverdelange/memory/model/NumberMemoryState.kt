package uk.co.oliverdelange.memory.model

data class NumberMemoryState(
    val config: NumberMemoryConfig = NumberMemoryConfig(),
    val started: Boolean = false,
    val celebrating: Boolean = false,
    val memorising: Boolean = false,
    val digits: Int = 1,
    val numberToMemorise: Int = 0,
    val attemptText: String = "",
    val reveals: Int = 0,
    val score: Score = Score(0, 0),
    val scores: List<Score> = emptyList()
)

data class NumberMemoryConfig(
    val celebrationTimeMs: Long = 500L,
    val memorisingTimeMs: Long = 1000L,
    val memorisingTimeAdditionalMs: Long = 0L,
)