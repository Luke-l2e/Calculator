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

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CalculatorTheme {

                val BACKGROUND_COLOR = Color.DarkGray
                var numberX by remember {
                    mutableStateOf("")
                }
                var numberY by remember {
                    mutableStateOf("")
                }
                var operator by remember {
                    mutableStateOf("")
                }
                var result by remember {
                    mutableStateOf("")
                }

                // A surface container using the 'background' color from the theme
                Column(
                    Modifier
                        .fillMaxSize()
                        .background(BACKGROUND_COLOR)
                        .padding(20.dp),

                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,

                    )
                {

                    Box(
                        modifier = Modifier
                            .fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        TextField(
                            value = numberX,
                            onValueChange = {
                                numberX = it
                            },
                            Modifier
                                .fillMaxWidth(),
                            singleLine = true
                            //placeholder = "Test"
                        )
                    }
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(),
                        contentAlignment = Alignment.Center,
                    ) {
                        Text(text = operator, color = Color.White)
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
                        Text(color = Color.White, text = result)
                    }
                    Row(
                        modifier = Modifier.padding(5.dp)
                    ) {
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
                    Row {

                        Button(onClick = {
                            if (numberX.isEmpty()) {
                                numberX = Math.PI.toString()
                            } else if (numberY.isEmpty()) {
                                numberY = Math.PI.toString()
                            } else {
                                numberX = Math.PI.toString()
                            }

                        }) {
                            Text("Pi")
                        }

                    }
                    Row {
                        var x: Double
                        var y: Double
                        val context = LocalContext.current
                        Button(
                            onClick = {
                                if (numberX.isEmpty()) {
                                    Toast.makeText(
                                        context,
                                        "Error - First number is missing",
                                        Toast.LENGTH_LONG
                                    ).show()
                                    return@Button
                                } else if (numberY.isEmpty()) {
                                    Toast.makeText(
                                        context,
                                        "Error - Second number is missing",
                                        Toast.LENGTH_LONG
                                    )
                                        .show()
                                    return@Button
                                } else if (operator.isEmpty()) {
                                    Toast.makeText(
                                        context,
                                        "Error - Please select an operator",
                                        Toast.LENGTH_LONG
                                    )
                                        .show()
                                    return@Button
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
                                    return@Button
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
                                    return@Button
                                }

                                if (operator == "+") {
                                    result = (x + y).toString()
                                } else if (operator == "-") {
                                    result = (x - y).toString()
                                } else if (operator == "*") {
                                    result = (x * y).toString()
                                } else {
                                    result = (x / y).toString()
                                }

                            },
                            Modifier
                                .padding(vertical = 20.dp)

                        ) {
                            Text(text = "=")
                        }

                        // result = solve(numberX, numberY, operator)
                    }


                }


            }
        }
    }


}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Greeting() {
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
        Greeting()
    }
}