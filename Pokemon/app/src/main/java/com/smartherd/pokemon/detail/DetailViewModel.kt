package com.smartherd.pokemon.detail


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.smartherd.pokemon.data.PokemonRepository
import com.smartherd.pokemon.models.PokemonData

class DetailViewModel : ViewModel() {

    private val _selectedPokemon = MutableLiveData<PokemonData>()
    private val _error = MutableLiveData<String>()

    val selectedPokemon: LiveData<PokemonData> get() = _selectedPokemon
    val error: LiveData<String> get() = _error


    fun getPokemonDetails(id: Int) {
        PokemonRepository.loadPokemonDetails(id, object : PokemonDetailsCallback {
            override fun onSuccess(pokemon: PokemonData) {
                _selectedPokemon.value = pokemon
            }

            override fun onError(error: String) {
                Log.e("Failed Api", "Failed Api with error code: $error")
            }
        })
    }
}
