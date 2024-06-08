package com.example.myapplication.ui.theme.screen

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
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.myapplication.ui.theme.MyApplicationTheme

val gridWidth: Int = 20
val gridLength: Int = 35

        @Composable
        fun DisplayGrid(
            modifier: Modifier,
            snakeViewModel: SnakeViewModel
        ) {
            val colors = generateColorGrid(coordinates = snakeViewModel.coordinates)
            ColorGrid(colors, gridWidth, modifier = modifier, cellSize = 18.dp)
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

        DisplayGrid(modifier, snakeViewModel = snakeViewModel)

        Row(
            modifier = Modifier,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Button(onClick = { direction = 2 }) {
                Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Left")
            }
            Column {
                Button(onClick = { direction = 0 }) {
                    Icon(imageVector = Icons.Filled.KeyboardArrowUp, contentDescription = "Up")
                }
                Spacer(modifier = Modifier.height(13.dp))
                Button(onClick = { direction = 1 }) {
                    Icon(imageVector = Icons.Filled.KeyboardArrowDown, contentDescription = "Down")
                }
            }
            Button(onClick = { direction = 3 }) {
                Icon(imageVector = Icons.Filled.ArrowForward, contentDescription = "Right")
            }
        }
    }
}

fun generateColorGrid(coordinates: List<Pair<Int, Int>>): List<Color> {
    val coloursList: MutableList<Color> = mutableListOf()
    for (i in 1..gridLength) {
        for (j in 1..gridWidth) {
            if (coordinates.any { it == Pair(i, j) }) {
                coloursList.add(Color.White)
            } else if (Pair(j, i) == foodCoordinates) {
                coloursList.add(Color.LightGray)
            } else if (Pair(j, i) == giantFoodCoordinates) {
                coloursList.add(Color.DarkGray)
            } else {
                coloursList.add(Color.Yellow)
            }
        }
    }
    return coloursList
}

@Composable
fun ColorGrid(colors: List<Color>, width: Int, cellSize: Dp, modifier: Modifier = Modifier) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(width),
        modifier = modifier
    ) {
        items(colors) { color ->
            Box(
                modifier = Modifier
                    .size(cellSize)  // Sets the size of each cell
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