package com.example.envagemobileapplication.Activities.Candidates.CandidateFragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.envagemobileapplication.Adapters.CandidateAssesmentsAdapter
import com.example.envagemobileapplication.Models.RequestModels.CandidateAssesmentRequestModel
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.GetAssesmentResp.GetAssesmentResp
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.GetAssesmentResp.Record
import com.example.envagemobileapplication.Oauth.TokenManager
import com.example.envagemobileapplication.Utils.Constants
import com.example.envagemobileapplication.Utils.Loader
import com.example.envagemobileapplication.databinding.FragmentCandidateAssesmentsBinding
import com.ezshifa.aihealthcare.network.ApiUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CandidateAssesmentsF : Fragment() {
    private var currentPage = 1
    lateinit var adapter: CandidateAssesmentsAdapter
    lateinit var assesmentsList: ArrayList<com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.GetAssesmentResp.Record>
    lateinit var token: String
    lateinit var tokenManager: TokenManager
    lateinit var loader: Loader
    lateinit var binding: FragmentCandidateAssesmentsBinding
    private var isLoading = false
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentCandidateAssesmentsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loader = Loader(requireContext())
        tokenManager = TokenManager(requireContext())
        token = tokenManager.getAccessToken().toString()


        clickListeners()
        networkCalls()

    }

    private fun networkCalls() {


        var guid = Constants.candidateId.toString()
        val model = CandidateAssesmentRequestModel(
            candidateFilters = emptyList(),
            candidateGUID = guid,
            pageIndex = 1,
            pageSize = 25,
            searchText = "",
            sortBy = "CreatedDate",
            sortDirection = 1
        )

        try {

            loader.show()
            assesmentsList = ArrayList()
            ApiUtils.getAPIService(requireContext()).getCandidateAssesmentForms(
                tokenManager.getAccessToken().toString(),
                model,

                )
                .enqueue(object : Callback<GetAssesmentResp> {
                    override fun onResponse(
                        call: Call<GetAssesmentResp>,
                        response: Response<GetAssesmentResp>
                    ) {
                        loader.hide()
                        if (response.body() != null) {

                            if (response.body()!!.data != null) {


                                for (i in 0 until response.body()!!.data.records.size) {

                                    assesmentsList.add(response.body()!!.data.records.get(i))
                                }
                                setupCandidateAdapter(assesmentsList)

                            }
                        }
                    }

                    override fun onFailure(call: Call<GetAssesmentResp>, t: Throwable) {
                        loader.show()
                        Log.i("exceptionddsfdsfds", t.toString())

                    }
                })
        } catch (ex: java.lang.Exception) {
            loader.show()
            Log.i("exceptionddsfdsfds", ex.toString())
        }
    }

    private fun clickListeners() {

        binding.swipeRefreshLayoutforemp.setOnRefreshListener {
            assesmentsList = ArrayList()
            var guid = Constants.candidateId.toString()
            val model = CandidateAssesmentRequestModel(
                candidateFilters = emptyList(),
                candidateGUID = guid,
                pageIndex = 1,
                pageSize = 25,
                searchText = "",
                sortBy = "CreatedDate",
                sortDirection = 1
            )

            try {

                loader.show()
                ApiUtils.getAPIService(requireContext()).getCandidateAssesmentForms(
                    tokenManager.getAccessToken().toString(),
                    model,

                    )
                    .enqueue(object : Callback<GetAssesmentResp> {
                        override fun onResponse(
                            call: Call<GetAssesmentResp>,
                            response: Response<GetAssesmentResp>
                        ) {
                            loader.hide()
                            binding.swipeRefreshLayoutforemp.isRefreshing = false
                            if (response.body() != null) {

                                if (response.body()!!.data != null) {


                                    for (i in 0 until response.body()!!.data.records.size) {

                                        assesmentsList.add(response.body()!!.data.records.get(i))
                                    }
                                    setupCandidateAdapter(assesmentsList)

                                }
                            }
                        }

                        override fun onFailure(call: Call<GetAssesmentResp>, t: Throwable) {
                            binding.swipeRefreshLayoutforemp.isRefreshing = false
                            loader.hide()
                            Log.i("exceptionddsfdsfds", t.toString())

                        }
                    })
            } catch (ex: java.lang.Exception) {
                binding.swipeRefreshLayoutforemp.isRefreshing = false
                loader.hide()
                Log.i("exceptionddsfdsfds", ex.toString())
            }
        }
        binding.rvCandidateAssesments.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager?
                val totalItemCount = layoutManager!!.itemCount
                val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()

                if (lastVisibleItemPosition == totalItemCount - 1) {
                    currentPage++
                    loadMoreData(currentPage)
                }
            }
        })
    }

    private fun setupCandidateAdapter(jobCandidateList: ArrayList<Record>) {

        try {
            binding.rvCandidateAssesments.setHasFixedSize(true)

            adapter = CandidateAssesmentsAdapter(
                requireContext(),
                jobCandidateList,childFragmentManager)

            binding.rvCandidateAssesments.layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.VERTICAL,
                false
            )

            binding.rvCandidateAssesments.adapter = adapter
        } catch (e: Exception) {

        }

    }

    private fun loadMoreData(page: Int) {

        loader.show()
        isLoading = true
        var guid = Constants.candidateId.toString()
        val model = CandidateAssesmentRequestModel(
            candidateFilters = emptyList(),
            candidateGUID = guid,
            pageIndex = page,
            pageSize = 25,
            searchText = "",
            sortBy = "CreatedDate",
            sortDirection = 1
        )

        try {

            loader.show()
            ApiUtils.getAPIService(requireContext()).getCandidateAssesmentForms(
                tokenManager.getAccessToken().toString(),
                model,

                )
                .enqueue(object : Callback<GetAssesmentResp> {
                    override fun onResponse(
                        call: Call<GetAssesmentResp>,
                        response: Response<GetAssesmentResp>
                    ) {
                        loader.hide()
                        if (response.body() != null) {

                            if (response.body()!!.data != null) {


                                for (i in 0 until response.body()!!.data.records.size) {

                                    assesmentsList.add(response.body()!!.data.records.get(i))
                                }

                                adapter.notifyDataSetChanged()
                              /*  setupCandidateAdapter(assesmentsList)*/

                            }
                        }
                    }

                    override fun onFailure(call: Call<GetAssesmentResp>, t: Throwable) {
                        loader.show()
                        Log.i("exceptionddsfdsfds", t.toString())

                    }
                })
        } catch (ex: java.lang.Exception) {
            loader.show()
            Log.i("exceptionddsfdsfds", ex.toString())
        }


    }

}