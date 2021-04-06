package com.karasdecc.treinodebolso.models

import com.google.firebase.auth.FirebaseUser

data class User(
    var name: String = "",
    var email: String = "",
    var trainings: List<Training> = listOf()
) {

    companion object{
        fun createFromFirebaseUser(firebaseUser: FirebaseUser) = User().apply {
            name = firebaseUser.displayName ?: ""
            email = firebaseUser.displayName ?: ""
        }
    }
}