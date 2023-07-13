package com.smartherd.pokemon.cache

import android.content.Context
import android.content.SharedPreferences
import android.text.TextUtils
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.smartherd.pokemon.models.PokemonData

private const val PREF_NAME = "PokemonCache"

object Cache {

    private lateinit var sharedPreferences: SharedPreferences

    private val gson = Gson()

    fun setup(context: Context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    fun savePokemon(key: String, pokemonList: List<PokemonData>) {
        val jsonString = gson.toJson(pokemonList)

        sharedPreferences
            .edit()
            .putString(key, jsonString)
            .apply()
    }

    fun readPokemon(key: String):List<PokemonData>? {
        val jsonString = sharedPreferences.getString(key,"")
        if (TextUtils.isEmpty(jsonString)){
            return null
        }
        val type = object : TypeToken<List<PokemonData>>() {}.type

        return try{
            gson.fromJson<List<PokemonData>>(jsonString, type)
        } catch (ignored : Exception){
            null
        }

    }
}