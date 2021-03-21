package com.karasdecc.treinodebolso.ui.fragments.setup

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import com.karasdecc.treinodebolso.R
import com.karasdecc.treinodebolso.databinding.FragmentSignInBinding
import com.karasdecc.treinodebolso.extensions.hideView
import com.karasdecc.treinodebolso.extensions.isValidEmail
import com.karasdecc.treinodebolso.extensions.showView
import com.karasdecc.treinodebolso.ui.BaseBindingFragment
import com.karasdecc.treinodebolso.helpers.SignInListeners
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class SignInFragment : BaseBindingFragment<FragmentSignInBinding>(FragmentSignInBinding::inflate) {

    private var onSignInListeners: SignInListeners? = null
    private lateinit var checkRunnable: Runnable
    private val handler = Handler(Looper.getMainLooper())

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is SignInListeners)
            onSignInListeners = context
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        setupRunnable()
    }

    private fun setupViews() {
        binding.apply {
            buttonBack.setOnClickListener {
                Navigation.findNavController(binding.root).navigate(R.id.actionSignInToIntro)
            }
            signInWithGoogle.setOnClickListener {
                onSignInListeners?.onGoogleClicked()
            }
            signInButton.setOnClickListener {
                lifecycleScope.launch {
                    signInButton.isEnabled = false
                    signInProgressBG.showView()
                    signInProgress.showView()
                    val loading = async {
                        onSignInListeners?.signInWithEmail(
                            signInEmailInput.text.toString(),
                            signInPasswordInput.text.toString()
                        )
                    }
                    onLoadingFinish(loading.await())
                }
            }
        }
    }

    private fun onLoadingFinish(p0: Unit?) {
        binding.apply{
            signInButton.isEnabled = true
            signInProgressBG.hideView()
            signInProgress.hideView()
        }
    }

    private fun validateInputs() {
        binding.apply {
            var valid = true

            val email = signInEmailInput.text.toString()
            if (email.isNotEmpty()) {
                if (!email.isValidEmail()) {
                    signInEmailField.error = getString(R.string.invalid_email)
                    valid = false
                } else {
                    signInEmailField.error = null
                }
            } else {
                signInEmailField.error = null
                valid = false
            }

            val pass = signInPasswordInput.text.toString()
            when {
                pass.isEmpty() -> {
                    signInPasswordField.error = null
                    valid = false
                }
                pass.length < 6 -> {
                    signInPasswordField.error = getString(R.string.password_error_length)
                    valid = false
                }
                else -> {
                    signInPasswordField.error = null
                }
            }

            signInButton.isEnabled = valid
        }
    }

    private fun setupRunnable() {
        checkRunnable = object : Runnable {
            override fun run() {
                lifecycleScope.launch {
                    validateInputs()
                }
                handler.postDelayed(this, CHECK_INTERVAL_MS)
            }
        }
    }

    companion object {
        const val CHECK_INTERVAL_MS = 600L
    }

}