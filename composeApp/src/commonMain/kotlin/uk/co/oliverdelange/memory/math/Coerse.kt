package uk.co.oliverdelange.memory.math

fun coerceToNearestMultiple(number: Int, multiple: Int): Int {
    require(multiple > 0) { "Multiple must be greater than 0" }
    return ((number + multiple / 2) / multiple) * multiple
}