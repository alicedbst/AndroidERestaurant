package fr.isen.dubost.androiderestaurant.model

import java.io.Serializable

data class Item(val name_fr: String,
                val images: ArrayList<String>,
                val ingredients: ArrayList<Ingredient>,
                val prices: ArrayList<Price>):
    Serializable
