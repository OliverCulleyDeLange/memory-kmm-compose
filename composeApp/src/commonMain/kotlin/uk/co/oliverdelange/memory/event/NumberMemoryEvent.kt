package uk.co.oliverdelange.memory.event

sealed interface NumberMemoryEvent : UiEvent {
    data object Go : NumberMemoryEvent
    data object Reveal : NumberMemoryEvent
    data object Backspace : NumberMemoryEvent
    data class Key(val key: String) : NumberMemoryEvent
}