package com.karasdecc.treinodebolso.vh

import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.karasdecc.treinodebolso.R
import com.karasdecc.treinodebolso.extensions.load
import com.karasdecc.treinodebolso.models.Training

class TrainingViewHolder(private val itemView: View) : RecyclerView.ViewHolder(itemView) {

    val card = itemView.findViewById<MaterialCardView>(R.id.trainingCard)
    val img = itemView.findViewById<AppCompatImageView>(R.id.trainingCardImage)
    val text = itemView.findViewById<AppCompatTextView>(R.id.trainingCardText)

    fun bind(item : Training) {
        text.text = item.title
        img.load(item.img)
    }
}