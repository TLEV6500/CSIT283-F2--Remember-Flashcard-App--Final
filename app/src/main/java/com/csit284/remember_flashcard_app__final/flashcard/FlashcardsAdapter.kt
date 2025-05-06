package com.csit284.remember_flashcard_app__final.flashcard

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.csit284.remember_flashcard_app__final.databinding.ItemFlashcardBinding
import com.csit284.remember_flashcard_app__final.model.data.flashcard.Flashcard

class FlashcardsAdapter(
    private val onClick: (Flashcard) -> Unit
) : ListAdapter<Flashcard, FlashcardsAdapter.ViewHolder>(DiffCallback()) {

    inner class ViewHolder(private val binding: ItemFlashcardBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(flashcard: Flashcard) {
            binding.textQuestion.text = flashcard.question
            binding.textAnswer.text = flashcard.answer
            binding.root.setOnClickListener { onClick(flashcard) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemFlashcardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val deck = getItem(position)
        holder.binding.textCardName.text = deck.name
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }
}