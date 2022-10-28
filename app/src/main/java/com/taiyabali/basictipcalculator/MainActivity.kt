package com.taiyabali.basictipcalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.taiyabali.basictipcalculator.databinding.ActivityMainBinding
import java.text.NumberFormat
import java.util.*

/**
 * Created by Techpass Master.
 * Website - https://techpassmaster.com/
 * Email id - hello@techpassmaster.com
 */

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var countPerson = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        onClick()
    }

    private fun onClick() {

        with(binding) {
            btnCalculate.setOnClickListener {
                calculateTip()
            }

            btnPersonPlus.setOnClickListener {
                countPerson++
                tvPersonCount.text = countPerson.toString()
            }

            btnPersonMinus.setOnClickListener {
                if (countPerson > 1) {
                    countPerson--
                    tvPersonCount.text = countPerson.toString()
                }
            }
        }
    }

    private fun calculateTip() {
            //      get bill cost from EditText.
            //      set total bill amount in the cost.
        val totalBill: String = binding.edtBill.text.toString()
        val cost = totalBill.toDoubleOrNull()

        if (totalBill.isNotEmpty()) {
            binding.iconCopy.visibility = View.VISIBLE
            binding.iconShare.visibility = View.VISIBLE

            //      get selected tip percentage
            val tipPercentage = when (binding.tipOptions.checkedRadioButtonId) {
                R.id.rbTip_twentyPercent -> 0.20
                R.id.rbTip_fifteenPercent -> 0.15
                else -> 0.10
            }

            //      Calculate the tip according to percentage and set per person tip
            val tip = tipPercentage * cost!!
            showTotalTipResult(tip)

            //      Calculate & set the per person tip
            val perPersonTip = (tip / countPerson).toString()
            showPerPersonTipResult(perPersonTip.toDouble())

            //      Calculate & set total bill + tip
            val billPlusTip = cost.plus(tip)
            showTotalBillPlusTipResult(billPlusTip)

            //      Calculate & set per person total
            val totalPerPerson = (billPlusTip / countPerson).toString()
            showTotalPerPersonResult(totalPerPerson.toDouble())
        } else {
            Toast.makeText(this, "Please enter bill Amount", Toast.LENGTH_SHORT).show()
        }
    }

    private fun showTotalTipResult(tip: Double) {
        val numberFormat = NumberFormat.getCurrencyInstance(Locale("en", "in")).format(tip)
        binding.tvTotalTip.text = ("Total Tip: $numberFormat")
    }

    private fun showTotalBillPlusTipResult(tip: Double) {
        val numberFormat = NumberFormat.getCurrencyInstance(Locale("en", "in")).format(tip)
        binding.tvTotalBillPlusTip.text = ("Total Amount (Bill+Tip): $numberFormat")
    }

    private fun showPerPersonTipResult(tip: Double) {
        val numberFormat = NumberFormat.getCurrencyInstance(Locale("en", "in")).format(tip)
        binding.tvTipPerPerson.text = ("Per Person Tip: $numberFormat")

        if (countPerson == 1) {
            binding.tvTipPerPerson.text = ("Tip: $numberFormat")
        } else {
            binding.tvTipPerPerson.text = ("Per Person Tip: $numberFormat")
        }
    }

    private fun showTotalPerPersonResult(tip: Double) {
        val numberFormat = NumberFormat.getCurrencyInstance(Locale("en", "in")).format(tip)
        if (countPerson == 1) {
            binding.tvTotalPerPerson.text = ("Total: $numberFormat")
        } else {
            binding.tvTotalPerPerson.text = ("Total Per Person: $numberFormat")
        }
    }
}