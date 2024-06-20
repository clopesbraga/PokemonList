package com.clopesbraga.pokemonlist.model

import com.google.gson.annotations.SerializedName

data class StatXModel(
    @SerializedName("name")val name: String,
    val url: String
)