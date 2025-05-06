package com.csit284.remember_flashcard_app__final.model.data.deck

import java.util.Date

data class DeckData(
    override val id: String = "",
    override val authorId: String = "",
    override val cardIds: Array<String> = emptyArray(),
    override val name: String,
    override val createdAt: Date = Date()
) : Deck {
    override fun toMap(): Map<String, Any> = mapOf(
        "authorId" to authorId,
        "cardIds" to cardIds,
        "name" to name,
        "createdAt" to createdAt
    )
}
