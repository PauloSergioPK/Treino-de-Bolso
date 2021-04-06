package com.karasdecc.treinodebolso.extensions

import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.DisplayMetrics
import android.util.Patterns
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.annotation.Dimension
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.Source
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.suspendCancellableCoroutine

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

val Context.isNetworkAvailable: Boolean get() {
    val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        val network  = cm.activeNetwork ?: return false
        val capabilities = cm.getNetworkCapabilities(network) ?: return false
        return when {
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH) -> true
            else -> false
        }
    } else {
        val networkInfo = cm.activeNetworkInfo ?: return false
        return networkInfo.isConnected
    }
}

/**
 * Awaits for completion of this [Query] (ab)using snapshot listener's cache management.
 * By using this you are guaranteed to receive up-to-date documents without needing
 * to manage the source yourself.
 *
 * @see [Source.DEFAULT]
 */
@ExperimentalCoroutinesApi
suspend inline fun Query.awaitRealtimeCollection(crashlytics: FirebaseCrashlytics? = null) =
    suspendCancellableCoroutine<MutableList<DocumentSnapshot>?> { continuation ->
        addSnapshotListener { value, error ->
            if (error == null && continuation.isActive && value != null) {
                val values = mutableListOf<DocumentSnapshot>()
                value.forEach { documentSnapshot ->
                    documentSnapshot?.let { values.add(it) }
                }
                continuation.resume(values) {
                    crashlytics?.recordException(it)
                    it.printStackTrace()
                }
            } else if (error != null && continuation.isActive) {
                continuation.resume(null) {
                    it.printStackTrace()
                    crashlytics?.recordException(it)
                    error.printStackTrace()
                    crashlytics?.recordException(error)
                }
            }
        }
    }