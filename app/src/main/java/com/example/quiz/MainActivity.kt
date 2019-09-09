package com.example.quiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val questions = arrayListOf<Question>()
    private val questionAdapter = QuestionAdapter(questions)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()
    }

    private fun initViews() {
        rvQuestions.layoutManager = LinearLayoutManager(this@MainActivity, RecyclerView.VERTICAL, false)
        rvQuestions.adapter = questionAdapter
        rvQuestions.addItemDecoration(DividerItemDecoration(this@MainActivity, DividerItemDecoration.VERTICAL))
        createItemTouchHelper().attachToRecyclerView(rvQuestions)

        Question.questionList.forEach { question : Question -> questions.add(question) }
    }

    private fun createItemTouchHelper(): ItemTouchHelper {

        // Callback which is used to create the ItemTouch helper. Only enables left swipe.
        // Use ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) to also enable right swipe.
        val callback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {

            // Enables or Disables the ability to move items up and down.
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            // Callback triggered when a user swiped an item.
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                questionAdapter.notifyItemChanged(position)

//                Left swipe for false, Right for true
                if (direction == ItemTouchHelper.RIGHT) {
                    if (!questions[position].answer) {
                        Snackbar.make(rvQuestions, R.string.correct_false, Snackbar.LENGTH_SHORT).show()
                    } else {
                        Snackbar.make(rvQuestions, R.string.incorrect, Snackbar.LENGTH_SHORT).show()
                    }
                } else {
                    if (questions[position].answer) {
                        Snackbar.make(rvQuestions, R.string.incorrect_true, Snackbar.LENGTH_SHORT).show()
                    } else {
                        Snackbar.make(rvQuestions, R.string.incorrect, Snackbar.LENGTH_SHORT).show()
                    }
                }
            }
        }
        return ItemTouchHelper(callback)
    }
}
