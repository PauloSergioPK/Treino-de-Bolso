package com.karasdecc.treinodebolso.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.karasdecc.treinodebolso.R
import com.karasdecc.treinodebolso.models.Exercise
import com.karasdecc.treinodebolso.vh.ExerciseViewHolder

class ExercisesAdapter : ListAdapter<Exercise,ExerciseViewHolder>(ExerciseDiffUtil()) {

    var callback: ExercisesCallback? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseViewHolder {
        return ExerciseViewHolder(
            LayoutInflater.from(parent.context).inflate(
            R.layout.list_item_exercise,parent,false)
        )
    }

    override fun onBindViewHolder(holder: ExerciseViewHolder, position: Int) {
        holder.bind(getItem(position))
        holder.card.setOnClickListener { callback?.onExerciseClicked(getItem(position)) }
    }

    interface ExercisesCallback{
        fun onExerciseClicked(exercise: Exercise)
    }

    class ExerciseDiffUtil : DiffUtil.ItemCallback<Exercise>(){

        override fun areItemsTheSame(oldItem: Exercise, newItem: Exercise): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Exercise, newItem: Exercise): Boolean {
            return oldItem == newItem
        }

    }
}