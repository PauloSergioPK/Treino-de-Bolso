package com.karasdecc.treinodebolso.ui.fragments.setup

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import com.karasdecc.treinodebolso.R
import com.karasdecc.treinodebolso.databinding.FragmentSignUpBinding
import com.karasdecc.treinodebolso.extensions.hideView
import com.karasdecc.treinodebolso.extensions.isValidEmail
import com.karasdecc.treinodebolso.extensions.showView
import com.karasdecc.treinodebolso.ui.BaseBindingFragment
import com.karasdecc.treinodebolso.helpers.SignInListeners
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class SignUpFragment : BaseBindingFragment<FragmentSignUpBinding>(FragmentSignUpBinding::inflate) {

    private var onSignUpListeners: SignInListeners? = null
    private lateinit var checkRunnable: Runnable
    private val handler = Handler(Looper.getMainLooper())

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is SignInListeners)
            onSignUpListeners = context
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        setUpRunnable()
    }

    override fun onResume() {
        super.onResume()
        handler.removeCallbacks(checkRunnable)
        handler.postDelayed(checkRunnable, CHECK_INTERVAL_MS)
    }

    override fun onPause() {
        super.onPause()
        handler.removeCallbacks(checkRunnable)
    }


    private fun setupViews() {
        binding.apply {
            buttonBack.setOnClickListener {
                Navigation.findNavController(binding.root).navigate(R.id.actionSignUpToIntro)
            }
            signUpWithGoogle.setOnClickListener {
                onSignUpListeners?.onGoogleClicked()
            }
            signUpButton.setOnClickListener {
                lifecycleScope.launch {
                    signUpButton.isEnabled = false
                    signUpProgressBG.showView()
                    signUpProgress.showView()
                    val loading = async {
                        onSignUpListeners?.signInWithEmail(
                            signUpNameInput.text.toString(),
                            signUpEmailInput.text.toString(),
                            signUpPasswordInput.text.toString()
                        )
                    }
                    onLoadingFinish(loading.await())
                }
            }
        }
    }

    private fun onLoadingFinish(p0: Unit?) {
        binding.apply{
            signUpButton.isEnabled = true
            signUpProgressBG.hideView()
            signUpProgress.hideView()
        }
    }

    private fun validateInputs() {
        binding.apply {
            var valid = true
            val name = signUpNameInput.text.toString()

            when {
                name.length in 1..3 -> {
                    valid = false
                    signUpNameField.error = getString(R.string.invalid_name)
                }
                name.isEmpty() -> {
                    valid = false
                }
                else -> {
                    signUpNameField.error = null
                }
            }
            val email = signUpEmailInput.text.toString()
            if (email.isNotEmpty()) {
                if (!email.isValidEmail()) {
                    valid = false
                    signUpEmailField.error = getString(R.string.invalid_email)
                } else {
                    signUpEmailField.error = null
                }
            } else {
                signUpEmailField.error = null
                valid = false
            }

            val pass = signUpPasswordInput.text.toString()
            when {
                pass.isEmpty() -> {
                    signUpPasswordField.error = null
                    valid = false
                }
                pass.length < 6 -> {
                    signUpPasswordField.error = getString(R.string.password_error_length)
                    valid = false
                }
                else -> {
                    signUpPasswordField.error = null
                }
            }
            signUpButton.isEnabled = valid
        }
    }

    private fun setUpRunnable() {
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