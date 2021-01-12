package com.example.findcook.registration

import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import com.example.findcook.BaseFragment

import com.example.findcook.R
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import java.lang.Exception

class SignUpFragment : BaseFragment() {

    private val REG_DEBUG = "REG_DEBUG"
    private val fbAuth = FirebaseAuth.getInstance()
    var errorFields = mutableSetOf<EditText>()



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_sign_up, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val emailEditText = view.findViewById<EditText>(R.id.register_email_editText)
        val passwordEditText = view.findViewById<EditText>(R.id.register_pass_editText)
        val registerButton = view.findViewById<Button>(R.id.create_account_button)


        if(TextUtils.isEmpty(passwordEditText?.text?.trim().toString()))
        {
            if (passwordEditText != null) {
                errorFields.add(passwordEditText)
            }
            updateButtonState(registerButton)
        }
        else {
            errorFields.remove(passwordEditText)
            updateButtonState(registerButton)
        }
        if(TextUtils.isEmpty(emailEditText?.text?.trim().toString()))
        {
            if (emailEditText != null) {
                errorFields.add(emailEditText)
            }
            updateButtonState(registerButton)
        }
        else {
            errorFields.remove(emailEditText)
            updateButtonState(registerButton)
        }


        setUpSignUp()


    }

    private fun setUpSignUp() {
    // This method checks the values from user input and register new user
        val registerButton = view?.findViewById<Button>(R.id.create_account_button)
        val emailEditText = view?.findViewById<EditText>(R.id.register_email_editText)
        val passwordEditText = view?.findViewById<EditText>(R.id.register_pass_editText)

        emailEditText?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (!android.util.Patterns.EMAIL_ADDRESS.matcher(emailEditText.text.trim())
                        .matches()
                ) {
                    // String nie pochodzi z resource ponieważ wyświetlał się jako liczba
                    // pomimo rzutowania do Stringa
                    emailEditText.error = "Email invalid"
                    errorFields.add(emailEditText)
                }
                else{
                    errorFields.remove(emailEditText)
                    passwordEditText?.error=null
                }
                updateButtonState(registerButton)
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        passwordEditText?.addTextChangedListener(object: TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                if(passwordEditText.text.trim().length<6 )
                {
                    passwordEditText.error="Password must contain at least 6 letters"
                    errorFields.add(passwordEditText)
                }
                else{
                    passwordEditText.error=null
                    errorFields.remove(passwordEditText)
                }
                updateButtonState(registerButton)
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        try {
            registerButton?.setOnClickListener {
                fbAuth.createUserWithEmailAndPassword(emailEditText?.text?.trim().toString(),
                                                      passwordEditText?.text?.trim().toString())
                    .addOnSuccessListener { authRes ->
                        if (authRes.user != null) startApplication()
                    }
                    .addOnFailureListener { exception ->
                        Snackbar.make(
                            requireView(),
                            R.string.register_fail_snack,
                            Snackbar.LENGTH_SHORT
                        ).show()
                        Log.d(REG_DEBUG, exception.message.toString())
                    }
            }
        }
        catch(exc: Exception)
        {
            Snackbar.make(requireView(),R.string.register_fail_snack,Snackbar.LENGTH_SHORT)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        errorFields.clear()
    }

     fun updateButtonState(button: Button?)
    {
        button?.isEnabled = errorFields.isEmpty()
    }



}