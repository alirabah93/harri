package com.smartherd.pokemon.activities

import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.smartherd.pokemon.R
import com.smartherd.pokemon.databinding.ActivityPokemonListBinding
import com.smartherd.pokemon.helpers.PokemonAdapter
import com.smartherd.pokemon.models.Pokemon
import com.smartherd.pokemon.models.PokemonListResponse
import com.smartherd.pokemon.services.PokemonService
import com.smartherd.pokemon.services.ServiceBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class PokemonListActivity : AppCompatActivity() {


    private lateinit var recyclerView: RecyclerView
    private lateinit var binding: ActivityPokemonListBinding
    private var limit = 20
    private var searchName = ""
    private var firstLoad = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPokemonListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        recyclerView = binding.pokemonRecyclerView

        val orientation = resources.configuration.orientation

        val spanCount = if (orientation == Configuration.ORIENTATION_LANDSCAPE) 4 else 2


        recyclerView.layoutManager = GridLayoutManager(this, spanCount)
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                // Check if the user has reached the bottom of the list
                if (!recyclerView.canScrollVertically(1) && newState == RecyclerView.SCROLL_STATE_IDLE) {
                    // Load more data
                    val adapter = recyclerView.adapter as? PokemonAdapter
                    adapter?.itemCount?.let { itemCount ->
                        if (searchName.isEmpty() && firstLoad) {
                            loadPokemon(itemCount)
                        }
                    }
                }
            }
        })

        val searchEditText = findViewById<EditText>(R.id.search_edit_text)
        searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Not needed for this implementation
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Not needed for this implementation
            }

            override fun afterTextChanged(s: Editable?) {
                val searchQuery = s.toString()
                searchName = searchQuery
                firstLoad = false
                loadPokemon(null)
            }
        })
    }

    override fun onResume() {
        super.onResume()
        loadPokemon(0)
    }

    private fun loadPokemon(offset: Int?) {

        val pokemonService: PokemonService = ServiceBuilder.buildService(PokemonService::class.java)

        limit = if (searchName.isNotEmpty()) {
            Int.MAX_VALUE
        } else {
            20
        }
        val requestCall: Call<PokemonListResponse> = pokemonService.getPokemonList(limit, offset)

        requestCall.enqueue(object : Callback<PokemonListResponse> {
            override fun onResponse(
                call: Call<PokemonListResponse>,
                response: Response<PokemonListResponse>
            ) {
                if (response.isSuccessful) {
                    val pokemonResponse = response.body()!!
                    val pokemonList: List<Pokemon> = pokemonResponse.results?.filter { pokemon ->
                        pokemon.name.startsWith(searchName, ignoreCase = true)
                    } ?: emptyList()

                    if (firstLoad) {
                        val adapter = recyclerView.adapter as? PokemonAdapter
                        adapter?.addItems(pokemonList)
                    } else {
                        recyclerView.adapter = PokemonAdapter(pokemonList)
                        firstLoad = true
                    }
                } else if (response.code() == 401) {
                    Toast.makeText(
                        this@PokemonListActivity,
                        "Your session has expired. Please Login again.",
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    Toast.makeText(
                        this@PokemonListActivity,
                        "Failed to retrieve items",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

            override fun onFailure(call: Call<PokemonListResponse>, t: Throwable) {
                Log.e("Failed Api", "Failed Api with error code: " + t.message)
                Toast.makeText(
                    this@PokemonListActivity,
                    "Error Occurred" + t.toString(),
                    Toast.LENGTH_LONG
                ).show()
            }
        })

    }

}