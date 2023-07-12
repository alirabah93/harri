package com.smartherd.pokemon.cache

import android.content.Context
import android.content.SharedPreferences
import android.text.TextUtils
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.smartherd.pokemon.models.PokemonData
import java.lang.Exception

private const val PREF_NAME = "PokemonCache"

object Cache {

    private lateinit var sharedPreferences: SharedPreferences

    private val gson = Gson()

    fun setup(
        context: Context
    ) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    fun savePokemonsPage(
        key: String,
        pokemons: List<PokemonData>
    ) {
        val jsonString = gson.toJson(pokemons)
        sharedPreferences
            .edit()
            .putString(key, jsonString)
            .apply()
    }

    fun savePokemon(
        key: String,
        pokemon: PokemonData
    ) {
        val jsonString = gson.toJson(pokemon)

        sharedPreferences
            .edit()
            .putString(key, jsonString)
            .apply()
    }

    fun readPokemon(
        key: String
    ): PokemonData? {

        sharedPreferences.contains(key)

        val jsonString = sharedPreferences.getString(key, "")
        if (TextUtils.isEmpty(jsonString)) {
            return null
        }

        val type = object : TypeToken<PokemonData>() {}.type

        return try {
            gson.fromJson<PokemonData>(jsonString, type)
        } catch (ignored: Exception) {
            null
        }
    }
}
