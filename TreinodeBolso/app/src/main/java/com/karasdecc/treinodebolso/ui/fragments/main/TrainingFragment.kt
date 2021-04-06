package com.karasdecc.treinodebolso.ui.fragments.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.karasdecc.treinodebolso.adapter.TrainingAdapter
import com.karasdecc.treinodebolso.databinding.FragmentTrainingBinding
import com.karasdecc.treinodebolso.models.Training
import com.karasdecc.treinodebolso.ui.BaseBindingFragment
import com.karasdecc.treinodebolso.ui.activities.exercices.ExerciseActivity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch

class TrainingFragment
    : BaseBindingFragment<FragmentTrainingBinding>(FragmentTrainingBinding::inflate),
    TrainingAdapter.TrainingCallBacks{

    private val trainingAdapter by lazy { TrainingAdapter() }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecycler()
    }

    @ExperimentalCoroutinesApi
    private fun setupRecycler(){
        lifecycleScope.launch {
            val user = auth.currentUser
            if(user != null) {
                val trainingList = userDatabaseManager.getUserTrainings(user)
                trainingAdapter.submitList(trainingList)
            }
            trainingAdapter.callbacks = this@TrainingFragment
            binding.trainingsRecycler.apply {
                adapter = trainingAdapter
                layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            }
        }
    }

    override fun onTrainingClicked(training: Training) {
        val intent = Intent(requireContext(),ExerciseActivity::class.java)
        intent.putExtra(ExerciseActivity.ARG_TRAINING,training)
        startActivity(intent)
    }
}