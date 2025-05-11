package com.yakson.sportbetapp.util

import android.content.Context
import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics

fun Context.logFirebaseEvent(eventName: String, params: Bundle.() -> Unit = {}) {
    val analytics = FirebaseAnalytics.getInstance(this)
    val bundle = Bundle().apply(params)
    analytics.logEvent(eventName, bundle)
} 