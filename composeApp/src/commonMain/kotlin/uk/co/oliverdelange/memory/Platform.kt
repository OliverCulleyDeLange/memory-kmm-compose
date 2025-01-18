package uk.co.oliverdelange.memory

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform