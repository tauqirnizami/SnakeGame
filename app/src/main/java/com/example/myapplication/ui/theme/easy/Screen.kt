package com.example.myapplication.ui.theme.easy

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.times
import com.example.myapplication.GameOverDialogue
import com.example.myapplication.ui.theme.MyApplicationTheme

const val gridWidth: Int = 21
const val gridLength: Int = 31
val cellSize: Dp = 19.dp

//@Composable
//fun DisplayGrid(
//    modifier: Modifier,
//    snakeViewModel: SnakeViewModel
//) {

//}

@Composable
fun Screen(
    modifier: Modifier = Modifier,
    snakeViewModel: SnakeViewModel = remember {SnakeViewModel()},
    navBack: ()-> Unit
) {
        val coordinates by snakeViewModel.coordinates.collectAsState()
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        /***Don't know why the commented code below wasn't updating when coordinates are updated!
         * Had to create a separate "DisplayGrid" function for it!
         * Pls correct it and make it even more optimized if possible!!!***/
//        val colors = generateColorGrid(coordinates = snakeViewModel.coordinates)
//        Column {
//            ColorGrid(colors, modifier = modifier, cellSize = 15.dp)
//            Text(text = "Score = $score")
//        }
        if (!snakeViewModel.gameGoing){
            GameOverDialogue(text = "Your Score = ${snakeViewModel.score}",
                onClose = { navBack() },
  /*              onRetry = {
                    snakeViewModel.gameGoing = true
                    snakeViewModel.foodCoordinates = Pair(10, 11)
                    snakeViewModel.score = 0L
                    snakeViewModel.directions = mutableStateListOf(0) //Using a list instead of a single int to keep track when user changes directions too quickly while the viewModel is on delay (the one for speed controlling). Eg., if user enter up and suddenly left as well, the game earlier used to only register the latter command. Using a mutable list  would keep track of all the given commands that currently hasn't been acted on.
                    snakeViewModel.giantFoodCoordinates = null
                    snakeViewModel.giantFoodCounter = 1
                }*/
            )
        }


    val colors = generateColorGrid(coordinates = coordinates, foodCoordinates = snakeViewModel.foodCoordinates,
        giantFoodCoordinates = snakeViewModel.giantFoodCoordinates)
    Column {
    ColorGrid(colors = colors, modifier = modifier)
        Text(text = "Score = ${snakeViewModel.score}")
    }
//        DisplayGrid(modifier, snakeViewModel = snakeViewModel)

        Row(
            modifier = Modifier,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            FloatingActionButton(onClick = { snakeViewModel.directions.add(2) }) {
                Icon(imageVector = Icons.Filled.KeyboardArrowLeft, contentDescription = "Left")
            }
            Column {
                FloatingActionButton(onClick = { snakeViewModel.directions.add(0) }) {
                    Icon(imageVector = Icons.Filled.KeyboardArrowUp, contentDescription = "Up")
                }
                Spacer(modifier = Modifier.height(31.dp))
                FloatingActionButton(onClick = { snakeViewModel.directions.add(1) }) {
                    Icon(imageVector = Icons.Filled.KeyboardArrowDown, contentDescription = "Down")
                }
            }
            FloatingActionButton(onClick = { snakeViewModel.directions.add(3) }) {
                Icon(imageVector = Icons.Filled.KeyboardArrowRight, contentDescription = "Right")
            }
        }
    }
}

fun generateColorGrid(coordinates: List<Pair<Int, Int>>, foodCoordinates: Pair<Int, Int>,
                      giantFoodCoordinates: Pair<Int, Int>?): List<Color> {
    val coloursList: MutableList<Color> = mutableListOf()
    for (i in 1..gridLength) {
        for (j in 1..gridWidth) {
            if (coordinates.any { it == Pair(i, j) }) {
                coloursList.add(Color.DarkGray)
            } else if (Pair(i, j) == foodCoordinates) {
                coloursList.add(Color.Gray)
            } else if (Pair(i, j) == giantFoodCoordinates) {
                coloursList.add(Color.Black)
            } else {
                coloursList.add(Color.White)
            }
        }
    }
    return coloursList
}

@Composable
fun ColorGrid(colors: List<Color>, modifier: Modifier = Modifier) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(gridWidth),
        modifier = modifier
            .size(gridWidth * cellSize, gridLength * cellSize)
//            .size(gridWidth*cellSize)
    ) {
        items(colors) { color ->
            Box(
                modifier = Modifier
                    .size(cellSize, cellSize)
                    .background(color)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ScreenPreview() {
    MyApplicationTheme {
        Screen(navBack = {})
    }
}