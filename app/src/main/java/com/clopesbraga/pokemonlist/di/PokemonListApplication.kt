package com.clopesbraga.pokemonlist.di

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class PokemonListApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@PokemonListApplication)
            modules(appModule)
        }

    }
}
