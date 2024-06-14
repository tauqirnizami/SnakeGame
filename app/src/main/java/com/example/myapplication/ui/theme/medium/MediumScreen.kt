package com.example.myapplication.ui.theme.medium

//package com.example.myapplication.ui.theme.mediumScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapplication.GameOverDialogue
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.example.myapplication.ui.theme.easy.ColorGrid
import com.example.myapplication.ui.theme.easy.gridLength
import com.example.myapplication.ui.theme.easy.gridWidth

//const val gridWidth: Int = 18
//const val gridLength: Int = 34

//@Composable
//fun MediumDisplayGrid(
//    modifier: Modifier,
//    snakeViewModel: MediumSnakeViewModel
//) {

//}

@Composable
fun MediumScreen(
    modifier: Modifier = Modifier,
    snakeViewModel: MediumSnakeViewModel = remember { MediumSnakeViewModel()},
    navBack: ()-> Unit
) {
    val coordinates by snakeViewModel.coordinates.collectAsState()
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {

        if (!snakeViewModel.gameGoing){
            GameOverDialogue(text = "Your Score = ${snakeViewModel.score}",
                onClose = { navBack() },
/*                onRetry = {
                    snakeViewModel.gameGoing = true
                    snakeViewModel.foodCoordinates = Pair(10, 11)
                    snakeViewModel.score = 0L
                    snakeViewModel.directions = mutableStateListOf(0) //Using a list instead of a single int to keep track when user changes directions too quickly while the viewModel is on delay (the one for speed controlling). Eg., if user enter up and suddenly left as well, the game earlier used to only register the latter command. Using a mutable list  would keep track of all the given commands that currently hasn't been acted on.
                    snakeViewModel.giantFoodCoordinates = null
                    snakeViewModel.giantFoodCounter = 1
                }*/
            )
        }

    val colors = mediumGenerateColorGrid(coordinates = coordinates, foodCoordinates = snakeViewModel.foodCoordinates,
        giantFoodCoordinates = snakeViewModel.giantFoodCoordinates)
    Column {
        ColorGrid(colors = colors, modifier = modifier)
        Text(text = "Score = ${snakeViewModel.score}")
    }
//        MediumDisplayGrid(modifier, snakeViewModel = snakeViewModel)

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

fun mediumGenerateColorGrid(coordinates: List<Pair<Int, Int>>, foodCoordinates: Pair<Int, Int>,
                            giantFoodCoordinates: Pair<Int, Int>?): List<Color> {
    val coloursList: MutableList<Color> = mutableListOf()
    for (i in 1..gridLength) {
        for (j in 1..gridWidth) {
            if (i == 1 || j == 1 || i == gridLength || j == gridWidth){
                coloursList.add(Color.Red)
            }
            else if (coordinates.any { it == Pair(i, j) }) {
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

@Preview(showBackground = true)
@Composable
fun MediumScreenPreview() {
    MyApplicationTheme {
        MediumScreen(navBack = {})
    }
}