package de.hhn.calculator.data

import de.hhn.calculator.R

data class Symbols(
    val NULL : Int = R.drawable.resource_null,
    var addition : Int = R.drawable.baseline_add_24,
    var subtraction : Int = R.drawable.baseline_horizontal_rule_24,
    var multiplication : Int = R.drawable.baseline_clear_24,
    var division : Int = R.drawable.slash_svgrepo_com,
    var clear : Int = R.drawable.baseline_restart_alt_24,
    var equalSign : Int = R.drawable.equal_sign,
    var pi : Int = R.drawable.pi2,
    var factorial : Int = R.drawable.factorial,
    var squareRoot : Int = R.drawable.wurzel,
    var power : Int = R.drawable.pow,
    var openRandom : Int = R.drawable.random_3_,
    var emptyRandom : Int = R.drawable.random_5_,
    var useRandom : Int = R.drawable.random_6_
)
