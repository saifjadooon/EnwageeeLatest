package com.example.envagemobileapplication.ViewModels

import android.content.Context
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.envagemobileapplication.Models.RequestModels.AddJobRequestModels.GetCustomJobTemplateReqModel
import com.example.envagemobileapplication.Models.RequestModels.sendMesageReqModel
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.GetOfferLetterTemplates.GetOfferLetterTemplates
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.MessageTemplatesRes.MessageTemplatesRes
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.getEmailTemplateResponse.GetEmailTemplateResponse
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.getTwilioConfig.GetTwilioConfig
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.getjobbyid.Getjobbyid
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.sendMessageResponse.SendMessageResponse
import com.example.envagemobileapplication.R
import com.example.envagemobileapplication.databinding.ActivityComposeMessageBinding
import com.ezshifa.aihealthcare.network.ApiUtils
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SendOfferLetterViewModel : ViewModel() {
    val LDofferLetterTemplate: LiveData<GetOfferLetterTemplates>
        get() = MLDofferLetterTemplate
    private val MLDofferLetterTemplate = MutableLiveData<GetOfferLetterTemplates>()

    val LDMessageTemplate: LiveData<MessageTemplatesRes>
        get() = MLDMessageTemplate
    private val MLDMessageTemplate = MutableLiveData<MessageTemplatesRes>()

    val LDEmailTemplate: LiveData<GetEmailTemplateResponse>
        get() = MLDEmailTemplate
    private val MLDEmailTemplate = MutableLiveData<GetEmailTemplateResponse>()


    val LDGetJobByJobId: LiveData<Getjobbyid>
        get() = MLDGetJobByJobId
    private val MLDGetJobByJobId = MutableLiveData<Getjobbyid>()

    val LDGetTwilioConfig: LiveData<GetTwilioConfig>
        get() = MLDGetTwilioConfig
    private val MLDGetTwilioConfig = MutableLiveData<GetTwilioConfig>()

    val LDSendMessage: LiveData<SendMessageResponse>
        get() = MLDSendMessage
    private val MLDSendMessage = MutableLiveData<SendMessageResponse>()


    //=============>>>>>>>>>>>>>>><<<<<<<<<<<<<<<<<<<<<<<<<///////////


    fun getCustomTemplates(context: Context, token: String?, model: GetCustomJobTemplateReqModel) {
        viewModelScope.launch {
            try {
                ApiUtils.getAPIService(context).getofferLetterTemplates(
                    token.toString(),
                    model,

                    )
                    .enqueue(object : Callback<GetOfferLetterTemplates> {
                        override fun onResponse(
                            call: Call<GetOfferLetterTemplates>,
                            response: Response<GetOfferLetterTemplates>
                        ) {
                            if (response.body() != null) {

                                MLDofferLetterTemplate.postValue(response.body())

                            }
                        }

                        override fun onFailure(call: Call<GetOfferLetterTemplates>, t: Throwable) {
                            Log.i("exceptionddsfdsfds", t.toString())

                        }
                    })
            } catch (ex: java.lang.Exception) {
                Log.i("exceptionddsfdsfds", ex.toString())
            }
        }

    }

    fun getCustomTemplatesForMessages(context: Context, token: String?) {
        viewModelScope.launch {
            try {
                ApiUtils.getAPIService(context).getMessagesTemplates(
                    token.toString()
                )
                    .enqueue(object : Callback<MessageTemplatesRes> {
                        override fun onResponse(
                            call: Call<MessageTemplatesRes>,
                            response: Response<MessageTemplatesRes>
                        ) {
                            if (response.body() != null) {

                                MLDMessageTemplate.postValue(response.body())

                            }
                        }

                        override fun onFailure(call: Call<MessageTemplatesRes>, t: Throwable) {
                            Log.i("exceptionddsfdsfds", t.toString())

                        }
                    })
            } catch (ex: java.lang.Exception) {
                Log.i("exceptionddsfdsfds", ex.toString())
            }
        }

    }


    fun getEmailTemplates(context: Context, token: String?) {

        viewModelScope.launch {
            try {
                ApiUtils.getAPIService(context).getEmailTemplates(
                    token.toString()
                )
                    .enqueue(object : Callback<GetEmailTemplateResponse> {
                        override fun onResponse(
                            call: Call<GetEmailTemplateResponse>,
                            response: Response<GetEmailTemplateResponse>
                        ) {
                            if (response.body() != null) {

                                MLDEmailTemplate.postValue(response.body())

                            }
                        }

                        override fun onFailure(call: Call<GetEmailTemplateResponse>, t: Throwable) {
                            Log.i("exceptionddsfdsfds", t.toString())

                        }
                    })
            } catch (ex: java.lang.Exception) {
                Log.i("exceptionddsfdsfds", ex.toString())
            }
        }

    }

    fun getjobbyJobid(context: Context, token: String?, jobguid: String) {

        viewModelScope.launch {
            try {
                ApiUtils.getAPIService(context).GetJobbyId(
                    token.toString(), jobguid
                )
                    .enqueue(object : Callback<Getjobbyid> {
                        override fun onResponse(
                            call: Call<Getjobbyid>,
                            response: Response<Getjobbyid>
                        ) {
                            if (response.body() != null) {

                                MLDGetJobByJobId.postValue(response.body())

                            }
                        }

                        override fun onFailure(call: Call<Getjobbyid>, t: Throwable) {
                            Log.i("exceptionddsfdsfds", t.toString())

                        }
                    })
            } catch (ex: java.lang.Exception) {
                Log.i("exceptionddsfdsfds", ex.toString())
            }
        }
    }

    fun getTwilioConfigration(context: Context, token: String?) {

        viewModelScope.launch {
            try {
                ApiUtils.getAPIService(context).GetTwilioConfig(
                    token.toString()
                )
                    .enqueue(object : Callback<GetTwilioConfig> {
                        override fun onResponse(
                            call: Call<GetTwilioConfig>,
                            response: Response<GetTwilioConfig>
                        ) {
                            if (response.body() != null) {

                                MLDGetTwilioConfig.postValue(response.body())

                            }
                        }

                        override fun onFailure(call: Call<GetTwilioConfig>, t: Throwable) {
                            Log.i("exceptionddsfdsfds", t.toString())

                        }
                    })
            } catch (ex: java.lang.Exception) {
                Log.i("exceptionddsfdsfds", ex.toString())
            }
        }
    }

    fun sendMessageResponse(
        context: Context,
        token: String?,
        model: sendMesageReqModel,
        binding: ActivityComposeMessageBinding
    ) {

        viewModelScope.launch {
            try {
                ApiUtils.getAPIService(context).sendMessage(
                    token.toString(), model
                )
                    .enqueue(object : Callback<SendMessageResponse> {
                        override fun onResponse(
                            call: Call<SendMessageResponse>,
                            response: Response<SendMessageResponse>
                        ) {
                            if (response.body() != null) {


                                MLDSendMessage.postValue(response.body())

                            }
                            else {
                                val errorBody = response.errorBody()?.string()

                                if (errorBody!!.contains("Invalid 'To' Phone Number:")){
                                    val rootView = binding.root
                                    val duration = Snackbar.LENGTH_SHORT
                                    val snackbar = Snackbar.make(rootView,"Invalid Phone Number", duration)
                                    snackbar.setActionTextColor(
                                        ContextCompat.getColor(
                                            context,
                                            R.color.red
                                        )
                                    )
                                    snackbar.show()
                                }
                                else if (errorBody!!.contains("not configured")){
                                    val rootView = binding.root
                                    val duration = Snackbar.LENGTH_SHORT
                                    val snackbar = Snackbar.make(rootView,"Twillio is not configured", duration)
                                    snackbar.setActionTextColor(
                                        ContextCompat.getColor(
                                            context,
                                            R.color.red
                                        )
                                    )
                                    snackbar.show()
                                }


                            }
                        }

                        override fun onFailure(call: Call<SendMessageResponse>, t: Throwable) {
                            Log.i("exceptionddsfdsfds", t.toString())

                        }
                    })
            } catch (ex: java.lang.Exception) {
                Log.i("exceptionddsfdsfds", ex.toString())
            }
        }
    }


}