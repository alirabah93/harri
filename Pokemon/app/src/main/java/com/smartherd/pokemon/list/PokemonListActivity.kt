package com.smartherd.pokemon.list

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import androidx.recyclerview.widget.GridLayoutManager
import com.smartherd.pokemon.R
import com.smartherd.pokemon.databinding.ActivityPokemonListBinding
import com.smartherd.pokemon.detail.PokemonDetailActivity
import com.smartherd.pokemon.models.PokemonData
import com.smartherd.pokemon.services.PokemonService
import com.smartherd.pokemon.services.ServiceBuilder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch

class PokemonListActivity : AppCompatActivity(), PokemonAdapter.OnPositionChangeListener {

    private lateinit var binding: ActivityPokemonListBinding
    private lateinit var pokemonAdapter: PokemonAdapter
    private lateinit var pokemonService: PokemonService
    private val pokemonRepository: PokemonRepository = PokemonRepository
    private var offset = 0
    private lateinit var progressBar: ProgressBar
    private var searchName = ""
    private val handler = Handler()


    private val coroutineScope = CoroutineScope(Dispatchers.Main + Job())
    private val searchQueryFlow = MutableStateFlow("")

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityPokemonListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupRecyclerView()

        progressBar = binding.progressBar
        pokemonService = ServiceBuilder.buildService(PokemonService::class.java)

        loadPokemon()
        setupSearchEditText()
    }

    override fun onReachedBottomList() {
        if (searchName.isEmpty()){
            showProgressBar()
            loadPokemon()
        }
    }

    private fun setupRecyclerView() {
        val spanCount = resources.getInteger(R.integer.span_count)
        binding.pokemonRecyclerView.layoutManager = GridLayoutManager(this, spanCount)
        pokemonAdapter = PokemonAdapter(emptyList())
        binding.pokemonRecyclerView.adapter = pokemonAdapter
        pokemonAdapter.setOnPositionChangeListener(this)
    }


    private fun setupSearchEditText() {
//        var searchPokemon: Job? = null
        binding.searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Not needed for this implementation
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Not needed for this implementation
            }
            override fun afterTextChanged(s: Editable?) {
                searchName = s.toString()
                handler.removeCallbacksAndMessages(null)
                handler.postDelayed({
                    showProgressBar()
                    searchPokemon()
                }, 300)

//                searchPokemon?.cancel()
//                searchPokemon = coroutineScope.launch {
//                    searchQueryFlow.emit(s.toString())
//                }
//
//
//                coroutineScope.launch {
//                    searchQueryFlow.debounce(300) // Adjust the debounce duration as needed (in milliseconds)
//                        .collectLatest { query ->
//                            searchName = query
//                            showProgressBar()
//                            searchPokemon()
//                        }
//                }
            }
        })
    }

    private fun loadPokemon(){
        pokemonRepository.loadPokemonPage(offset, object : PokemonRepository.PokemonCallback{
            override fun onSuccess(pokemons: List<PokemonData>) {
                println("this is pokemonList: ${pokemons.size}")
                pokemonAdapter.addItems(pokemons)
                hideProgressBar()
                offset = pokemons.size
            }
            override fun onError(error: String) {
                Log.e("Failed Api", "Failed Api with error code: $error")
            }
        })
    }

    private fun searchPokemon(){
        pokemonRepository.searchPokemon(searchName, object : PokemonRepository.PokemonCallback{
            override fun onSuccess(pokemons: List<PokemonData>) {
                pokemonAdapter.clearItems()
                pokemonAdapter.addItems(pokemons)
                hideProgressBar()
            }
            override fun onError(error: String) {
                Log.e("Failed Api", "Failed Api with error code: $error")
            }
        })
    }
    private fun showProgressBar() {
        progressBar.visibility = View.VISIBLE
    }
    private fun hideProgressBar() {
        progressBar.visibility = View.GONE
    }
}
