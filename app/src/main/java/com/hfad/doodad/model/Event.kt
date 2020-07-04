package com.hfad.doodad.model

import androidx.lifecycle.Observer

class Event<T>(val data: T) {
    private var observed = false


    fun observeIfNotAlready(): T? {
        return if (observed) null
        else {
            observed = true
            data
        }
    }

    fun peek() = data
}

class EventObserver< T >( val onChange : ( Event< T > ) -> Unit ) : Observer<Event<T>>{
    override fun onChanged(t: Event<T>) {
        if (t.observeIfNotAlready() != null )
            onChange(t)
    }
}