package com.csit284.remember_flashcard_app__final.model

interface AuthRepository {
    fun login(email: String, password: String, callback: (Result<Unit>) -> Unit)
    fun signup(email: String, password: String, callback: (Result<Unit>) -> Unit)
    fun isUserLoggedIn(): Boolean
    fun logout()
}