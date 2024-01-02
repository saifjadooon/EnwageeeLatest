package com.example.envagemobileapplication.ViewModels

import android.app.Activity
import android.content.Context
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.envagemobileapplication.Models.RequestModels.GetAssessmentFormsRM
import com.example.envagemobileapplication.Models.RequestModels.SendAssessmentRequestModel
import com.example.envagemobileapplication.Models.RequestModels.SortDirectionJobs
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.GetAssesmentForms.GetAssessmentForms
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.GetJobHeaderSummary.GetJobHeaderSummary
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.GetJobsResponse.GetJobsResponse
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.SendAssessmentResponse.SendAssessmentResponse
import com.ezshifa.aihealthcare.network.ApiUtils
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class JobSummaryViewModel : ViewModel() {

    val LDgetJobs: LiveData<GetJobsResponse>
        get() = MLDGetJobs

    private val MLDGetJobs = MutableLiveData<GetJobsResponse>()

    val LDgetJobHeaderSummary: LiveData<GetJobHeaderSummary>
        get() = MLDgetJobHeaderSummary

    private val MLDgetJobHeaderSummary = MutableLiveData<GetJobHeaderSummary>()


    val LDgetAssessmentForms: LiveData<GetAssessmentForms>
        get() = MLDgetAssessmentForms

    private val MLDgetAssessmentForms = MutableLiveData<GetAssessmentForms>()

    val LDsendAssessmentResponse: LiveData<SendAssessmentResponse>
        get() = MLDsendAssessmentResponse

    private val MLDsendAssessmentResponse = MutableLiveData<SendAssessmentResponse>()

    val LDcloseBottomSheetFlag: LiveData<Boolean>
        get() = MLDcloseBottomSheetFlag

    private val MLDcloseBottomSheetFlag = MutableLiveData<Boolean>()


    //=======>>>>>>>>>>><<<<<<<<<<<<<<<<<////
    fun getAssessmentForms(fragment: Fragment, token: String, payload: GetAssessmentFormsRM?) {
        viewModelScope.launch {
            try {
                ApiUtils.getAPIService(fragment.requireContext()).GetAssessmentForms(
                    token,
                    payload!!
                )
                    .enqueue(object : Callback<GetAssessmentForms> {
                        override fun onResponse(
                            call: Call<GetAssessmentForms>,
                            response: Response<GetAssessmentForms>
                        ) {
                            if (response.body() != null) {

                                MLDgetAssessmentForms.postValue(response.body())

                            }
                        }

                        override fun onFailure(call: Call<GetAssessmentForms>, t: Throwable) {
                            Log.i("exceptionddsfdsfds", t.toString())

                        }
                    })
            } catch (ex: java.lang.Exception) {
                Log.i("exceptionddsfdsfds", ex.toString())
            }
        }
    }

    fun sendAssessmentForm(fragment: Context, token: String, payload: SendAssessmentRequestModel) {

        viewModelScope.launch {
            try {
                ApiUtils.getAPIService(fragment).SendAssessmentForm(
                    token,
                    payload!!
                )
                    .enqueue(object : Callback<SendAssessmentResponse> {
                        override fun onResponse(
                            call: Call<SendAssessmentResponse>,
                            response: Response<SendAssessmentResponse>
                        ) {
                            if (response.body() != null) {

                                MLDsendAssessmentResponse.postValue(response.body())

                            }
                        }

                        override fun onFailure(call: Call<SendAssessmentResponse>, t: Throwable) {
                            Log.i("exceptionddsfdsfds", t.toString())

                        }
                    })
            } catch (ex: java.lang.Exception) {
                Log.i("exceptionddsfdsfds", ex.toString())
            }
        }
    }

    fun getJobHeaderSummary(context: Activity, token: String, jobGuid: String?) {
        viewModelScope.launch {
            try {
                ApiUtils.getAPIService(context).GetJobHeaderSummary(
                    token,
                    jobGuid!!
                )
                    .enqueue(object : Callback<GetJobHeaderSummary> {
                        override fun onResponse(
                            call: Call<GetJobHeaderSummary>,
                            response: Response<GetJobHeaderSummary>
                        ) {
                            if (response.body() != null) {

                                MLDgetJobHeaderSummary.postValue(response.body())

                            }
                        }

                        override fun onFailure(call: Call<GetJobHeaderSummary>, t: Throwable) {
                            Log.i("exceptionddsfdsfds", t.toString())

                        }
                    })
            } catch (ex: java.lang.Exception) {
                Log.i("exceptionddsfdsfds", ex.toString())
            }
        }
    }

    fun getJobs(
        context: Activity,
        accessToken: String?,
        sortDirection: SortDirectionJobs,
        isfromJobBottomSheet: Boolean,


        ) {
        viewModelScope.launch {
            try {
                ApiUtils.getAPIService(context).getJobs(
                    sortDirection,
                    accessToken.toString(),
                )
                    .enqueue(object : Callback<GetJobsResponse> {
                        override fun onResponse(
                            call: Call<GetJobsResponse>,
                            response: Response<GetJobsResponse>
                        ) {
                            if (response.body() != null) {

                                MLDGetJobs.postValue(response.body())

                            }
                        }

                        override fun onFailure(call: Call<GetJobsResponse>, t: Throwable) {
                            Log.i("exceptionddsfdsfds", t.toString())

                        }
                    })
            } catch (ex: java.lang.Exception) {
                Log.i("exceptionddsfdsfds", ex.toString())
            }
        }
    }

}