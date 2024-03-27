package com.example.envagemobileapplication.ViewModels

import android.content.Context
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.envagemobileapplication.Models.RequestModels.VeriftOtpRm
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.SendEmailForgotPaswordResp.SendEmailForgotPaswordResp
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.verifyOtpResponse.VerifyOtpResponse
import com.example.envagemobileapplication.R
import com.example.envagemobileapplication.Utils.Loader
import com.example.envagemobileapplication.databinding.FragmentEnterEmailBinding
import com.example.envagemobileapplication.databinding.FragmentVerifyOtpBinding
import com.ezshifa.aihealthcare.network.ApiUtils
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ForGotPasswordSharedViewModel : ViewModel() {

    val LDSendEmail: LiveData<SendEmailForgotPaswordResp>
        get() = SendEmailMLD
    private val SendEmailMLD = MutableLiveData<SendEmailForgotPaswordResp>()


    val LDVerifyOtp: LiveData<VerifyOtpResponse>
        get() = verifyOtpMLD
    private val verifyOtpMLD = MutableLiveData<VerifyOtpResponse>()


    fun sendEmail(
        email: String,
        requireContext: Context,
        loader: Loader,
        binding: FragmentEnterEmailBinding
    ) {

        viewModelScope.launch {
            try {
                ApiUtils.getAPIService(requireContext)
                    .sendEmailForotPassword(email)
                    .enqueue(object : Callback<SendEmailForgotPaswordResp> {
                        override fun onResponse(
                            call: Call<SendEmailForgotPaswordResp>,
                            response: Response<SendEmailForgotPaswordResp>
                        ) {
                            if (response.isSuccessful && response.body() != null) {
                                SendEmailMLD.postValue(response.body())
                            } else {
                                loader.hide()
                                val errorBody = response.errorBody()?.string()

                                if (errorBody!!.contains("This email does not exist on our system")) {
                                    val rootView = binding.root
                                    val duration = Snackbar.LENGTH_SHORT
                                    val snackbar = Snackbar.make(
                                        rootView,
                                        "This email does not exist on our system",
                                        duration
                                    )
                                    snackbar.setActionTextColor(
                                        ContextCompat.getColor(
                                            requireContext,
                                            R.color.red
                                        )
                                    )
                                    snackbar.show()
                                } else {
                                    val rootView = binding.root
                                    val duration = Snackbar.LENGTH_SHORT
                                    val snackbar = Snackbar.make(
                                        rootView,
                                        errorBody.toString(),
                                        duration
                                    )
                                    snackbar.setActionTextColor(
                                        ContextCompat.getColor(
                                            requireContext,
                                            R.color.red
                                        )
                                    )
                                    snackbar.show()
                                }


                            }
                        }

                        override fun onFailure(
                            call: Call<SendEmailForgotPaswordResp>,
                            t: Throwable
                        ) {

                        }
                    })
            } catch (ex: java.lang.Exception) {

            }
        }


    }


    fun verifyOtp(
        modelclass: VeriftOtpRm,
        requireContext: Context,
        binding: FragmentVerifyOtpBinding,
        loader: Loader
    ) {

        viewModelScope.launch {
            try {
                ApiUtils.getAPIService(requireContext)
                    .VerifyCode(modelclass)
                    .enqueue(object : Callback<VerifyOtpResponse> {
                        override fun onResponse(
                            call: Call<VerifyOtpResponse>,
                            response: Response<VerifyOtpResponse>
                        ) {
                            if (response.isSuccessful && response.body() != null) {
                                loader.hide()
                                verifyOtpMLD.postValue(response.body())
                            } else {
                                loader.hide()
                                val errorBody = response.errorBody()?.string()

                                if (errorBody!!.contains("Your verfication code expired")) {
                                    val rootView = binding.root
                                    val duration = Snackbar.LENGTH_SHORT
                                    val snackbar = Snackbar.make(
                                        rootView,
                                        "Your verfication code expired, try again",
                                        duration
                                    )
                                    snackbar.setActionTextColor(
                                        ContextCompat.getColor(
                                            requireContext,
                                            R.color.red
                                        )
                                    )
                                    snackbar.show()
                                } else if (errorBody!!.contains("Your verification code does not match with our records")) {
                                    val rootView = binding.root
                                    val duration = Snackbar.LENGTH_SHORT
                                    val snackbar = Snackbar.make(
                                        rootView,
                                        "Your verification code does not match with our records",
                                        duration
                                    )
                                    snackbar.setActionTextColor(
                                        ContextCompat.getColor(
                                            requireContext,
                                            R.color.red
                                        )
                                    )
                                    snackbar.show()
                                }


                            }
                        }

                        override fun onFailure(call: Call<VerifyOtpResponse>, t: Throwable) {
                            loader.hide()
                        }
                    })
            } catch (ex: java.lang.Exception) {
                loader.hide()
            }
        }


    }

}