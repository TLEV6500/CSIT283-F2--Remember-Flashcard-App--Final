package com.csit284.remember_flashcard_app__final.deck

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.csit284.remember_flashcard_app__final.R
import com.csit284.remember_flashcard_app__final.databinding.ActivityDeckCollectionBinding
import com.csit284.remember_flashcard_app__final.model.data.deck.DeckData
import com.csit284.remember_flashcard_app__final.model.firebase.FirebaseDeckRepository
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText

class DeckCollectionActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDeckCollectionBinding
    private lateinit var viewModel: DeckViewModel
    private lateinit var adapter: DecksAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDeckCollectionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize ViewModel and Adapter
        viewModel = DeckViewModel(FirebaseDeckRepository())

        // Init Recycler View
        setupRecyclerView()

        // Load decks
        loadDecks()

        // Setup FAB to create a new deck
        binding.fabAddDeck.setOnClickListener {
            showCreateDeckDialog()
        }
    }

    private fun setupRecyclerView() {
        adapter = DecksAdapter()
        adapter.onDeleteClick = this::showDeleteDialog

        adapter.onDeckClick = { deck ->
            val intent = Intent(this, DeckActivity::class.java).apply {
                putExtra("DECK_ID", deck.id)  // Pass the selected deck's ID
            }
            startActivity(intent)
        }

        binding.recyclerViewDecks.adapter = adapter
    }

    private fun loadDecks() {
        viewModel.loadDecks {
            it.onSuccess { decks ->
                runOnUiThread {
                    adapter.submitList(decks)
                }
            }
            it.onFailure { err ->
                runOnUiThread {
                    Toast.makeText(this@DeckCollectionActivity, err.message, Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }

    private fun createDeck(name: String) {
        viewModel.createDeck(name) {
            it.onSuccess() {
                runOnUiThread {
                    Toast.makeText(this@DeckCollectionActivity, "Deck created!", Toast.LENGTH_SHORT)
                        .show()
                    loadDecks()  // Refresh the list
                }
            }
            it.onFailure { err ->
                runOnUiThread {
                    Toast.makeText(this@DeckCollectionActivity, err.message, Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }

    private fun showCreateDeckDialog() {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_create_deck, null)
        val editText = dialogView.findViewById<TextInputEditText>(R.id.editDeckName)
        val dialog = MaterialAlertDialogBuilder(this, R.style.ThemeOverlay_App_MaterialAlertDialog)
            .setTitle("New Deck")
            .setView(R.layout.dialog_create_deck)  // custom dialog layout
            .setPositiveButton("Create") { _, _ ->
                val name = editText.text.toString()
                createDeck(name)
            }
            .setNegativeButton("Cancel", null)
            .create()
        dialog.show()
    }

    private fun showDeleteDialog(deck: DeckData) {
        // Show confirmation dialog before deleting
        MaterialAlertDialogBuilder(this)
            .setTitle("Delete ${deck.name} Deck?")
            .setMessage("All cards in this deck will be permanently deleted.")
            .setPositiveButton("Delete") { _, _ ->
                viewModel.deleteDeck(deck.id) {
                    runOnUiThread {
                        adapter.removeDeck(deck.id)
                        Toast.makeText(
                            this@DeckCollectionActivity,
                            if (it.isSuccess) "Deleted ${deck.name}"
                            else it.exceptionOrNull()?.message ?: ""
                            , Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
}