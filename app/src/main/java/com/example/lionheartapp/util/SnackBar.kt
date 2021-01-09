package com.example.lionheartapp.util

import android.content.Context
import android.view.View
import androidx.core.content.ContextCompat
import com.example.lionheartapp.R
import com.google.android.material.snackbar.Snackbar

class SnackBar {

    fun showSnackbar(view: View, context: Context) {
        Snackbar.make(
            view,
            "For full experience enable Wifi/Mobile data",
            Snackbar.LENGTH_INDEFINITE
        )
            .setAction("Close") { }
            .setActionTextColor(ContextCompat.getColor(context, R.color.white))
            .show()
    }
}