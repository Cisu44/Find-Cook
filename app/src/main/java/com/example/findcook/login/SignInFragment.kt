package com.example.findcook.login

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.navigation.fragment.findNavController
import com.example.findcook.BaseFragment
import com.example.findcook.R
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth

class SignInFragment : BaseFragment() {

    private val fbAuth=FirebaseAuth.getInstance()
    private val LOG_DEBUG="LOG_DEBUG"


        override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_sign_in,container,false)
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpSignInClick()
        setUpSignUpClick()

    }

    private fun setUpSignUpClick() {
        // Go to register form when button click

          view?.findViewById<Button>(R.id.register_button)?.setOnClickListener{
            findNavController().navigate(R.id.action_sign_in_to_sign_up)
        }
    }

    private fun setUpSignInClick() {
        // Login user when button click

        val emailET = view?.findViewById<EditText>(R.id.sign_in_email_editText)
        val passwordET = view?.findViewById<EditText>(R.id.sign_in_pass_editText)

        view?.findViewById<Button>(R.id.sign_in_button)?.setOnClickListener {
            val email =
                    (emailET)?.text?.trim()
                            .toString()
            val pass = (passwordET)?.text?.trim()
                    .toString()

            if (email.isNotEmpty() && pass.isNotEmpty()) {
                fbAuth.signInWithEmailAndPassword(email, pass)
                        .addOnSuccessListener { authRes ->
                            if (authRes.user != null) startApplication()
                        }
                        .addOnFailureListener { exception ->
                            Snackbar.make(
                                    requireView(),
                                    R.string.login_fail_snack,
                                    Snackbar.LENGTH_SHORT
                            ).show()
                            Log.d(LOG_DEBUG, exception.message.toString())
                        }

            }

        }
    }



}

