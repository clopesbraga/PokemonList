package com.clopesbraga.pokemonlist.model

import com.google.gson.annotations.SerializedName

data class PokemonListModel(

    @SerializedName("id")
    val id: Int,
    @SerializedName(value = "name")
    val name: String,
    @SerializedName(value = "sprites")
    val sprites: Sprites,

)
data class Sprites(
    @SerializedName(value = "back_default")
    val back_default: String,
    @SerializedName(value = "back_female")
    val back_female: Any,
    @SerializedName(value = "back_shiny")
    val back_shiny: String,
    @SerializedName(value = "back_shiny_female")
    val back_shiny_female: Any,
    @SerializedName(value = "front_default")
    val front_default: String,
    @SerializedName(value = "front_female")
    val front_female: Any,
    @SerializedName(value = "front_shiny")
    val front_shiny: String,
    @SerializedName(value = "front_shiny_female")
    val front_shiny_female: Any,

)