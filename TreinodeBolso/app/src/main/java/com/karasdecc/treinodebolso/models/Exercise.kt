package com.karasdecc.treinodebolso.models

data class Exercise(
    var title: String = "",
    var reps: Int = 0,
    var repsCount: Int = 0,
    var images: List<String> = listOf(),
    var video: String = "",
    var specification: String = "",
    var description: String = ""
) {
}