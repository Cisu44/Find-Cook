package com.example.findcook.data

data class User(
    val uid: String? = null,
    val email: String? = null,
    val favouriteRecipes: ArrayList<Int>? = arrayListOf<Int>(),
    val forLaterRecipes: ArrayList<Int>? = arrayListOf<Int>()
)