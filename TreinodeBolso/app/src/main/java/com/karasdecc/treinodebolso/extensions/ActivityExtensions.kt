package com.karasdecc.treinodebolso.extensions

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater

typealias ActivityInflater<T> = (LayoutInflater) -> T

inline fun <reified T : Activity> Activity.initActivity(block: Intent.() -> Unit = {}) {
    val intent = Intent(this, T::class.java)
    block(intent)
    startActivity(intent)
}

fun Activity.launchUrl(url: String) {
    val uri = Uri.parse(url)
    try {
        startActivity(Intent(Intent.ACTION_VIEW, uri))
    } catch (ex: Exception) {
        ex.printStackTrace()
    }
}