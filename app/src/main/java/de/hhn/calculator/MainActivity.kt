package de.hhn.calculator

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Vibrator
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
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
import de.hhn.calculator.data.Utilities
import de.hhn.calculator.data.Values
import de.hhn.calculator.ui.theme.CalculatorTheme
import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.sqrt

@Suppress("DEPRECATION")
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
                        verticalArrangement = Arrangement.SpaceBetween,
                    )
                    {
                        Box(
                            Modifier
                                .fillMaxSize()
                                .padding(bottom = 40.dp)) {
                            Image(
                                imageVector = ImageVector.vectorResource(symbols.openRandom),
                                contentDescription = "Random Number Generator",
                                modifier = Modifier
                                    .padding(top = 40.dp, bottom = 0.dp)
                                    .scale(3f)
                                    .offset(x = 3.dp)
                                    .clip(RoundedCornerShape(30))
                                    .clickable {
                                        startRandomNumberGenerator(vibrator, values, context)
                                    }
                                    .align(Center),
                                alignment = Center
                            )
                            Text(
                                text = "Random Number\nGenerator",
                                color = colors.font,
                                textAlign = TextAlign.Center,
                                modifier = Modifier
                                    .padding(bottom = 20.dp)
                                    .alpha(0.5f)
                                    .align(Center)
                            )
                        }
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            horizontalAlignment = CenterHorizontally,
                            verticalArrangement = Arrangement.Bottom,
                        ) {
                            TextField(
                                value = values.numberX,
                                onValueChange = {
                                    values =
                                        values.copy(numberX = Utilities.validateValue(it, context))
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
                                    values =
                                        values.copy(numberY = Utilities.validateValue(it, context))
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
                            Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column(
                                Modifier.fillMaxSize(),
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
                                            Utilities.vibrate(vibrator, values.vibrationShort)
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
                                            Utilities.vibrate(vibrator, values.vibrationShort)
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
                                            Utilities.vibrate(vibrator, values.vibrationShort)
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
                                            Utilities.vibrate(vibrator, values.vibrationShort)
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
                                            Utilities.vibrate(vibrator, values.vibrationShort)
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
                                            Utilities.vibrate(vibrator, values.vibrationShort)
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
                                            Utilities.vibrate(vibrator, values.vibrationShort)
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
                                            Utilities.vibrate(vibrator, values.vibrationShort)
                                            values = if (values.numberX.isEmpty()) {
                                                values.copy(numberX = Math.PI.toString())
                                            } else {
                                                if (values.numberY.isEmpty()) {
                                                    values.copy(numberY = Math.PI.toString())
                                                } else {
                                                    values.copy(numberX = Math.PI.toString())
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
                            }
                            Row(
                                Modifier
                                    .fillMaxWidth()
                                    .padding(top = 20.dp),
                                horizontalArrangement = Arrangement.SpaceAround
                            ) {
                                Button(
                                    onClick = {
                                        Utilities.vibrate(vibrator, values.vibrationShort)
                                        values = Values()
                                    },
                                    buttonModifier.width(100.dp),
                                    shape = CircleShape,
                                    colors = buttonColors
                                ) {
                                    Icon(
                                        painter = painterResource(id = symbols.clear),
                                        contentDescription = "Clear",
                                        tint = colors.font,
                                        modifier = Modifier.scale(1.5f)
                                    )
                                }
                                Button(
                                    onClick = {
                                        values = values.copy(
                                            result = validateAndCalculateBasicResult(
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
            Utilities.showToast(context, "Please enter a valid integer for x.")
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
        return when (resultValue) {
            Double.NEGATIVE_INFINITY -> {
                Utilities.showToast(context, "Result exceeds the negative boundary")
                ""
            }

            Double.POSITIVE_INFINITY -> {
                Utilities.showToast(context, "Result exceeds the positive boundary")
                ""
            }

            else -> {
                resultValue.toString()
            }
        }
    }

    private fun calculatePower(values: Values, context: Context): String {
        if (values.numberX.isEmpty() || values.numberX == "-") {
            Utilities.showToast(context, "Please enter a valid first number.")
            return ""
        }
        if (values.numberY.isEmpty() || values.numberY == "-") {
            Utilities.showToast(context, "Please enter valid second number.")
            return ""
        }
        return when (val resultValue = values.numberX.toDouble().pow(values.numberY.toDouble())) {
            Double.NEGATIVE_INFINITY -> {
                Utilities.showToast(context, "Result exceeds the negative boundary")
                ""
            }

            Double.POSITIVE_INFINITY -> {
                Utilities.showToast(context, "Result exceeds the positive boundary")
                ""
            }

            else -> {
                resultValue.toString()
            }
        }
    }


    private fun validateAndCalculateBasicResult(
        vibrator: Vibrator,
        values: Values,
        context: Context,
        symbols: Symbols
    ): String {
        val x: Double
        val y: Double
        Utilities.vibrate(vibrator, values.vibrationShort)
        if (values.numberX.isEmpty() || values.numberX == "-") {
            Utilities.vibrate(vibrator, values.vibrationLong)
            Utilities.showToast(context, "First number is missing")
            return ""
        } else if (values.numberY.isEmpty() || values.numberY == "-") {
            Utilities.vibrate(vibrator, values.vibrationLong)
            Utilities.showToast(context, "Second number is missing")
            return ""
        } else if (!values.basicOperators.contains(values.operator)) {
            Utilities.vibrate(vibrator, values.vibrationLong)
            Utilities.showToast(context, "Please select a basic operator")
            return ""
        }

        try {
            x = values.numberX.toDouble()
        } catch (e: NumberFormatException) {
            Utilities.vibrate(vibrator, values.vibrationLong)
            Utilities.showToast(context, "First number is invalid")
            return ""
        }
        try {
            y = values.numberY.toDouble()
        } catch (e: NumberFormatException) {
            Utilities.vibrate(vibrator, values.vibrationLong)
            Utilities.showToast(context, "Second number is invalid")
            return ""
        }
        val resultValue: Double = when (values.operator) {
            symbols.addition -> (x + y)
            symbols.subtraction -> (x - y)
            symbols.multiplication -> (x * y)
            else -> {
                if (y == 0.0) {
                    Utilities.vibrate(vibrator, values.vibrationLong)
                    Utilities.showToast(context, "Division by Zero is undefined")
                    return ""
                }
                (x / y)
            }
        }

        return when (resultValue) {
            Double.NEGATIVE_INFINITY -> {
                Utilities.showToast(context, "Result exceeds the negative boundary")
                ""
            }

            Double.POSITIVE_INFINITY -> {
                Utilities.showToast(context, "Result exceeds the positive boundary")
                ""
            }

            else -> {
                resultValue.toString()
            }
        }
    }

    private fun startRandomNumberGenerator(
        vibrator: Vibrator,
        values: Values,
        context: Context
    ) {
        Utilities.vibrate(vibrator, values.vibrationShort)
        val randomNumberGenerator =
            Intent(context, RandomNumberGenerator::class.java)
        randomNumberGenerator.putExtra("numberX", values.numberX)
        randomNumberGenerator.putExtra("numberY", values.numberY)
        context.startActivity(randomNumberGenerator)
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
        return if (values.numberX.isEmpty() || values.numberX.startsWith("-")) {
            if (values.numberY.isEmpty() || values.numberY.startsWith("-")) {
                Utilities.showToast(context, "Please enter a positive number.")
                ""
            } else {
                sqrt(values.numberY.toDouble()).toString()
            }
        } else {
            sqrt(values.numberX.toDouble()).toString()
        }
    }
}
