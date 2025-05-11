package com.yakson.sportbetapp.data

import com.yakson.sportbetapp.model.Event
import javax.inject.Inject

class BetRepository @Inject constructor(
    private val api: BetApi
) {
    private var cachedEvents: List<Event> = emptyList()

    suspend fun getEvents(): List<Event> {
        cachedEvents = api.getUpcomingEvents()
        return cachedEvents
    }

    fun getCachedEvents(): List<Event> {
        return cachedEvents
    }
} 