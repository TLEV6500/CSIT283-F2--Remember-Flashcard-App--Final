package com.csit284.remember_flashcard_app__final.core

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.csit284.remember_flashcard_app__final.databinding.ActivityLandingBinding
import com.csit284.remember_flashcard_app__final.about.AboutActivity
import com.csit284.remember_flashcard_app__final.auth.AuthActivity

class LandingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLandingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("TLEV", this.localClassName)
        super.onCreate(savedInstanceState)
        binding = ActivityLandingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnStart.setOnClickListener {
            startActivity(Intent(this, AuthActivity::class.java))
        }
        binding.btnAbout.setOnClickListener {
            startActivity(Intent(this, AboutActivity::class.java))
        }
    }
}