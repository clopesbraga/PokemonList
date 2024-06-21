package com.clopesbraga.pokemonlist.model

import com.google.gson.annotations.SerializedName

data class PokemonListModel(

    @SerializedName("id") val id: Int,
    @SerializedName(value = "name") val name: String,
    @SerializedName(value = "sprites") val sprites: SpritesModel,
    @SerializedName(value = "stats") val stats: List<StatModel>,

    )