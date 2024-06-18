package com.clopesbraga.pokemonlist.ui.views

import PokemonsListViewModel
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.material3.Surface
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController

import com.clopesbraga.pokemonlist.ui.screens.PokemonListScreen
import com.clopesbraga.pokemonlist.ui.theme.PokemonListTheme
import com.clopesbraga.pokemonlist.ui.components.NavHostPages
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        installSplashScreen()
        setContent {
            val viewModel= koinViewModel<PokemonsListViewModel>()
            val navController = rememberNavController()
            NavHostPages(viewModel,navController)
            PokemonListTheme {
                Surface{
                    PokemonListScreen(viewModel = viewModel,navController)
                }
            }
        }
    }
}
