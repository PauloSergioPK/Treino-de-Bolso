package com.karasdecc.treinodebolso.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.karasdecc.treinodebolso.R
import com.karasdecc.treinodebolso.models.Training
import com.karasdecc.treinodebolso.vh.TrainingViewHolder


class TrainingAdapter : ListAdapter<Training,TrainingViewHolder>(TrainingDiffUtil()) {

    var callbacks: TrainingCallBacks? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrainingViewHolder {
        return TrainingViewHolder(LayoutInflater.from(parent.context).inflate(
            R.layout.list_item_training,parent,false)
        )
    }

    override fun onBindViewHolder(holder: TrainingViewHolder, position: Int) {
        holder.bind(getItem(position))
        holder.card.setOnClickListener { callbacks?.onTrainingClicked(getItem(position)) }
    }

    class TrainingDiffUtil : DiffUtil.ItemCallback<Training>(){

        override fun areItemsTheSame(oldItem: Training, newItem: Training): Boolean {
            return oldItem.title == newItem.title
        }

        override fun areContentsTheSame(oldItem: Training, newItem: Training): Boolean {
            return oldItem == newItem
        }

    }

    interface TrainingCallBacks{
        fun onTrainingClicked(training: Training)
    }
}