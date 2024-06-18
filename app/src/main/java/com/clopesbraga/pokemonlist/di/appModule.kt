package com.clopesbraga.pokemonlist.di

import PokemonsListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {

    viewModelOf(::PokemonsListViewModel)
}