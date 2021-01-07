package com.example.findcook

import android.content.Intent
import androidx.fragment.app.Fragment

abstract class BaseFragment : Fragment(){

    protected fun startApplication() {
        val intent: Intent = Intent(requireContext(), MainMenuAcitivity::class.java).apply {
            flags = (Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        }
        startActivity(intent)
    }
}



