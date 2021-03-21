package com.karasdecc.treinodebolso.ui.activities.main

import android.os.Bundle
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.karasdecc.treinodebolso.databinding.ActivityMainBinding
import com.karasdecc.treinodebolso.ui.BaseBindingActivity
import kotlin.math.sign

class MainActivity : BaseBindingActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {

    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var googleSignInOptions: GoogleSignInOptions
    private val auth: FirebaseAuth by lazy { Firebase.auth }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupViews()
        configureGoogleSignIn()
    }

    private fun setupViews(){
        binding.button2.setOnClickListener {
            signOut()
            finish()
        }
    }

    private fun configureGoogleSignIn() {
        googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions)
    }

    private fun signOut() {
        auth.signOut()
        googleSignInClient.signOut()
    }

}