package com.karasdecc.treinodebolso.models

data class User(
    var name: String = "",
    var email: String = "",
    var aTraining: Training? = null,
    var bTraining: Training? = null,
    var cTraining: Training? = null,
    var dTraining: Training? = null,
    var eTraining: Training? = null,
    var fTraining: Training? = null,
    var gTraining: Training? = null,
    var crossfitTraining: Training? = null
) {
}