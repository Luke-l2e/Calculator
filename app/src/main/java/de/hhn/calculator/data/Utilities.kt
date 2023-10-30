package de.hhn.calculator.data

import android.content.Context
import android.widget.Toast

class Utilities {
    fun validateValue(it: String, context: Context): String {
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
    fun showToast(context: Context, text: String) {
        Toast.makeText(
            context,
            text,
            Toast.LENGTH_SHORT
        )
            .show()
    }
}