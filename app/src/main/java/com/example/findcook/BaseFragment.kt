package com.example.findcook

import android.content.Intent
import android.os.Bundle
import android.transition.TransitionInflater
import androidx.fragment.app.Fragment

abstract class BaseFragment : Fragment(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

       // val transInflater = TransitionInflater.from(requireContext())
        //enterTransition = transInflater.inflateTransition()
    }

    protected fun startApplication() {
        val intent: Intent = Intent(requireContext(), MainMenuAcitivity::class.java).apply {
            flags = (Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        }
        startActivity(intent)
    }
}



