package de.hhn.calculator

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Vibrator
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import de.hhn.calculator.data.Colors
import de.hhn.calculator.data.Symbols
import de.hhn.calculator.data.Values
import de.hhn.calculator.ui.theme.CalculatorTheme
import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.sqrt

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CalculatorTheme {
                val colors by remember {
                    mutableStateOf(Colors())
                }
                val symbols by remember {
                    mutableStateOf(Symbols())
                }
                var values by remember { mutableStateOf(Values()) }
                val context = LocalContext.current
                val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
                val buttonColors = ButtonDefaults.buttonColors(
                    containerColor = colors.item
                )
                val buttonModifier: Modifier = Modifier
                Column(
                    Modifier
                        .fillMaxSize()
                        .background(colors.background)
                )
                {
                    TopAppBar(
                        title = {
                            Text(
                                "Calculator",
                                Modifier.fillMaxWidth(),
                                fontSize = 10.em,
                                textAlign = TextAlign.Center
                            )
                        },
                        colors = TopAppBarDefaults.largeTopAppBarColors(
                            containerColor = colors.background,
                            titleContentColor = colors.font,
                            navigationIconContentColor = colors.font
                        )
                    )
                    Column(
                        Modifier
                            .fillMaxSize()
                            .background(colors.background)
                            .verticalScroll(rememberScrollState())
                            .padding(20.dp),
                        horizontalAlignment = CenterHorizontally,
                        verticalArrangement = Arrangement.SpaceAround,
                    )
                    {
                        Image(
                            imageVector = ImageVector.vectorResource(symbols.openRandom),
                            contentDescription = "test",
                            modifier = Modifier
                                .scale(3f)
                                .clickable {
                                    startRandomNumberGenerator(vibrator, values, context)
                                }
                                .offset(x = 3.dp)
                        )
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            horizontalAlignment = CenterHorizontally,
                            verticalArrangement = Arrangement.Bottom,
                        ) {
                            TextField(
                                value = values.numberX,
                                onValueChange = {
                                    values = values.copy(numberX = validateValue(it, context))
                                },
                                Modifier
                                    .fillMaxWidth(),
                                singleLine = true,
                                textStyle = TextStyle(color = Color.White),
                                placeholder = {
                                    CreatePlaceHolderText()
                                },
                                leadingIcon = {
                                    Icon(
                                        painter = painterResource(id = symbols.numberX),
                                        contentDescription = "Number X",
                                        tint = colors.font
                                    )
                                },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                                colors = TextFieldDefaults.textFieldColors(
                                    containerColor = colors.background,
                                    unfocusedIndicatorColor = colors.item,
                                    focusedIndicatorColor = colors.focus
                                )
                            )

                            var transparency = 1.0f
                            if (values.operator == symbols.NULL) {
                                transparency = 0.0f
                            }
                            Icon(
                                painter = painterResource(id = values.operator),
                                contentDescription = "Operator",
                                tint = colors.font,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 20.dp)
                                    .align(CenterHorizontally)
                                    .alpha(transparency)
                            )

                            TextField(
                                value = values.numberY,
                                onValueChange = {
                                    values = values.copy(numberY = validateValue(it, context))
                                },
                                Modifier
                                    .fillMaxWidth(),
                                shape = RoundedCornerShape(25),
                                placeholder = {
                                    CreatePlaceHolderText()
                                },
                                leadingIcon = {
                                    Icon(
                                        painter = painterResource(id = symbols.numberY),
                                        contentDescription = "Number Y",
                                        tint = colors.font
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
                                value = values.result,
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
                        }
                        Column(
                            horizontalAlignment = CenterHorizontally
                        ) {
                            Row(
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .fillMaxWidth()
                            ) {
                                Button(
                                    onClick = {
                                        vibrate(vibrator, values.vibrationShort)
                                        values = values.copy(operator = symbols.addition)
                                    },
                                    buttonModifier,
                                    shape = CircleShape,
                                    colors = buttonColors
                                ) {
                                    Icon(
                                        painter = painterResource(id = symbols.addition),
                                        contentDescription = "Addition",
                                        tint = colors.font
                                    )
                                }
                                Button(
                                    onClick = {
                                        vibrate(vibrator, values.vibrationShort)
                                        values = values.copy(operator = symbols.subtraction)
                                    },
                                    buttonModifier,
                                    colors = buttonColors
                                ) {
                                    Icon(
                                        painter = painterResource(id = symbols.subtraction),
                                        contentDescription = "Subtraction",
                                        tint = colors.font
                                    )
                                }
                                Button(
                                    onClick = {
                                        vibrate(vibrator, values.vibrationShort)
                                        values = values.copy(operator = symbols.multiplication)
                                    },
                                    buttonModifier,
                                    colors = buttonColors
                                ) {
                                    Icon(
                                        painter = painterResource(id = symbols.multiplication),
                                        contentDescription = "Multiplication",
                                        tint = colors.font
                                    )
                                }
                                Button(
                                    onClick = {
                                        vibrate(vibrator, values.vibrationShort)
                                        values = values.copy(operator = symbols.division)
                                    },
                                    buttonModifier,
                                    colors = buttonColors
                                ) {
                                    Icon(
                                        painter = painterResource(id = symbols.division),
                                        contentDescription = "Multiplication",
                                        tint = colors.font
                                    )
                                }
                            }
                            Row(
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .fillMaxWidth()
                            ) {
                                Button(
                                    onClick = {
                                        vibrate(vibrator, values.vibrationShort)
                                        values = values.copy(
                                            operator = symbols.factorial,
                                            result = calculateFactorialOfNumberX(
                                                context,
                                                values
                                            )
                                        )
                                    },
                                    buttonModifier,
                                    shape = CircleShape,
                                    colors = buttonColors
                                ) {
                                    Icon(
                                        painter = painterResource(id = symbols.factorial),
                                        contentDescription = "Factorial",
                                        tint = colors.font
                                    )
                                }
                                Button(
                                    onClick = {
                                        vibrate(vibrator, values.vibrationShort)
                                        values = values.copy(
                                            operator = symbols.power,
                                            result = calculatePower(values, context)
                                        )
                                    },
                                    buttonModifier,
                                    shape = CircleShape,
                                    colors = buttonColors
                                )
                                {
                                    Icon(
                                        painter = painterResource(id = symbols.power),
                                        contentDescription = "Power",
                                        tint = colors.font
                                    )
                                }
                                Button(
                                    onClick = {
                                        vibrate(vibrator, values.vibrationShort)
                                        values =
                                            values.copy(
                                                operator = symbols.squareRoot,
                                                result = calculateSquareRootOfNumber(
                                                    values,
                                                    context
                                                )
                                            )
                                    },
                                    buttonModifier,
                                    colors = buttonColors
                                )
                                {
                                    Icon(
                                        painter = painterResource(id = symbols.squareRoot),
                                        contentDescription = "Sqrt",
                                        tint = colors.font
                                    )
                                }
                                Button(
                                    onClick = {
                                        vibrate(vibrator, values.vibrationShort)
                                        if (values.numberX.isEmpty()) {
                                            values = values.copy(numberX = Math.PI.toString())
                                        } else {
                                            if (values.numberY.isEmpty()) {
                                                values = values.copy(numberY = Math.PI.toString())
                                            } else {
                                                values = values.copy(numberX = Math.PI.toString())
                                            }
                                        }
                                    },
                                    buttonModifier,
                                    colors = buttonColors
                                )
                                {
                                    Icon(
                                        painter = painterResource(id = symbols.pi),
                                        contentDescription = "Pi",
                                        tint = colors.font
                                    )
                                }
                            }
                            Row(
                                Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceAround
                            ) {
                                Button(
                                    onClick = {
                                        vibrate(vibrator, values.vibrationShort)
                                        values = Values()
                                    },
                                    buttonModifier.width(100.dp),
                                    shape = CircleShape,
                                    colors = buttonColors
                                ) {
                                    Icon(
                                        painter = painterResource(id = symbols.clear),
                                        contentDescription = "Clear",
                                        tint = colors.font
                                    )
                                }
                                Button(
                                    onClick = {
                                        values = values.copy(
                                            result = validateAndCalculateResult(
                                                vibrator,
                                                values,
                                                context,
                                                symbols
                                            )
                                        )
                                    },
                                    buttonModifier.width(100.dp),
                                    colors = buttonColors,
                                )
                                {
                                    Icon(
                                        painter = painterResource(id = symbols.equalSign),
                                        contentDescription = "Equals",
                                        tint = colors.font
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private fun calculateFactorialOfNumberX(context: Context, values: Values): String {
        val valueX: Int
        try {
            valueX = values.numberX.trim().toInt()
        } catch (e: NumberFormatException) {
            showToast(context, "Please enter a valid integer for x.")
            return ""
        }

        var resultValue = 1.0
        var counter = abs(valueX)
        while (counter != 0) {
            resultValue *= counter
            counter--
        }
        if (valueX < 0) {
            resultValue = -resultValue
        }
        when (resultValue) {
            Double.NEGATIVE_INFINITY -> {
                showToast(context, "Result exceeds the negative boundary")
                return ""
            }

            Double.POSITIVE_INFINITY -> {
                showToast(context, "Result exceeds the positive boundary")
                return ""
            }

            else -> {
                return resultValue.toString()
            }
        }
    }

    private fun calculatePower(values: Values, context: Context): String {
        if (values.numberX.isEmpty() || values.numberX == "-") {
            showToast(context, "Please enter a valid first number.")
            return ""
        }
        if (values.numberY.isEmpty() || values.numberY == "-") {
            showToast(context, "Please enter valid second number.")
            return ""
        }
        val resultValue = values.numberX.toDouble().pow(values.numberY.toDouble())
        when (resultValue) {
            Double.NEGATIVE_INFINITY -> {
                showToast(context, "Result exceeds the negative boundary")
                return ""
            }

            Double.POSITIVE_INFINITY -> {
                showToast(context, "Result exceeds the positive boundary")
                return ""
            }

            else -> {
                return resultValue.toString()
            }
        }
    }


    private fun validateAndCalculateResult(
        vibrator: Vibrator,
        values: Values,
        context: Context,
        symbols: Symbols
    ): String {
        val x: Double
        val y: Double
        val resultValue: Double
        vibrate(vibrator, values.vibrationShort)
        if (values.numberX.isEmpty() || values.numberX == "-") {
            vibrate(vibrator, values.vibrationLong)
            showToast(context, "First number is missing")
            return ""
        } else if (values.numberY.isEmpty() || values.numberY == "-") {
            vibrate(vibrator, values.vibrationLong)
            showToast(context, "Second number is missing")
            return ""
        } else if (!values.basicOperators.contains(values.operator)) {
            vibrate(vibrator, values.vibrationLong)
            showToast(context, "Please select a basic operator")
            return ""
        }

        try {
            x = values.numberX.toDouble()
        } catch (e: NumberFormatException) {
            vibrate(vibrator, values.vibrationLong)
            showToast(context, "First number is invalid")
            return ""
        }
        try {
            y = values.numberY.toDouble()
        } catch (e: NumberFormatException) {
            vibrate(vibrator, values.vibrationLong)
            showToast(context, "Second number is invalid")
            return ""
        }
        resultValue = when (values.operator) {
            symbols.addition -> (x + y)
            symbols.subtraction -> (x - y)
            symbols.multiplication -> (x * y)
            else -> {
                if (y == 0.0) {
                    vibrate(vibrator, values.vibrationLong)
                    showToast(context, "Division by Zero is undefined")
                    return ""
                }
                (x / y)
            }
        }

        when (resultValue) {
            Double.NEGATIVE_INFINITY -> {
                showToast(context, "Result exceeds the negative boundary")
                return ""
            }

            Double.POSITIVE_INFINITY -> {
                showToast(context, "Result exceeds the positive boundary")
                return ""
            }

            else -> {
                return resultValue.toString()
            }
        }
    }

    private fun showToast(context: Context, text: String) {
        Toast.makeText(
            context,
            text,
            Toast.LENGTH_SHORT
        )
            .show()
    }

    private fun startRandomNumberGenerator(
        vibrator: Vibrator,
        values: Values,
        context: Context
    ) {
        vibrate(vibrator, values.vibrationShort)
        val randomNumberGenerator =
            Intent(context, RandomNumberGenerator::class.java)
        randomNumberGenerator.putExtra("numberX", values.numberX)
        randomNumberGenerator.putExtra("numberY", values.numberY)
        context.startActivity(randomNumberGenerator)
    }


    private fun validateValue(it: String, context: Context): String {
        if (it.isBlank()) {
            return ""
        }
        if (it != "-") {
            val value: Double
            try {
                value = it.toDouble()
            } catch (e: NumberFormatException) {
                return it.dropLast(1)
            }
            if (value == Double.NEGATIVE_INFINITY || value == Double.POSITIVE_INFINITY) {
                showToast(context, "Value Limit reached")
                return it.dropLast(1)
            }
        }
        return it
    }

    @Composable
    private fun CreatePlaceHolderText() {
        Text(
            text = "Please enter a number",
            color = Color.White,
            modifier = Modifier.alpha(0.5F)
        )
    }

    private fun calculateSquareRootOfNumber(values: Values, context: Context): String {
        if (values.numberX.isEmpty() || values.numberX.startsWith("-")) {
            if (values.numberY.isEmpty() || values.numberY.startsWith("-")) {
                showToast(context, "Please enter a positive number.")
                return ""
            } else {
                return sqrt(values.numberY.toDouble()).toString()
            }
        } else {
            return sqrt(values.numberX.toDouble()).toString()
        }
    }


    private fun vibrate(vibrator: Vibrator, duration: Long) {
        vibrator.cancel()
        vibrator.vibrate(duration)
    }
}
