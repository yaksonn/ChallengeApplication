package com.yakson.sportbetapp.ui.basket

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.yakson.sportbetapp.model.Outcome
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class BetBasketViewModel @Inject constructor(
    @Named("basket") private val basketDataStore: DataStore<Preferences>,
    private val gson: Gson
) : ViewModel() {

    private val _basket = MutableStateFlow<Set<Outcome>>(emptySet())
    val basket: StateFlow<Set<Outcome>> = _basket.asStateFlow()

    private val _totalPrice = MutableStateFlow(0.0)
    val totalPrice: StateFlow<Double> = _totalPrice.asStateFlow()

    init {
        loadBasket()
    }

    private fun loadBasket() {
        viewModelScope.launch {
            basketDataStore.data.collectLatest { preferences ->
                val basketJson = preferences[BASKET_KEY] ?: "[]"
                val type = object : TypeToken<Set<Outcome>>() {}.type
                val basket = gson.fromJson<Set<Outcome>>(basketJson, type) ?: emptySet()
                _basket.value = basket
                _totalPrice.value = basket.sumOf { it.price }
            }
        }
    }

    fun addToBasket(outcome: Outcome) {
        viewModelScope.launch {
            val currentBasket = _basket.value.toMutableSet()
            currentBasket.add(outcome)
            saveBasket(currentBasket)
        }
    }

    fun removeFromBasket(outcome: Outcome) {
        viewModelScope.launch {
            val currentBasket = _basket.value.toMutableSet()
            currentBasket.remove(outcome)
            saveBasket(currentBasket)
        }
    }

    fun clearBasket() {
        viewModelScope.launch {
            saveBasket(emptySet())
        }
    }

    private suspend fun saveBasket(basket: Set<Outcome>) {
        basketDataStore.edit { preferences ->
            val basketJson = gson.toJson(basket)
            preferences[BASKET_KEY] = basketJson
        }
    }

    companion object {
        private val BASKET_KEY = stringPreferencesKey("basket")
    }
} 