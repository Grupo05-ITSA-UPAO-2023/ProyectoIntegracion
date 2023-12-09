package com.example.proyectointegracion

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.example.proyectointegracion.databinding.ActivityLoginBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity : BaseActivity() {
    var GOOOGLE_SIGN_IN = 100
    private var binding: ActivityLoginBinding? = null
    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        //Para utilizar los ID de todas las xml
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        auth = Firebase.auth

        //Google
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail().build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)
        //Login boton
        binding?.loginButton?.setOnClickListener { signInUser() }

        //Register boton
        binding?.registerButton1?.setOnClickListener {

            // Crear un Intent para abrir RegisterActivity
            startActivity(
                Intent(this, RegisterActivity::class.java))
            finish()
        }

        //Registrarse con google
        binding?.googleButton?.setOnClickListener {
            sinInWithGoogle()
        }

        // Analytics Event
        val analytics = FirebaseAnalytics.getInstance(this)
        val bundle = Bundle()
        bundle.putString("message", "Integración de Firebase completa")
        analytics.logEvent("InitScreen", bundle)
    }

    private fun signInUser() {
        val email = binding?.emailEditText1?.text.toString()
        val password = binding?.passwordEditText1?.text.toString()
        if (validateForm(email, password)) {
            showProgressBar()
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "Sesion iniciadad Exitosamente!", Toast.LENGTH_SHORT)
                            .show()
                        startActivity(Intent(this, MainActivity::class.java))
                        finish()
                    } else {
                        Toast.makeText(
                            this,
                            "Oops! Ocurrio un error en el login",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    hideProgressBar()
                }
        }
    }

    private fun sinInWithGoogle() {
        val signInIntent = googleSignInClient.signInIntent
        launcher.launch(signInIntent)
    }

    private val launcher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult())
        { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                handleResults(task)
            }
        }

    private fun handleResults(task: Task<GoogleSignInAccount>) {
        if (task.isSuccessful) {
            val account: GoogleSignInAccount? = task.result
            if (account != null) {
                updateUI(account)
            }
        } else {
            Toast.makeText(this, "Sign In Failed, try again.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateUI(account: GoogleSignInAccount) {
        showProgressBar()
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        auth.signInWithCredential(credential).addOnCompleteListener {
            if (it.isSuccessful) {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            } else {
                Toast.makeText(this, "Logueo fallido, intente otra vez", Toast.LENGTH_SHORT).show()
            }
            hideProgressBar()
        }
    }

    private fun validateForm(email: String, password: String): Boolean {
        return when {
            TextUtils.isEmpty(email) && !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                binding?.emailEditText1?.error = "Email incorrecto o no existente"
                false
            }

            TextUtils.isEmpty(password) -> {
                binding?.passwordEditText1?.error = "Ingrese contraseña"
                binding?.emailEditText1?.error = null
                false
            }

            else -> {
                binding?.emailEditText1?.error = null
                binding?.passwordEditText1?.error = null
                true
            }
        }
    }
}