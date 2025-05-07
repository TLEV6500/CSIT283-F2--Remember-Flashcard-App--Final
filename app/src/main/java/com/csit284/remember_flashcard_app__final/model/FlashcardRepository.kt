package com.csit284.remember_flashcard_app__final.model

import com.csit284.remember_flashcard_app__final.model.data.flashcard.BasicFlashcard
import com.csit284.remember_flashcard_app__final.model.data.flashcard.Flashcard

interface FlashcardRepository {
    fun createFlashcard(deckId: String, flashcard: BasicFlashcard,callback: (Result<Unit>) -> Unit)
    fun getFlashcard(cardId: String, callback: (Result<BasicFlashcard>) -> Unit)
    fun getFlashcards(deckId: String,callback: (Result<List<BasicFlashcard>>) -> Unit)
    fun updateFlashcard(deckId: String, flashcard: BasicFlashcard, callback: (Result<Unit>) -> Unit)
    fun deleteFlashcard(deckId: String, flashcard: BasicFlashcard, callback: (Result<BasicFlashcard>) -> Unit)
}