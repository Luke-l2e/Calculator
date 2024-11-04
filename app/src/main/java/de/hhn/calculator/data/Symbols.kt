package de.hhn.calculator.data

import de.hhn.calculator.R

data class Symbols(
    val NULL : Int = R.drawable.resource_null,
    var addition : Int = R.drawable.baseline_add_24,
    var subtraction : Int = R.drawable.baseline_horizontal_rule_24,
    var multiplication : Int = R.drawable.baseline_clear_24,
    var division : Int = R.drawable.slash_fat,
    var clear : Int = R.drawable.clear,
    var equalSign : Int = R.drawable.equal_sign,
    var pi : Int = R.drawable.pi,
    var factorial : Int = R.drawable.factorial,
    var squareRoot : Int = R.drawable.squareroot,
    var power : Int = R.drawable.power,
    var openRandom : Int = R.drawable.random_open,
    var emptyRandom : Int = R.drawable.random_empty,
    var useRandom : Int = R.drawable.random_use,
    var numberX : Int = R.drawable.x,
    var numberY : Int = R.drawable.y,
    var redraw : Int = R.drawable.redraw
)
