package com.clopesbraga.pokemonlist.ui.screens

import PokemonsListViewModel
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.clopesbraga.pokemonlist.R
import com.clopesbraga.pokemonlist.model.PokemonListModel
import com.clopesbraga.pokemonlist.ui.components.BoxItems
import com.clopesbraga.pokemonlist.ui.components.SearchBar
import org.koin.androidx.compose.koinViewModel


private const val ITEMS = 10
private const val RED =  255
private const val GREEN = 191
private const val BLUE = 0

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PokemonListScreen(navController: NavHostController) {

    val shouldShowBottomBar = remember { mutableStateOf(true) }
    var searchText by remember { mutableStateOf("") }

    val viewModel= koinViewModel<PokemonsListViewModel>()
    val pokemonList: List<PokemonListModel> by viewModel.pokemonsListState.collectAsState()

    val itemsPerPage = ITEMS
    var currentPage by remember { mutableStateOf(0) }
    viewModel.pagination(currentPage)

    val items: List<PokemonListModel> = pokemonList.map { pokemon ->
        PokemonListModel(pokemon.id, pokemon.name, pokemon.sprites)
    }

    val pokemonlistFiltered = if (searchText.isBlank()) {
        items.subList(
            currentPage * itemsPerPage,
            minOf((currentPage + 1) * itemsPerPage, items.size)
        )
    } else {
        pokemonList.filter {
            it.name.contains(searchText, ignoreCase = true)
        }
    }
    val image = painterResource(id = R.drawable.ic_page_number)

    Scaffold(
        bottomBar = {
            if (shouldShowBottomBar.value) {
                BottomAppBar(
                    containerColor = Color(RED, GREEN, BLUE),
                    modifier = Modifier.clip(RoundedCornerShape(50.dp))
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceAround,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(
                            onClick = { currentPage = maxOf(0, currentPage - 1) },
                            enabled = currentPage > 0
                        ) {
                            Column(horizontalAlignment = Alignment.Start){
                            Icon(image, stringResource(R.string.previus_page))
                            Icon(Icons.Filled.ArrowBack,"")
                        }
                        }

                        Box(
                            contentAlignment = Alignment.BottomCenter,
                            modifier = Modifier
                                .paint(painter = painterResource(id = R.mipmap.ic_number_page_foreground))

                        ) {
                            Text("Page ${currentPage + 1}")
                        }
                        IconButton(
                            onClick = {
                                if ((currentPage + 1) * itemsPerPage < items.size) {
                                    currentPage++
                                }
                            },
                            enabled = (currentPage + 1) * itemsPerPage < items.size
                        ) {
                            Column(horizontalAlignment = Alignment.End){
                                Icon(image, stringResource(R.string.next_page))
                                Icon(Icons.Filled.ArrowForward, "")
                            }
                        }
                    }
                }
            }
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .background(Color.White)
        ) {

            stickyHeader {

                Box {
                    Column(modifier = Modifier.background(Color.Red)) {

                        Text(
                            text = "Pokemons",
                            style = MaterialTheme.typography.titleLarge,
                            color = Color.White,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 32.dp, top = 40.dp, bottom = 32.dp),
                        )
                        SearchBar(hint = stringResource(R.string.hint_search),) { pokemonName ->
                            searchText = pokemonName
                        }
                    }
                }
            }
            items(pokemonlistFiltered) { item ->
                BoxItems(item, navController)
            }
        }
    }
}


