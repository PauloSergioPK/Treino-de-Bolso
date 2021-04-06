package com.karasdecc.treinodebolso.models

import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.Exclude

data class Training(
    @Exclude @set:Exclude var img: String = "",
    @Exclude @set:Exclude var title: String = "",
    @Transient var exercises: List<DocumentReference?> = listOf(),
    @Exclude @set:Exclude var exercisesList : List<Exercise> = listOf()
) {

}