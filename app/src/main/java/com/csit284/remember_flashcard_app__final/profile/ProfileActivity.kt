package com.csit284.remember_flashcard_app__final.profile

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.csit284.remember_flashcard_app__final.databinding.ActivityProfileBinding
import com.csit284.remember_flashcard_app__final.model.firebase.FirebaseProfileRepository

class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding
    private lateinit var viewModel: ProfileViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("TLEV", this.localClassName)
        super.onCreate(savedInstanceState)

        val userRepo = FirebaseProfileRepository()
        viewModel = ProfileViewModel(userRepo)

        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.included.topAppBar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


    }
}