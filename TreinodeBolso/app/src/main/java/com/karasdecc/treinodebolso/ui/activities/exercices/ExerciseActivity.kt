package com.karasdecc.treinodebolso.ui.activities.exercices

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import com.karasdecc.treinodebolso.adapter.ExercisesAdapter
import com.karasdecc.treinodebolso.databinding.ActivityExerciceBinding
import com.karasdecc.treinodebolso.models.Exercise
import com.karasdecc.treinodebolso.models.Training
import com.karasdecc.treinodebolso.ui.BaseBindingActivity

class ExerciseActivity
    : BaseBindingActivity<ActivityExerciceBinding>(ActivityExerciceBinding::inflate), ExercisesAdapter.ExercisesCallback{

    private var training : Training? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        training = intent.getSerializableExtra(ARG_TRAINING) as Training?
        setupToolbar()
        setupViews()
    }

    private fun setupToolbar(){
        setSupportActionBar(binding.exercisesToolbar)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == android.R.id.home)
            finish()
        return true
    }

    private fun setupViews(){
        val exerciseAdapter = ExercisesAdapter()
        exerciseAdapter.submitList(training?.exercisesList)
        exerciseAdapter.callback = this
        binding.exerciseRecycler.apply{
            adapter = exerciseAdapter
            layoutManager = LinearLayoutManager(this@ExerciseActivity,LinearLayoutManager.VERTICAL,false)
        }
    }

    override fun onExerciseClicked(exercise: Exercise) {
        val intent = Intent(this,ExerciseDetailsActivity::class.java)
        intent.putExtra(ExerciseDetailsActivity.ARG_EXERCISE,exercise)
        startActivity(intent)
    }

    companion object{
        const val ARG_TRAINING = "arg training"
    }
}