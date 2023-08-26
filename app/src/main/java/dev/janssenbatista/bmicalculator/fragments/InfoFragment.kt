package dev.janssenbatista.bmicalculator.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import dev.janssenbatista.bmicalculator.databinding.FragmentInfoBinding

class InfoFragment : Fragment() {

    private val binding by lazy { FragmentInfoBinding.inflate(layoutInflater) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        return binding.root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        savedInstanceState?.let {
            val name = it.getString("Name") ?: "Some name"
            Log.i(TAG, name)
        }
    }

    companion object {
        const val TAG = "InfoFragment"
    }

}