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

var foodCoordinates: Pair<Int, Int> = Pair(10, 11)
var score = 0L
var direction = 0
var giantFoodCoordinates: Pair<Int, Int>? = null
var giantFoodCounter: Int = 1

class SnakeViewModel : ViewModel() {
    /*Pair(length/y-coordinate, width/x-coordinate)*/
    var coordinates = mutableStateListOf(Pair(13, 16), Pair(14, 16), Pair(15, 16))
        private set

    private var gameGoing by mutableStateOf(true)

    init {
        viewModelScope.launch(Dispatchers.Default) {
            delay(1000L) //This is to let allow the viewModel to setup properly before being used. I was getting error due to usage of state variable (in viewModel) before waiting for the viewModel to be able to initialize properly first
            while (gameGoing) {
                delay(if (score < 500) 500 - score else 0)
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
            coordinates.add(coordinates.last())
        }

        if (coordinates.drop(1).any { it == newHead }) {
            gameGoing = false
        }

        if (giantFoodCounter % 9 == 0) {
            giantFoodCoordinates = food(foodCoordinates)
            giantFoodCounter = 1
            viewModelScope.launch(Dispatchers.Default) {
                delay((if (score < 30) 15 else if (score < 100) 10 else if (score < 300) 6 else 4) * 1000L)
                giantFoodCoordinates = null
            }
        }

        if (newHead == giantFoodCoordinates) {
            giantFoodCounter = 1
            score += 5
//            for (i in 1 .. 5) {
            coordinates.add(coordinates.last())
//            }
            coordinates.add(coordinates.last())

            coordinates.add(coordinates.last())

            coordinates.add(coordinates.last())

            coordinates.add(coordinates.last())
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