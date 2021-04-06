package com.karasdecc.treinodebolso.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.karasdecc.treinodebolso.models.Training
import org.koin.core.KoinComponent

class TrainingViewModel(
    private val db: FirebaseFirestore
) : ViewModel(), KoinComponent {

    private var _trainings : MutableLiveData<List<Training>> = MutableLiveData()
    val trainings get() = _trainings

    fun setTrainings(trainingList: List<Training>){
        _trainings.value = trainingList
    }
}