package com.example.findcook.data

data class Recipe(
    val categories: List<String>? = null,
    val complexityLevel: String? = null,
    val description: String? = null,
    val ingredients: List<String>? = null,
    val ingredientsAmount: Int? = null,
    val name: String? = null,
    val steps: List<String>? = null
)