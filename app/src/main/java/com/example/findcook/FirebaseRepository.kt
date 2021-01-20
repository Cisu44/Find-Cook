package com.example.findcook

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.findcook.data.Recipe
import com.example.findcook.data.RecipeSmall
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage

class FirebaseRepository {
    private val REPO_DEBUG = "REPO_DEBUG"

    private val storage = FirebaseStorage.getInstance()
    private val authentication = FirebaseAuth.getInstance()
    private val cloudDatabase = FirebaseFirestore.getInstance()

    fun getRecipesHome(): LiveData<List<RecipeSmall>>{
        val cloudResult = MutableLiveData<List<RecipeSmall>>()

        cloudDatabase.collection("Recipes")
            .get().addOnSuccessListener {
                val recipeSmall = it.toObjects(RecipeSmall::class.java)
                cloudResult.postValue(recipeSmall)
            }
            .addOnFailureListener{
                Log.d(REPO_DEBUG, it.message.toString())
            }
        return cloudResult
    }
}