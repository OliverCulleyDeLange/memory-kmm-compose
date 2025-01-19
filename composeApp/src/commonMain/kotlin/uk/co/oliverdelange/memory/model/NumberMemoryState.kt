package uk.co.oliverdelange.memory.model

data class NumberMemoryState(
    val config: NumberMemoryConfig = NumberMemoryConfig(),
    val started: Boolean = false,
    val result: Result? = null,
    val memorising: Boolean = false,
    val digits: Int = 1,
    val numberToMemorise: String = "",
    val attemptText: String = "",
    val score: Score = Score(0),
    val scores: List<Score> = emptyList()
)

enum class Result  {
    Pass, Fail
}

data class NumberMemoryConfig(
    val celebrationTimeMs: Long = 500L,
    val memorisingTimeMs: Long = 1000L,
    val memorisingTimeAdditionalMs: Long = 0L,
)