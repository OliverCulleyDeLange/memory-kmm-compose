package uk.co.oliverdelange.memory.model

data class NumberMemoryState(
    val config: NumberMemoryConfig = NumberMemoryConfig(),
    val started: Boolean = false,
    val result: Result? = null,
    val memorising: Boolean = false,
    val currentDigits: Int = 1,
    val numberToMemorise: String = "",
    val attemptText: String = "",
    val avgDigits: Double = 0.0,
    val maxDigits: Int = 0,
    // Every passed round adds the number of digits memorised to this list
    val passedDigits: List<Int> = emptyList()
)

enum class Result  {
    Pass, Fail
}

data class NumberMemoryConfig(
    val celebrationTimeMs: Long = 500L,
    val memorisingTimeMs: Long = 1000L,
    val memorisingTimeAdditionalMs: Long = 0L,
)