package com.csit284.remember_flashcard_app__final.model.firebase

import com.csit284.remember_flashcard_app__final.model.FlashcardRepository
import com.csit284.remember_flashcard_app__final.model.data.flashcard.Flashcard
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions

class FirebaseFlashcardRepository(
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
) : FlashcardRepository {

    override fun createFlashcard(deckId: String, flashcard: Flashcard, callback: (Result<Unit>)->Unit) {
        firestore.collection("decks/$deckId/flashcards")
            .add(flashcard.toMap())
            .addOnSuccessListener { callback(Result.success(Unit)) }
            .addOnFailureListener { err -> callback(Result.failure(err)) }
        Result.success(Unit)
    }

    override fun getFlashcards(deckId: String, callback: (Result<List<Flashcard>>)->Unit) {
        firestore.collection("decks/$deckId/flashcards")
            .whereEqualTo("deckId", deckId)
            .get()
            .addOnSuccessListener {
                val cards = it.toObjects(Flashcard::class.java)
                callback(Result.success(cards))
            }
            .addOnFailureListener { err -> callback(Result.failure(err)) }
    }

    override fun updateFlashcard(deckId: String, flashcard: Flashcard, callback: (Result<Unit>) -> Unit) {
        require(flashcard.id.isNotEmpty()) { "Flashcard ID must not be empty" }
        firestore.collection("decks/$deckId/flashcards")
            .document(flashcard.id)
            .set(flashcard.toMap(), SetOptions.merge())
            .addOnSuccessListener { callback(Result.success(Unit)) }
            .addOnFailureListener { err -> callback(Result.failure(err)) }
    }

    override fun deleteFlashcard(deckId: String, flashcard: Flashcard, callback: (Result<Flashcard>) -> Unit) {
        val old = flashcard
        firestore.collection("decks/$deckId/flashcards")
            .document(flashcard.id)
            .delete()
            .addOnSuccessListener { callback(Result.success(old)) }
            .addOnFailureListener { err -> callback(Result.failure(err)) }
    }
}