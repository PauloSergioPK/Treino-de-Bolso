package com.karasdecc.treinodebolso.models

import com.google.firebase.auth.FirebaseUser

data class User(
    var name: String = "",
    var email: String = "",
    var aTraining: Training? = null,
    var bTraining: Training? = null,
    var cTraining: Training? = null,
    var dTraining: Training? = null,
    var eTraining: Training? = null,
    var fTraining: Training? = null,
    var gTraining: Training? = null,
    var crossfitTraining: Training? = null
) {

    companion object{
        fun createFromFirebaseUser(firebaseUser: FirebaseUser) = User().apply {
            name = firebaseUser.displayName ?: ""
            email = firebaseUser.displayName ?: ""
        }
    }
}