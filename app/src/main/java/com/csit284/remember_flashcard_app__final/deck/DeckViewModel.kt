package com.csit284.remember_flashcard_app__final.deck

import com.csit284.remember_flashcard_app__final.model.DeckRepository
import com.csit284.remember_flashcard_app__final.model.data.deck.Deck
import com.csit284.remember_flashcard_app__final.model.data.deck.DeckData

// viewmodel/DeckViewModel.kt
// Todo: maybe change DeckRepository.OperationCallback from object singletons to two-parameter lambdas, next time
class DeckViewModel(private val deckRepo: DeckRepository) {
//    private var _decks: MutableList<DeckData> = mutableListOf()
//    val decks: List<DeckData>
//        get() = _decks

    fun loadDecks(callback: (Result<List<DeckData>>)->Unit) {
        deckRepo.getDecks(callback)
    }

    fun createDeck(name: String, callback: (Result<Unit>)->Unit) {
        deckRepo.createDeck(name, callback)
    }

    fun updateDeck(deck: Deck, callback: (Result<Unit>)->Unit) {
        deckRepo.updateDeck(deck, callback)
    }

    fun deleteDeck(deckId: String, callback: (Result<DeckData>)->Unit) {
        deckRepo.deleteDeck(deckId, callback)
    }
}