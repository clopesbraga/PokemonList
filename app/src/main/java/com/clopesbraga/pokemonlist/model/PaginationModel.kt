package com.clopesbraga.pokemonlist.model

import com.google.gson.annotations.SerializedName

data class PaginationModel(
    val count: Int,
    @SerializedName("next")
    val next: String,
    @SerializedName("previous")
    val previous: String,
    @SerializedName("results")
    val results: List<Result>
)

data class Result(
    @SerializedName("name")
    val name: String,
    @SerializedName("url")
    val url: String
)