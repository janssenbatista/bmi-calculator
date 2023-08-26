package dev.janssenbatista.bmicalculator.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import dev.janssenbatista.bmicalculator.R
import dev.janssenbatista.bmicalculator.databinding.FragmentHomeBinding
import dev.janssenbatista.bmicalculator.dialogs.ResultDialog
import dev.janssenbatista.bmicalculator.viewmodels.MainViewModel

class HomeFragment : Fragment() {

    private val binding by lazy { FragmentHomeBinding.inflate(layoutInflater) }
    private lateinit var mainViewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]
        mainViewModel.inputError.observe(requireActivity()) { inputError ->
            if (inputError.heightError) {
                binding.apply {
                    textFieldHeightContainer.isErrorEnabled = true
                    textFieldHeightContainer.error = getString(R.string.invalid_height)
                }
            }
            if (inputError.weightError) {
                binding.apply {
                    textFieldWeightContainer.isErrorEnabled = true
                    textFieldWeightContainer.error = getString(R.string.invalid_weight)
                }
            }
        }

        binding.textFieldHeight.doOnTextChanged { _, _, _, _ ->
            binding.textFieldHeightContainer.isErrorEnabled = false
        }
        binding.textFieldWeight.doOnTextChanged { _, _, _, _ ->
            binding.textFieldWeightContainer.isErrorEnabled = false
        }

        binding.buttonCalculate.setOnClickListener {
            val isInputsValid = mainViewModel.validateInputs(
                binding.textFieldHeight.text.toString(),
                binding.textFieldWeight.text.toString()
            )
            if (isInputsValid) {
                val result = mainViewModel.calculateBmi()
                ResultDialog(result).show(requireActivity().supportFragmentManager, "ResultDialog")
                binding.apply {
                    textFieldHeight.setText("")
                    textFieldWeight.setText("")
                    textFieldHeight.requestFocus()
                }
            }
        }
    }

}