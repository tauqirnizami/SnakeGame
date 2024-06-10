package com.example.myapplication.ui.theme.difficult

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
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.example.myapplication.ui.theme.easy.ColorGrid
import com.example.myapplication.ui.theme.easy.directions
import com.example.myapplication.ui.theme.easy.foodCoordinates
import com.example.myapplication.ui.theme.easy.giantFoodCoordinates
import com.example.myapplication.ui.theme.easy.gridLength
import com.example.myapplication.ui.theme.easy.gridWidth
import com.example.myapplication.ui.theme.easy.score

@Composable
fun DifficultDisplayGrid(
    modifier: Modifier,
    snakeViewModel: DifficultSnakeViewModel
) {
    val colors = difficultGenerateColorGrid(coordinates = snakeViewModel.coordinates, walls = snakeViewModel.extraWallsCoordinates)
    Column {
        ColorGrid(colors = colors, modifier = modifier)
        Text(text = "Score = $score")
    }

}

@Composable
fun DifficultScreen(
    modifier: Modifier = Modifier,
    snakeViewModel: DifficultSnakeViewModel = DifficultSnakeViewModel()
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        DifficultDisplayGrid(modifier, snakeViewModel = snakeViewModel)

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

fun difficultGenerateColorGrid(coordinates: List<Pair<Int, Int>>, walls: List<Pair<Int, Int>>): List<Color> {
    val coloursList: MutableList<Color> = mutableListOf()
    for (i in 1..gridLength) {
        for (j in 1..gridWidth) {
            if (i == 1 || j == 1 || i == gridLength || j == gridWidth || walls.any{it == Pair(i,j)}){
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
fun ScreenPreview() {
    MyApplicationTheme {
        DifficultScreen()
    }
}