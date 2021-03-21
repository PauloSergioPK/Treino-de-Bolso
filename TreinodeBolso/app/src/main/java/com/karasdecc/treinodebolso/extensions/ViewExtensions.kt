package com.karasdecc.treinodebolso.extensions

import android.view.View

fun View.setVisible(isVisible: Boolean) {
    this.visibility = if (isVisible) View.VISIBLE else View.GONE
}

fun View.isVisible(): Boolean = visibility == View.VISIBLE

fun View.showView() = setVisible(true)

fun View.hideView() = setVisible(false)
