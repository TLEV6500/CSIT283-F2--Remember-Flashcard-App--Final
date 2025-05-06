package com.csit284.remember_flashcard_app__final.model.data.flashcard

import com.csit284.remember_flashcard_app__final.model.data.Mappable
import com.google.firebase.firestore.FieldValue
import java.util.Date

data class Flashcard(
    val id: String,
    val authorId: String,
    val deckId: String,
    val question: String,
    val answer: String,
    val lastReviewed: Date?
) : Mappable {

    override fun toMap(): Map<String, Any> = mapOf(
        "authorId" to authorId,
        "deckId" to deckId,
        "question" to question,
        "answer" to answer,
        "lastReviewed" to (lastReviewed ?: FieldValue.serverTimestamp())
    )

    companion object {
        fun toMap(flashcard: Flashcard): Map<String, Any> = mapOf(
            "authorId" to flashcard.authorId,
            "deckId" to flashcard.deckId,
            "question" to flashcard.question,
            "answer" to flashcard.answer,
            "lastReviewed" to (flashcard.lastReviewed ?: FieldValue.serverTimestamp())
        )
    }
}