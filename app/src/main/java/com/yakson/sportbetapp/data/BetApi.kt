package com.yakson.sportbetapp.data

import com.yakson.sportbetapp.BuildConfig
import com.yakson.sportbetapp.model.Event
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface BetApi {
    @GET("sports/upcoming/odds/")
    suspend fun getUpcomingEvents(
        @Query("apiKey") apiKey: String = BuildConfig.ODDS_API_KEY,
        @Query("regions") regions: String = "eu",
        @Query("markets") markets: String = "h2h,spreads,totals"
    ): List<Event>
} 