package com.clopesbraga.pokemonlist.api

import com.clopesbraga.pokemonlist.model.PaginationModel
import com.clopesbraga.pokemonlist.model.PokemonListModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface PokemonsRequestEndpoints {

    companion object{

        private const val PAGING = "pokemon"
        private const val POKEMONSINFO = "pokemon/{name}"

    }

    @GET(PAGING)
    suspend fun getpage(
        @Query(value = "offset") offset:Int,
        @Query(value = "limit") limit:Int
    ) : Response<PaginationModel>

    @GET(POKEMONSINFO)
    suspend fun getPokemonDetails(
        @Path("name") name: String
    ) : Response<PokemonListModel>
}