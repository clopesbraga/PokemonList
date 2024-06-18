import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.clopesbraga.pokemonlist.api.PokemonsApi
import com.clopesbraga.pokemonlist.api.PokemonsRequestEndpoints
import com.clopesbraga.pokemonlist.model.PokemonListModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PokemonsListViewModel : ViewModel() {

    companion object {
        private const val LIMIT = 150
    }

    private val _pokemons = MutableStateFlow<List<PokemonListModel>>(emptyList())
    val pokemonsListState = _pokemons.asStateFlow()

    fun pagination(page: Int) {
        val offset = page * LIMIT
        viewModelScope.launch {
            try {
                val pokemonAPI = PokemonsApi.createService(PokemonsRequestEndpoints::class.java)
                val response = pokemonAPI.getpage(offset, LIMIT)

                if (response.isSuccessful) {
                    val pokemonNames = response.body()?.results?.map { it.name } ?: emptyList()
                    val pokemonDetails = pokemonNames.map { name ->
                        async(Dispatchers.IO) {
                            getPokemonDetails(name)
                        }
                    }.awaitAll()
                    _pokemons.value = pokemonDetails.filterNotNull()
                }
            } catch (e: Exception) {
                Log.d("ERROR_REQUEST",e.message ?: "Unkwon Error")
            }
        }
    }

    private suspend fun getPokemonDetails(name: String): PokemonListModel? {
        return try {
            val pokemonAPI = PokemonsApi.createService(PokemonsRequestEndpoints::class.java)
            val response = pokemonAPI.getPokemonDetails(name)
            if (response.isSuccessful) response.body() else null
        } catch (e: Exception) {
            Log.d("unkwon_request_erro", e.message ?: "Unkwon Error")
            null
        }
    }
}