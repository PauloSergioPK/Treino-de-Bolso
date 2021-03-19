package com.karasdecc.treinodebolso.extensions

import android.net.Uri
import android.widget.ImageView
import com.bumptech.glide.Glide

fun ImageView.load(uri: Uri?) {
    Glide.with(context).load(uri).into(this)
}

fun ImageView.load(url: String?) {
    Glide.with(context).load(url).into(this)
}
