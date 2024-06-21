package com.clopesbraga.pokemonlist.model

import com.google.gson.annotations.SerializedName

data class StatModel(
    @SerializedName(value = "base_stat") val base_stat: Int,
    @SerializedName(value = "effort") val effort: Int,
    @SerializedName(value = "stat")val stat: StatXModel
)