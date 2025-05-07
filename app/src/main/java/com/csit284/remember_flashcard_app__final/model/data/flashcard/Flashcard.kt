package com.csit284.remember_flashcard_app__final.model.data.flashcard

import com.csit284.remember_flashcard_app__final.model.data.Mappable
import com.google.firebase.firestore.FieldValue
import java.io.Serializable
import java.util.Date

interface Flashcard : Mappable, Serializable {
    val id: String
    val authorId: String
    val deckId: String
    val question: String
    val answer: String
    val lastReviewed: Date?
    val intervalInDays: Int
    val easeFactor: Double
    val nextReviewDate: Date?
    val reviewCount: Int

    companion object {
        fun toMap(flashcard: Flashcard): Map<String, Any> = mapOf(
            "authorId" to flashcard.authorId,
            "deckId" to flashcard.deckId,
            "question" to flashcard.question,
            "answer" to flashcard.answer,
            "intervalInDays" to flashcard.intervalInDays,
            "easeFactor" to flashcard.easeFactor,
            "nextReviewDate" to (flashcard.nextReviewDate ?: 0),
            "reviewCount" to flashcard.reviewCount,
            "lastReviewed" to (flashcard.lastReviewed ?: FieldValue.serverTimestamp())
        )
    }
}