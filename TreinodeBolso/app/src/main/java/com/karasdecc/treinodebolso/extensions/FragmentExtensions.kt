package com.karasdecc.treinodebolso.extensions

import android.view.LayoutInflater
import android.view.ViewGroup

typealias FragmentInflater<T> = (LayoutInflater, ViewGroup?, Boolean) -> T