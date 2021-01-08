package com.example.findcook.ui

import android.content.Context
import android.content.Intent
import android.graphics.Rect
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
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

        fbAuth.currentUser?.let { _ ->
            val intent = Intent(applicationContext, MainMenuAcitivity::class.java).apply{
                flags = (Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            }
            startActivity(intent)
        }
    }


    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        // This method provides hiding keyboard after click on outside of editText

          if(ev?.action==MotionEvent.ACTION_DOWN)
        {
            val v = currentFocus
            if(v is EditText)
            {
                val outRect = Rect()
                v.getGlobalVisibleRect(outRect)
                if(!outRect.contains(ev.rawX.toInt(),ev.rawY.toInt())){
                    Log.d("focus", "touchevent")
                    v.clearFocus()
                    val imm=getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(v.windowToken,0)
                }
            }
        }
        return super.dispatchTouchEvent(ev)
    }

}