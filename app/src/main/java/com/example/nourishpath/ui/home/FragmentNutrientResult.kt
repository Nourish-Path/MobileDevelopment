package com.example.nourishpath.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.nourishpath.databinding.FragmentNutrientResultBinding
import com.example.nourishpath.models.NutrientData

class FragmentNutrientResult : Fragment() {
    private var _binding: FragmentNutrientResultBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNutrientResultBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val nutrientData = arguments?.getParcelable<NutrientData>("nutrientData")

        if (nutrientData != null) {
            val totalNutrients = nutrientData.totalNutrients
            binding.tvresult.text = totalNutrients.entries.joinToString("\n") {
                "${it.key}: ${it.value} g"
            }
        } else {
            binding.tvresult.text = "Data nutrisi tidak tersedia."
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}