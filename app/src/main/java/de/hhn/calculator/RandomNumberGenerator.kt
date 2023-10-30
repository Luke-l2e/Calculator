package de.hhn.calculator

import android.content.Context
import android.os.Bundle
import android.os.Vibrator
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import de.hhn.calculator.data.Colors
import de.hhn.calculator.data.Symbols
import de.hhn.calculator.data.Values
import de.hhn.calculator.ui.theme.CalculatorTheme
import kotlin.random.Random

class RandomNumberGenerator : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CalculatorTheme {
                val context = LocalContext.current
                val colors by remember {
                    mutableStateOf(Colors())
                }
                val symbols by remember {
                    mutableStateOf(Symbols())
                }
                var values by remember {
                    mutableStateOf(
                        Values(
                            numberX = intent.getStringExtra("numberX").toString(),
                            numberY = intent.getStringExtra("numberY").toString(),
                            result = calculateRandomNumber(
                                context,
                                intent.getStringExtra("numberX").toString(),
                                intent.getStringExtra("numberY").toString()
                            )
                        )
                    )
                }
                val buttonColors = ButtonDefaults.buttonColors(
                    containerColor = colors.item
                )
                val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
                val activity = (LocalLifecycleOwner.current as ComponentActivity)

                Column(
                    Modifier
                        .fillMaxSize()
                        .background(colors.background)
                        .verticalScroll(rememberScrollState())
                )
                {
                    TopAppBar(
                        title = {
                            Text("Random", Modifier.fillMaxWidth(), fontSize = 10.em)
                        },
                        navigationIcon = {
                            IconButton(onClick = { activity.finish() }) {
                                Icon(Icons.Filled.ArrowBack, "Head back")
                            }
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
                            .padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.SpaceEvenly,
                    ) {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
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
                        Button(
                            onClick = {
                                vibrate(vibrator, values.vibrationShort)
                                values = values.copy(
                                    result = calculateRandomNumber(
                                        context,
                                        values.numberX,
                                        values.numberY
                                    )
                                )
                            },
                            Modifier.width(100.dp),
                            colors = buttonColors,
                        )
                        {
                            Icon(
                                painter = painterResource(id = symbols.redraw),
                                contentDescription = "Redraw",
                                tint = colors.font
                            )
                        }
                    }
                }
            }
        }
    }

    private fun calculateRandomNumber(context: Context, numberX: String, numberY: String): String {
        if (numberX.isEmpty() || numberX == "-") {
            showToast(context, "First number is missing")
            return ""
        } else if (numberY.isEmpty() || numberY == "-") {
            showToast(context, "Second number is missing")
            return ""
        }
        val valueX = numberX.toDouble()
        val valueY = numberY.toDouble()
        if (valueX == valueY) {
            showToast(context, "The numbers are identical")
            return ""
        }
        return if (valueX < valueY) {
            Random.nextDouble(valueX, valueY).toString()
        } else {
            Random.nextDouble(valueY, valueX).toString()
        }
    }


    @Composable
    private fun CreatePlaceHolderText() {
        Text(
            text = "Please enter a number",
            color = Color.White,
            modifier = Modifier.alpha(0.5F)
        )
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

    private fun showToast(context: Context, text: String) {
        Toast.makeText(
            context,
            text,
            Toast.LENGTH_SHORT
        )
            .show()
    }

    private fun vibrate(vibrator: Vibrator, duration: Long) {
        vibrator.cancel()
        vibrator.vibrate(duration)
    }
}