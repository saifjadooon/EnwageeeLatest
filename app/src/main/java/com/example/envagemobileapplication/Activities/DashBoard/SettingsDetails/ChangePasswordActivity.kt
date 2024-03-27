package com.example.envagemobileapplication.Activities.DashBoard.SettingsDetails

import BaseActivity
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.TextWatcher
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.envagemobileapplication.Models.RequestModels.ChangePasswordRequest
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.UpdatePasswordResp.UpdatePasswordResp
import com.example.envagemobileapplication.Oauth.TokenManager
import com.example.envagemobileapplication.R
import com.example.envagemobileapplication.Utils.Loader
import com.example.envagemobileapplication.databinding.ActivityChangePasswordBinding
import com.ezshifa.aihealthcare.network.ApiUtils
import com.google.android.material.snackbar.Snackbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ChangePasswordActivity : BaseActivity() {
    var isallok = false
    lateinit var binding: ActivityChangePasswordBinding
    lateinit var token: String
    lateinit var tokenManager: TokenManager
    lateinit var loader: Loader
    var newpasswordenterd = ""
    var global = com.example.envagemobileapplication.Utils.Global
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityChangePasswordBinding.inflate(layoutInflater)

        loader = Loader(this)
        tokenManager = TokenManager(this)
        token = tokenManager.getAccessToken().toString()

        val hintcurrentPassword = "Current Password *"
        val formattedhintcurrentPassword = formatHintWithRedAsterisk(hintcurrentPassword)
        binding.TICurrentPassword.hint = formattedhintcurrentPassword

        val hintnewPassword = "New Password *"
        val formattedhintnewPassword = formatHintWithRedAsterisk(hintnewPassword)
        binding.TINewPassword.hint = formattedhintnewPassword

        val hintConfirmnewPassword = "Confirm Password *"
        val formattedhintConfirmnewPassword = formatHintWithRedAsterisk(hintConfirmnewPassword)
        binding.TIconfirmNewPassword.hint = formattedhintConfirmnewPassword


        binding.etNewPassword.addTextChangedListener(PasswordTextWatcher())

        binding.etConfirmNewPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // This method is not used in this example.
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // This method is not used in this example.
            }

            override fun afterTextChanged(editable: Editable?) {
                val enteredText = editable.toString()


                if (enteredText != newpasswordenterd) {
                    binding.btnSave.isClickable = false
                    binding.TIconfirmNewPassword.setError("Password does not match") // Set the error message
                    binding.TIconfirmNewPassword.setErrorTextAppearance(R.style.ErrorText)
                    binding.TIconfirmNewPassword.setErrorIconDrawable(null)// Set the error message
                    binding.btnSave.visibility = View.INVISIBLE
                } else {
                    binding.btnSave.isClickable = true
                    binding.TIconfirmNewPassword.error = null
                    binding.btnSave.visibility = View.VISIBLE
                }

            }
        })
        binding.etCurrentPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // This method is not used in this example.
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                binding.TICurrentPassword.error = null
                // This method is not used in this example.
            }

            override fun afterTextChanged(editable: Editable?) {
                val enteredText = editable.toString()


            }
        })


        binding.btnSave.setOnClickListener {
            var currentPassword = binding.etCurrentPassword.text.toString()
            var newPassword = binding.etNewPassword.text.toString()
            var confirmPassword = binding.etConfirmNewPassword.text.toString()

            if (currentPassword == newPassword){
                val rootView = binding.root
                val duration = Snackbar.LENGTH_SHORT
                val snackbar = Snackbar.make(
                    rootView,
                    "Your new password cannot be the same as your current password",
                    duration
                )
                snackbar.setActionTextColor(
                    ContextCompat.getColor(
                        this@ChangePasswordActivity,
                        R.color.red
                    )
                )
                snackbar.show()
            }
            else {
                if (currentPassword != "" && newPassword != "" && confirmPassword != "") {
                    if (isallok) {
                        var previousPassword = binding.etCurrentPassword.text.toString()
                        var newPassword = binding.etConfirmNewPassword.text.toString()
                        val changePasswordRequest = ChangePasswordRequest(previousPassword, newPassword)
                        callUpdatePasswordApi(changePasswordRequest)
                    }

                }
                else {

                    if (currentPassword == "") {
                        binding.TICurrentPassword.error = "Current Password is Required"
                    }
                    if (newPassword == "") {
                        binding.TINewPassword.error = "New Password is Required"
                    }
                    if (confirmPassword == "") {
                        binding.TIconfirmNewPassword.error = "Confirm Password is Required"
                    }

                }
            }


        }

        binding.ivCross4.setOnClickListener {
            global.showDialog(this, this)
        }
        setContentView(binding.root)
    }

    private fun callUpdatePasswordApi(reqModel: ChangePasswordRequest) {

        try {
            loader.show()
            ApiUtils.getAPIService(this).updatePassword(
                token, reqModel
            )
                .enqueue(object : Callback<UpdatePasswordResp> {
                    override fun onResponse(
                        call: Call<UpdatePasswordResp>,
                        response: Response<UpdatePasswordResp>
                    ) {
                        loader.hide()
                        if (response.body() != null) {
                            var respMessage = response.body()!!.data.message
                            if (respMessage.equals("Current Password is incorrect")) {
                                val rootView = binding.root
                                val duration = Snackbar.LENGTH_SHORT
                                val snackbar = Snackbar.make(
                                    rootView,
                                    "Current Password is incorrect",
                                    duration
                                )
                                snackbar.setActionTextColor(
                                    ContextCompat.getColor(
                                        this@ChangePasswordActivity,
                                        R.color.red
                                    )
                                )
                                snackbar.show()
                            } else if (respMessage.equals("Password updated successfully")) {

                                Toast.makeText(
                                    this@ChangePasswordActivity,
                                    "Password updated successfully",
                                    Toast.LENGTH_LONG
                                ).show()
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
                                        this@ChangePasswordActivity,
                                        R.color.red
                                    )
                                )
                                snackbar.show()
                            }
                        }
                    }

                    override fun onFailure(call: Call<UpdatePasswordResp>, t: Throwable) {
                        loader.hide()
                        Log.i("exc", t.toString())
                    }
                })
        } catch (ex: java.lang.Exception) {
            loader.hide()
            Log.i("exception", ex.toString())
        }

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
            updateTextColor(binding.lengthTextView, isLengthValid)
            updateTextColor(binding.upperCaseTextView, hasUpperCase)
            updateTextColor(binding.lowerCaseTextView, hasLowerCase)
            updateTextColor(binding.specialCharTextView, hasSpecialChar)
            updateTextColor(binding.numberTextView, hasNumber)


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
}