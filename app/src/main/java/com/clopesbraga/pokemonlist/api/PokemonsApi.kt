package com.clopesbraga.pokemonlist.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PokemonsApi {

    companion object {

        private lateinit var retrofit: Retrofit
        private var baseUrl = "https://pokeapi.co/api/v2/"

        private fun getNetWorkInstance(): Retrofit {

            val httpClient = OkHttpClient.Builder()

            if (!::retrofit.isInitialized) {

                retrofit = Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .client(httpClient.build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            return retrofit
        }

        fun <S> createService(servicesClass: Class<S>): S {
            return getNetWorkInstance().create(servicesClass)
        }
    }

}