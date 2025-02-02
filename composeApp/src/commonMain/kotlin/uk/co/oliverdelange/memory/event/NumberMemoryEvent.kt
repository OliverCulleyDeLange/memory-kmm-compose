package uk.co.oliverdelange.memory.event

sealed interface NumberMemoryEvent : UiEvent {
    data object Go : NumberMemoryEvent
    data object Skip : NumberMemoryEvent
    data object Backspace : NumberMemoryEvent
    data class SetAdditionalTimePerDigit(val time: Long) : NumberMemoryEvent
    data class Key(val key: String) : NumberMemoryEvent
}