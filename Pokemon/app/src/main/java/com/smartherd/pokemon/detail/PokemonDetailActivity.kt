package com.smartherd.pokemon.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.smartherd.pokemon.R

class PokemonDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pokemon_detail)
    }

    companion object {
        const val ARG_ITEM_NAME = "item_name"
    }
}