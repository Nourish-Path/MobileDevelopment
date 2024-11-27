package com.example.nourishpath.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.nourishpath.R
import com.example.nourishpath.databinding.FragmentChildInputBinding
import com.example.nourishpath.models.StuntingModel

class FragmentChildInput : Fragment() {
    private var _binding: FragmentChildInputBinding? = null
    private val binding get() = _binding!!
    private lateinit var stuntingModel: StuntingModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChildInputBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Inisialisasi model
        stuntingModel = StuntingModel(requireContext())
        stuntingModel.loadModel("checkstunting.tflite")

        // Tombol Lanjutkan
        binding.btnNext.setOnClickListener {
            val age = binding.etChildAge.text.toString().toFloatOrNull()
            val weight = binding.etChildWeight.text.toString().toFloatOrNull()
            val height = binding.etChildHeight.text.toString().toFloatOrNull()

            if (age == null || weight == null || height == null) {
                Toast.makeText(requireContext(), "Harap isi semua data!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Tambahkan validasi agar input sesuai dengan 5 fitur yang dibutuhkan
            val prediction = stuntingModel.predictStunting(age, weight, height)

            val isStunting = prediction >= 0.5
            val message = if (isStunting) {
                "Anak berpotensi stunting!"
            } else {
                "Anak tidak stunting."
            }

            Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()

        // Navigasi ke NutrientFragment dengan data yang diinputkan
            val bundle = Bundle().apply {
                putFloat("age", age)
                putFloat("weight", weight)
                putFloat("height", height)
            }
            findNavController().navigate(R.id.action_childInput_to_nutrientFragment, bundle)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        stuntingModel.close() // Tutup interpreter saat Fragment dihancurkan
    }
}