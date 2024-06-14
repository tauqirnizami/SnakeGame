package com.example.myapplication.ui.theme.navigation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.R
import com.example.myapplication.ui.theme.MyApplicationTheme

@Composable
fun LevelsScreen(
    onClick1: () -> Unit,
    onClick2: () -> Unit,
    onClick3: () -> Unit,

    modifier: Modifier = Modifier
) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(45.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "Game Modes",
                modifier = modifier,
                fontSize = 66.5.sp,
                fontWeight = FontWeight.ExtraBold,
                fontStyle = FontStyle.Italic,
                lineHeight = 71.sp,
                textAlign = TextAlign.Center,
                fontFamily = FontFamily(Font(R.font.gamja_flower)),
            )

            Spacer(modifier = Modifier.height(131.dp))

            Button(
                onClick = { onClick1() },
                modifier = modifier.fillMaxWidth().padding(start = 17.dp, end = 17.dp),
                shape = CutCornerShape(
                    bottomStart = 13.dp,
                    topEnd = 13.dp,
                    topStart = 5.dp,
                    bottomEnd = 5.dp
                ),
                elevation = ButtonDefaults.buttonElevation(
                    defaultElevation = 10.dp,
                    pressedElevation = 5.dp,
                )
            ) {
                Text(
                    text = "Garden",
                    fontSize = 25.sp,
                    lineHeight = 27.sp,
                    fontFamily = FontFamily(Font(R.font.autour_one)),
                    textAlign = TextAlign.Center
                )
            }
            Spacer(modifier = Modifier.height(87.dp))

            Button(
                onClick = { onClick2() },
                modifier = modifier.fillMaxWidth().padding(start = 17.dp, end = 17.dp),
                shape = CutCornerShape(
                    bottomStart = 13.dp,
                    topEnd = 13.dp,
                    topStart = 5.dp,
                    bottomEnd = 5.dp
                ),
                elevation = ButtonDefaults.buttonElevation(
                    defaultElevation = 10.dp,
                    pressedElevation = 5.dp,
                )
            ) {
                Text(
                    text = "Jungle",
                    fontSize = 25.sp,
                    lineHeight = 27.sp,
                    fontFamily = FontFamily(Font(R.font.autour_one)),
                    textAlign = TextAlign.Center
                )
            }
            Spacer(modifier = Modifier.height(87.dp))

            Button(
                onClick = { onClick3() },
                modifier = modifier.fillMaxWidth().padding(start = 17.dp, end = 17.dp),
                shape = CutCornerShape(
                    bottomStart = 13.dp,
                    topEnd = 13.dp,
                    topStart = 5.dp,
                    bottomEnd = 5.dp
                ),
                elevation = ButtonDefaults.buttonElevation(
                    defaultElevation = 10.dp,
                    pressedElevation = 5.dp,
                )
            ) {
                Text(
                    text = "Savanna",
                    fontSize = 25.sp,
                    lineHeight = 27.sp,
                    fontFamily = FontFamily(Font(R.font.autour_one)),
                    textAlign = TextAlign.Center
                )
            }
        }
}

@Composable
fun GameScreen(
    onClick1: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(45.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Snake Game",
            modifier = modifier,
            fontSize = 66.5.sp,
            fontWeight = FontWeight.ExtraBold,
            fontStyle = FontStyle.Italic,
            lineHeight = 71.sp,
            textAlign = TextAlign.Center,
            fontFamily = FontFamily(Font(R.font.gamja_flower)),
        )

        Spacer(modifier = Modifier.height(151.dp))

        Button(
            onClick = { onClick1() },
            modifier = modifier.fillMaxWidth().padding(start = 17.dp, end = 17.dp),
            shape = CutCornerShape(
                bottomStart = 13.dp,
                topEnd = 13.dp,
                topStart = 5.dp,
                bottomEnd = 5.dp
            ),
            elevation = ButtonDefaults.buttonElevation(
                defaultElevation = 10.dp,
                pressedElevation = 5.dp,
            )
        ) {
            Text(
                text = "Play the Game",
                fontSize = 25.sp,
                lineHeight = 27.sp,
                fontFamily = FontFamily(Font(R.font.autour_one)),
                textAlign = TextAlign.Center
            )
        }
        Spacer(modifier = Modifier.height(87.dp))

/*        Button(
            onClick = { onClick2() },
            modifier = modifier.fillMaxWidth().padding(start = 17.dp, end = 17.dp),
            shape = CutCornerShape(
                bottomStart = 13.dp,
                topEnd = 13.dp,
                topStart = 5.dp,
                bottomEnd = 5.dp
            ),
            elevation = ButtonDefaults.buttonElevation(
                defaultElevation = 10.dp,
                pressedElevation = 5.dp,
            )
        ) {
            Text(
                text = "High Scores",
                fontSize = 25.sp,
                lineHeight = 27.sp,
                fontFamily = FontFamily(Font(R.font.autour_one)),
                textAlign = TextAlign.Center
            )
        }*/
    }
}

@Composable
@Preview
fun LevelsScreenPreview(){
    MyApplicationTheme {
        LevelsScreen({}, {} ,{})
    }
}

@Composable
@Preview
fun GameScreenPreview(){
    MyApplicationTheme {
        GameScreen(
            {},
        )
    }
}