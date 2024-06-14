package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.example.myapplication.ui.theme.easy.Screen
import com.example.myapplication.ui.theme.medium.MediumScreen
import com.example.myapplication.ui.theme.navigation.SnakeGame

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
//                    MediumScreen(navBack = {})
//                    DifficultScreen()
//                    Screen(navBack = {})
                    SnakeGame(navController = rememberNavController())
                }
            }
        }
    }
}

@Composable
fun GameOverDialogue(
    text: String,
    onClose: () -> Unit,
//    onRetry: ()-> Unit,
    modifier: Modifier = Modifier
) {

    AlertDialog(
        onDismissRequest = {
            onClose()
        },
        title = { Text(text = text,
            textAlign = TextAlign.Justify,
            fontSize = 17.sp)
        },
        modifier = modifier,
        confirmButton = {
            TextButton(onClick = {
                onClose()
            }) {
                Text(text = "Go Back")
            }
        },
        /*confirmButton = {
            TextButton(onClick = {
                onRetry()
            }) {
                Text(text = "Retry")
            }
        }*/
    )
}

//@Preview(showBackground = true)
//@Composable
//fun GreetingPreview() {
//    MyApplicationTheme {
////        Greeting("Android")
//    }
//}