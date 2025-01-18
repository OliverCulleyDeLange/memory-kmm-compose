package uk.co.oliverdelange.memory.math

import kotlin.random.Random

fun generateRandomNumberString(digits: Int): String {
    if (digits <= 0) throw IllegalArgumentException("Digits must be greater than 0")
    return (1..digits)
        .map { Random.nextInt(0, 10) }
        .joinToString("")
}