package com.csit284.remember_flashcard_app__final.auth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.csit284.remember_flashcard_app__final.R
import com.csit284.remember_flashcard_app__final.dashboard.DashboardActivity
import com.csit284.remember_flashcard_app__final.databinding.ActivityAuthBinding
import com.csit284.remember_flashcard_app__final.model.AuthRepository
import com.csit284.remember_flashcard_app__final.model.firebase.FirebaseAuthRepository

class AuthActivity : AppCompatActivity() {
    private lateinit var viewModel: AuthViewModel
    private lateinit var binding: ActivityAuthBinding
    private var isLoginMode = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val authRepo: AuthRepository = FirebaseAuthRepository()
        viewModel = AuthViewModel(authRepo)

        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupUI()
        binding.btnSubmit.setOnClickListener { handleAuth() }
        binding.toggleAuthText.setOnClickListener { toggleMode() }
    }

    private fun setupUI() {
        binding.titleText.text = if (isLoginMode) "Login" else "Sign Up"
        binding.btnSubmit.text = if (isLoginMode) "Login" else "Sign Up"
        binding.toggleAuthText.text = if (isLoginMode)
            "Don't have an account? Sign up"
        else
            "Already have an account? Login"
    }

    private fun toggleMode() {
        isLoginMode = !isLoginMode
        setupUI()
    }

    private fun handleAuth() {
        val email = binding.etEmail.text.toString()
        val password = binding.etPassword.text.toString()

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Email/Password cannot be empty", Toast.LENGTH_SHORT).show()
            return
        }

        if (isLoginMode) {
            viewModel.login(email, password) { task ->
                task.onSuccess {
                    Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, DashboardActivity::class.java))
                }
                task.onFailure {
                    Toast.makeText(this, "Login failed: ${it.message}", Toast.LENGTH_SHORT).show()
                    Log.e("AuthActivity", it.message ?: "No msg")
                }
            }
        } else {
            viewModel.signup(email, password) { task ->
                task.onSuccess {
                    Toast.makeText(this, "Signup successful", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, DashboardActivity::class.java))
                }
                task.onFailure {
                    Toast.makeText(this, "Signup failed: ${it.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
