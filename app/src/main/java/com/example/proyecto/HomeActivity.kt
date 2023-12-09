package com.example.proyecto

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.proyecto.databinding.ActivityHomeBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class HomeActivity : AppCompatActivity() {

    private var binding: ActivityHomeBinding? = null
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Para utilizar los ID de todas las xml
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        auth = Firebase.auth

        //Cerrar Sesion
        binding?.logOutButton?.setOnClickListener {
            if(auth.currentUser!= null){
                auth.signOut()
                startActivity(Intent(this,LoginActivity::class.java))
                finish()
            }
        }
    }
}