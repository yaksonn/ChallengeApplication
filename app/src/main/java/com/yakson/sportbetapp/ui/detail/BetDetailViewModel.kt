package com.yakson.sportbetapp.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yakson.sportbetapp.data.BetRepository
import com.yakson.sportbetapp.model.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BetDetailViewModel @Inject constructor(
    private val repository: BetRepository
) : ViewModel() {
    private val _event = MutableLiveData<Event?>()
    val event: LiveData<Event?> = _event

    fun getEventById(eventId: String) {
        viewModelScope.launch {
            var foundEvent = repository.getCachedEvents().find { it.id == eventId }
            if (foundEvent == null) {
                repository.getEvents()
                foundEvent = repository.getCachedEvents().find { it.id == eventId }
            }
            _event.value = foundEvent
        }
    }
} 