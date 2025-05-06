package com.csit284.remember_flashcard_app__final.model.data.flashcard

import java.util.Date

data class BasicFlashcardWithExtra(
    override val id: String,
    override val authorId: String,
    override val deckId: String,
    override val question: String,
    override val answer: String,
    val extra: String,
    override val lastReviewed: Date?,
) : Flashcard {
    override fun toMap(): Map<String, Any> {
        val map = Flashcard.toMap(this)
        return map.plus(
            "extra" to extra
        )
    }
}
