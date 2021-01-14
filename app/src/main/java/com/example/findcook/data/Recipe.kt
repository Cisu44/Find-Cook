package com.example.findcook.data

data class Recipe(
    val category: String = "",
    val complexityLevel: String = "",
    val description: String = "",
    val ingredients: List<String> = listOf() ,
    val ingredientsAmount: Int = 0,
    val name: String = "" ,
    val steps: List<String> = listOf(),
    val image: String = ""
)