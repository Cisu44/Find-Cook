package com.example.findcook.ui.registration

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.example.findcook.BaseFragment
import com.example.findcook.R
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import java.lang.Exception

class SignUpFragment : BaseFragment() {

    private val REG_DEBUG="REG_DEBUG"
    private val fbAuth=FirebaseAuth.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_sign_up, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpSignUpClick()
    }

    private fun setUpSignUpClick() {
        view?.findViewById<Button>(R.id.create_account_button)?.setOnClickListener {
            try
            {
                val email = view?.findViewById<EditText>(R.id.register_email_editText)?.text?.trim().toString()
                val pass = view?.findViewById<EditText>(R.id.register_pass_editText)?.text?.trim().toString()
                val repeatPass = view?.findViewById<EditText>(R.id.register_repeat_password_editText)?.text?.trim().toString()

                if(pass==repeatPass)
                {
                    fbAuth.createUserWithEmailAndPassword(email,pass)
                        .addOnSuccessListener { authRes ->
                            if(authRes.user != null) startApplication()
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
            catch (exc: Exception)
            {
                Snackbar.make(requireView(),exc.message.toString(),Snackbar.LENGTH_SHORT)
            }
        }
    }


}