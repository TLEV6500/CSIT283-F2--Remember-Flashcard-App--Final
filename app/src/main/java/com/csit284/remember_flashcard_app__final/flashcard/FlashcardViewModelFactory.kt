package com.csit284.remember_flashcard_app__final.flashcard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.csit284.remember_flashcard_app__final.model.FlashcardRepository

class FlashcardViewModelFactory(
    private val repository: FlashcardRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return FlashcardViewModel(repository) as T
    }
}