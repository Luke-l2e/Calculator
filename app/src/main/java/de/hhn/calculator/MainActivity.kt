package de.hhn.calculator

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import de.hhn.calculator.ui.theme.CalculatorTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CalculatorTheme {
                val COLOR_BACKGROUND = Color.DarkGray
                val COLOR_ITEM = Color(4, 121, 201)
                val COLOR_FOCUS = Color(255, 140, 0)

                val context = LocalContext.current
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
                val buttonColors = ButtonDefaults.buttonColors(
                    containerColor = COLOR_ITEM
                )
                val buttonModifier: Modifier = Modifier

                // A surface container using the 'background' color from the theme
                Column(
                    Modifier
                        .fillMaxSize()
                        .background(COLOR_BACKGROUND)
                        .padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Bottom,
                )
                {
                    TextField(
                        value = numberX,
                        onValueChange = {
                            if (it.isBlank()) {
                                numberX = ""
                                return@TextField
                            }
                            var value: Double
                            try {
                                value = it.toDouble()
                            } catch (e: NumberFormatException) {
                                return@TextField
                            }
                            if (value == Double.NEGATIVE_INFINITY || value == Double.POSITIVE_INFINITY) {
                                Toast.makeText(context, "Value Limit reached", Toast.LENGTH_SHORT)
                                    .show()
                                return@TextField
                            }
                            numberX = it
                        },
                        Modifier
                            .fillMaxWidth(),
                        singleLine = true,
                        textStyle = TextStyle(color = Color.White),
                        placeholder = {
                            Text(
                                text = "Please enter a number",
                                color = Color.White,
                                modifier = Modifier.alpha(0.5F)
                            )
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                        colors = TextFieldDefaults.textFieldColors(
                            containerColor = COLOR_BACKGROUND,
                            unfocusedIndicatorColor = COLOR_ITEM,
                            focusedIndicatorColor = COLOR_FOCUS
                        )
                    )

                    Text(
                        text = operator,
                        color = Color.White,
                        fontSize = 14.em,
                        textAlign = TextAlign.Center,
                    )

                    TextField(
                        value = numberY,
                        onValueChange = {
                            if (it.isBlank()) {
                                numberY = ""
                                return@TextField
                            }
                            var value: Double
                            try {
                                value = it.toDouble()
                            } catch (e: NumberFormatException) {
                                return@TextField
                            }
                            if (value == Double.NEGATIVE_INFINITY || value == Double.POSITIVE_INFINITY) {
                                Toast.makeText(context, "Value Limit reached", Toast.LENGTH_SHORT)
                                    .show()
                                return@TextField
                            }
                            numberY = it
                        },
                        Modifier
                            .fillMaxWidth(),
                        shape = RoundedCornerShape(25),
                        placeholder = {
                            Text(
                                text = "Please enter a number",
                                color = Color.White,
                                modifier = Modifier.alpha(0.5F)
                            )
                        },
                        singleLine = true,
                        textStyle = TextStyle(color = Color.White),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                        colors = TextFieldDefaults.textFieldColors(
                            containerColor = COLOR_BACKGROUND,
                            unfocusedIndicatorColor = COLOR_ITEM,
                            focusedIndicatorColor = COLOR_FOCUS
                        )
                    )

                    Text(
                        text = result,
                        color = Color.White,
                        fontSize = 12.em,
                        textAlign = TextAlign.Start
                    )

                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(105.dp)
                    ) {
                        OutlinedButton(
                            onClick = {
                                numberX = ""
                                numberY = ""
                                operator = ""
                                result = ""
                            },
                            buttonModifier,
                            border = BorderStroke(5.dp, COLOR_ITEM),
                            colors = buttonColors,
                            shape = CircleShape
                        ) {
                            Icon(
                                Icons.Default.Clear,
                                contentDescription = "Clear content"
                            )
                        }
                        Button(
                            onClick = {
                                operator = "+"
                            },
                            buttonModifier,
                            shape = CircleShape,
                            colors = buttonColors
                        ) {
                            Icon(
                                Icons.Default.Add,
                                contentDescription = "Addition",
                                tint = Color.White
                            )
                        }
                        Button(
                            onClick = {
                                operator = "-"
                            },
                            buttonModifier,
                            colors = buttonColors
                        ) {
                            Text(text = "-")
                        }
                        Button(
                            onClick = {
                                operator = "*"
                            },
                            buttonModifier,
                            colors = buttonColors
                        ) {
                            Text(text = "*")
                        }
                        Button(
                            onClick = {
                                operator = "/"
                            },
                            buttonModifier,
                            colors = buttonColors
                        ) {
                            Text(text = "/")
                        }
                    }
                    Row {

                        Button(
                            onClick = {
                                if (numberX.isEmpty()) {
                                    numberX = Math.PI.toString()
                                } else if (numberY.isEmpty()) {
                                    numberY = Math.PI.toString()
                                } else {
                                    numberX = Math.PI.toString()
                                }
                            },
                            buttonModifier,
                            colors = buttonColors
                        )
                        {
                            Text("Pi")
                        }

                    }
                    Row {
                        var x: Double
                        var y: Double
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
//  TODO : Rewrite this entire part
                                var resultValue: Double
                                if (operator == "+") {
                                    resultValue = (x + y)
                                } else if (operator == "-") {
                                    resultValue = (x - y)
                                } else if (operator == "*") {
                                    resultValue = (x * y)
                                } else {
                                    resultValue = (x / y)
                                }
                                if (resultValue.isNaN()) {
                                    println("NaN ------------")
                                } else if (resultValue == Double.NEGATIVE_INFINITY) {
                                    println("Negative Inifinity ------------")
                                    Toast.makeText(
                                        context,
                                        "Result exceeds the negative boundary",
                                        Toast.LENGTH_LONG
                                    ).show()
                                } else if (resultValue == Double.POSITIVE_INFINITY) {
                                    println("Positive Inifinity ------------")
                                    Toast.makeText(
                                        context,
                                        "Result exceeds the positive boundary",
                                        Toast.LENGTH_LONG
                                    ).show()
                                } else {
                                    println("-------------- Nothing")
                                    result = resultValue.toString()
                                }
                            },
                            buttonModifier,
                            colors = buttonColors
                        )
                        {
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