package com.example.envagemobileapplication.Activities.ForgotPasword.ForgotPasswordFragments

import android.app.AlertDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.activityViewModels
import com.example.envagemobileapplication.Models.RequestModels.VeriftOtpRm
import com.example.envagemobileapplication.Oauth.TokenManager
import com.example.envagemobileapplication.R
import com.example.envagemobileapplication.Utils.Loader
import com.example.envagemobileapplication.ViewModels.ForGotPasswordSharedViewModel
import com.example.envagemobileapplication.databinding.FragmentVerifyOtpBinding


class VerifyOtpF : Fragment() {
    val sharedViewModel: ForGotPasswordSharedViewModel by activityViewModels()
    lateinit var binding: FragmentVerifyOtpBinding
    lateinit var tokenManager: TokenManager
    var global = com.example.envagemobileapplication.Utils.Global
    private lateinit var otpEditTexts: Array<EditText>
    private var currentEditTextIndex = 0
    lateinit var loader: Loader
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentVerifyOtpBinding.inflate(inflater, container, false)
        initViews()
        clickListeners()
        observers()
        return binding.root
    }

    private fun observers() {
        sharedViewModel.LDVerifyOtp.observe(requireActivity()) {
            loader.hide()

            if (it.data != null) {
                if (it.data.data != null) {
                    var token = it.data.data
                    global.verifyotpTOken = token
                    replaceFragment(ChangePasswordF())
                }

            }
        }
    }

    private fun initViews() {
        tokenManager = TokenManager(requireContext())
        loader = Loader(requireContext())
        otpEditTexts = arrayOf(
            binding.et1,
            binding.et2,
            binding.et3,
            binding.et4,
            binding.et5,
            binding.et6
        )
        for (i in otpEditTexts.indices) {
            val index = i
            otpEditTexts[i].addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    if (s?.length == 1 && index < otpEditTexts.size - 1 && otpEditTexts[index].text.isNotEmpty()) {
                        otpEditTexts[index + 1].requestFocus()
                    }
                }

                override fun afterTextChanged(s: Editable?) {}
            })

            // Set OnKeyListener for EditTexts
            otpEditTexts[i].setOnKeyListener { _, keyCode, _ ->
                if (keyCode == KeyEvent.KEYCODE_DEL && otpEditTexts[i].text.isEmpty() && index > 0) {
                    otpEditTexts[index - 1].requestFocus()
                    true
                } else if (keyCode == KeyEvent.KEYCODE_DEL && otpEditTexts[i].text.isEmpty() && index == 0) {
                    false
                } else {
                    false
                }
            }
        }

