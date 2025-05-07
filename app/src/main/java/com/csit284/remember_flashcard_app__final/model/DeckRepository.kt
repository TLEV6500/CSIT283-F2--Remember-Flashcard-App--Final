package com.csit284.remember_flashcard_app__final.model

import com.csit284.remember_flashcard_app__final.model.data.deck.Deck
import com.csit284.remember_flashcard_app__final.model.data.deck.DeckData

interface DeckRepository {
    fun getDecks(callback: (decks: Result<List<DeckData>>)->Unit)
    fun createDeck(name: String, callback: (Result<Unit>)->Unit)
    fun updateDeck(deck: DeckData, callback: (Result<Unit>)->Unit)
    fun deleteDeck(deckId: String, callback: (deck: Result<DeckData>)->Unit)
}