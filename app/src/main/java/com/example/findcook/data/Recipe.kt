package com.example.findcook.data

data class Recipe(
    val category: String = "",
    val complexityLevel: String = "",
    val description: String = "",
    val ingredients: HashMap<String,String> = hashMapOf() ,
    val ingredientsAmount: String = "",
    val name: String = "" ,
    val steps: HashMap<String,String> = hashMapOf() ,
    val image: String = ""
)