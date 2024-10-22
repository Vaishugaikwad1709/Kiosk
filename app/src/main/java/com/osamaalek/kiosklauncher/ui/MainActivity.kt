package com.osamaalek.kiosklauncher.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.osamaalek.kiosklauncher.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Display HomeFragment if it's not already displayed
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerView, HomeFragment())
                .commit()
        }
    }

    override fun onResume() {
        super.onResume()
        forceReAuthentication()
    }

    private fun forceReAuthentication() {
        val auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser

        if (currentUser == null) {
            // Prompt the user to authenticate if not already authenticated
            val authIntent = Intent(this, AuthActivity::class.java)
            authIntent.putExtra("EXIT_KIOSK_MODE", false)
            startActivity(authIntent)
            finish()
        } else {
            Log.d("MainActivity", "User is already authenticated, no need to re-authenticate")
        }
    }

    override fun onBackPressed() {
        // Prevent back navigation if on HomeFragment
        if (supportFragmentManager.findFragmentById(R.id.fragmentContainerView) is HomeFragment) {
            // Show a message indicating that back navigation is disabled
            Toast.makeText(this, "Back navigation is disabled in kiosk mode.", Toast.LENGTH_SHORT).show()
        } else if (supportFragmentManager.findFragmentById(R.id.fragmentContainerView) is AppsListFragment) {
            // If currently in AppsListFragment, navigate back to HomeFragment
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerView, HomeFragment())
                .commit()
        } else {
            super.onBackPressed()
        }
    }
}
