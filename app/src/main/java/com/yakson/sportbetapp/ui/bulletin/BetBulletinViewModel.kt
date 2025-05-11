package com.yakson.sportbetapp.ui.bulletin

import androidx.lifecycle.*
import com.yakson.sportbetapp.data.BetRepository
import com.yakson.sportbetapp.model.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BetBulletinViewModel @Inject constructor(private val repository: BetRepository) :
    ViewModel() {

    private val _events = MutableLiveData<List<Event>>()
    val events: LiveData<List<Event>> = _events

    fun fetchEvents() {
        viewModelScope.launch {
            _events.value = repository.getEvents()
        }
    }

    fun searchEvents(query: String) {
        viewModelScope.launch {
            val allEvents = repository.getCachedEvents()
            _events.value = allEvents.filter { event ->
                event.home_team.contains(query, ignoreCase = true) ||
                        event.away_team.contains(query, ignoreCase = true)
            }
        }
    }

} 