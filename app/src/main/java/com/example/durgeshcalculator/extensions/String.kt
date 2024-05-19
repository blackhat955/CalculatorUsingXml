package com.example.durgeshcalculator.extensions

fun String.compareLast(currentText: String): Boolean =
    (this.last() == currentText.last())


fun String.lastAnSameSymbol(): Boolean =
    (this.last().toString().isArithmeticSymbol())

fun String.isArithmeticSymbol(): Boolean =
    (this == "+" || this == "-" || this == "*" || this == "/")