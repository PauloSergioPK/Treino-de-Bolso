package com.karasdecc.treinodebolso.ui.setup

import android.os.Bundle
import android.view.View
import androidx.navigation.Navigation
import com.karasdecc.treinodebolso.R
import com.karasdecc.treinodebolso.databinding.FragmentSignInBinding
import com.karasdecc.treinodebolso.ui.BaseBindingFragment

class SignInFragment : BaseBindingFragment<FragmentSignInBinding>(FragmentSignInBinding::inflate){

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
    }

    private fun setupViews(){
        binding.buttonBack.setOnClickListener {
//            activity?.onBackPressed()
            Navigation.findNavController(binding.root).navigate(R.id.actionSignInToIntro)
        }
    }
}