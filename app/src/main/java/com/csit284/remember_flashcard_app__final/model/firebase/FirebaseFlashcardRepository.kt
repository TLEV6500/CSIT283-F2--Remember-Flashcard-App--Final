package com.csit284.remember_flashcard_app__final.model.firebase

import com.csit284.remember_flashcard_app__final.Application
import com.csit284.remember_flashcard_app__final.model.FlashcardRepository
import com.csit284.remember_flashcard_app__final.model.data.deck.DeckData
import com.csit284.remember_flashcard_app__final.model.data.flashcard.BasicFlashcard
import com.csit284.remember_flashcard_app__final.model.data.flashcard.Flashcard
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions

class FirebaseFlashcardRepository(
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance(),
    private val app: Application
) : FlashcardRepository {

    override fun createFlashcard(deckId: String, flashcard: BasicFlashcard, callback: (Result<Unit>)->Unit) {
        firestore.collection("decks/$deckId/flashcards")
            .add(flashcard.toMap())
            .addOnSuccessListener {
                app.updateSavedFlashcard(flashcard)
                callback(Result.success(Unit))
            }
            .addOnFailureListener { err -> callback(Result.failure(err)) }
        Result.success(Unit)
    }

    override fun getFlashcard(cardId: String,  callback: (Result<BasicFlashcard>) -> Unit) {
        if (app.getFlashcard(cardId) != null) callback(Result.success(app.getFlashcard(cardId)!!))
        else firestore.collection("")
            .document(cardId)
            .get()
            .addOnSuccessListener {
                val card = it.toObject(BasicFlashcard::class.java)
                if (card != null) {
                    app.updateSavedFlashcard(card)
                    callback(Result.success(card))
                }
                else callback(Result.failure(Exception("Error converting flashcard data to object")))
            }
            .addOnFailureListener { err -> callback(Result.failure(err)) }
    }

    override fun getFlashcards(deckId: String, callback: (Result<List<BasicFlashcard>>)->Unit) {
        if (app.getDeck(deckId) != null) {
            val cards = app.getFlashcards(deckId)
            if (cards != null) callback(Result.success(cards))
        }
        firestore.collection("decks/$deckId/flashcards")
            .whereEqualTo("deckId", deckId)
            .get()
            .addOnSuccessListener {
                val cards = it.toObjects(BasicFlashcard::class.java)
                app.updateSavedFlashcards(cards)
                callback(Result.success(cards))
            }
            .addOnFailureListener { err -> callback(Result.failure(err)) }
    }

    override fun updateFlashcard(deckId: String, flashcard: BasicFlashcard, callback: (Result<Unit>) -> Unit) {
        require(flashcard.id.isNotEmpty()) { "Flashcard ID must not be empty" }
        firestore.collection("decks/$deckId/flashcards")
            .document(flashcard.id)
            .set(flashcard.toMap(), SetOptions.merge())
            .addOnSuccessListener {
                app.updateSavedFlashcard(flashcard)
                callback(Result.success(Unit))
            }
            .addOnFailureListener { err -> callback(Result.failure(err)) }
    }

    override fun deleteFlashcard(deckId: String, flashcard: BasicFlashcard, callback: (Result<BasicFlashcard>) -> Unit) {
        val old = flashcard
        firestore.collection("decks/$deckId/flashcards")
            .document(flashcard.id)
            .delete()
            .addOnSuccessListener {
                app.deleteSavedFlashcard(flashcard.id)
                callback(Result.success(old))
            }
            .addOnFailureListener { err -> callback(Result.failure(err)) }
    }
}