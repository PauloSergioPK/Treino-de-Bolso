package com.karasdecc.treinodebolso.models

import com.google.firebase.firestore.Exclude
import java.io.Serializable

data class Exercise(
    @Exclude @set:Exclude @get:Exclude var id: String = "",
    @Exclude @set:Exclude var title: String = "",
    @Exclude @set:Exclude var reps: Int = 0,
    @Exclude @set:Exclude var repsCount: Int = 0,
    @Exclude @set:Exclude var images: List<String> = listOf(),
    @Exclude @set:Exclude var video: String = "",
    @Exclude @set:Exclude var specification: String = "",
    @Exclude @set:Exclude var description: String = "",
) : Serializable{
}