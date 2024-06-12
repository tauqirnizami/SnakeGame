package com.example.myapplication.ui.theme.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.data.HighScoreData
import com.example.myapplication.ui.theme.easy.Screen
import com.example.myapplication.ui.theme.easy.score

enum class ScreenNames {
    StartScreen,
    LevelSelectScreen,
    HighScore,
    Easy,
    Medium,
    Difficult
}

/*@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameTopAppBar(
    modifier: Modifier = Modifier
        .fillMaxWidth()
) {
    TopAppBar(
        title = {
                Text(
                    text = "Snake Game",
                    textAlign = TextAlign.Center,
                    color = Color(android.graphics.Color.parseColor("#fba001")),
                    modifier = Modifier
                        .padding(9.dp),
                    fontFamily = FontFamily(Font(R.font.gamja_flower)),
                    fontSize = 27.sp
                )
        },
        modifier = modifier
    )
}*/

@Composable
fun HealthEase(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {

    Scaffold(
/*        topBar = {
            GameTopAppBar(
                modifier = modifier
            )
        }*/
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = ScreenNames.StartScreen.name,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            composable(ScreenNames.StartScreen.name) {
                GameScreen(
                    onClick1 = { navController.navigate(ScreenNames.LevelSelectScreen.name) },
                    onClick2 = {navController.navigate(ScreenNames.HighScore.name)}
                )
            }
            composable(ScreenNames.LevelSelectScreen.name) {
                LevelsScreen(
                    onClick1 = { navController.navigate(ScreenNames.Easy.name) },
                    onClick2 = { navController.navigate(ScreenNames.Medium.name) },
                    onClick3 = { navController.navigate(ScreenNames.Difficult.name) }
                )
            }
            composable(ScreenNames.HighScore.name) {
                HighScoresScreen(easyScore = easyList.firstOrNull()?.highScore ?: "0",
                    mediumScore = mediumList.firstOrNull()?.highScore ?: "0",
                    difficultScore = mediumList.firstOrNull()?.highScore ?: "0")
            }
            composable(ScreenNames.Easy.name) {
                Screen(highScore = easyList.firstOrNull()?.highScore ?: "0",
                    navBack = {navController.navigate(ScreenNames.LevelSelectScreen.name)})
            }
            composable(ScreenNames.Others.name) {
                OthersButtonScreen(
                    onClick1 = { navController.navigate(ScreenNames.Sports.name) },
                    onClick2 = { navController.navigate(ScreenNames.StreakCounter.name) },
                    onClick = { navController.navigate(ScreenNames.Motivation.name) }
                )
            }
        }
    }
}