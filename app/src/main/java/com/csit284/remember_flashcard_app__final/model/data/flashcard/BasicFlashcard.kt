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
    override val intervalInDays: Int = 1, // days
    override val easeFactor: Double = 2.5,
    override val lastReviewed: Date?,
    override val nextReviewDate: Date? = null,
    override val reviewCount: Int = 0
) : Flashcard {
    override fun toMap(): Map<String, Any> = Flashcard.toMap(this)
}