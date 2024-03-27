package com.example.envagemobileapplication.Activities.ForgotPasword.ForgotPasswordFragments

import android.app.AlertDialog
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.TextWatcher
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.envagemobileapplication.Models.RequestModels.ForGotPasswordReqmodel
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.forgotpasswordResponse.ForgotpasswordResponse
import com.example.envagemobileapplication.Oauth.TokenManager
import com.example.envagemobileapplication.R
import com.example.envagemobileapplication.Utils.Loader
import com.example.envagemobileapplication.databinding.FragmentChangePasswordBinding
import com.ezshifa.aihealthcare.network.ApiUtils
import com.google.android.material.snackbar.Snackbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChangePasswordF : Fragment() {
    var newpasswordenterd = ""
    lateinit var binding: FragmentChangePasswordBinding
    lateinit var tokenManager: TokenManager
    var global = com.example.envagemobileapplication.Utils.Global
    private lateinit var otpEditTexts: Array<EditText>
    private var currentEditTextIndex = 0
    var isallok = false
    lateinit var loader: Loader
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        binding = FragmentChangePasswordBinding.inflate(inflater, container, false)
        initViews()
        clickListeners()
        return binding.root
    }

    private fun initViews() {
        tokenManager = TokenManager(requireContext())
        loader = Loader(requireContext())
        val hintnewPassword = "New Password *"
        val formattedhintnewPassword = formatHintWithRedAsterisk(hintnewPassword)
        binding.TINewPassword.hint = formattedhintnewPassword

        val hintConfirmnewPassword = "Confirm Password *"
        val formattedhintConfirmnewPassword = formatHintWithRedAsterisk(hintConfirmnewPassword)
        binding.TIConfirmPassword.hint = formattedhintConfirmnewPassword

        binding.etNewPassword.addTextChangedListener(PasswordTextWatcher())


    }

    private fun clickListeners() {

        binding.ivback.setOnClickListener {

            showDialog()
        }
        binding.save.setOnClickListener {
            var token = global.verifyotpTOken
            var newPassword = binding.etNewPassword.text.toString()
            var confirmnewPassword = binding.etConfirmPassword.text.toString()
            if (newPassword != "" && token != "" && confirmnewPassword != "") {
                if (isallok) {

                    val changePasswordRequest = ForGotPasswordReqmodel(token!!, newPassword)
                    callUpdatePasswordApi(changePasswordRequest)
                }

            } else {

                if (newPassword == "") {
                    binding.TINewPassword.error = "New Password is Required"
                    binding.TINewPassword.setErrorIconDrawable(null)// Set the error message
                    binding.TINewPassword.setErrorTextAppearance(R.style.ErrorText);
                }
                if (confirmnewPassword == "") {
                    binding.TIConfirmPassword.error = "Confirm Password is Required"
                    binding.TIConfirmPassword.setErrorIconDrawable(null)// Set the error message
                    binding.TIConfirmPassword.setErrorTextAppearance(R.style.ErrorText);

                }


            }

            //  replaceFragment(PasswordUpdatedF())
        }

        binding.etConfirmPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // This method is not used in this example.
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // This method is not used in this example.
            }

            override fun afterTextChanged(editable: Editable?) {
                val enteredText = editable.toString()


                if (enteredText != newpasswordenterd) {
                    binding.save.isClickable = false
                    binding.TIConfirmPassword.setError("Password does not match") // Set the error message
                    binding.TIConfirmPassword.setErrorTextAppearance(R.style.ErrorText)
                    binding.TIConfirmPassword.setErrorIconDrawable(null)// Set the error message
                    binding.save.visibility = View.INVISIBLE
                } else {
                    binding.save.isClickable = true
                    binding.TIConfirmPassword.error = null
                    binding.save.visibility = View.VISIBLE
                }

            }
        })

        binding.ivback.setOnClickListener {
            global.showDialog(requireActivity(), requireActivity())
        }
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

    fun formatHintWithRedAsterisk(hint: String): CharSequence {
        val spannable = SpannableStringBuilder(hint)
        val indexOfAsterisk = hint.indexOf('*')

        if (indexOfAsterisk >= 0) {
            spannable.setSpan(
                ForegroundColorSpan(Color.RED),
                indexOfAsterisk,
                indexOfAsterisk + 1,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }

        return spannable
    }

    private inner class PasswordTextWatcher : TextWatcher {

        override fun beforeTextChanged(
            charSequence: CharSequence?,
            start: Int,
            before: Int,
            count: Int
        ) {
        }

        override fun onTextChanged(
            charSequence: CharSequence?,
            start: Int,
            before: Int,
            count: Int
        ) {
            binding.TINewPassword.error = null
        }

        override fun afterTextChanged(editable: Editable?) {
            val password = editable.toString()
            newpasswordenterd = password

            // Example validations
            val isLengthValid = password.length >= 8
            val hasUpperCase = password.any { it.isUpperCase() }
            val hasLowerCase = password.any { it.isLowerCase() }
            val hasSpecialChar = password.any { it.isLetterOrDigit().not() }
            val hasNumber = password.any { it.isDigit() }

            // Update text color based on individual validations
            updateTextColor(binding.lengthTextView2, isLengthValid)
            updateTextColor(binding.upperCaseTextView2, hasUpperCase)
            updateTextColor(binding.lowerCaseTextView2, hasLowerCase)
            updateTextColor(binding.specialCharTextView2, hasSpecialChar)
            updateTextColor(binding.numberTextView2, hasNumber)


            val isAllValid =
                isLengthValid && hasUpperCase && hasLowerCase && hasSpecialChar && hasNumber
            if (isAllValid) {
                isallok = true
            } else {
                isallok = false
            }
        }

        private fun updateTextColor(textView: TextView, isValid: Boolean) {
            if (isValid) {
                textView.setTextColor(resources.getColor(R.color.green_color))
            } else {
                textView.setTextColor(resources.getColor(R.color.red_color))
            }
        }
    }

    private fun callUpdatePasswordApi(reqModel: ForGotPasswordReqmodel) {

        try {
            loader.show()
            ApiUtils.getAPIService(requireContext()).resetPassword(
                reqModel
            )
                .enqueue(object : Callback<ForgotpasswordResponse> {
                    override fun onResponse(
                        call: Call<ForgotpasswordResponse>,
                        response: Response<ForgotpasswordResponse>
                    ) {
                        loader.hide()
                        if (response.body() != null) {
                            var respMessage = response.body()!!.data.message

                            if (respMessage.equals("Password created successfully")) {

                                replaceFragment(PasswordUpdatedF())
                            } else {
                                val rootView = binding.root
                                val duration = Snackbar.LENGTH_SHORT
                                val snackbar = Snackbar.make(
                                    rootView,
                                    respMessage.toString(),
                                    duration
                                )
                                snackbar.setActionTextColor(
                                    ContextCompat.getColor(
                                        requireContext(),
                                        R.color.red
                                    )
                                )
                                snackbar.show()
                            }
                        }
                    }

                    override fun onFailure(call: Call<ForgotpasswordResponse>, t: Throwable) {
                        loader.hide()
                        Log.i("exc", t.toString())
                    }
                })
        } catch (ex: java.lang.Exception) {
            loader.hide()
            Log.i("exception", ex.toString())
        }

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