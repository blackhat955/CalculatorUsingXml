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
                notImplemented(it)
            }

            btParentheses.setOnClickListener {
                notImplemented(it)
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
            val txt: String = etDisplay.text.toString()
            val expression: Expression = ExpressionBuilder(txt).build()
            try {
                val result: Double = expression.evaluate()
                etDisplay.text =  getBoldString(result.toString())
            } catch (arithmeticException: ArithmeticException) {
                etDisplay.text = arithmeticException.message
            } catch (illegalArgumentException: IllegalArgumentException) {
                etDisplay.text = illegalArgumentException.message
            }
        }
    }

    private fun clear() {

        binding.etDisplay.text = ""
    }

//    private fun notImplemented(it: View) =
//        Snackbar.make(it, "Not implemented yet!", Snackbar.LENGTH_SHORT).show()

    private fun notImplemented(it: View) {
        // Assuming it is the selected symbol
        binding.btParentheses.text = "+/-"
//        Snackbar.make(it, "working!", Snackbar.LENGTH_SHORT).show()
        val symbol = "+"

        println("Original symbol: $symbol")

        // Reverse the symbol
        val reversedSymbol = when (symbol) {
            "+" -> "-"
            "-" -> "+"
            else -> symbol // Handle other symbols if needed
        }

        println("Reversed symbol: $reversedSymbol")

        // Use the reversed symbol as needed

        // For demonstration, let's update the text of a TextView with the reversed symbol

        val text = binding.etDisplay.text.toString().lastOrNull()
        val sym= binding.etDisplay.text.toString()
        if(text=='+'){
            binding.etDisplay.text= sym.substring(0,sym.length-1)+'-'
        }

        else if(text=='-'){
            binding.etDisplay.text=sym.substring(0,sym.length-1)+'+'
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

    private fun type(view: View) {
        val button = view as MaterialButton
        binding.etDisplay.text = getBoldString("${binding.etDisplay.text}${button.text}")
    }
}