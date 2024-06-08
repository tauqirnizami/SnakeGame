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

var foodCoordinates: Pair<Int, Int> = Pair(14, 14)
var score = 0L
var direction = 0
var giantFoodCoordinates: Pair<Int, Int>? = null
/*@SuppressLint("MutableCollectionMutableState")
class SnakePosition {
    var coordinates by mutableStateOf(listOf(Pairs(16, 17), Pairs(17, 17), Pairs(18, 17)))
    var direction by mutableStateOf(0)
    var coordinateFirst = coordinates[0]
    val listSize = coordinates.size

    suspend fun coordinatesUpdation(delayInMilis: Long) {
        delay(delayInMilis)
        for (i in listSize-1 downTo 1) {
//            coordinates[i + 1] = coordinates[i]
            coordinates[i] = coordinates[i-1]
            coordinates[1] = coordinates[2]
        }
        when (direction) {
            0 -> coordinates[0].first =
                if (coordinateFirst.first > 1) coordinateFirst.first-- else 35

            1 -> coordinates[0].first =
                if (coordinateFirst.first < 35) coordinateFirst.first++ else 1

            2 -> coordinates[0].second =
                if (coordinateFirst.second > 1) coordinateFirst.second-- else 20

            else -> coordinates[0].second =
                if (coordinateFirst.second < 20) coordinateFirst.second++ else 1
        }
    }

    fun append() {
//        coordinates.add(coordinates[listSize-1])
        coordinates = coordinates + coordinates[listSize-1]
    }
}

data class Pairs(var first: Short, var second: Short)*/


//data class Pair(val first: Int, val second: Int)

/*class SnakeViewModel: ViewModel() {
    var coordinates by mutableStateOf(listOf(Pair(16, 17), Pair(17, 17), Pair(18, 17)))
        private set

    var direction by mutableStateOf(0)
//        private set

    init {
        viewModelScope.launch {
            coordinates
        }
    }

    suspend fun coordinatesUpdation(delayInMillis: Long) {
        delay(delayInMillis)

        // Compute the new head position based on the direction
        val head = coordinates.first()
        val newHead = when (direction) {
            0 -> Pair(if (head.first > 1) head.first - 1 else 35, head.second) // UP
            1 -> Pair(if (head.first < 35) head.first + 1 else 1, head.second) // DOWN
            2 -> Pair(head.first, if (head.second > 1) head.second - 1 else 20) // LEFT
            else -> Pair(head.first, if (head.second < 20) head.second + 1 else 1) // RIGHT
        }

        // Update the coordinates with the new head and shift the body
        coordinates = listOf(newHead) + coordinates.dropLast(1)
    }

//    fun setDirection(newDirection: Int) {
//        // Optional: Add logic to prevent the snake from reversing direction
//        direction = newDirection
//    }

    fun append() {
        // Append a new segment at the end of the snake
        val tail = coordinates.last()
        coordinates = coordinates + tail
    }

    fun food(): Pair<Int, Int>{
        val unavailableCoordinates = coordinates // + Big Food Coordinates
        var a: Pair<Int, Int>
        do {
            a = Pair((1..35).random(), (1..20).random())
        }while (unavailableCoordinates.any{it == a})

        return a
    }
}*/

class SnakeViewModel() : ViewModel() {
        /*Pair(length/y-coordinate, width/x-coordinate)*/
    var coordinates = mutableStateListOf(Pair(16, 17), Pair(17, 17), Pair(18, 17))
        private set

    private var delayInMillis: Long = 500L

    private var gameGoing by mutableStateOf(true)


    suspend fun delayChange() {/*TODO*/
        delayInMillis = if (score == 0L) 500 else if (score <= 500) 500 / score else 0
    }


    init {
        viewModelScope.launch(Dispatchers.Default) {
//            launch {
            while (gameGoing) {
                delay(delayInMillis)
                coordinatesUpdation()
            }
//            }
//            launch {
//                delayChange()
//            }
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
//            append()
            val tail = coordinates.last()
            coordinates.add(tail)
        }

        if (coordinates.drop(1).any { it == newHead }) {
            gameGoing = false
        }

        if (score % 8 == 0L) {
            giantFoodCoordinates = food(foodCoordinates)
            viewModelScope.launch(Dispatchers.Default) {
                delay((if (score < 30) 15 else if (score < 100) 10 else if (score < 300) 6 else 4) * 1000L)
                giantFoodCoordinates = null
            }
        }
////        append(5)
//        if (newHead == giantFoodCoordinates) {
//            score += 5
//            for (i in 1..5) {
//                val tail = coordinates.last()
//                coordinates.add(tail)
//            }
        if (newHead == giantFoodCoordinates) {
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


    /*fun append(count: Int) {
        repeat(count) {
    //        Append a new segment at the end of the snake
            val tail = coordinates.last()
            coordinates.add(tail)
        }
    }*/

    fun food(otherFood: Pair<Int, Int>?): Pair<Int, Int> {
//        val unavailableCoordinates = coordinates
//        val unavailableCoordinates = coordinates.toMutableList()
//        if (otherFood != null) {
//            unavailableCoordinates.add(otherFood)
//        }

        var a: Pair<Int, Int>
        do {
            a = Pair((1..gridLength).random(), (1..gridWidth).random())
        } while (coordinates.any { it == a } || otherFood == a)
//        while (unavailableCoordinates.any { it == a })
        return a
    }
}
