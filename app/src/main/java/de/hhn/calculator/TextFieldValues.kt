package de.hhn.calculator

data class TextFieldValues(
    private var operators: Operators = Operators(),
    var numberX: String = "",
    var numberY: String = "",
    var operator: Int = operators.EMPTY,
    var result: String = ""
)
