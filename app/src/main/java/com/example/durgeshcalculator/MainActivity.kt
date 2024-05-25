package com.example.durgeshcalculator

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.durgeshcalculator.databinding.ActivityMainBinding
import com.example.durgeshcalculator.extensions.compareLast
import com.example.durgeshcalculator.extensions.lastAnSameSymbol
import com.google.android.material.button.MaterialButton
import com.google.android.material.snackbar.Snackbar
import net.objecthunter.exp4j.Expression
import net.objecthunter.exp4j.ExpressionBuilder
import android.text.SpannableString
import android.text.Spanned
import android.text.style.StyleSpan
import android.text.style.TextAppearanceSpan
import android.util.TypedValue
import android.content.Context
import android.content.res.Resources
import android.graphics.Typeface

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        bindClicks()
    }

    private fun bindClicks() {
        binding.apply {
            btClear.setOnClickListener {
                clear()
            }

            btOne.setOnClickListener {
                type(it)
            }

            btTwo.setOnClickListener {
                type(it)
            }

            btThree.setOnClickListener {
                type(it)
            }

            btFour.setOnClickListener {
                type(it)
            }

            btFive.setOnClickListener {
                type(it)
            }

            btSix.setOnClickListener {
                type(it)
            }

            btSeven.setOnClickListener {
                type(it)
            }

            btEight.setOnClickListener {
                type(it)
            }

            btNine.setOnClickListener {
                type(it)
            }

            btZero.setOnClickListener {
                type(it)
            }

            btMinus.setOnClickListener {
                arithmeticClick(it)
            }

            btPlus.setOnClickListener {
                arithmeticClick(it)

            }

            btDivide.setOnClickListener {
                arithmeticClick(it)
            }

            btMultiply.setOnClickListener {
                arithmeticClick(it)
            }

            btPercent.setOnClickListener {
                percentage(it)
            }

            btParentheses.setOnClickListener {
                toggleParentheses(it)
            }

//            btBack.setOnClickListener {
//                backspace()
//            }

            btEqual.setOnClickListener {
                calculate()
            }

            btDot.setOnClickListener {
                dotClick(it)
            }
        }
    }


    private fun percentage(view: View) {
        val button = view as MaterialButton
        binding.apply {
            val currentText = etDisplay.text.toString()
            if (currentText.isNotEmpty() && !currentText.lastAnSameSymbol()) {
                if (button.text.toString() == "%") {
                    // Check if the last character is a number and not a symbol
                    if (currentText.last().isDigit()) {
                        // Convert the current number to a percentage
                        val number = currentText.toDouble()
                        val percentage = number / 100
                        etDisplay.setText(percentage.toString())
                    }
                } else if (!button.text.toString().compareLast(currentText)) {
                    type(button)
                }
            }
        }
    }


    private fun dotClick(view: View) {
        val button = view as MaterialButton
        binding.apply {
            if (etDisplay.text.isNotEmpty() && !etDisplay.text.toString().lastAnSameSymbol()) {
                if (!button.text.toString().compareLast(etDisplay.text.toString())) {
                    type(button)
                }
            }
        }
    }
    fun getBoldString(input: String): SpannableString {
        val spannableString = SpannableString(input)
        spannableString.setSpan(
            StyleSpan(Typeface.BOLD),
            0,
            input.length,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        return spannableString
    }


    private fun calculate() {
        binding.apply {
            val txt: String = operand + currentInput
            val expression: Expression = ExpressionBuilder(txt).build()
            try {
                val result: Double = expression.evaluate()
                // Format the result to 6 decimal places
                val formattedResult = String.format("%.6f", result).trimEnd('0').trimEnd('.')
                etDisplay.text = getBoldString(formattedResult)
                // Reset operand and current input after calculation
                operand = ""
                currentInput = formattedResult
            } catch (arithmeticException: ArithmeticException) {
                etDisplay.text = arithmeticException.message
            } catch (illegalArgumentException: IllegalArgumentException) {
                etDisplay.text = illegalArgumentException.message
            }
        }
    }


    private fun clear() {
        currentInput = ""
        operator = null
        operand = ""
        binding.etDisplay.text = getBoldString("0")
    }

//    private fun notImplemented(it: View) =
//        Snackbar.make(it, "Not implemented yet!", Snackbar.LENGTH_SHORT).show()

    private fun toggleParentheses(it: View) {
        val text = binding.etDisplay.text.toString()

        // Check if the text is not empty
        if (text.isNotEmpty()) {
            // Try to parse the text to a number
            try {
                val number = text.toDouble()
                // Toggle the sign of the number
                val toggledNumber = -number
                // Update the display with the toggled number
                binding.etDisplay.text = toggledNumber.toString()
            } catch (e: NumberFormatException) {
                // Handle the exception if the text is not a valid number
                // Optional: Show a toast or snackbar to indicate invalid input
            }
        }
    }



    private fun backspace() {
        binding.etDisplay.text = binding.etDisplay.text.dropLast(1)
    }

    private fun arithmeticClick(it: View?) {
        val button = it as MaterialButton
        binding.apply {
            if (etDisplay.text.isNotEmpty() && etDisplay.text.toString().lastAnSameSymbol()) {
                if (!button.text.toString().compareLast(binding.etDisplay.text.toString())) {
                    etDisplay.text = etDisplay.text.toString().dropLast(1)
                    type(button)
                }
            } else {
                type(button)
            }
        }
    }


    // this is the test to make it's working more close the iphone
    private var currentInput = ""
    private var operator: Char? = null
    private var operand: String = ""

    private fun type(view: View) {
        val button = view as MaterialButton
        val inputText = button.text.toString()

        // Only handle numbers and update the display
        if (inputText.toDoubleOrNull() != null) {
            currentInput += inputText
            binding.etDisplay.text = getBoldString(currentInput)
        } else {
            // Handle operators
            when (inputText) {
                "+", "-", "*", "/" -> {
                    if (currentInput.isNotEmpty()) {
                        operand += currentInput + inputText
                        currentInput = ""
                    }
                }
            }
        }
    }

}