package com.rover.roverandroiddemo.demoApp.utils

import android.content.Context
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.rover.roverandroiddemo.R

object NotificationHelper {

    private const val TAG = "NOTIFICATION_HELPER"

    @JvmStatic
    fun displayErrorSnackBar(context: Context, view: View, message: String) {
        if (!view.isAttachedToWindow) return
        val snackBar = Snackbar.make(
            view,
            message,
            Snackbar.LENGTH_INDEFINITE
        ).setDuration(2500)
        val snackBarView = snackBar.view
        snackBar.setActionTextColor(ContextCompat.getColor(context, android.R.color.white))
        snackBarView.setBackgroundColor(ContextCompat.getColor(context, R.color.error_color))
        snackBar.show()
    }

    @JvmStatic
    fun displaySuccessSnackBar(context: Context, view: View, message: String) {
        if (!view.isAttachedToWindow) return
        val snackBar = Snackbar.make(
            view,
            message,
            Snackbar.LENGTH_INDEFINITE
        ).setDuration(2500)
        val snackBarView = snackBar.view
        snackBar.setActionTextColor(ContextCompat.getColor(context, android.R.color.white))
        snackBarView.setBackgroundColor(ContextCompat.getColor(context, R.color.success_color))
        snackBar.show()
    }

    @JvmStatic
    fun isWindowShown(context: Context): Boolean {
        if (context !is AppCompatActivity) return false
        val isWindowShown = context.window?.decorView?.isShown!!
        if (!isWindowShown){
            Log.e(TAG, "Activity not active")
        }
        return isWindowShown
    }
}