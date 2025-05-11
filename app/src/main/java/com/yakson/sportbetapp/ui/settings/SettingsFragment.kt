package com.yakson.sportbetapp.ui.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.yakson.sportbetapp.R
import com.yakson.sportbetapp.base.BaseFragment
import com.yakson.sportbetapp.databinding.FragmentSettingsBinding
import com.yakson.sportbetapp.ui.auth.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint
import com.yakson.sportbetapp.util.showLogoutDialog

@AndroidEntryPoint
class SettingsFragment : BaseFragment<FragmentSettingsBinding, AuthViewModel>() {

    override val viewModel: AuthViewModel by viewModels()

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentSettingsBinding = FragmentSettingsBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: android.view.View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservers()
        setupClickListeners()
    }

    private fun setupObservers() {
        viewModel.error.observe(viewLifecycleOwner) { error ->
            error?.let {
                Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
            }
        }

        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.logoutButton.isEnabled = !isLoading
        }
    }

    private fun setupClickListeners() {
        val versionName = requireContext().packageManager
            .getPackageInfo(requireContext().packageName, 0).versionName
        binding.tvVersion.text = buildString {
            append(getString(R.string.version))
            append(versionName.toString())
        }

        val currentTheme = AppCompatDelegate.getDefaultNightMode()
        when (currentTheme) {
            AppCompatDelegate.MODE_NIGHT_YES -> binding.themeRadioGroup.check(R.id.darkThemeRadio)
            AppCompatDelegate.MODE_NIGHT_NO -> binding.themeRadioGroup.check(R.id.lightThemeRadio)
            else -> binding.themeRadioGroup.check(R.id.systemThemeRadio)
        }

        binding.themeRadioGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.lightThemeRadio -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                R.id.darkThemeRadio -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                R.id.systemThemeRadio -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
            }
        }

        binding.logoutButton.setOnClickListener {
            showLogoutDialog {
                viewModel.logout()
                findNavController().navigate(R.id.action_settingsFragment_to_welcomeFragment)
            }
        }
    }
} 