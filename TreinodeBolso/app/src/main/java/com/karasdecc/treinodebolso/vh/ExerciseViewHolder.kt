package com.karasdecc.treinodebolso.vh

import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.karasdecc.treinodebolso.R
import com.karasdecc.treinodebolso.models.Exercise

class ExerciseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val card = itemView.findViewById<MaterialCardView>(R.id.exerciseCard)
    val title = itemView.findViewById<AppCompatTextView>(R.id.exerciseTitle)
    val reps = itemView.findViewById<AppCompatTextView>(R.id.exerciseReps)

    fun bind(item: Exercise){
        title.text = item.title
        reps.text = "${item.repsCount}x${item.reps}"
    }
}