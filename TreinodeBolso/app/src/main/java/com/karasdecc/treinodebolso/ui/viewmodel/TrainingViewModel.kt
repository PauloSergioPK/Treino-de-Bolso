package com.karasdecc.treinodebolso.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import org.koin.core.KoinComponent

class TrainingViewModel(
    private val db: FirebaseFirestore
) : ViewModel(), KoinComponent {
}