package com.karasdecc.treinodebolso.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.karasdecc.treinodebolso.models.Training
import com.karasdecc.treinodebolso.vh.TrainingViewHolder


class TrainingAdapter(private val dataSet : List<Training>) : RecyclerView.Adapter<TrainingViewHolder>() {

    var callBacks: TrainingCallBacks? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrainingViewHolder {

    }

    override fun onBindViewHolder(holder: TrainingViewHolder, position: Int) {
        holder.bind(dataSet[position])
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    interface TrainingCallBacks{
        fun onTrainingClicked(training: Training)
    }
}