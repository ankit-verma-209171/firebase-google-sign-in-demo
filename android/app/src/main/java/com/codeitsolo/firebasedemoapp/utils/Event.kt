package com.codeitsolo.firebasedemoapp.utils

/**
 * A wrapper class for data that is exposed via LiveData to handle one-time events.
 * This is useful for events like navigation or showing a Snackbar, where you want to ensure
 * the event is only handled once.
 *
 * @param T The type of the content wrapped by this Event.
 */
class Event<out T>(private val content: T) {

    private var hasBeenHandled = false

    fun getContentIfNotHandled(): T? =
        if (hasBeenHandled) null else {
            hasBeenHandled = true
            content
        }
}
