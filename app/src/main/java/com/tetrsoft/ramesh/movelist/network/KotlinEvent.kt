package com.tetrasoft.ramesh.movelist.network

open class KotlinEvent {
    companion object {
        object LoadingEvent : KotlinEvent()
        object CompletedEvent : KotlinEvent()
        data class FailedEvent(val error: String) : KotlinEvent()
    }
}