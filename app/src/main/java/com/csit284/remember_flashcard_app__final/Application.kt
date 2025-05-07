package com.csit284.remember_flashcard_app__final

import android.app.Application
import com.csit284.remember_flashcard_app__final.model.data.deck.DeckData
import com.csit284.remember_flashcard_app__final.model.data.flashcard.BasicFlashcard
import com.csit284.remember_flashcard_app__final.model.data.flashcard.Flashcard
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseUser

class Application : Application() {
    var user: FirebaseUser? = null
    val data = mutableMapOf<String, Any>()

    private var defaultDeckId: String = ""
    fun setDefaultDeck(deck: DeckData) {
        if (defaultDeckId.isNotEmpty()) return
        defaultDeckId = deck.id
        decks[deck.id] = deck
    }
    private val decks = mutableMapOf<String, DeckData>()
    private val decksContent = mutableMapOf<String, MutableMap<String,BasicFlashcard>>()
    private val cards = mutableMapOf<String, BasicFlashcard>()
    fun getDeck(id: String) = decks[id]
    fun getFlashcard(cardId: String) = cards[cardId]
    fun getFlashcards(deckId: String) = decksContent[deckId]?.values?.toList()
    fun updateDeckContent(deckId: String, cards: List<BasicFlashcard>) {
        decksContent[deckId]?.plus(cards.map { Pair(it.id, it) })
        updateSavedFlashcards(cards)
    }
    fun updateSavedDecks(list: List<DeckData>) = list.forEach { deck ->
        if (decks[deck.id] != deck) decks[deck.id] = deck
    }
    fun updateSavedDeck(deck: DeckData) {
        if (decks[deck.id] != deck) decks[deck.id] = deck
    }
    fun updateSavedFlashcards(list: List<BasicFlashcard>) = list.forEach { card ->
        if (cards[card.id] != card) cards[card.id] = card
    }
    fun updateSavedFlashcard(card: BasicFlashcard) {
        if (cards[card.id] != card) cards[card.id] = card
    }
    fun deleteSavedFlashcard(cardId: String): BasicFlashcard? {
        val card = cards.remove(cardId) ?: return null
        val deckId = card?.deckId
        if (deckId != null) decksContent[deckId]?.remove(cardId)
        return card
    }
    fun deleteSavedDeck(deckId: String) {
        decks.remove(deckId)
        val deckMap = decksContent.remove(deckId)
        if (deckMap != null) decksContent[defaultDeckId]?.putAll(deckMap)
    }

    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)

    }
}