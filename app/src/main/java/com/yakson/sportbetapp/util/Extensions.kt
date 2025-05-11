package com.yakson.sportbetapp.util

import android.graphics.drawable.Drawable
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.auth.FirebaseAuth
import com.yakson.sportbetapp.R
import java.text.SimpleDateFormat
import java.util.*

fun View.showView(show: Boolean) {
    this.visibility = when (show) {
        true -> View.VISIBLE
        false -> View.GONE
    }
}

fun TextView.loadTeamLogo(logoUrl: String?) {
    if (logoUrl.isNullOrEmpty()) {
        setCompoundDrawablesWithIntrinsicBounds(null, null, null, null)
        return
    }

    Glide.with(this)
        .load(logoUrl)
        .into(object : CustomTarget<Drawable>() {
            override fun onResourceReady(
                resource: Drawable,
                transition: Transition<in Drawable>?
            ) {
                setCompoundDrawablesWithIntrinsicBounds(resource, null, null, null)
            }

            override fun onLoadCleared(placeholder: Drawable?) {
                setCompoundDrawablesWithIntrinsicBounds(null, null, null, null)
            }
        })
}

fun String.formatDate(): String {
    return try {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
        val outputFormat = SimpleDateFormat("dd MMM - HH:mm", Locale.getDefault())
        val date = inputFormat.parse(this)
        outputFormat.format(date!!)
    } catch (e: Exception) {
        this
    }
}

fun Fragment.showLogoutDialog(onLogout: () -> Unit) {
    MaterialAlertDialogBuilder(requireContext())
        .setTitle(R.string.logout)
        .setMessage(R.string.logout_confirmation)
        .setPositiveButton(R.string.yes) { dialog, _ ->
            onLogout()
            dialog.dismiss()
        }
        .setNegativeButton(R.string.no) { dialog, _ ->
            dialog.dismiss()
        }
        .show()
} 