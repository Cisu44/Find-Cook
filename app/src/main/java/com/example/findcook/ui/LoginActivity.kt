package com.example.findcook.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.findcook.MainMenuAcitivity
import com.example.findcook.R
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

   private val fbAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }

    override fun onStart() {
        super.onStart()
        isCurrentUserLoggedIn()
    }

    private fun isCurrentUserLoggedIn() {
        // This method checks if current app user has already logged in

        fbAuth.currentUser?.let { auth ->
            val intent = Intent(applicationContext, MainMenuAcitivity::class.java).apply{
                flags = (Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            }
            startActivity(intent)
        }
    }


}