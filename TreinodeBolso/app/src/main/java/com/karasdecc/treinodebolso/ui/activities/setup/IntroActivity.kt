package com.karasdecc.treinodebolso.ui.activities.setup

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.karasdecc.treinodebolso.R
import com.karasdecc.treinodebolso.databinding.ActivityIntroBinding
import com.karasdecc.treinodebolso.ui.BaseBindingActivity
import com.karasdecc.treinodebolso.helpers.SignInListeners
import com.karasdecc.treinodebolso.ui.activities.main.MainActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class IntroActivity : BaseBindingActivity<ActivityIntroBinding>(ActivityIntroBinding::inflate),
    SignInListeners, FirebaseAuth.AuthStateListener{

    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var googleSignInOptions: GoogleSignInOptions
    private val auth: FirebaseAuth by lazy { Firebase.auth }

    private var isNewSignIn = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        configureGoogleSignIn()
    }

    override fun onStart() {
        super.onStart()
        auth.addAuthStateListener(this)
    }

    override fun onStop() {
        super.onStop()
        auth.removeAuthStateListener(this)
    }

    override suspend fun signInWithEmail(email: String, password: String) = withContext(Dispatchers.IO) {
        return@withContext runCatching {
            auth.signInWithEmailAndPassword(email, password).await()
        }.getOrElse {
            val errorMessage = getSignInErrorMessage(it)
            withContext(Dispatchers.Main) {
                Snackbar.make(binding.root, errorMessage, Snackbar.LENGTH_LONG).show()
            }
            null
        }?.let {
            isNewSignIn = true
        }
    }

    override suspend fun signInWithEmail(name: String, email: String, password: String) = withContext(Dispatchers.IO) {
        return@withContext runCatching {
            val creationResult = auth.createUserWithEmailAndPassword(email, password).await()
            val user = auth.currentUser
            val profileRequest = UserProfileChangeRequest.Builder()
                .setDisplayName(name)
                .build()
            user?.updateProfile(profileRequest)
            creationResult
        }.getOrElse {
            val msg = if ((it.message ?: "").contains("already in use")) {
                getString(R.string.email_already_in_use)
            } else {
                getString(R.string.sign_up_error)
            }
            withContext(Dispatchers.Main) {
                Snackbar.make(binding.root, msg, Snackbar.LENGTH_LONG).show()
            }
            null
        }?.let {
            isNewSignIn = true
        }
    }

    override fun onGoogleClicked() {
        try {
            val signInIntent: Intent = googleSignInClient.signInIntent
            startActivityForResult(signInIntent, RC_GOOGLE_SIGN_IN)
        } catch (e: Exception) {
            e.printStackTrace()
            Snackbar.make(binding.root,getString(R.string.login_error),Snackbar.LENGTH_SHORT).show()
        }
    }

    private fun configureGoogleSignIn() {
        googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_GOOGLE_SIGN_IN) {
            lifecycleScope.launch {
                runCatching {
                    GoogleSignIn.getSignedInAccountFromIntent(data).await()
                }.getOrElse {
                    Snackbar.make(binding.root,getString(R.string.login_error),Snackbar.LENGTH_SHORT).show()
                    it.printStackTrace()
                    null
                }?.let {
                    authWithGoogle(it)
                }
            }
        }
    }

    private suspend fun authWithGoogle(account: GoogleSignInAccount) =
        withContext(Dispatchers.Main) {
            val credential = GoogleAuthProvider.getCredential(account.idToken, null)
            return@withContext runCatching {
                auth.signInWithCredential(credential).await()
            }.getOrElse {
                Snackbar.make(binding.root,getString(R.string.login_error),Snackbar.LENGTH_SHORT).show()
                it.printStackTrace()
                null
            }?.let {
                isNewSignIn = true
            }
        }

    override fun onAuthStateChanged(p0: FirebaseAuth) {
        val user = auth.currentUser
        if(user != null){
            val intent = if(isNewSignIn)
                Intent(this, SyncDataActivity::class.java)
            else
                Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun getSignInErrorMessage(t: Throwable): String {
        val msg = t.message ?: return getString(R.string.login_error)

        return when {
            msg.contains("no user") -> getString(R.string.user_not_found)
            msg.contains("network error") -> getString(R.string.you_are_offline)
            msg.contains("password is invalid") -> getString(R.string.wrong_password)
            else -> getString(R.string.login_error)
        }
    }

    companion object {
        private const val RC_GOOGLE_SIGN_IN = 1
    }

}