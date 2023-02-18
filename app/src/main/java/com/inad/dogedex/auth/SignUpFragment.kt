package com.inad.dogedex.auth

import android.content.Context
import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.inad.dogedex.R
import com.inad.dogedex.databinding.FragmentSignUpBinding
import com.inad.dogedex.isValidEmail


class SignUpFragment : Fragment() {

    interface SignUpFragmentActions {
        fun onSignUpfieldsValidated(email: String, password: String, passwordConfirmation: String)
    }

    private lateinit var signUpfragmentActions: SignUpFragmentActions

    private lateinit var binding: FragmentSignUpBinding

    override fun onAttach(context: Context) {
        super.onAttach(context)
        signUpfragmentActions = try {
            context as SignUpFragmentActions
        } catch (e: ClassCastException) {
            throw ClassCastException("$context must implement SignUpFragmentActions")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignUpBinding.inflate(inflater)
        setupSignUpButton()
        return binding.root
    }

    private fun setupSignUpButton() {
        binding.signUpButton.setOnClickListener {
            validateFields()
        }
    }

    private fun validateFields() {
        binding.emailInput.error = ""
        binding.passwordInput.error = ""
        binding.confirmPasswordInput.error = ""

        val email = binding.emailEdit.text.toString()

        if (!isValidEmail(email)) {
            binding.emailInput.error = getString(R.string.email_is_not_valid)
            return
        }

        val password = binding.passwordEdit.text.toString()
        if (password.isEmpty()) {
            binding.passwordInput.error = getString(R.string.password_must_not_be_empty)
            return
        }

        val passwordConfirmation = binding.confirmPasswordEdit.text.toString()
        if (passwordConfirmation.isEmpty()) {
            binding.confirmPasswordInput.error = getString(R.string.password_must_not_be_empty)
            return
        }

        if (password != passwordConfirmation) {
            binding.passwordInput.error = getString(R.string.passwords_do_not_match)
            return
        }

        signUpfragmentActions.onSignUpfieldsValidated(email, password, passwordConfirmation)
    }

}