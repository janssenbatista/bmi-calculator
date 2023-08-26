package dev.janssenbatista.bmicalculator.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.graphics.Color
import android.os.Bundle
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import dev.janssenbatista.bmicalculator.R
import dev.janssenbatista.bmicalculator.databinding.DialogFragmentResultBinding
import java.math.BigDecimal

class ResultDialog(private val resultValue: BigDecimal) :
    DialogFragment() {

    private val binding by lazy { DialogFragmentResultBinding.inflate(layoutInflater) }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        setResult(
            resultValue.toDouble(),
            binding.textViewResultValue,
            binding.textViewResultMessage
        )
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.apply {
                setView(binding.root)
                setNegativeButton(R.string.close) { dialog, _ ->
                    dialog.cancel()
                }
            }

            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")

    }

    private fun setResult(
        resultValue: Double,
        textViewResultValue: TextView,
        textViewResultMessage: TextView
    ) {
        val formattedValue = String.format("%.2f", resultValue)
        textViewResultValue.text = formattedValue
        when {
            resultValue < 18.5 -> {
                textViewResultValue.setTextColor(Color.RED)
                textViewResultMessage.text = requireActivity().getString(R.string.underweight)
            }

            resultValue in 18.5..24.9 -> {
                textViewResultValue.setTextColor(Color.GREEN)
                textViewResultMessage.text = requireActivity().getString(R.string.normal_weight)
            }

            resultValue in 25.0..29.9 -> {
                textViewResultValue.setTextColor(Color.rgb(255, 255, 0))
                textViewResultMessage.text = requireActivity().getString(R.string.overweight)
            }

            resultValue in 30.0..34.9 -> {
                textViewResultValue.setTextColor(Color.RED)
                textViewResultMessage.text = requireActivity().getString(R.string.obese_class_1)
            }

            resultValue in 35.0..39.9 -> {
                textViewResultValue.setTextColor(Color.RED)
                textViewResultMessage.text = requireActivity().getString(R.string.obese_class_2)
            }

            else -> {
                textViewResultValue.setTextColor(Color.RED)
                textViewResultMessage.text = requireActivity().getString(R.string.obese_class_3)
            }
        }
    }
}