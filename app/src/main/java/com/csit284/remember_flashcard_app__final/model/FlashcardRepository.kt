package com.csit284.remember_flashcard_app__final.model

import com.csit284.remember_flashcard_app__final.model.data.flashcard.Flashcard

interface FlashcardRepository {
    fun createFlashcard(deckId: String, flashcard: Flashcard,callback: (Result<Unit>) -> Unit)
    fun getFlashcards(deckId: String,callback: (Result<List<Flashcard>>) -> Unit)
    fun updateFlashcard(deckId: String, flashcard: Flashcard, callback: (Result<Unit>) -> Unit)
    fun deleteFlashcard(deckId: String, flashcard: Flashcard, callback: (Result<Flashcard>) -> Unit)
}