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
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import com.example.myapplication.ui.theme.MyApplicationTheme

const val gridWidth: Int = 18
const val gridLength: Int = 34
val cellSize: Dp = 15.dp

@Composable
fun DisplayGrid(
    modifier: Modifier,
    snakeViewModel: SnakeViewModel
) {
    val colors = generateColorGrid(coordinates = snakeViewModel.coordinates)
    Column {
    ColorGrid(colors = colors, modifier = modifier)
        Text(text = "Score = $score")
    }
    
}

@Composable
fun Screen(
    modifier: Modifier = Modifier,
    snakeViewModel: SnakeViewModel = SnakeViewModel()
) {
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
        DisplayGrid(modifier, snakeViewModel = snakeViewModel)

        Row(
            modifier = Modifier,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Button(onClick = { directions.add(2) }) {
                Icon(imageVector = Icons.Filled.KeyboardArrowLeft, contentDescription = "Left")
            }
            Column {
                Button(onClick = { directions.add(0) }) {
                    Icon(imageVector = Icons.Filled.KeyboardArrowUp, contentDescription = "Up")
                }
                Spacer(modifier = Modifier.height(13.dp))
                Button(onClick = { directions.add(1) }) {
                    Icon(imageVector = Icons.Filled.KeyboardArrowDown, contentDescription = "Down")
                }
            }
            Button(onClick = { directions.add(3) }) {
                Icon(imageVector = Icons.Filled.KeyboardArrowRight, contentDescription = "Right")
            }
        }
    }
}

fun generateColorGrid(coordinates: List<Pair<Int, Int>>): List<Color> {
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
        Screen()
    }
}