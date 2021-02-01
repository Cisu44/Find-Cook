package com.example.findcook.data

data class User(
    val uid: String? = null,
    val email: String? = null,
    val favouriteRecipes: ArrayList<String>? = arrayListOf<String>(),
    val forLaterRecipes: ArrayList<String>? = arrayListOf<String>()
)