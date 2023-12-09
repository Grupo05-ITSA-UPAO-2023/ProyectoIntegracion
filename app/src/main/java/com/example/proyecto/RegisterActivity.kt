package com.example.proyecto

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.widget.Toast
import com.example.proyecto.databinding.ActivityRegisterBinding
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class RegisterActivity : BaseActivity() {


    private var binding: ActivityRegisterBinding? = null
    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Para utilizar los ID de todas las xml
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        auth = Firebase.auth

        binding?.registerButton2?.setOnClickListener { registerUser() }


        // Analytics Event
        val analytics = FirebaseAnalytics.getInstance(this)
        val bundle = Bundle()
        bundle.putString("message", "Integración de Firebase completa")
        analytics.logEvent("InitScreen", bundle)
    }

    private fun registerUser() {
        val email = binding?.emailEditText2?.text.toString()
        val password = binding?.passwordEditText2?.text.toString()

        if (validateForm(email, password)) {
            showProgressBar()
            auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Registrado Exitosamente!", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, HomeActivity::class.java))
                    finish()
                } else {
                    Toast.makeText(
                        this,
                        "Oops! Ocurrio un error en el registro",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                hideProgressBar()
            }
        }
    }

    private fun validateForm(email: String, password: String): Boolean {
        return when {
            TextUtils.isEmpty(email) && !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                binding?.emailEditText2?.error = "Ingrese email"
                false
            }

            TextUtils.isEmpty(password) -> {
                binding?.passwordEditText2?.error = "Ingrese contraseña"
                binding?.emailEditText2?.error = null
                false
            }

            else -> {
                binding?.emailEditText2?.error = null
                binding?.passwordEditText2?.error = null
                true
            }
        }
    }
}