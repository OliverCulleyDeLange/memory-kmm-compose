package uk.co.oliverdelange.memory.math

fun <T : Number> median(numbers: List<T>): Double {
    if (numbers.isEmpty()) return 0.0

    val sorted = numbers.sortedBy { it.toDouble() }
    val size = sorted.size

    return if (size % 2 == 0) {
        // Average of two middle elements for even-sized list
        (sorted[size / 2 - 1].toDouble() + sorted[size / 2].toDouble()) / 2
    } else {
        // Middle element for odd-sized list
        sorted[size / 2].toDouble()
    }
}