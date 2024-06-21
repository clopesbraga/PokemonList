package com.clopesbraga.pokemonlist.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.keyframes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navOptions
import coil.compose.rememberAsyncImagePainter
import com.clopesbraga.pokemonlist.R
import com.clopesbraga.pokemonlist.model.PokemonListModel
import com.clopesbraga.pokemonlist.model.SkillsModel
import com.clopesbraga.pokemonlist.ui.screens.PokemonListScreen
import com.clopesbraga.pokemonlist.ui.screens.PokemonDetailScreen
import com.clopesbraga.pokemonlist.ui.screens.PokemonLoginScreen
import kotlinx.coroutines.delay


@Composable
fun SearchBar(modifier: Modifier = Modifier, hint: String, onSearch: (String) -> Unit = {}) {
    var text by remember {
        mutableStateOf("")
    }
    var isHintDisplayed by remember {
        mutableStateOf(false)
    }

    Box(modifier = modifier) {
        BasicTextField(
            value = text,
            onValueChange = {
                text = it
                onSearch(it)
            },
            maxLines = 1,
            singleLine = true,
            textStyle = TextStyle(color = Color.Black),
            modifier = Modifier
                .fillMaxWidth()
                .shadow(5.dp, CircleShape)
                .background(Color.White, CircleShape)
                .padding(horizontal = 20.dp, vertical = 12.dp)
                .onFocusChanged {
                    isHintDisplayed = it.isFocused.not()
                }
        )
        if (isHintDisplayed) {
            Text(
                text = hint,
                color = Color.LightGray,
                modifier = Modifier
                    .padding(horizontal = 20.dp, vertical = 12.dp)
            )
        }
    }
}

@Composable
fun Navigation() {

    val navController = rememberNavController()
    NavHost(
        navController = navController, startDestination = "login")
    {
        composable("list") {
            PokemonListScreen(navController)
        }

        composable("login") {
            PokemonLoginScreen(navController)
        }
        composable(
            "details/{image}/hp/{hp}/attack/{attack}/defense/{defense}",
            arguments = listOf(
                navArgument("image") { type = NavType.StringType },
                navArgument("hp") { type = NavType.IntType },
                navArgument("attack") { type = NavType.IntType },
                navArgument("defense") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val image = backStackEntry.arguments?.getString("image")
            val hp = backStackEntry.arguments?.getInt("hp")
            val attack = backStackEntry.arguments?.getInt("attack")
            val defense = backStackEntry.arguments?.getInt("defense")

            if (image != null) {
                if (attack != null) {
                    if (hp != null) {
                        if (defense != null) {
                            PokemonDetailScreen(navController,image, hp, attack, defense)
                        }
                    }
                }
            }

        }
    }

}

@Composable
fun BoxItems(item: PokemonListModel, navController: NavHostController
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Card(
            modifier = Modifier
                .weight(1f)
                .clickable {
                    val image = extractFileNameFromUrl(item.sprites.front_default)
                    val hp = item.stats.find { it.stat.name == "hp" }?.base_stat
                    val attack = item.stats.find { it.stat.name == "attack" }?.base_stat
                    val defense = item.stats.find { it.stat.name == "defense" }?.base_stat

                    navController.navigate("details/$image/hp/$hp/attack/$attack/defense/$defense", navOptions = navOptions {
                        popUpTo(navController.graph.startDestinationId)
                    })
                },
        ) {
            Row {

                ImageFromUrl(imageUrl = item.sprites.front_default)
                Text(
                    text = item.name,
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.align(Alignment.CenterVertically)
                )

            }

        }
    }
}

@Composable
fun ImageFromUrl(imageUrl: String) {
    val painter = rememberAsyncImagePainter(imageUrl)
    Image(
        painter = painter,
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .size(100.dp)
            .clip(CircleShape)
    )
}

@Composable
fun SkillsBar(hp:Int, attack:Int, defense:Int){

    val pokemonSkills = listOf(
        SkillsModel(name ="HP",level = hp.toFloat()),
        SkillsModel(name ="ATTACK",level = attack.toFloat()),
        SkillsModel(name ="DEFENSE",level = defense.toFloat())
    )

    AnimatedVisibility(visible = true) {

        Column{
            for(skill in pokemonSkills){
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(16.dp)

                ){

                    var level by remember {
                        mutableStateOf(0f)
                    }
                    LaunchedEffect(null) {
                        delay(1000)
                        level = skill.level
                    }
                    val animatedCircularValue by animateFloatAsState(
                        targetValue = level,
                        label = stringResource(id = R.string.circular_shape),
                        animationSpec = keyframes {
                            this.durationMillis = 100000
                        }
                    )
                    CircularProgressIndicator(
                        progress = { animatedCircularValue },
                    )
                    Text(text = skill.name)

                }

            }
        }

    }

}


private fun extractFileNameFromUrl(url: String): String {
    return url.substringAfterLast("/")
}



