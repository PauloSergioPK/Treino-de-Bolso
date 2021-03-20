package com.karasdecc.treinodebolso.ui.setup

import android.os.Bundle
import android.view.View
import androidx.navigation.Navigation
import com.karasdecc.treinodebolso.R
import com.karasdecc.treinodebolso.databinding.FragmentIntroBinding
import com.karasdecc.treinodebolso.ui.BaseBindingFragment

class IntroFragment : BaseBindingFragment<FragmentIntroBinding>(FragmentIntroBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
    }

    private fun setupViews(){
        binding.buttonSignIn.setOnClickListener {
            val currentNavigation = Navigation.findNavController(binding.root).currentDestination
            if(currentNavigation == null)
                Navigation.findNavController(binding.root).navigate(R.id.introFragment)
            Navigation.findNavController(binding.root).navigate(
                R.id.actionSignIn
            )
        }
        binding.buttonSignUp.setOnClickListener {
            val currentNavigation = Navigation.findNavController(binding.root).currentDestination
            if(currentNavigation == null)
                Navigation.findNavController(binding.root).navigate(R.id.introFragment)
            Navigation.findNavController(binding.root).navigate(R.id.actionSignUp
            )
        }
    }
}