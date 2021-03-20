package com.karasdecc.treinodebolso.ui.setup

import android.os.Bundle
import android.view.View
import androidx.navigation.Navigation
import com.karasdecc.treinodebolso.R
import com.karasdecc.treinodebolso.databinding.FragmentSignUpBinding
import com.karasdecc.treinodebolso.ui.BaseBindingFragment

class SignUpFragment : BaseBindingFragment<FragmentSignUpBinding>(FragmentSignUpBinding::inflate){

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
    }

    private fun setupViews(){
        binding.buttonBack.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(R.id.actionSignUpToIntro)
        }
    }
}