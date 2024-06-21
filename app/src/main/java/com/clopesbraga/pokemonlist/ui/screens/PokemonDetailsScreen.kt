package com.clopesbraga.pokemonlist.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.clopesbraga.pokemonlist.R
import com.clopesbraga.pokemonlist.ui.components.SkillsBar

val baseurl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PokemonDetailScreen(
    navController: NavHostController,
    image:String,
    hp:Int,
    attack:Int,
    defense:Int
) {

    Scaffold(
        topBar= {
            TopAppBar(
                title = { Text("Pokemon Details") },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.navigate("list")
                    }) {
                        Icon(Icons.Filled.ArrowBack, "Back")
                    }
                }
            )}
    ) { paddingValues ->

        Column(modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally)
        {

            Box (
                Modifier
                    .align(Alignment.CenterHorizontally)
                    .fillMaxWidth(),
                contentAlignment = Alignment.BottomCenter
            ){

                val painter = rememberAsyncImagePainter(baseurl + image)

                Image(
                    painter = painter,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(300.dp)
                        .clip(CircleShape)
                )

            }

            Row{
                Text(
                    text= stringResource(R.string.description_text),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    textAlign = TextAlign.Justify,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Light)


            }
            SkillsBar(hp,attack,defense)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PokemonDetailScreenPreview() {
    PokemonDetailScreen(
        navController = NavHostController(LocalContext.current),
        image = "1",
        hp = 10000f.toInt(),
        attack = 1,
        defense = 1
    )
}