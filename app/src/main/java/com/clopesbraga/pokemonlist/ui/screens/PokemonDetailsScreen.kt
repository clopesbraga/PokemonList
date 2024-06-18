package com.clopesbraga.pokemonlist.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.clopesbraga.pokemonlist.ui.components.ImageFromUrl


@Composable
fun PokemonDetailScreen(image: String) {

    val baseurl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/"
    Column {
        Box {

            ImageFromUrl(baseurl + image)

            Text(
                text = "Tela Detalhes pokemons",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                textAlign = TextAlign.Justify,
                fontSize = 18.sp,
                fontWeight = FontWeight.Light
            )

        }


    }

}
