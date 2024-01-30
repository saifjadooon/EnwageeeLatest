package com.example.envagemobileapplication.ViewModels

import android.content.Context
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.envagemobileapplication.Models.RequestModels.GetFltrDtaBulkMsgRm
import com.example.envagemobileapplication.Models.RequestModels.UpdateCandidateStatusRequestModel
import com.example.envagemobileapplication.Models.RequestModels.sendMesageReqModel
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.CandidateJobStatusRes.CandidateJobStatusRes
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.MessageTemplatesRes.MessageTemplatesRes
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.SearchClientByNameResp.SearchClientByNameResp
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.UpdateCandidateResponse.UpdateCandidateResponse
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.getBulkMsgFilterdResp.GetBulkMsgFilterdResp
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.getClientJobsResp.GetClientJobsResp
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.getTwilioConfig.GetTwilioConfig
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.sendMessageResponse.SendMessageResponse
import com.example.envagemobileapplication.R
import com.example.envagemobileapplication.databinding.ActivityComposeMessageBinding
import com.example.envagemobileapplication.databinding.FragmentSendBulkMessageBinding
import com.ezshifa.aihealthcare.network.ApiUtils
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BulkMessagesViewModel : ViewModel(){

    val LDSendMessage: LiveData<SendMessageResponse>
        get() = MLDSendMessage
    private val MLDSendMessage = MutableLiveData<SendMessageResponse>()

    val LDgetCandidateJobStatuses: LiveData<CandidateJobStatusRes> get() = MLDgetCandidateJobStatuses
    private val MLDgetCandidateJobStatuses = MutableLiveData<CandidateJobStatusRes>()

    val LDGetBulkMsgFilterdResp: LiveData<GetBulkMsgFilterdResp> get() = MLDGetBulkMsgFilterdResp
    private val MLDGetBulkMsgFilterdResp = MutableLiveData<GetBulkMsgFilterdResp>()

    val LDSearchClientByName: LiveData<SearchClientByNameResp>
        get() = MLDSearchClientByName

    private val MLDSearchClientByName = MutableLiveData<SearchClientByNameResp>()

    val LDGetClientJobs: LiveData<GetClientJobsResp>
        get() = MLDGetClientJobs

    private val MLDGetClientJobs = MutableLiveData<GetClientJobsResp>()

    val LDGetTwilioConfig: LiveData<GetTwilioConfig>
        get() = MLDGetTwilioConfig
    private val MLDGetTwilioConfig = MutableLiveData<GetTwilioConfig>()

    val LDMessageTemplate: LiveData<MessageTemplatesRes>
        get() = MLDMessageTemplate
    private val MLDMessageTemplate = MutableLiveData<MessageTemplatesRes>()

    //================>>>>>>><<<<<<<<<<<</////////////////////



    fun searhClientbyName(context: Context, token: String) {
        viewModelScope.launch {
            try {
                ApiUtils.getAPIService(context).SearchClientbyName( token.toString(),
                )
                    .enqueue(object : Callback<SearchClientByNameResp> {
                        override fun onResponse(
                            call: Call<SearchClientByNameResp>,
                            response: Response<SearchClientByNameResp>
                        ) {

                            if (response.body() != null) {

                                MLDSearchClientByName.postValue(response.body())

                            }
                        }

                        override fun onFailure(call: Call<SearchClientByNameResp>, t: Throwable) {
                            Log.i("exceptionddsfdsfds", t.toString())

                        }
                    })
            } catch (ex: java.lang.Exception) {
                Log.i("exceptionddsfdsfds", ex.toString())
            }
        }
    }

    fun getclientJobs(context: Context, token: String, clientid: Int) {

        viewModelScope.launch {
            try {
                ApiUtils.getAPIService(context).getClientJobs( token.toString(),clientid
                )
                    .enqueue(object : Callback<GetClientJobsResp> {
                        override fun onResponse(
                            call: Call<GetClientJobsResp>,
                            response: Response<GetClientJobsResp>
                        ) {

                            if (response.body() != null) {

                                MLDGetClientJobs.postValue(response.body())

                            }
                        }

                        override fun onFailure(call: Call<GetClientJobsResp>, t: Throwable) {
                            Log.i("exceptionddsfdsfds", t.toString())

                        }
                    })
            } catch (ex: java.lang.Exception) {
                Log.i("exceptionddsfdsfds", ex.toString())
            }
        }

    }

    fun getCandidateJobStatuses(requireContext: Context, token: String?) {
        viewModelScope.launch {
            try {
                ApiUtils.getAPIService(requireContext)
                    .GetCandidateStatuses(
                        token.toString()
                    )
                    .enqueue(object : Callback<CandidateJobStatusRes> {
                        override fun onResponse(
                            call: Call<CandidateJobStatusRes>,
                            response: Response<CandidateJobStatusRes>
                        ) {
                            if (response.body() != null) {
                                MLDgetCandidateJobStatuses.postValue(response.body())
                            }
                        }

                        override fun onFailure(call: Call<CandidateJobStatusRes>, t: Throwable) {

                        }
                    })
            } catch (ex: java.lang.Exception) {

            }
        }
    }

    fun getMultiStatusCandidateData(requireContext: Context, token: String, model: GetFltrDtaBulkMsgRm) {
        viewModelScope.launch {
            try {
                ApiUtils.getAPIService(requireContext)
                    .getBulkMsgFiltered(
                        token.toString(),model
                    )
                    .enqueue(object : Callback<GetBulkMsgFilterdResp> {
                        override fun onResponse(
                            call: Call<GetBulkMsgFilterdResp>,
                            response: Response<GetBulkMsgFilterdResp>
                        ) {
                            if (response.body() != null) {
                                MLDGetBulkMsgFilterdResp.postValue(response.body())
                            }
                        }

                        override fun onFailure(call: Call<GetBulkMsgFilterdResp>, t: Throwable) {

                        }
                    })
            } catch (ex: java.lang.Exception) {

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

    fun getCustomTemplatesForMessages(context: Context, token: String?) {
        viewModelScope.launch {
            try {
                ApiUtils.getAPIService(context).getCandidateMessagesTemplate(
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


    fun sendMessageResponse(
        context: Context,
        token: String?,
        model: sendMesageReqModel,
        binding: FragmentSendBulkMessageBinding
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
                            } else {
                                val errorBody = response.errorBody()?.string()
                                if (errorBody!!.contains("Invalid 'To' Phone Number:")){
                                    val rootView = binding.root
                                    val duration = Snackbar.LENGTH_SHORT
                                    val snackbar = Snackbar.make(rootView,"Invalid Phone Number", duration)
                                    snackbar.setActionTextColor(ContextCompat.getColor(context, R.color.red))
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