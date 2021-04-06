package com.karasdecc.treinodebolso.ui.activities.exercices

import android.os.Bundle
import android.view.MenuItem
import androidx.annotation.NonNull
import androidx.recyclerview.widget.LinearLayoutManager
import com.karasdecc.treinodebolso.adapter.ExerciseDetailsImagesAdapter
import com.karasdecc.treinodebolso.databinding.ActivityExerciseDetailsBinding
import com.karasdecc.treinodebolso.models.Exercise
import com.karasdecc.treinodebolso.ui.BaseBindingActivity
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import java.util.*

class ExerciseDetailsActivity : BaseBindingActivity<ActivityExerciseDetailsBinding>(ActivityExerciseDetailsBinding::inflate) {

    private var exercise: Exercise? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        exercise = intent.getSerializableExtra(ARG_EXERCISE) as Exercise?
        setupToolbar()
        setupViews()
    }

    private fun setupToolbar(){
        setSupportActionBar(binding.exerciseToolbar)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == android.R.id.home)
            finish()
        return true
    }

    private fun setupViews(){
        if(exercise != null){
            binding.apply{
                exerciseTitle.text = exercise!!.title
                exerciseDescription.text = exercise!!.description
                exerciseExpecification.text = exercise!!.specification
                setupRecycler()
                setupMedia()
            }
        }
    }

    private fun setupRecycler(){
        if(exercise != null){
            val imgs = exercise?.images
            if(imgs != null) {
                val exerciseDetailsImagesAdapter = ExerciseDetailsImagesAdapter(imgs)
                binding.apply{
                    exerciseRecycler.apply{
                        adapter = exerciseDetailsImagesAdapter
                        layoutManager = LinearLayoutManager(
                            this@ExerciseDetailsActivity,
                            LinearLayoutManager.HORIZONTAL,
                            false
                        )
                    }
                }
            }
        }
    }

    private fun setupMedia(){
        binding.exerciseVideo.apply {
            val stack: Stack<Char> = Stack()
            val lastIndex = exercise?.video?.lastIndex
            if (lastIndex != null) {
                for (i in lastIndex downTo 0) {
                    if (exercise?.video?.get(i)?.equals('=') == true)
                        break
                    stack.push(exercise?.video?.get(i))
                }
                var videoId: String = ""
                while (!stack.isEmpty()) {
                    videoId += stack[stack.lastIndex]
                    stack.pop()
                }
                lifecycle.addObserver(this)

                initializeWithWebUi(object : AbstractYouTubePlayerListener() {
                    override fun onReady(@NonNull youTubePlayer: YouTubePlayer) {
                        youTubePlayer.cueVideo(videoId, 0f)
                    }
                }, true)
            }
        }
    }

    companion object {
        const val ARG_EXERCISE = "arg exercise"
    }
}