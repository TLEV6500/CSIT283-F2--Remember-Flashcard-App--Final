package com.csit284.remember_flashcard_app__final.deck

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.csit284.remember_flashcard_app__final.R
import com.csit284.remember_flashcard_app__final.databinding.ActivityDeckBinding
import com.csit284.remember_flashcard_app__final.flashcard.FlashcardViewModel

// DeckActivity.kt
class DeckActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDeckBinding
    private lateinit var viewModel: FlashcardViewModel
    private lateinit var adapter: FlashcardsAdapter
    private var deckId: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDeckBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Get deckId from Intent
        deckId = intent.getStringExtra("DECK_ID") ?: run {
            Toast.makeText(this, "Invalid deck", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        // Initialize ViewModel and Adapter
        viewModel = FlashcardViewModel(FirebaseFlashcardRepository())
        adapter = FlashcardsAdapter { flashcard ->
            // Handle flashcard click (e.g., open editor)
        }

        // Setup RecyclerView
        binding.recyclerViewFlashcards.adapter = adapter
        binding.recyclerViewFlashcards.layoutManager = LinearLayoutManager(this)

        // Load flashcards
        loadFlashcards()
    }

    private fun loadFlashcards() {
        viewModel.loadFlashcards(deckId) { result ->
            runOnUiThread {
                when (result) {
                    is Result.Success -> adapter.submitList(result.getOrNull())
                    is Result.Failure -> Toast.makeText(
                        this,
                        "Failed to load flashcards",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}