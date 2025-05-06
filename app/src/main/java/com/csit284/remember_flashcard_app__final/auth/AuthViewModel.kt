package com.csit284.remember_flashcard_app__final.auth

import com.csit284.remember_flashcard_app__final.model.AuthRepository

class AuthViewModel(
    private val authRepo: AuthRepository
) {
    fun login(email: String, password: String, callback: (Result<Unit>) -> Unit) {
        authRepo.login(email, password, callback)
    }

    fun signup(email: String, password: String, callback: (Result<Unit>) -> Unit) {
        authRepo.signup(email, password, callback)
    }

    fun isUserLoggedIn(): Boolean {
        return authRepo.isUserLoggedIn()
    }
}