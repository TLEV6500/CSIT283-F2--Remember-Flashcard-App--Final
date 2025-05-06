package com.csit284.remember_flashcard_app__final.model.data.deck

import com.csit284.remember_flashcard_app__final.model.data.Mappable
import java.util.Date

interface Deck : Mappable {
    val id: String
    val authorId: String
    val cardIds: Array<String>?
    val name: String
    val createdAt: Date?
}