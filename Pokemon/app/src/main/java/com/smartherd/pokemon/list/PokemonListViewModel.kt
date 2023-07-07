package com.smartherd.pokemon.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.smartherd.pokemon.data.PokemonRepository
import com.smartherd.pokemon.models.PokemonData
    const val SEARCH_PAGE_LIMIT = 100
class PokemonListViewModel : ViewModel() {

    private val _pokemonList = MutableLiveData<List<PokemonData>>()
    val pokemonList: LiveData<List<PokemonData>> get() = _pokemonList

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    private val _offset = MutableLiveData(0)
    val offset: LiveData<Int> get() = _offset
    fun setOffset(newOffset: Int) { _offset.value = newOffset }

    fun loadPokemon() {
        PokemonRepository.loadPokemonPage(_offset.value!!, object : PokemonCallback {
            override fun onSuccess(pokemons: List<PokemonData>) {
                _pokemonList.value = pokemons
                if (pokemons.size > 20 && _offset.value == 0) {
                    _offset.value = pokemons.size
                } else {
                    _offset.value = _offset.value!! + 20
                }
            }

            override fun onError(error: String) {
                _error.value = error
            }
        })
    }

    fun searchPokemon(searchName: String) {
        PokemonRepository.clearSearchedPokemons()
        PokemonRepository.searchPokemonName(
            _offset.value!!,
            SEARCH_PAGE_LIMIT,
            searchName,
            object : PokemonCallback {
                override fun onSuccess(pokemons: List<PokemonData>) {
                    _pokemonList.value = pokemons
                    _offset.value = _offset.value!! + SEARCH_PAGE_LIMIT
                }

                override fun onError(error: String) {
                    _error.value = error
                }
            })
    }

}
