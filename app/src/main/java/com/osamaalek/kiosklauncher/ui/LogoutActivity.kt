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

class LogoutActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var logoutButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_logout)

        auth = FirebaseAuth.getInstance()

        emailEditText = findViewById(R.id.emailEditText)
        passwordEditText = findViewById(R.id.passwordEditText)
        logoutButton = findViewById(R.id.logoutButton)

        logoutButton.setOnClickListener {
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
        Log.d("LogoutActivity", "Attempting to authenticate user with email: $email")
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d("LogoutActivity", "Authentication successful")
                    val user = auth.currentUser
                    onUserAuthenticated(user)
                } else {
                    Log.w("LogoutActivity", "Authentication failed", task.exception)
                    Toast.makeText(baseContext, "Authentication failed. Please check your email and password.", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun onUserAuthenticated(user: FirebaseUser?) {
        // Perform any post-authentication tasks like logging out
        FirebaseAuth.getInstance().signOut()

        // After logout, redirect to the login screen or the main screen
        val intent = Intent(this, AuthActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intent)
        finish()
    }

    override fun onBackPressed() {
        // Prevent going back to LoginActivity after logout
        moveTaskToBack(true)
    }
}
