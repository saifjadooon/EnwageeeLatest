package com.example.envagemobileapplication.ViewModels

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.envagemobileapplication.Models.RequestModels.PaygroupRequestModel
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.GetIndustryListResponse.GetIndustryListResponse
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.GetJobRequestResp.GetJobRequestResp
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.PayGroupResponse.PayGroupResponse
import com.ezshifa.aihealthcare.network.ApiUtils
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class JobRequisitionViewModel  : ViewModel(){

    val LDgetIndustry: LiveData<GetIndustryListResponse>
        get() = MLDgetIndustry
    private val MLDgetIndustry = MutableLiveData<GetIndustryListResponse>()

    val LDgetJobRequestResp: LiveData<GetJobRequestResp>
        get() = MLDgetJobRequestResp
    private val MLDgetJobRequestResp = MutableLiveData<GetJobRequestResp>()

    val LDpayGroup: LiveData<PayGroupResponse>
        get() = MLDpayGroup
    private val MLDpayGroup = MutableLiveData<PayGroupResponse>()



    ///===================>>>>>>>>>>>>>>>>><<<<<<<<<<<<<<<<<<<======//

    fun getindustry(context: Context, token: String) {


        viewModelScope.launch {
            try {
                ApiUtils.getAPIService(context).getIndustry(
                    token
                )
                    .enqueue(object : Callback<GetIndustryListResponse> {
                        override fun onResponse(
                            call: Call<GetIndustryListResponse>,
                            response: Response<GetIndustryListResponse>
                        ) {
                            if (response.body() != null) {
                                MLDgetIndustry.postValue(response.body())
                            }
                        }

                        override fun onFailure(call: Call<GetIndustryListResponse>, t: Throwable) {
                            Log.i("exc", t.toString())

                        }
                    })
            } catch (ex: java.lang.Exception) {
                Log.i("exceptionddsfdsfds", ex.toString())
            }
        }

    }

    fun getjobReqByID(context: Context, token: String,jobid:String) {


        viewModelScope.launch {
            try {
                ApiUtils.getAPIService(context).GetJobRequestbyid(
                    token,jobid.toInt()
                )
                    .enqueue(object : Callback<GetJobRequestResp> {
                        override fun onResponse(
                            call: Call<GetJobRequestResp>,
                            response: Response<GetJobRequestResp>
                        ) {
                            if (response.body() != null) {
                                MLDgetJobRequestResp.postValue(response.body())
                            }
                        }

                        override fun onFailure(call: Call<GetJobRequestResp>, t: Throwable) {
                            Log.i("exc", t.toString())

                        }
                    })
            } catch (ex: java.lang.Exception) {
                Log.i("exceptionddsfdsfds", ex.toString())
            }
        }

    }

    fun searchPayGroupApi(context: Context, token: String, model: PaygroupRequestModel) {
        viewModelScope.launch {
            try {
                ApiUtils.getAPIService(context).searchPaygroup(
                    token, model
                )
                    .enqueue(object : Callback<PayGroupResponse> {
                        override fun onResponse(
                            call: Call<PayGroupResponse>,
                            response: Response<PayGroupResponse>
                        ) {
                            if (response.body() != null) {
                                MLDpayGroup.postValue(response.body())
                            }
                        }

                        override fun onFailure(call: Call<PayGroupResponse>, t: Throwable) {
                            Log.i("exc", t.toString())

                        }
                    })
            } catch (ex: java.lang.Exception) {
                Log.i("exceptionddsfdsfds", ex.toString())
            }
        }

    }
}