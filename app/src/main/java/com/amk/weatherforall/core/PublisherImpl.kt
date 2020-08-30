package com.amk.weatherforall.core

import com.amk.weatherforall.core.interfaces.Observable
import com.amk.weatherforall.core.interfaces.Publisher

class PublisherImpl:Publisher {

    private val observables:MutableList<Observable> = mutableListOf()
    override fun subscribe(observable: Observable) {
        observables.add(observable)
    }

    override fun unsubscribe(observable: Observable) {
        observables.remove(observable)
    }

    override fun notify(text: String) {
        observables.forEach { it.updateCityName(text) }
    }
}