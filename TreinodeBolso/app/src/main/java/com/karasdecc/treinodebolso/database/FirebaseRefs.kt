package com.karasdecc.treinodebolso.database

import com.google.firebase.auth.FirebaseAuth
import org.koin.core.KoinComponent
import org.koin.core.inject

object FirebaseRefs: KoinComponent {
    private val auth: FirebaseAuth by inject()
    private val user get() = auth.currentUser
    private val uid get() = user?.uid

    val userTrainingRefs: String get() {
        require(uid != null) { "Currently there is no user signed in" }
        return "$USERS/$uid/${TRAININGS}"
    }

    const val EXERCISE = "Exercise"
    const val TRAINING = "Training"
    const val TRAININGS = "Trainings"
    const val USERS = "Users"
}