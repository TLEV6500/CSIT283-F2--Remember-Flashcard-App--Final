package com.csit284.remember_flashcard_app__final.deck

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.csit284.remember_flashcard_app__final.R
import com.csit284.remember_flashcard_app__final.databinding.ItemDeckBinding
import com.csit284.remember_flashcard_app__final.model.data.deck.DeckData
import com.google.android.material.dialog.MaterialAlertDialogBuilder

// Todo: Fix later: Either implement a equals() method for Deck interface or Change type used here to data subclass
class DecksAdapter : ListAdapter<DeckData, DecksAdapter.ViewHolder>(DiffCallback()) {
    var onDeleteClick: ((DeckData)->Unit)? = null
    var onDeckClick: ((DeckData)->Unit)? = null

    inner class ViewHolder(val binding: ItemDeckBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(deck: DeckData) {
            binding.textDeckName.text = deck.name

            // Setup options button
            binding.btnOptions.setOnClickListener { view ->
                showPopupMenu(view, deck)
            }
        }

        private fun showPopupMenu(view: View, deck: DeckData) {
            val popup = PopupMenu(view.context, view)
            popup.menuInflater.inflate(R.menu.deck_item_menu, popup.menu)

            popup.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.action_delete -> {
                        removeDeck(deck.id)
                        true
                    }
                    else -> false
                }
            }
            popup.show()
        }
    }

    fun removeDeck(deckId: String) {
        val newList = currentList.toMutableList().apply {
            removeIf { it.id == deckId }
        }
        submitList(newList) // Triggers RecyclerView update
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemDeckBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val deck = getItem(position)
        holder.binding.textDeckName.text = deck.name
    }

    class DiffCallback : DiffUtil.ItemCallback<DeckData>() {
        override fun areItemsTheSame(oldItem: DeckData, newItem: DeckData) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: DeckData, newItem: DeckData) = oldItem == newItem
    }
}