package com.example.envagemobileapplication.ViewModels

import android.app.Activity
import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.envagemobileapplication.Models.RequestModels.SortDirectionClientContacts
import com.example.envagemobileapplication.Models.RequestModels.SortDirectionGetOnlineCandidate
import com.example.envagemobileapplication.Models.RequestModels.SortDirectionJobRequisition
import com.example.envagemobileapplication.Models.RequestModels.SortDirectionJobs
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.GetAllOfferLetterResp.GetAllOfferLetterResp
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.GetClientContactsResponse.GetClientContactsResponse
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.GetIndustryListResponse.GetIndustryListResponse
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.GetJobRequests.GetJobRequests
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.GetJobsResponse.GetJobsResponse
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.GetJobsStatusesResponse.GetJobsStatusesResponse
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.JobRequsitionStatusResp.JobRequsitionStatusResp
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.getJobSkills.GetJobSkills
import com.ezshifa.aihealthcare.network.ApiUtils
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ClientSummaryViewModel : ViewModel() {
    val LDgetOfferLetters: LiveData<GetAllOfferLetterResp>
        get() = MLDgetOfferLetters

    private val MLDgetOfferLetters = MutableLiveData<GetAllOfferLetterResp>()
    val LDgetContacts: LiveData<GetClientContactsResponse>
        get() = MLDGetContacts

    private val MLDGetContacts = MutableLiveData<GetClientContactsResponse>()


    val LDgetJobReqs: LiveData<GetJobRequests>
        get() = MLDJobReqs

    private val MLDJobReqs = MutableLiveData<GetJobRequests>()

    val LDgetJobs: LiveData<GetJobsResponse>
        get() = MLDGetJobs

    private val MLDGetJobs = MutableLiveData<GetJobsResponse>()

    val LDgetJobStatusResponse: LiveData<GetJobsStatusesResponse>
        get() = MLDgetJobStatusResponse

    private val MLDgetJobStatusResponse =
        MutableLiveData<GetJobsStatusesResponse>()

    val LDgetIndustry: LiveData<GetIndustryListResponse>
        get() = MLDgetIndustry
    private val MLDgetIndustry = MutableLiveData<GetIndustryListResponse>()


    val LDgetJobSkills: LiveData<GetJobSkills>
        get() = MLDgetJobSkills
    private val MLDgetJobSkills = MutableLiveData<GetJobSkills>()

    val LDgetJobRequsitionStatusResp: LiveData<JobRequsitionStatusResp>
        get() = MLDgetJobRequsitionStatusResp
    private val MLDgetJobRequsitionStatusResp = MutableLiveData<JobRequsitionStatusResp>()

    val LDcloseChangeStatusBs: LiveData<String>
        get() = MLDcloseChangeStatusBs
    private val MLDcloseChangeStatusBs = MutableLiveData<String>()

    //--------------->>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>><<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<///

    fun getallOfferLetters(
        context: Activity,
        accessToken: String?,
        sortDirection: SortDirectionGetOnlineCandidate
    ) {
        viewModelScope.launch {
            try {
                ApiUtils.getAPIService(context).getAllOfferLetters(
                    sortDirection,
                    accessToken.toString(),
                )
                    .enqueue(object : Callback<GetAllOfferLetterResp> {
                        override fun onResponse(
                            call: Call<GetAllOfferLetterResp>,
                            response: Response<GetAllOfferLetterResp>
                        ) {
                            if (response.body() != null) {

                                MLDgetOfferLetters.postValue(response.body())

                            }
                        }

                        override fun onFailure(call: Call<GetAllOfferLetterResp>, t: Throwable) {
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

    fun getContacts(
        context: Activity,
        accessToken: String?,
        sortDirection: SortDirectionClientContacts,


        ) {
        viewModelScope.launch {
            try {
                ApiUtils.getAPIService(context).getClientContacts(
                    sortDirection,
                    accessToken.toString(),
                )
                    .enqueue(object : Callback<GetClientContactsResponse> {
                        override fun onResponse(
                            call: Call<GetClientContactsResponse>,
                            response: Response<GetClientContactsResponse>
                        ) {
                            if (response.body() != null) {

                                MLDGetContacts.postValue(response.body())
                            }
                        }

                        override fun onFailure(
                            call: Call<GetClientContactsResponse>,
                            t: Throwable
                        ) {
                            Log.i("exceptionddsfdsfds", t.toString())

                        }
                    })
            } catch (ex: java.lang.Exception) {
                Log.i("exceptionddsfdsfds", ex.toString())
            }
        }
    }

    fun getJobRequests(
        context: Activity,
        accessToken: String?,
        sortDirection: SortDirectionJobRequisition,


        ) {
        viewModelScope.launch {
            try {
                ApiUtils.getAPIService(context).getJobRequests(
                    sortDirection,
                    accessToken.toString(),
                )
                    .enqueue(object : Callback<GetJobRequests> {
                        override fun onResponse(
                            call: Call<GetJobRequests>,
                            response: Response<GetJobRequests>
                        ) {
                            if (response.body() != null) {

                                MLDJobReqs.postValue(response.body())
                            }
                        }

                        override fun onFailure(
                            call: Call<GetJobRequests>,
                            t: Throwable
                        ) {
                            Log.i("exceptionddsfdsfds", t.toString())

                        }
                    })
            } catch (ex: java.lang.Exception) {
                Log.i("exceptionddsfdsfds", ex.toString())
            }
        }
    }


    fun getallJobStatuses(
        context: Activity,
        accessToken: String?
    ) {
        viewModelScope.launch {
            try {
                ApiUtils.getAPIService(context).GetJobStatuses(

                    accessToken.toString(),
                )
                    .enqueue(object : Callback<GetJobsStatusesResponse> {
                        override fun onResponse(
                            call: Call<GetJobsStatusesResponse>,
                            response: Response<GetJobsStatusesResponse>
                        ) {
                            if (response.body() != null) {
                                MLDgetJobStatusResponse.postValue(response.body())
                            }
                        }

                        override fun onFailure(
                            call: Call<GetJobsStatusesResponse>,
                            t: Throwable
                        ) {
                            Log.i("exceptionddsfdsfds", t.toString())

                        }
                    })
            } catch (ex: java.lang.Exception) {
                Log.i("exceptionddsfdsfds", ex.toString())
            }
        }

    }

    fun searchDataApi() {
        TODO("Not yet implemented")
    }

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

    fun getjobskills(context: Context, token: String, jobrequestid: Int) {

        viewModelScope.launch {
            try {
                ApiUtils.getAPIService(context).getjobskills(
                    token, jobrequestid.toString()
                )
                    .enqueue(object : Callback<GetJobSkills> {
                        override fun onResponse(
                            call: Call<GetJobSkills>,
                            response: Response<GetJobSkills>
                        ) {
                            if (response.body() != null) {
                                MLDgetJobSkills.postValue(response.body())
                            }
                        }

                        override fun onFailure(call: Call<GetJobSkills>, t: Throwable) {
                            Log.i("exc", t.toString())

                        }
                    })
            } catch (ex: java.lang.Exception) {
                Log.i("exceptionddsfdsfds", ex.toString())
            }
        }

    }

    fun getjobReqStatuses(context: Context, token: String, jobrequestid: Int) {

        viewModelScope.launch {
            try {
                ApiUtils.getAPIService(context).getJobRequsitionStatuses(
                    token
                )
                    .enqueue(object : Callback<JobRequsitionStatusResp> {
                        override fun onResponse(
                            call: Call<JobRequsitionStatusResp>,
                            response: Response<JobRequsitionStatusResp>
                        ) {
                            if (response.body() != null) {
                                MLDgetJobRequsitionStatusResp.postValue(response.body())
                            }
                        }

                        override fun onFailure(call: Call<JobRequsitionStatusResp>, t: Throwable) {
                            Log.i("exc", t.toString())

                        }
                    })
            } catch (ex: java.lang.Exception) {
                Log.i("exceptionddsfdsfds", ex.toString())
            }
        }

    }

    fun closeChangeStatusBottomsheet(statusname: String) {
        MLDcloseChangeStatusBs.postValue(statusname)
    }

}