package com.csit284.remember_flashcard_app__final.flashcard

import android.util.Log
import com.csit284.remember_flashcard_app__final.model.FlashcardRepository
import com.csit284.remember_flashcard_app__final.model.data.flashcard.BasicFlashcard
import com.csit284.remember_flashcard_app__final.model.data.flashcard.Flashcard

class FlashcardViewModel(
    private val flashcardRepo: FlashcardRepository
) {
    // Load all flashcards for a deck
    fun loadFlashcards(deckId: String, callback: (Result<List<BasicFlashcard>>) -> Unit) {
        flashcardRepo.getFlashcards(deckId) { result ->
            callback(result)
        }
    }

    fun loadFlashcard(cardId: String, callback: (Result<BasicFlashcard>) -> Unit) {
        flashcardRepo.getFlashcard(cardId) { result ->
            callback(result)
        }
    }

    // Create or update a flashcard
    fun saveFlashcard(
        deckId: String = "",
        flashcard: BasicFlashcard,
        callback: (Result<Unit>) -> Unit
    ) {
        if (flashcard.id.isEmpty()) {
            // New flashcard
            flashcardRepo.createFlashcard(deckId, flashcard, callback)
        } else {
            // Existing flashcard
            flashcardRepo.updateFlashcard(deckId, flashcard, callback)
        }
    }

    // Delete a flashcard
    fun deleteFlashcard(
        deckId: String,
        flashcard: BasicFlashcard,
        callback: (Result<BasicFlashcard>) -> Unit
    ) {
        flashcardRepo.deleteFlashcard(deckId, flashcard, callback)
    }

    // Optional: Helper for UI state management
    fun validateFlashcard(question: String, answer: String): Boolean {
        return question.isNotBlank() && answer.isNotBlank()
    }
}