package com.csit284.remember_flashcard_app__final.model.data.user

import java.util.Date

data class User(
    val id: String,
    val email: String,
    val firstName: String,
    val lastName: String,
    val birthDate: Date,
) {
    val age: Long get() = Date().time - birthDate.time
}