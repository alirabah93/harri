package com.smartherd.pokemon.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPokemonListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        recyclerView = binding.pokemonRecyclerView

    }

    override fun onResume() {
        super.onResume()

        loadPokemon()
    }

    private fun loadPokemon() {

        val pokemonService : PokemonService = ServiceBuilder.buildService(PokemonService::class.java)

        val requestCall : Call<PokemonListResponse> = pokemonService.getPokemonList(20, 0)

        requestCall.enqueue(object : Callback<PokemonListResponse> {
            override fun onResponse(
                call: Call<PokemonListResponse>,
                response: Response<PokemonListResponse>
            ) {
                if (response.isSuccessful) {
                    val pokemonResponse = response.body()!!
                    val pokemonList : List<Pokemon> = pokemonResponse.results
                    recyclerView.adapter = PokemonAdapter(pokemonList)
                } else if(response.code() == 401) {
                    Toast.makeText(this@PokemonListActivity, "Your session has expired. Please Login again.", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(this@PokemonListActivity, "Failed to retrieve items", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<PokemonListResponse>, t: Throwable) {
                Log.e("Failed Api", "Failed Api with error code: " + t.message)
                Toast.makeText(this@PokemonListActivity, "Error Occurred" + t.toString(), Toast.LENGTH_LONG).show()
            }
        })

    }
}