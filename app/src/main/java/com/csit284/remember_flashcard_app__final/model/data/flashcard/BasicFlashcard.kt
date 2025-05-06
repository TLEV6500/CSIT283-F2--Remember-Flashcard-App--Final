package com.csit284.remember_flashcard_app__final.model.data.flashcard

import com.google.firebase.firestore.FieldValue
import java.time.LocalDateTime
import java.util.Date

data class BasicFlashcard(
    override val id: String,
    override val authorId: String,
    override val deckId: String,
    override val question: String,
    override val answer: String,
    override val lastReviewed: Date?
) : Flashcard {
    override fun toMap(): Map<String, Any> = mapOf(
        "authorId" to authorId,
        "deckId" to deckId,
        "question" to question,
        "answer" to answer,
        "lastReviewed" to (lastReviewed ?: FieldValue.serverTimestamp())
    )
}