package de.hhn.calculator

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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import de.hhn.calculator.ui.theme.CalculatorTheme
import java.text.DecimalFormat

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CalculatorTheme {
                var colors by remember {
                    mutableStateOf(Colors())
                }
                var operators by remember {
                    mutableStateOf(Operators())
                }
                var textFieldValues by remember { mutableStateOf(TextFieldValues()) }
                val context = LocalContext.current
                val buttonColors = ButtonDefaults.buttonColors(
                    containerColor = colors.item
                )
                val buttonModifier: Modifier = Modifier
                Column(
                    Modifier
                        .fillMaxSize()
                        .background(colors.background)
                        .padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Bottom,
                )
                {
                    TextField(
                        value = textFieldValues.numberX,
                        onValueChange = {
                            if (it.isBlank()) {
                                textFieldValues = textFieldValues.copy(numberX = "")
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
                            textFieldValues = textFieldValues.copy(numberX = it)
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
                            containerColor = colors.background,
                            unfocusedIndicatorColor = colors.item,
                            focusedIndicatorColor = colors.focus
                        )
                    )
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        // TODO : Workaround instead of 0.    id = 0 -> crash
                        if (textFieldValues.operator != operators.EMPTY) {
                            Icon(
                                painter = painterResource(id = textFieldValues.operator),
                                contentDescription = "Multiplication",
                                tint = colors.font
                            )
                        } else {
                            Icon(
                                painter = painterResource(id = operators.addition),
                                contentDescription = "Multiplication",
                                tint = colors.font,
                                modifier = Modifier.alpha(0.0f)
                            )
                        }

                    }


//                    Text(
//                        text = textFieldValues.operator.toString(),
//                        color = Color.White,
//                        fontSize = 9.em,
//                        textAlign = TextAlign.Center,
//                    )

                    TextField(
                        value = textFieldValues.numberY,
                        onValueChange = {
                            if (it.isBlank()) {
                                textFieldValues = textFieldValues.copy(numberY = "")
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
                            textFieldValues = textFieldValues.copy(numberY = it)
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
                            containerColor = colors.background,
                            unfocusedIndicatorColor = colors.item,
                            focusedIndicatorColor = colors.focus
                        )
                    )

                    TextField(
                        value = textFieldValues.result,
                        onValueChange = {},
                        Modifier
                            .fillMaxWidth(),
                        singleLine = true,
                        readOnly = true,
                        textStyle = TextStyle(
                            color = Color.White,
                            textAlign = TextAlign.Right,
                            fontSize = 10.em
                        ),
                        colors = TextFieldDefaults.textFieldColors(
                            containerColor = colors.background,
                            unfocusedIndicatorColor = colors.background,
                            focusedIndicatorColor = colors.background
                        )
                    )

                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(105.dp)
                    ) {
                        Button(
                            onClick = {
                                textFieldValues = TextFieldValues()
                            },
                            buttonModifier,
                            shape = CircleShape,
                            colors = buttonColors
                        ) {
                            // TODO : Class operators to Symbols
                            Icon(
                                painter = painterResource(id = R.drawable.baseline_restart_alt_24),
                                contentDescription = "Clear content",
                                tint = colors.font
                            )
                        }
                        Button(
                            onClick = {
                                textFieldValues =
                                    textFieldValues.copy(operator = operators.addition)
                            },
                            buttonModifier,
                            shape = CircleShape,
                            colors = buttonColors
                        ) {
                            Icon(
                                painter = painterResource(id = operators.addition),
                                contentDescription = "Addition",
                                tint = colors.font
                            )
                        }
                        Button(
                            onClick = {
                                textFieldValues =
                                    textFieldValues.copy(operator = operators.subtraction)
                            },
                            buttonModifier,
                            colors = buttonColors
                        ) {
                            Icon(
                                painter = painterResource(id = operators.subtraction),
                                contentDescription = "Subtraction",
                                tint = colors.font
                            )
                        }
                        Button(
                            onClick = {
                                textFieldValues =
                                    textFieldValues.copy(operator = operators.multiplication)
                            },
                            buttonModifier,
                            colors = buttonColors
                        ) {
                            Icon(
                                painter = painterResource(id = operators.multiplication),
                                contentDescription = "Multiplication",
                                tint = colors.font
                            )
                        }
                        Button(
                            onClick = {
                                textFieldValues =
                                    textFieldValues.copy(operator = operators.division)
                            },
                            buttonModifier,
                            colors = buttonColors
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.slash_svgrepo_com),
                                contentDescription = "Multiplication",
                                tint = colors.font
                            )
                        }
                    }
                    Row {

                        Button(
                            onClick = {
                                if (textFieldValues.numberX.isEmpty()) {
                                    textFieldValues =
                                        textFieldValues.copy(numberX = Math.PI.toString())
                                } else if (textFieldValues.numberY.isEmpty()) {
                                    textFieldValues =
                                        textFieldValues.copy(numberY = Math.PI.toString())
                                } else {
                                    textFieldValues =
                                        textFieldValues.copy(numberX = Math.PI.toString())
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
                                if (textFieldValues.numberX.isEmpty()) {
                                    Toast.makeText(
                                        context,
                                        "Error - First number is missing",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    return@Button
                                } else if (textFieldValues.numberY.isEmpty()) {
                                    Toast.makeText(
                                        context,
                                        "Error - Second number is missing",
                                        Toast.LENGTH_SHORT
                                    )
                                        .show()
                                    return@Button
                                } else if (textFieldValues.operator == operators.EMPTY) {
                                    Toast.makeText(
                                        context,
                                        "Error - Please select an operator",
                                        Toast.LENGTH_SHORT
                                    )
                                        .show()
                                    return@Button
                                }

                                try {
                                    x = textFieldValues.numberX.toDouble()
                                } catch (e: NumberFormatException) {
                                    Toast.makeText(
                                        context,
                                        "Error - First input contains an unknown input",
                                        Toast.LENGTH_SHORT
                                    )
                                        .show()
                                    return@Button
                                }
                                try {
                                    y = textFieldValues.numberY.toDouble()
                                } catch (e: NumberFormatException) {
                                    Toast.makeText(
                                        context,
                                        "Error - Second input contains an unknown input",
                                        Toast.LENGTH_SHORT
                                    )
                                        .show()
                                    return@Button
                                }
//  TODO : Rewrite this entire part
                                var resultValue: Double
                                if (textFieldValues.operator == operators.addition) {
                                    resultValue = (x + y)
                                } else if (textFieldValues.operator == operators.subtraction) {
                                    resultValue = (x - y)
                                } else if (textFieldValues.operator == operators.multiplication) {
                                    resultValue = (x * y)
                                } else {
                                    if (y == 0.0) {
                                        Toast.makeText(
                                            context,
                                            "Error - Division with Zero is undefined",
                                            Toast.LENGTH_SHORT
                                        )
                                            .show()
                                        return@Button
                                    }
                                    resultValue = (x / y)
                                }
                                if (resultValue.isNaN()) {
                                    println("NaN ------------")
                                } else if (resultValue == Double.NEGATIVE_INFINITY) {
                                    println("Negative Inifinity ------------")
                                    Toast.makeText(
                                        context,
                                        "Result exceeds the negative boundary",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                } else if (resultValue == Double.POSITIVE_INFINITY) {
                                    println("Positive Inifinity ------------")
                                    Toast.makeText(
                                        context,
                                        "Result exceeds the positive boundary",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                } else {
                                    println("-------------- Nothing")
                                    val decimalFormat = DecimalFormat("###.#")
                                    textFieldValues =
                                        textFieldValues.copy(
                                            result = decimalFormat.format(
                                                resultValue
                                            )
                                        )
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