// Set Focus to the first EditText
        currentEditTextIndex = 0
        otpEditTexts[currentEditTextIndex].requestFocus()
    }

    private fun clickListeners() {

        binding.ivback.setOnClickListener {

            showDialog()
        }

        binding.et1.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.TI1.error = null
            }

            override fun afterTextChanged(s: Editable?) {
                binding.TI1.error = null
            }
        })
        binding.et2.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.TI2.error = null
            }

            override fun afterTextChanged(s: Editable?) {
                binding.TI2.error = null
            }
        })
        binding.et3.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.TI3.error = null
            }

            override fun afterTextChanged(s: Editable?) {
                binding.TI3.error = null
            }
        })
        binding.et4.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.TI4.error = null
            }

            override fun afterTextChanged(s: Editable?) {
                binding.TI4.error = null
            }
        })
        binding.et5.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.TI5.error = null
            }

            override fun afterTextChanged(s: Editable?) {
                binding.TI5.error = null
            }
        })
        binding.et6.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.TI6.error = null
            }

            override fun afterTextChanged(s: Editable?) {
                binding.TI6.error = null
            }
        })
        binding.verifyotp.setOnClickListener {

            var et1 = binding.et1.text.toString()
            var et2 = binding.et2.text.toString()
            var et3 = binding.et3.text.toString()
            var et4 = binding.et4.text.toString()
            var et5 = binding.et5.text.toString()
            var et6 = binding.et6.text.toString()

            if (et1 != "" && et2 != "" && et3 != "" && et4 != "" && et5 != "" && et6 != "") {
                loader.show()

                var otp = et1 + et2 + et3 + et4 + et5 + et6
                var email = global.emailforgotpassword

                var modelclass = VeriftOtpRm(otp, email)

                sharedViewModel.verifyOtp(modelclass, requireContext(), binding, loader)
                /*  replaceFragment(ChangePasswordF())*/
            } else {


                if (et1.equals("")) {

                    binding.TI1.setError("");
                    binding.TI1.setErrorIconDrawable(null)// Set the error message
                    binding.TI1.setErrorTextAppearance(R.style.ErrorText);
                }
                if (et2.equals("")) {
                    binding.TI2.setError("");
                    binding.TI2.setErrorIconDrawable(null)// Set the error message
                    binding.TI2.setErrorTextAppearance(R.style.ErrorText);
                }
                if (et3.equals("")) {
                    binding.TI3.setError("");
                    binding.TI3.setErrorIconDrawable(null)// Set the error message
                    binding.TI3.setErrorTextAppearance(R.style.ErrorText);
                }
                if (et4.equals("")) {
                    binding.TI4.setError("");
                    binding.TI4.setErrorIconDrawable(null)// Set the error message
                    binding.TI4.setErrorTextAppearance(R.style.ErrorText);
                }
                if (et5.equals("")) {
                    binding.TI5.setError("");
                    binding.TI5.setErrorIconDrawable(null)// Set the error message
                    binding.TI5.setErrorTextAppearance(R.style.ErrorText);
                }
                if (et6.equals("")) {
                    binding.TI6.setError("");
                    binding.TI6.setErrorIconDrawable(null)// Set the error message
                    binding.TI6.setErrorTextAppearance(R.style.ErrorText);
                }

            }

        }
        /*    binding.etEmail.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    charSequence: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(
                    charSequence: CharSequence?,
                    start: Int,
                    before: Int,
                    count: Int
                ) {
                }

                override fun afterTextChanged(editable: Editable?) {

                    val validatedEmail: String? =
                        validateEmail(editable.toString(), binding.etEmail)

                    if (validatedEmail != null) {
                        // Use the validated email
                        email = validatedEmail

                    } else {
                        email = ""
                        // Handle the case where the email is invalid
                    }
                }
            })
            binding.sendotp.setOnClickListener{
                replaceFragment(VerifyOtpF())
            }*/
    }

    fun replaceFragment(fragment: Fragment) {
        val fragmentManager: FragmentManager = requireActivity().supportFragmentManager
        val transaction: FragmentTransaction = fragmentManager.beginTransaction()
        transaction.replace(
            com.example.envagemobileapplication.R.id.cc_forgotpassword,
            fragment
        )
        transaction.addToBackStack(null)
        transaction.commit()
    }


    private fun showDialog() {
        val alertDialogBuilder = AlertDialog.Builder(requireActivity())

        // Set the dialog title and message
        alertDialogBuilder.setTitle("Discard Changes")
        alertDialogBuilder.setMessage("Are you sure you want to discard changes?")

        // Add positive button and its click listener
        alertDialogBuilder.setPositiveButton("Yes") { dialog, which ->
            // Handle the OK button click (if needed)
            dialog.dismiss()
            requireActivity().finishAffinity()
        }

        // Add negative button and its click listener (optional)
        alertDialogBuilder.setNegativeButton("No") { dialog, which ->
            // Handle the Cancel button click (if needed)
            dialog.dismiss() // Dismiss the dialog
        }

        // Create and show the alert dialog
        val alertDialog: AlertDialog = alertDialogBuilder.create()
        alertDialog.show()

    }

}