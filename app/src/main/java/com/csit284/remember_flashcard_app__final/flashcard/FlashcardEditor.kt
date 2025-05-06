package com.csit284.remember_flashcard_app__final.flashcard

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.csit284.remember_flashcard_app__final.R
import com.csit284.remember_flashcard_app__final.databinding.ActivityFlashcardEditorBinding
import com.csit284.remember_flashcard_app__final.model.data.flashcard.Flashcard
import com.csit284.remember_flashcard_app__final.model.firebase.FirebaseFlashcardRepository

class FlashcardEditorActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFlashcardEditorBinding
    private lateinit var viewModel: FlashcardViewModel
    private lateinit var flashcard: Flashcard

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFlashcardEditorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        flashcard = intent.getParcelableExtra("FLASHCARD") ?: return finish()
        viewModel = FlashcardViewModel(FirebaseFlashcardRepository())

        binding.editQuestion.setText(flashcard.question)
        binding.editAnswer.setText(flashcard.answer)

        binding.btnSave.setOnClickListener {
            flashcard = flashcard.copy(
                question = binding.editQuestion.text.toString(),
                answer = binding.editAnswer.text.toString()
            )
            saveFlashcard()
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
            }
        }
    }
}