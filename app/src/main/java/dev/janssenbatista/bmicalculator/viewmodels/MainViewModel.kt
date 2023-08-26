package dev.janssenbatista.bmicalculator.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dev.janssenbatista.bmicalculator.models.InputError
import java.math.BigDecimal
import java.math.MathContext
import kotlin.properties.Delegates

class MainViewModel : ViewModel() {

    private val _inputError = MutableLiveData(InputError())
    val inputError: LiveData<InputError> = _inputError
    private var height by Delegates.notNull<BigDecimal>()
    private var weight by Delegates.notNull<BigDecimal>()

    fun validateInputs(height: String, weight: String): Boolean {
        val heightRegex = Regex("\\d\\.\\d{1,2}")
        val weightRegex = Regex("[1-9]\\d*")
        if (!heightRegex.matches(height)) {
            _inputError.value = _inputError.value?.copy(heightError = true)
            return false
        } else {
            _inputError.value = _inputError.value?.copy(heightError = false)
        }
        if (!weightRegex.matches(weight)) {
            _inputError.value =
                _inputError.value?.copy(weightError = true)
            return false
        } else {
            _inputError.value = _inputError.value?.copy(weightError = false)
        }
        this.height = height.toBigDecimal(MathContext.DECIMAL32)
        this.weight = weight.toBigDecimal(MathContext.DECIMAL32)
        return true
    }

    fun calculateBmi(): BigDecimal {
        return weight.div(height.pow(2))
    }


}


