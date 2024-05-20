package com.smartherd.pokemon.list

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.smartherd.pokemon.models.PokemonTypeColor

@BindingAdapter("loadWithGlide")
fun loadWithGlide(imageView: ImageView, imageUrl: String){
    Glide.with(imageView.context)
        .load(imageUrl)
        .into(imageView)
}

@BindingAdapter("backgroundColor")
fun backgroundColor(imageView: ImageView, typeName: String) {
    val color = PokemonTypeColor.fromTypeName(typeName).getColor(imageView.context)
    imageView.setBackgroundColor(color)
}