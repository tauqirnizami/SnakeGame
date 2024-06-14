package com.example.myapplication.ui.theme.difficult

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.ui.theme.easy.directions
import com.example.myapplication.ui.theme.easy.foodCoordinates
import com.example.myapplication.ui.theme.easy.gameGoing
import com.example.myapplication.ui.theme.easy.giantFoodCoordinates
import com.example.myapplication.ui.theme.easy.giantFoodCounter
import com.example.myapplication.ui.theme.easy.gridLength
import com.example.myapplication.ui.theme.easy.gridWidth
import com.example.myapplication.ui.theme.easy.score
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

val extraWalls: List<Pair<Int, Int>> = extraWallsGenerator()

fun extraWallsGenerator(): List<Pair<Int, Int>>{
    val length: MutableList<Int> = mutableListOf()
    val width: MutableList<Int> = mutableListOf()

    for (i in 3 until gridLength-1){
        length.add(i)
    }
    for (i in 3 until gridWidth-1){
        width.add(i)
    }

    length.removeAll(listOf(10, 13, 14, 15, 16,))
    width.removeAll(listOf(7,8,9, 12,13,14))

    val list: MutableList<Pair<Int, Int>> = mutableListOf()
    var b: Pair<Int, Int>

    do {
        b = Pair((0 until length.size).random(), (0 until width.size).random())

        list.add(Pair(length[b.first], width[b.second]))
        list.add(Pair(list.last().first, list.last().second+1))
        list.add(Pair(list.last().first, list.last().second+1))
        list.add(Pair(list.last().first, list.last().second+1))

    }while (list.size<19)
    return list
}

class DifficultSnakeViewModel : ViewModel() {
    /*Pair(length/y-coordinate, width/x-coordinate)*/
    var coordinates = mutableStateListOf(Pair(14, 14), Pair(15, 14), Pair(16, 14))
        private set

    private val eachExtraWallWidthPlusOne = 4
//    val extraWalls: List<Pair<Int, Int>> = listOf(Pair(2,3), Pair(3,4), Pair(4,5))

    init {
        gameGoing = true
        foodCoordinates = Pair(10, 9)
        score = 0L
        directions =
            mutableStateListOf(0) //Using a list instead of a single int to keep track when user changes directions too quickly while the viewModel is on delay (the one for speed controlling). Eg., if user enter up and suddenly left as well, the game earlier used to only register the latter command. Using a mutable list  would keep track of all the given commands that currently hasn't been acted on.
        giantFoodCoordinates = null
        giantFoodCounter = 1

        viewModelScope.launch(Dispatchers.Main) {
            while (gameGoing) {
                delay(if (score < 333) 500 - (score * 1.5).toLong() else 0) //This controls the snake speed
                coordinatesUpdation()
            }
        }
    }

    private suspend fun coordinatesUpdation() {

        // Compute the new head position based on the direction
        if (directions.size > 1) {
            directions.removeAt(0)
        }
        val head = coordinates.first()
        val newHead = when (directions[0]) {
            0 -> Pair(head.first - 1, head.second) // UP
            1 -> Pair(head.first + 1, head.second) // DOWN
            2 -> Pair(head.first, head.second - 1) // LEFT
            else -> Pair(head.first, head.second + 1) // RIGHT
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

        //Getting Out
        if (coordinates.drop(1).any { it == newHead } || extraWalls.any { it == newHead } ||
            newHead.first == 1 || newHead.second == 1 || newHead.first == gridLength || newHead.second == gridWidth) {
            gameGoing = false
        }

        //Giant Food
        if (giantFoodCounter % 9 == 0) {
            giantFoodCoordinates = food(foodCoordinates)
            giantFoodCounter = 1
            viewModelScope.launch(Dispatchers.Default) {
                delay((if (score < 50) 15 else if (score < 150) 10 else if (score < 300) 6 else 4) * 1000L)
                giantFoodCoordinates = null
            }
        }

        if (newHead == giantFoodCoordinates) {
            giantFoodCounter = 1
            score += 5

            coordinates.add(coordinates.last())

            coordinates.add(coordinates.last())

            coordinates.add(coordinates.last())

            coordinates.add(coordinates.last())

            coordinates.add(coordinates.last())
        }
    }

    private fun food(otherFood: Pair<Int, Int>?): Pair<Int, Int> {
        var a: Pair<Int, Int>
        do {
            a = Pair((2..<gridLength).random(), (2..<gridWidth).random())
        } while (coordinates.any { it == a } || otherFood == a)
        return a
    }

    private fun walls(): List<Pair<Int, Int>> {
        var a: MutableList<Pair<Int, Int>> = mutableListOf()
        val b: MutableList<Pair<Int, Int>> = mutableListOf()
        var done = true
        do {
            do {
                b.clear()
                b.add(
                        Pair((2 until gridLength).random(), (2 until gridWidth-eachExtraWallWidthPlusOne).random()))
                b.addAll(listOf( Pair(b[0].first, b[0].second + 1), Pair(b[0].first, b[0].second + 1)))

                for (i in 0..2) {
                    if (!(coordinates.any { it == b[i] } || b[i] == foodCoordinates || b[i] == Pair(
                            coordinates[0].first + 1,
                            coordinates[0].second
                        ))) {
                        done = false
                    }
                }
            } while (done)
            a.addAll(b)
            a = a.toSet().toMutableList()
            if (a.size%3 !=0){
                a.clear()
            }
        } while (a.size < 8)
        return a
    }
}