package com.osamaalek.kiosklauncher.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.osamaalek.kiosklauncher.R
import com.osamaalek.kiosklauncher.util.KioskUtil

class AuthActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var loginButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        auth = FirebaseAuth.getInstance()

        emailEditText = findViewById(R.id.emailEditText)
        passwordEditText = findViewById(R.id.passwordEditText)
        loginButton = findViewById(R.id.loginButton)

        loginButton.setOnClickListener {
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                performAuthentication(email, password)
            } else {
                Toast.makeText(this, "Please enter email and password", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun performAuthentication(email: String, password: String) {
        Log.d("AuthActivity", "Attempting to authenticate user with email: $email")
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d("AuthActivity", "Authentication successful")
                    val user = auth.currentUser
                    onUserAuthenticated(user)
                } else {
                    Log.w("AuthActivity", "Authentication failed", task.exception)
                    Toast.makeText(baseContext, "Authentication failed. Please check your email and password.", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun onUserAuthenticated(user: FirebaseUser?) {
        if (user != null) {
            Log.d("AuthActivity", "User authenticated successfully")
            val exitKioskMode = intent.getBooleanExtra("EXIT_KIOSK_MODE", false)
            if (exitKioskMode) {
                KioskUtil.stopKioskMode(this)
            } else {
                KioskUtil.startKioskMode(this)
            }
            val mainIntent = Intent(this, MainActivity::class.java)
            startActivity(mainIntent)
            finish()  // Ensure this is called to close AuthActivity
        } else {
            Log.d("AuthActivity", "User is null, cannot proceed")
        }
    }
}
