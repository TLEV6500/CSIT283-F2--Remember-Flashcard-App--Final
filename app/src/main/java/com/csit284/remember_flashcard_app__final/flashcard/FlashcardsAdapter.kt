package com.csit284.remember_flashcard_app__final.flashcard

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.csit284.remember_flashcard_app__final.R
import com.csit284.remember_flashcard_app__final.databinding.ItemFlashcardBinding
import com.csit284.remember_flashcard_app__final.model.data.flashcard.Flashcard

// FlashcardAdapter.kt
class FlashcardsAdapter(
    private var flashcards: List<Flashcard>,
    private val onItemClick: (Flashcard) -> Unit
) : RecyclerView.Adapter<FlashcardsAdapter.FlashcardViewHolder>() {

    class FlashcardViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val question: TextView = view.findViewById(R.id.cardQuestion)
        val answer: TextView = view.findViewById(R.id.cardAnswer)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FlashcardViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_flashcard, parent, false)
        return FlashcardViewHolder(view)
    }

    override fun onBindViewHolder(holder: FlashcardViewHolder, position: Int) {
        val flashcard = flashcards[position]
        holder.question.text = flashcard.question
        holder.answer.text = flashcard.answer
        holder.itemView.setOnClickListener { onItemClick(flashcard) }
    }

    override fun getItemCount() = flashcards.size

    fun updateData(newFlashcards: List<Flashcard>) {
        flashcards = newFlashcards
        notifyDataSetChanged()
    }
}