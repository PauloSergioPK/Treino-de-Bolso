package com.karasdecc.treinodebolso.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.karasdecc.treinodebolso.R
import com.karasdecc.treinodebolso.extensions.load

class ExerciseDetailsImagesAdapter(private val dataSet: List<String>) : RecyclerView.Adapter<ExerciseDetailsImagesAdapter.ExerciseDetailsImageVH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseDetailsImageVH {
        return ExerciseDetailsImageVH(
            LayoutInflater.from(parent.context).inflate(
                R.layout.list_item_exercise_image,parent,false
            )
        )
    }

    override fun onBindViewHolder(holder: ExerciseDetailsImageVH, position: Int) {
        holder.bind(dataSet[position])
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    inner class ExerciseDetailsImageVH(itemView: View) : RecyclerView.ViewHolder(itemView){

        val img = itemView.findViewById<AppCompatImageView>(R.id.exerciseImage)

        fun bind(imgUrl: String){
            img.load(imgUrl)
        }
    }
}