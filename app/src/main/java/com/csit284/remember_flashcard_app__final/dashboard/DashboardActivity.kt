package com.csit284.remember_flashcard_app__final.dashboard

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.csit284.remember_flashcard_app__final.R
import com.csit284.remember_flashcard_app__final.databinding.ActivityDashboardBinding
import com.csit284.remember_flashcard_app__final.deck.DeckCollectionActivity
import com.csit284.remember_flashcard_app__final.profile.ProfileActivity
import com.csit284.remember_flashcard_app__final.study.StudySessionActivity

class DashboardActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDashboardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnStudyNow.setOnClickListener {
            startActivity(Intent(this, StudySessionActivity::class.java))
        }
        binding.btnMyDecks.setOnClickListener {
            startActivity(Intent(this, DeckCollectionActivity::class.java))
        }
        binding.btnProfile.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }
    }
}
