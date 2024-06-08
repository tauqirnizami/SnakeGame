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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.myapplication.ui.theme.MyApplicationTheme
import kotlin.random.Random

val gridWidth: Int = 20
val gridLength: Int = 35

@Composable
fun Screen(
    modifier: Modifier = Modifier,
//           snakeViewModel: SnakeViewModel = SnakeViewModel()
) {

//    var snakeObject = remember{ SnakePosition()}

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        @Composable
        fun DisplayGrid(
            modifier: Modifier,
            snakeViewModel: SnakeViewModel = SnakeViewModel()
        ) {
//        var continew by remember {
//            mutableStateOf(0)
//        }
//        var score = remember { 0 }
//        var duration = remember { 0L }
            val colors = generateColorGrid(coordinates = snakeViewModel.coordinates)

//    stop(((ceil(score.toFloat()/10))*10).toLong())

//        if (score % 5 == 0 && duration <= 501) {
//            duration += 3
//        }
//        stop(501 / duration)
//        score++

            ColorGrid(colors, gridWidth, modifier = modifier, cellSize = 18.dp)
//        continew++

        }
        DisplayGrid(modifier)

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
            if (coordinates.any{it == Pair(j, i)}){
                coloursList.add(Color.White)
            }
            else if(Pair(j, i) == foodCoordinates){
                coloursList.add(Color.LightGray)
            }
            else if(Pair(j, i) == giantFoodCoordinates){
                coloursList.add(Color.DarkGray)
            }
            else{
                coloursList.add(Color.White)
            }
        }
    }
//    return List(width * length) { generateRandomColor() }
    return coloursList
}

fun generateRandomColor(): Color {
    return Color(
        red = Random.nextFloat(),
        green = Random.nextFloat(),
        blue = Random.nextFloat(),
        alpha = 1f
    )
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