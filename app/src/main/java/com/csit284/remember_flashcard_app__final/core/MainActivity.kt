package com.csit284.remember_flashcard_app__final.core

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.csit284.remember_flashcard_app__final.R
import com.csit284.remember_flashcard_app__final.auth.AuthActivity
import com.csit284.remember_flashcard_app__final.dashboard.DashboardActivity
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("TLEV", this.localClassName)
        // Install splash screen before super.onCreate()
        val splashScreen = installSplashScreen()
        val auth = FirebaseAuth.getInstance();
        Log.d("Firebase", "Auth initialized: ${auth.app.name}")

        // Optional: Keep splash screen visible while loading data
        splashScreen.setKeepOnScreenCondition {
            auth.currentUser == null  // Example: Wait for auth check
        }

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Redirect to Login or Dashboard based on auth state
        if (auth.currentUser != null) {
            startActivity(Intent(this, LandingActivity::class.java))
        } else {
            startActivity(Intent(this, DashboardActivity::class.java))
        }
        finish() // Close MainActivity immediately
    }
}