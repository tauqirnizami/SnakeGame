package com.example.myapplication.ui.theme.easy

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

//var gameGoing = true

class SnakeViewModel : ViewModel() {
    /*Pair(length/y-coordinate, width/x-coordinate)*/
//    var coordinates = mutableStateListOf(Pair(14, 14), Pair(15, 14), Pair(16, 14))
//        private set
    private val _coordinates = MutableStateFlow(listOf(Pair(14, 14), Pair(15, 14), Pair(16, 14)))
    val coordinates: StateFlow<List<Pair<Int, Int>>> = _coordinates

    var gameGoing by mutableStateOf(true)

var foodCoordinates: Pair<Int, Int> = Pair(10, 11)
var score = 0L
var directions = mutableStateListOf(0) //Using a list instead of a single int to keep track when user changes directions too quickly while the viewModel is on delay (the one for speed controlling). Eg., if user enter up and suddenly left as well, the game earlier used to only register the latter command. Using a mutable list  would keep track of all the given commands that currently hasn't been acted on.
var giantFoodCoordinates: Pair<Int, Int>? = null
private var giantFoodCounter: Int = 1

    init {
 /*       gameGoing = true
        foodCoordinates = Pair(10, 11)
        score = 0L
        directions = mutableStateListOf(0)
        giantFoodCoordinates = null
        giantFoodCounter = 1*/

        viewModelScope.launch(Dispatchers.Main) {
//            delay(550L) //This is to let the viewModel to setup properly before being used. I was getting error due to usage of state variable (probably "coordinates") before waiting for the viewModel to be able to initialize properly first

            while (gameGoing) {
                delay(if (score < 500) 500 - score else 0) //This controls the snake speed
                coordinatesUpdation()
            }
        }
    }

    private fun snakeMove(newHead: Pair<Int, Int>){
        val currentList = _coordinates.value.toMutableList()
        currentList.add(0, newHead)
        currentList.removeLast()
        _coordinates.value = currentList
    }

    private fun eatFood(){
        val currentList = _coordinates.value.toMutableList()
        currentList.add(currentList.last())
        _coordinates.value = currentList
    }

    private suspend fun coordinatesUpdation() {

        // Compute the new head position based on the direction
        if (directions.size>1) {
            directions.removeAt(0)
        }
        val head = _coordinates.value.first()
        val newHead = when (directions[0]) {
            0 -> Pair(if (head.first > 1) head.first - 1 else gridLength, head.second) // UP
            1 -> Pair(if (head.first < gridLength) head.first + 1 else 1, head.second) // DOWN
            2 -> Pair(head.first, if (head.second > 1) head.second - 1 else gridWidth) // LEFT
            else -> Pair(head.first, if (head.second < gridWidth) head.second + 1 else 1) // RIGHT
        }

        // Update the coordinates with the new head and shift the body
        snakeMove(newHead = newHead)

        //Getting Out
            val currentList = _coordinates.value.toMutableList()
        if (currentList.drop(1).any { it == newHead }) {
            gameGoing = false
        }

        //Eating Food
        if (newHead == foodCoordinates) {
            foodCoordinates = food(giantFoodCoordinates, currentList = currentList)
            score++
            giantFoodCounter++
//            coordinates.add(coordinates.last())
            eatFood()
        }

        //Giant Food
        if (giantFoodCounter % 9 == 0) {
            giantFoodCoordinates = food(foodCoordinates, currentList)
            giantFoodCounter = 1
            viewModelScope.launch(Dispatchers.Default) {
                delay((if (score < 30) 15 else if (score < 100) 10 else if (score < 300) 6 else 4) * 1000L)
                giantFoodCoordinates = null
            }
        }

        if (newHead == giantFoodCoordinates) {
            giantFoodCounter = 1
            score += 5

            eatFood()
            eatFood()
            eatFood()
            eatFood()
            eatFood()
        }
    }

    private fun food(otherFood: Pair<Int, Int>?, currentList: List<Pair<Int, Int>>): Pair<Int, Int> {
        var a: Pair<Int, Int>
        do {
            a = Pair((1..gridLength).random(), (1..gridWidth).random())
        } while (currentList.any { it == a } || otherFood == a)
        return a
    }
}