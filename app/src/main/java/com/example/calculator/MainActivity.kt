package com.example.calculator

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.calculator.ui.theme.CalculatorTheme
import java.lang.NumberFormatException

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CalculatorTheme {

                var numberX by remember {
                    mutableStateOf("numberX")
                }
                var numberY by remember {
                    mutableStateOf("numberY")
                }
                var operator by remember {
                    mutableStateOf("")
                }
                var result by remember { mutableStateOf("") }

                // A surface container using the 'background' color from the theme
                Column(
                    Modifier
                        .fillMaxSize()
                        .background(Color.Red)
                        .padding(10.dp)
                        .background(Color.Green),

                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,

                    )
                {

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.Magenta),
                        contentAlignment = Alignment.Center
                    ) {
                        TextField(
                            value = numberX,
                            onValueChange = {
                                numberX
                            },
                            Modifier.fillMaxWidth(),
                            singleLine = true,
                            //placeholder = "Test"
                        )
                    }
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = operator)
                    }
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        TextField(
                            value = numberY, onValueChange = { text ->
                                numberY = text
                            }, Modifier.fillMaxWidth(),
                            singleLine = true
                        )
                    }
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = result)
                    }
                    Row() {
                        Button(
                            onClick = {
                                numberX = ""
                                numberY = ""
                                operator = ""
                            },
                            Modifier
                                .padding(vertical = 20.dp)

                        ) {
                            Text(text = "Clear")
                        }
                        Button(
                            onClick = {
                                operator = "+"
                            },
                            Modifier
                                .padding(vertical = 20.dp)

                        ) {
                            Text(text = "+")
                        }
                        Button(
                            onClick = {
                                operator = "-"
                            },
                            Modifier
                                .padding(vertical = 20.dp)

                        ) {
                            Text(text = "-")
                        }
                        Button(
                            onClick = {
                                operator = "*"
                            },
                            Modifier
                                .padding(vertical = 20.dp)

                        ) {
                            Text(text = "*")
                        }
                        Button(
                            onClick = {
                                operator = "/"
                            },
                            Modifier
                                .padding(vertical = 20.dp)

                        ) {
                            Text(text = "/")
                        }
                    }
                    Row() {
                        solve(numberX, numberY, operator)
                    }


                }


            }
        }
    }


}

@Composable
fun solve(numberX: String, numberY: String, operator: String) {
    var x: Double
    var y: Double
    val context = LocalContext.current
    Button(
        onClick = {
            if (numberX.isEmpty()) {
                Toast.makeText(context, "Error - First number is missing", Toast.LENGTH_LONG).show()
            } else if (numberY.isEmpty()) {
                Toast.makeText(context, "Error - Second number is missing", Toast.LENGTH_LONG)
                    .show()
            } else if (operator.isEmpty()) {
                Toast.makeText(context, "Error - Please select an operator", Toast.LENGTH_LONG)
                    .show()
            }

            try {
                x = numberX.toDouble()
            } catch (e: NumberFormatException) {
                Toast.makeText(
                    context,
                    "Error - First input contains an unknown input",
                    Toast.LENGTH_LONG
                )
                    .show()
            }
            try {
                y = numberY.toDouble()
            } catch (e: NumberFormatException) {
                Toast.makeText(
                    context,
                    "Error - Second input contains an unknown input",
                    Toast.LENGTH_LONG
                )
                    .show()
            }
        },
        Modifier
            .padding(vertical = 20.dp)

    ) {
        Text(text = "=")
    }


}

@Composable
fun clear() {

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    var test by remember { mutableStateOf("") }
    TextField(
        value = test,
        onValueChange = { test = it },
        label = { Text("Textfield") },
        placeholder = { Text("Enter Text") }
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CalculatorTheme {
        Greeting("Android")
    }
}