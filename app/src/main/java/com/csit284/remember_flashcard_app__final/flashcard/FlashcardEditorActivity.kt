package com.csit284.remember_flashcard_app__final.flashcard

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.csit284.remember_flashcard_app__final.Application
import com.csit284.remember_flashcard_app__final.databinding.ActivityFlashcardEditorBinding
import com.csit284.remember_flashcard_app__final.model.data.flashcard.BasicFlashcard
import com.csit284.remember_flashcard_app__final.model.data.flashcard.Flashcard
import com.csit284.remember_flashcard_app__final.model.firebase.FirebaseFlashcardRepository

class FlashcardEditorActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFlashcardEditorBinding
    private lateinit var viewModel: FlashcardViewModel
    private lateinit var flashcard: BasicFlashcard
    private val app = application as Application
    private var flashcardId: String? = null
    private lateinit var deckId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("TLEV", this.localClassName)
        super.onCreate(savedInstanceState)
        binding = ActivityFlashcardEditorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.included.topAppBar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        deckId = intent.getStringExtra("DECK_ID") ?: ""
        flashcardId = intent.getStringExtra("FLASHCARD_ID")

        if (flashcardId != null) {
            loadFlashcard()
        }

        viewModel = FlashcardViewModel(FirebaseFlashcardRepository(app = application as Application))

        binding.questionEditText.setText(flashcard.question)
        binding.answerEditText.setText(flashcard.answer)

        binding.cancelButton.setOnClickListener {
            finish()
        }

        binding.saveButton.setOnClickListener {
            flashcard = flashcard.copy(
                question = binding.questionEditText.text.toString(),
                answer = binding.answerEditText.text.toString()
            )
            saveFlashcard()
        }
    }

    private fun loadFlashcard() {
        if (flashcardId == null) return

        viewModel.loadFlashcard(flashcardId!!) {
                it.onSuccess { card ->
                    binding.questionEditText.setText(card.question)
                    binding.answerEditText.setText(card.answer)
                    flashcard = card
                }
                it.onFailure { err ->
                    Toast.makeText(this, err.message, Toast.LENGTH_SHORT).show()
                    Log.e("APP_REMEMBER", (localClassName+" " + err.message))
                }
            }
    }

    private fun saveFlashcard() {
        viewModel.saveFlashcard(flashcard = flashcard) {
            it.onSuccess {
                runOnUiThread {
                    Toast.makeText(this, "Saved!", Toast.LENGTH_SHORT).show()
                    finish()
                }
            }

            it.onFailure { err ->
                runOnUiThread { Toast.makeText(this, "Error: ${err.message}", Toast.LENGTH_SHORT).show() }
                Log.e("APP_REMEMBER", (localClassName+" " + err.message))
            }
        }
    }
}