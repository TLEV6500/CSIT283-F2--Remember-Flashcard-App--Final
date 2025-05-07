package com.csit284.remember_flashcard_app__final.model.data.deck

import com.csit284.remember_flashcard_app__final.model.data.Mappable
import java.io.Serializable
import java.util.Date

interface Deck : Mappable, Serializable {
    val id: String
    val authorId: String
    val cardIds: Array<String>?
    val name: String
    val createdAt: Date?
}