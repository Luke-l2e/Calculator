package de.hhn.calculator.data


data class Values(
    private var symbols: Symbols = Symbols(),
    val vibrationShort: Long = 15,
    val vibrationLong: Long = 250,
    var numberX: String = "",
    var numberY: String = "",
    var operator: Int = symbols.NULL,
    var result: String = ""
)
