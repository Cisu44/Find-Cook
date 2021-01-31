package com.example.findcook.home

import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.findcook.FirebaseRepository
import com.example.findcook.data.Recipe
import java.util.*
import kotlin.collections.ArrayList

class HomeViewModel : ViewModel() {
    private val repository = FirebaseRepository()

    val recipes = repository.getRecipes()


}