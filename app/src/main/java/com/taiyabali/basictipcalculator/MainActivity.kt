package com.taiyabali.basictipcalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.taiyabali.basictipcalculator.databinding.ActivityMainBinding
import java.text.NumberFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var countPerson = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        onClick()           // click event for buttons

    }

    private fun onClick() {

        with(binding) {

            btnCalculate.setOnClickListener {
                // fun for calculate tip
                calculateTip()
            }

            btnPersonPlus.setOnClickListener {
                countPerson++
                tvNumberOfPersonCount.text = countPerson.toString()
            }

            btnPersonMinus.setOnClickListener {
                if (countPerson > 1) {
                    countPerson--
                    tvNumberOfPersonCount.text = countPerson.toString()
                }
            }
        }

    }

    private fun calculateTip() {

        val totalBill: String =
            binding.edtBill.text.toString()     //      get bill cost from EditText
        val cost =
            totalBill.toDoubleOrNull()          //      keep total bill amount in the cost var with double data type

//      here, check tip percentage based on which radio button selected
        val tipPercentage = when (binding.tipOptions.checkedRadioButtonId) {
            R.id.rbTip_twenty_percent -> 0.20
            R.id.rbTip_fifteen_percent -> 0.15
            else -> 0.10
        }

//        check if bill cost null or 0 then show 0 tip
        if (cost == null || cost == 0.0) {
            showTotalTipResult(0.0)
            showPerPersonTipResult(0.0)
            showTotalBillPlusTipResult(0.0)
            showTotalPerPersonResult(0.0)
            return
        }

        val tip =
            tipPercentage * cost                                //      Calculate the tip according to percentage
        showTotalTipResult(tip)                                 //      fun for display per person tip result

        val perPersonTip =
            (tip / countPerson).toString()                      //      Calculate the per person tip
        showPerPersonTipResult(perPersonTip.toDouble())         //      fun for display per person tip result

        val billPlusTip =
            cost.plus(tip)                                      //      Calculate the total bill + tip
        showTotalBillPlusTipResult(billPlusTip)                 //      fun for display total bill + tip result

        val total_per_person =
            (billPlusTip / countPerson).toString()              //      Calculate total per person
        showTotalPerPersonResult(total_per_person.toDouble())   //      fun for display per person total
    }


    private fun showTotalTipResult(tip: Double) {
        val numberFormat = NumberFormat.getCurrencyInstance(Locale("en", "in")).format(tip)
        binding.tvTotalTip.text = ("Total Tip: $numberFormat")
    }

    private fun showPerPersonTipResult(tip: Double) {
        val numberFormat = NumberFormat.getCurrencyInstance(Locale("en", "in")).format(tip)
        binding.tvTipPerPerson.text = ("Per Person Tip: $numberFormat")
    }


    private fun showTotalBillPlusTipResult(tip: Double) {
        val numberFormat = NumberFormat.getCurrencyInstance(Locale("en", "in")).format(tip)
        binding.tvTotalBillPlusTip.text = ("Total (Bill+Tip): $numberFormat")
    }

    private fun showTotalPerPersonResult(tip: Double) {
        val numberFormat = NumberFormat.getCurrencyInstance(Locale("en", "in")).format(tip)
        binding.tvTotalPerPerson.text = ("Total Per Person: $numberFormat")
    }

}