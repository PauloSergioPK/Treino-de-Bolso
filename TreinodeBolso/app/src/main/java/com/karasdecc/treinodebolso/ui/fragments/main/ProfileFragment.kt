package com.karasdecc.treinodebolso.ui.fragments.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.karasdecc.treinodebolso.R
import com.karasdecc.treinodebolso.databinding.FragmentProfileBinding
import com.karasdecc.treinodebolso.extensions.showView
import com.karasdecc.treinodebolso.ui.BaseBindingFragment
import com.karasdecc.treinodebolso.ui.activities.setup.IntroActivity

class ProfileFragment : BaseBindingFragment<FragmentProfileBinding>(FragmentProfileBinding::inflate) {

    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var googleSignInOptions: GoogleSignInOptions

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureGoogleSignIn()
        setupViews()
    }

    private fun setupViews(){
        val user = auth.currentUser
        if(user != null) {
            binding.apply {
                profileSignOut.setOnClickListener {
                    signOut()
                }
                profileEmail.text = user.email
                profileName.text = user.displayName
                activity?.let {
                    profileImageContainer.showView()
                    Glide.with(it)
                        .load(user.photoUrl)
                        .error(ContextCompat.getDrawable(requireContext(),R.drawable.imagery_intro))
                        .apply(RequestOptions().centerCrop())
                        .into(binding.profileImage)
                }
            }
        }
    }

    private fun configureGoogleSignIn() {
        googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(requireActivity(), googleSignInOptions)
    }

    private fun signOut(){
        googleSignInClient.signOut()
        auth.signOut()
        val intent = Intent(requireContext(), IntroActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        activity?.finish()
    }
}