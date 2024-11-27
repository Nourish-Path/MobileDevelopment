package com.example.nourishpath.ui.settings

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.lifecycleScope
import com.example.nourishpath.R
import com.example.nourishpath.databinding.FragmentSettingsBinding
import kotlinx.coroutines.launch

class SettingsFragment : Fragment() {
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var binding: FragmentSettingsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedPreferences = requireContext().getSharedPreferences("user_preferences", Context.MODE_PRIVATE)
        val isDarkMode = sharedPreferences.getBoolean("DARK_MODE", false)

        binding.themeSwitch.isChecked = isDarkMode
        setDarkMode(isDarkMode)

        binding.themeSwitch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked != isDarkMode) {
                setDarkMode(isChecked)
                with(sharedPreferences.edit()) {
                    putBoolean("DARK_MODE", isChecked)
                    apply()
                }
            }
        }
    }

    private fun setDarkMode(isDarkMode: Boolean) {
        lifecycleScope.launch {
            if (isDarkMode) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
    }
}