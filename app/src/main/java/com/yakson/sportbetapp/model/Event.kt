package com.yakson.sportbetapp.model

data class Event(
    val id: String,
    val sport_key: String,
    val commence_time: String,
    val home_team: String,
    val away_team: String,
    val bookmakers: List<Bookmaker>,
    val home_team_logo: String? = null,
    val away_team_logo: String? = null
)

data class Bookmaker(
    val key: String,
    val title: String,
    val last_update: String,
    val markets: List<Market>
)

data class Market(
    val key: String,
    val outcomes: List<Outcome>
)

data class Outcome(
    val name: String,
    val price: Double
) 