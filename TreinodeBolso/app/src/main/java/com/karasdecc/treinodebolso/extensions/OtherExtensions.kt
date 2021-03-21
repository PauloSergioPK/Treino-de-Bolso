package com.karasdecc.treinodebolso.extensions

import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.util.DisplayMetrics
import android.util.Patterns
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.annotation.Dimension

fun String.isValidEmail(): Boolean {
    return isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(this).matches()
}

fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}

@JvmOverloads @Dimension(unit = Dimension.PX) fun Number.dpToPx(
    metrics: DisplayMetrics = Resources.getSystem().displayMetrics
): Float {
    return toFloat() * metrics.density
}

@JvmOverloads @Dimension(unit = Dimension.DP) fun Number.pxToDp(
    metrics: DisplayMetrics = Resources.getSystem().displayMetrics
): Float {
    return toFloat() / metrics.density
}
