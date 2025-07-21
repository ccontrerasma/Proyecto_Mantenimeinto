package com.paras_test_android.assignment_paras

import java.math.RoundingMode
import java.text.DecimalFormat

/**
 * Extension function to round off a double to two decimal places
 * Will be used across all activities
 */
fun Double.roundToTwoDecimalPlaces(): Double {
    val df = DecimalFormat("#.##")
    df.roundingMode = RoundingMode.CEILING
    return df.format(this).toDouble()
}
