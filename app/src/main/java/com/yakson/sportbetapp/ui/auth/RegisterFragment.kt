package com.yakson.sportbetapp.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.yakson.sportbetapp.R
import com.yakson.sportbetapp.base.BaseFragment
import com.yakson.sportbetapp.databinding.FragmentRegisterBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : BaseFragment<FragmentRegisterBinding, AuthViewModel>() {

    override val viewModel: AuthViewModel by viewModels()

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentRegisterBinding = FragmentRegisterBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
    }

    private fun setupUI() {
        binding.apply {
            btnRegister.setOnClickListener {
                val name = etNameInput.text.toString()
                val email = etEmailInput.text.toString()
                val password = etPasswordInput.text.toString()
                if (validateInput(email, password)) {
                    viewModel.register(name, email, password)
                }
            }

            tvLogin.setOnClickListener {
                navigateToLogin()
            }
        }
    }

    private fun validateInput(email: String, password: String): Boolean {
        var isValid = true

        if (email.isEmpty()) {
            binding.etEmailInput.error = getString(R.string.email_required)
            isValid = false
        }

        if (password.isEmpty()) {
            binding.etPasswordInput.error = getString(R.string.password_required)
            isValid = false
        }

        return isValid
    }

    private fun navigateToLogin() {
        val action = RegisterFragmentDirections.actionRegisterFragmentToLoginFragment()
        findNavController().navigate(action)
    }

    private fun navigateToMain() {
        val action = RegisterFragmentDirections.actionRegisterFragmentToBetBulletinFragment()
        findNavController().navigate(action)
    }
} 