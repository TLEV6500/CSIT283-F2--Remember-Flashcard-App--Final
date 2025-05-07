package com.csit284.remember_flashcard_app__final.study

import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.csit284.remember_flashcard_app__final.Application
import com.csit284.remember_flashcard_app__final.R
import com.csit284.remember_flashcard_app__final.databinding.ActivityStudySessionBinding
import com.csit284.remember_flashcard_app__final.flashcard.FlashcardViewModel
import com.csit284.remember_flashcard_app__final.model.data.flashcard.BasicFlashcard
import com.csit284.remember_flashcard_app__final.model.firebase.FirebaseFlashcardRepository
import java.util.Calendar
import kotlin.math.max

class StudySessionActivity : AppCompatActivity() {
    private lateinit var binding: ActivityStudySessionBinding
    private lateinit var viewModel: FlashcardViewModel
    private lateinit var countDownTimer: CountDownTimer
    private var currentCardIndex = 0
    private lateinit var currentDeckId: String
    private val cards = mutableListOf<BasicFlashcard>()
    private var isAnswerShown = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val cardRepo = FirebaseFlashcardRepository(app=application as Application)
        viewModel = FlashcardViewModel(cardRepo)

        binding = ActivityStudySessionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        currentDeckId = intent.getStringExtra("DECK_ID") ?: ""
        setupTimer()
        loadCards()
        setupClickListeners()
    }

    private fun setupClickListeners() {
        binding.flipButton.setOnClickListener {
            toggleCard()
        }

        // Rating buttons
        binding.againButton.setOnClickListener { handleRating(0) }
        binding.hardButton.setOnClickListener { handleRating(1) }
        binding.goodButton.setOnClickListener { handleRating(2) }
        binding.easyButton.setOnClickListener { handleRating(3) }
    }

    private fun setupTimer() {
        val studyDurationMillis = 5 * 60 * 1000L // 5 minutes

        countDownTimer = object : CountDownTimer(studyDurationMillis, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val minutes = millisUntilFinished / 1000 / 60
                val seconds = millisUntilFinished / 1000 % 60
                binding.timerText.text = String.format("%02d:%02d", minutes, seconds)
            }

            override fun onFinish() {
                binding.timerText.text = "00:00"
                // Optionally end the session
            }
        }.start()
    }

    private fun loadCards() {
        viewModel.loadFlashcards(currentDeckId) {
            it.onSuccess { list ->
                runOnUiThread {
                    cards.addAll(list)
                    if (cards.isNotEmpty()) {
                        showCard(currentCardIndex)
                    } else {
                        Toast.makeText(this, "No cards in this deck", Toast.LENGTH_SHORT).show()
                        finish()
                    }
                }
            }
            it.onFailure { err ->
                Toast.makeText(this, err.message, Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun showCard(index: Int) {
        if (index in cards.indices) {
            binding.questionText.text = cards[index].question
            binding.answerText.text = cards[index].answer
            binding.cardFlipper.displayedChild = 0 // Show front first
            isAnswerShown = false
            binding.flipButton.text = "Show Answer"
            binding.ratingButtons.visibility = View.GONE
        }
    }

    private fun toggleCard() {
        if (isAnswerShown) {
            binding.cardFlipper.showPrevious()
            binding.flipButton.text = "Show Answer"
            binding.ratingButtons.visibility = View.GONE
        } else {
            binding.cardFlipper.showNext()
            binding.flipButton.text = "Hide Answer"
            binding.ratingButtons.visibility = View.VISIBLE
        }
        isAnswerShown = !isAnswerShown
    }

    private fun handleRating(rating: Int) {
        if (currentCardIndex !in cards.indices) return

        val card = cards[currentCardIndex]
        updateCardSpacedRepetition(card, rating)

        // Move to next card or finish session
        if (currentCardIndex + 1 < cards.size) {
            currentCardIndex++
            showCard(currentCardIndex)
        } else {
            Toast.makeText(this, "Session completed!", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    private fun updateCardSpacedRepetition(card: BasicFlashcard, rating: Int) {
        val now = Calendar.getInstance().time
        val newInterval: Int
        val newEaseFactor: Double

        // Simplified SM-2 algorithm
//        when (rating) {
//            0 -> { // Again
//                newInterval = 1
//                newEaseFactor = max(1.3, card.easeFactor - 0.2)
//            }
//            1 -> { // Hard
//                newInterval = (card.interval * 1.5).toInt().coerceAtLeast(1)
//                newEaseFactor = max(1.3, card.easeFactor - 0.15)
//            }
//            2 -> { // Good
//                newInterval = (card.interval * card.easeFactor).toInt().coerceAtLeast(1)
//                newEaseFactor = card.easeFactor
//            }
//            else -> { // Easy
//                newInterval = (card.interval * card.easeFactor * 1.3).toInt().coerceAtLeast(1)
//                newEaseFactor = card.easeFactor + 0.15
//            }
//        }

        // Calculate next review date
//        val calendar = Calendar.getInstance()
//        calendar.add(Calendar.DAY_OF_YEAR, newInterval)
//        val nextReviewDate = calendar.time
//
//        // Update card in Firestore
//        val cardRef = FirebaseFirestore.getInstance()
//            .collection("cards")
//            .document(card.id)
//
//        cardRef.update(
//            "interval", newInterval,
//            "easeFactor", newEaseFactor,
//            "lastReviewed", now,
//            "nextReviewDate", nextReviewDate,
//            "reviewCount", card.reviewCount + 1
//        )
//
//        // Log the review session
//        logReviewSession(card.id, rating)
    }

    private fun logReviewSession(cardId: String, rating: Int) {
//        val sessionData = hashMapOf(
//            "userId" to FirebaseAuth.getInstance().currentUser?.uid,
//            "cardId" to cardId,
//            "rating" to rating,
//            "timestamp" to Calendar.getInstance().time
//        )
//
//        FirebaseFirestore.getInstance()
//            .collection("study_sessions")
//            .add(sessionData)
    }
}