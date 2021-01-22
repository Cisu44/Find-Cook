package com.example.findcook.data

data class Recipe(
    val category: String = "",
    val complexityLevel: String = "",
    val description: String = "",
    val ingredients: Map<String,String> = mapOf() ,
    val ingredientsAmount: String = "",
    val name: String = "" ,
    val steps: Map<String,String> = mapOf() ,
    val image: String = ""
)