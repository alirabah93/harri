package com.smartherd.pokemon.models

import android.content.Context
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import com.smartherd.pokemon.R

enum class PokemonTypeColor(private val typeName: String, @ColorRes private val colorRes: Int) {
    FIRE("fire", R.color.fire),
    WATER("water", R.color.water),
    GRASS("grass", R.color.grass),
    ELECTRIC("electric", R.color.electric),
    ICE("ice", R.color.ice),
    FIGHTING("fighting", R.color.fighting),
    POISON("poison", R.color.poison),
    GROUND("ground", R.color.ground),
    FLYING("flying", R.color.flying),
    PSYCHIC("psychic", R.color.psychic),
    BUG("bug", R.color.bug),
    ROCK("rock", R.color.rock),
    GHOST("ghost", R.color.ghost),
    DRAGON("dragon", R.color.dragon),
    DARK("dark", R.color.dark),
    STEEL("steel", R.color.steel),
    FAIRY("fairy", R.color.fairy),
    NORMAL("normal", R.color.normal);

    fun getColor(context: Context): Int {
        return ContextCompat.getColor(context, colorRes)
    }

    companion object {
        fun fromTypeName(typeName: String): PokemonTypeColor {
            return values().find { it.typeName == typeName } ?: NORMAL
        }
    }
}
