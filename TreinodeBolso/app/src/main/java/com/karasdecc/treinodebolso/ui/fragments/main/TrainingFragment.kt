package com.karasdecc.treinodebolso.ui.fragments.main

import android.os.Bundle
import android.view.View
import com.karasdecc.treinodebolso.databinding.FragmentTrainingBinding
import com.karasdecc.treinodebolso.ui.BaseBindingFragment

class TrainingFragment : BaseBindingFragment<FragmentTrainingBinding>(FragmentTrainingBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecycler()
    }

    private fun setupRecycler(){

    }
}