package com.example.myapplication.ui.theme.screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

var foodCoordinates: Pair<Int, Int> = Pair(14, 13)
var score = 0L
var direction = 0
var giantFoodCoordinates: Pair<Int, Int>? = null
var giantFoodCounter: Int = 0

class SnakeViewModel() : ViewModel() {
    /*Pair(length/y-coordinate, width/x-coordinate)*/
    var coordinates = mutableStateListOf(Pair(16, 17), Pair(17, 17), Pair(18, 17))
        private set

    private var delayInMillis: Long = 500L

    private var gameGoing by mutableStateOf(true)


    private fun delayChange() {/*TODO*/
        delayInMillis = if (score == 0L) 500 else if (score <= 500) 500 / score else 0
    }


    init {
        viewModelScope.launch(Dispatchers.Default) {
            delay(1000L)
            while (gameGoing) {
                delay(delayInMillis)
                delayChange()
                coordinatesUpdation()
            }
        }
    }

    private suspend fun coordinatesUpdation() {

        // Compute the new head position based on the direction
        val head = coordinates.first()
        val newHead = when (direction) {
            0 -> Pair(if (head.first > 1) head.first - 1 else gridLength, head.second) // UP
            1 -> Pair(if (head.first < gridLength) head.first + 1 else 1, head.second) // DOWN
            2 -> Pair(head.first, if (head.second > 1) head.second - 1 else gridWidth) // LEFT
            else -> Pair(head.first, if (head.second < gridWidth) head.second + 1 else 1) // RIGHT
        }

        // Update the coordinates with the new head and shift the body
        coordinates.add(0, newHead)
        coordinates.removeLast()

        //Eating Food
        if (newHead == foodCoordinates) {
            foodCoordinates = food(giantFoodCoordinates)
            score++
            giantFoodCounter++
            val tail = coordinates.last()
            coordinates.add(tail)
        }

        if (coordinates.drop(1).any { it == newHead }) {
            gameGoing = false
        }

        if (giantFoodCounter % 8 == 0 && giantFoodCounter!=0) {
            giantFoodCoordinates = food(foodCoordinates)
            viewModelScope.launch(Dispatchers.Default) {
                delay((if (score < 30) 15 else if (score < 100) 10 else if (score < 300) 6 else 4) * 1000L)
                giantFoodCoordinates = null
                giantFoodCounter = 0
            }
        }

        if (newHead == giantFoodCoordinates) {
            giantFoodCounter = 0
            score += 5
            var tail = coordinates.last()
            coordinates.add(tail)

            tail = coordinates.last()
            coordinates.add(tail)

            tail = coordinates.last()
            coordinates.add(tail)

            tail = coordinates.last()
            coordinates.add(tail)

            tail = coordinates.last()
            coordinates.add(tail)
        }
    }

    private fun food(otherFood: Pair<Int, Int>?): Pair<Int, Int> {
        var a: Pair<Int, Int>
        do {
            a = Pair((1..gridLength).random(), (1..gridWidth).random())
        } while (coordinates.any { it == a } || otherFood == a)
        return a
    }
}
