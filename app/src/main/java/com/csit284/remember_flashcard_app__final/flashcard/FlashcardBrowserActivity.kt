package com.csit284.remember_flashcard_app__final.flashcard

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.csit284.remember_flashcard_app__final.Application
import com.csit284.remember_flashcard_app__final.R
import com.csit284.remember_flashcard_app__final.databinding.ActivityFlashcardBrowserBinding
import com.csit284.remember_flashcard_app__final.databinding.ActivityFlashcardEditorBinding
import com.csit284.remember_flashcard_app__final.model.FlashcardRepository
import com.csit284.remember_flashcard_app__final.model.firebase.FirebaseFlashcardRepository

// FlashcardBrowserActivity.kt
class FlashcardBrowserActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: FlashcardsAdapter
    private lateinit var deckId: String
    private lateinit var binding: ActivityFlashcardBrowserBinding
    private lateinit var viewModel: FlashcardViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("TLEV", this.localClassName)
        super.onCreate(savedInstanceState)

        val flashcardRepo: FlashcardRepository = FirebaseFlashcardRepository(app = application as Application)
        viewModel = FlashcardViewModel(flashcardRepo)

        binding = ActivityFlashcardBrowserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.included.topAppBar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        deckId = intent.getStringExtra("DECK_ID") ?: ""

        // Setup RecyclerView
        recyclerView = binding.flashcardsRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = FlashcardsAdapter(emptyList()) { flashcard ->
            // Launch editor when a card is clicked
            val intent = Intent(this, FlashcardEditorActivity::class.java).apply {
                putExtra("FLASHCARD_ID", flashcard.id)
                putExtra("DECK_ID", deckId)
            }
            startActivity(intent)
        }
        recyclerView.adapter = adapter

        // Add new card FAB
        binding.addCardFab.setOnClickListener {
            val intent = Intent(this, FlashcardEditorActivity::class.java).apply {
                putExtra("DECK_ID", deckId)
            }
            startActivity(intent)
        }

        loadFlashcards()
    }

    private fun loadFlashcards() {
        viewModel.loadFlashcards(deckId) {
            it.onSuccess { cards ->
                adapter.updateData(cards)
            }
            it.onFailure { err ->
                Toast.makeText(this, "Error loading flashcards: " + err.message, Toast.LENGTH_SHORT).show()
                Log.e("APP_REMEMBER", (localClassName+" " + err.message))
            }
        }
    }
}