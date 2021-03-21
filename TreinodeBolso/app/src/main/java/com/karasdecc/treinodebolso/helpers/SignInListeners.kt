package com.karasdecc.treinodebolso.helpers

interface SignInListeners {
    fun onGoogleClicked()
    suspend fun signInWithEmail(email : String, password : String) : Unit?
    suspend fun signInWithEmail(name: String, email : String, password : String) : Unit?
}