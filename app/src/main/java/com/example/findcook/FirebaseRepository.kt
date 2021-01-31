package com.example.findcook

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.findcook.data.Recipe
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage

class FirebaseRepository {
    private val REPO_DEBUG = "REPO_DEBUG"

    private val storage = FirebaseStorage.getInstance()
    private val authentication = FirebaseAuth.getInstance()
    private val cloudDatabase = FirebaseFirestore.getInstance()

    fun getRecipes(): LiveData<List<Recipe>>{
        val cloudResult = MutableLiveData<List<Recipe>>()

        cloudDatabase.collection("Recipes")
            .get().addOnSuccessListener {
                val recipe = it.toObjects(Recipe::class.java)
                cloudResult.postValue(recipe)
            }
            .addOnFailureListener{
                Log.d(REPO_DEBUG, it.message.toString())
            }
        return cloudResult
    }
    fun addFavouriteRecipe(recipe: Recipe){

        cloudDatabase.collection("Users")
            .document(authentication.currentUser?.uid!!)
            .update("favouriteRecipes",FieldValue.arrayUnion(recipe.rID))
            .addOnSuccessListener {
                Log.d(REPO_DEBUG, "Recipe added to favourites")
            }
            .addOnFailureListener{
                Log.d(REPO_DEBUG, it.message.toString())
            }
    }
    fun addForLaterRecipe(recipe: Recipe){

        cloudDatabase.collection("Users")
            .document(authentication.currentUser?.uid!!)
            .update("forLaterRecipes",FieldValue.arrayUnion(recipe.rID))
            .addOnSuccessListener {
                Log.d(REPO_DEBUG, "Recipe added to For Later")
            }
            .addOnFailureListener{
                Log.d(REPO_DEBUG, it.message.toString())
            }
    }


}
