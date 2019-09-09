package com.example.quiz

data class Question(

    var question: String,
    var answer: Boolean

) {
    companion object {
        val questionList = arrayOf(
            Question("This is an Iphone app", false),
            Question("HvA is located Amsterdam", true),
            Question("Google is small", false),
            Question("Android studio is powered by IntellijIDE", true)
        )
    }
}