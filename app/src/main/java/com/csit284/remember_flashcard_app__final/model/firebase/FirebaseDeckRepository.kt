package com.csit284.remember_flashcard_app__final.model.firebase

import com.csit284.remember_flashcard_app__final.model.DeckRepository
import com.csit284.remember_flashcard_app__final.model.data.deck.Deck
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

import com.csit284.remember_flashcard_app__final.model.data.deck.DeckData

// Todo: Make generic to use different Deck subtypes, for future improvement
class FirebaseDeckRepository(
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance(),
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
) : DeckRepository {

    // Fetch all decks for the current user
    override fun getDecks(callback: (decks: Result<List<DeckData>>)->Unit) {
        val userId = auth.currentUser?.uid ?: run {
            callback(Result.failure(Exception("User not authenticated")))
            return
        }

        firestore.collection("decks")
            .whereEqualTo("userId", userId)
            .get()
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    val decks = it.result.toObjects(DeckData::class.java)
                    callback(Result.success(decks))
                }
                else callback(Result.failure(it.exception ?:Exception("Failed to fetch decks")))
            }
    }

    // Create a new deck
    override fun createDeck(name: String, callback: (Result<Unit>)->Unit) {
        val userId = auth.currentUser?.uid ?: run {
            callback(Result.failure(Exception("User not authenticated")))
            return
        }

        val deck = DeckData(name = name, authorId = userId)
        firestore.collection("decks")
            .add(deck)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    callback(Result.success(Unit))
                } else callback(Result.failure(it.exception ?: Exception("Failed to create deck")))
            }
    }

    // Update a deck
    override fun updateDeck(deck: Deck, callback: (Result<Unit>)->Unit) {
        if (deck.id.isEmpty()) {
            callback(Result.failure(Exception("Deck ID is empty")))
            return
        }

        firestore.collection("decks")
            .document(deck.id)
            .set(deck)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    callback(Result.success(Unit))
                } else callback(Result.failure(it.exception ?: Exception("Failed to update deck")))
            }
    }

    // Delete a deck
    override fun deleteDeck(deckId: String, callback: (deck: Result<DeckData>) -> Unit) {
        if (deckId.isEmpty()) {
            callback(Result.failure(Exception("Deck ID is empty")))
            return
        }

        val doc = firestore.collection("decks").document(deckId)
        val deck = doc.get().result.toObject(DeckData::class.java)!!
        doc.delete()
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    callback(Result.success(deck))
                } else callback(Result.failure(it.exception ?: Exception("Failed to delete deck")))
            }
    }
}