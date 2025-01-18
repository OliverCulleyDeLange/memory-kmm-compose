package uk.co.oliverdelange.memory.math

import kotlin.math.pow
import kotlin.random.Random

fun generateRandomNumber(digits: Int): Int {
    if (digits <= 0) throw IllegalArgumentException("Digits must be greater than 0")
    val start = 10.0.pow(digits - 1).toInt()
    val end = 10.0.pow(digits).toInt() - 1
    return Random.nextInt(start, end + 1)
}