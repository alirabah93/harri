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

    fun setOffset(newOffset: Int) {
        _offset.value = newOffset
    }

    fun getOffset(): Int {
        return _offset.value ?: 0
    }

    private val _viewState = MutableLiveData("list")
    val viewState: LiveData<String> = _viewState

    fun loadPokemon() {
        PokemonRepository.loadPokemonPage(getOffset(), object : PokemonCallback {
            override fun onSuccess(pokemons: List<PokemonData>) {
                _pokemonList.value = pokemons
                if (pokemons.size > 20 && _offset.value == 0) {
                    _offset.value = pokemons.size
                } else {
                    _offset.value = getOffset() + 20
                }

                _viewState.value = if (pokemons.isEmpty()) {
                    "empty"
                } else {
                    "list"
                }
            }

            override fun onError(error: String) {
                _error.value = error
                _viewState.value = "error"
            }
        })
    }

    fun searchPokemon(searchName: String) {
        PokemonRepository.clearSearchedPokemons()
        PokemonRepository.searchPokemonName(
            getOffset(),
            SEARCH_PAGE_LIMIT,
            searchName,
            object : PokemonCallback {
                override fun onSuccess(pokemons: List<PokemonData>) {
                    _pokemonList.value = pokemons
                    _offset.value = getOffset() + SEARCH_PAGE_LIMIT

                    _viewState.value = if (pokemons.isEmpty()) {
                        "empty"
                    } else {
                        "list"
                    }
                }

                override fun onError(error: String) {
                    _error.value = error
                    _viewState.value = "error"
                }
            })
    }

}
