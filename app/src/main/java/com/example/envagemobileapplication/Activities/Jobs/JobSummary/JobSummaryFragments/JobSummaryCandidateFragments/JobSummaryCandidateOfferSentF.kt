package com.example.envagemobileapplication.Activities.Jobs.JobSummary.JobSummaryFragments.JobSummaryCandidateFragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.envagemobileapplication.Adapters.OfferLetterAdapter
import com.example.envagemobileapplication.Models.RequestModels.SortDirectionGetOnlineCandidate
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.GetAllOfferLetterResp.GetAllOfferLetterResp
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.GetAllOfferLetterResp.Record
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.OnlineApplicantsResponse.OnlineApplicantsResponse
import com.example.envagemobileapplication.Oauth.TokenManager
import com.example.envagemobileapplication.Utils.Global
import com.example.envagemobileapplication.Utils.Loader
import com.example.envagemobileapplication.ViewModels.JobSummaryCandidateViewModel
import com.example.envagemobileapplication.databinding.FragmentJobSummaryCandidateOfferSentBinding
import com.ezshifa.aihealthcare.network.ApiUtils
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class JobSummaryCandidateOfferSentF : Fragment() {
    private var totalOffersSendListcount: Int? = 0
    var global = com.example.envagemobileapplication.Utils.Global
    private var isfromswipeRefresh: Boolean = false
    lateinit var adapter: OfferLetterAdapter
    lateinit var token: String
    private var currentPage = 1
    lateinit var tokenManager: TokenManager
    val viewModel: JobSummaryCandidateViewModel by activityViewModels()
    lateinit var loader: Loader
    private var isLoading = false
    lateinit var binding: FragmentJobSummaryCandidateOfferSentBinding
    lateinit var totalOfferLetterList: ArrayList<Record>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding =
            FragmentJobSummaryCandidateOfferSentBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (isAdded) {
            initViews()
            clicklisteners()
            observers()
            networkCalls()
        }

    }

    private fun networkCalls() {
        val model = SortDirectionGetOnlineCandidate(
            applicantFilter = emptyList(),
            jobId = Global.GlobalJobID!!,
            pageIndex = 1,
            pageSize = 25,
            searchText = "",
            sortBy = "CreatedDate",
            sortDirection = 1
        )
        loader.show()

        viewModel.getallOfferLetters(requireActivity(), token, model)
    }

    private fun observers() {

        viewModel.LDgetOfferLetters.observe(requireActivity()) {
            loader.hide()
            if (it != null) {
                if (isfromswipeRefresh) {
                    binding.swipeRefreshLayout.isRefreshing = false
                }
                if (it.data.records.size >= 1) {

                    totalOffersSendListcount = it.data.totalRecords
                    binding.tvNoApplicant.visibility = View.GONE
                    binding.rvOfferLetters.visibility = View.VISIBLE
                    totalOfferLetterList = ArrayList()
                    for (i in 0 until it.data.records.size) {
                        totalOfferLetterList.add(it.data.records.get(i))
                    }
                    setupOnlineApplicantAdapter(totalOfferLetterList)
                } else {
                    binding.tvNoApplicant.visibility = View.VISIBLE
                    binding.rvOfferLetters.visibility = View.GONE
                }
            }
        }
    }

    private fun clicklisteners() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            isfromswipeRefresh = true
            /*1802*/
            val model = SortDirectionGetOnlineCandidate(
                applicantFilter = emptyList(),
                jobId = Global.GlobalJobID!!,
                pageIndex = 1,
                pageSize = 25,
                searchText = "",
                sortBy = "CreatedDate",
                sortDirection = 1
            )
            loader.show()

            viewModel.getallOfferLetters(requireActivity(), token, model)

        }

        binding.rvOfferLetters.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)


                // Scrolling downward
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager?
                val totalItemCount = layoutManager!!.itemCount
                val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()

                if (!isLoading && lastVisibleItemPosition == totalItemCount - 1) {
                    // Load more data here
                    currentPage++
                    if (totalItemCount < totalOffersSendListcount!!) {
                        loadMoreData(currentPage)
                    }

                }

            }
        })

    }


    private fun initViews() {
        loader = Loader(requireContext())
        tokenManager = TokenManager(requireContext())
        token = tokenManager.getAccessToken().toString()
   }


    private fun setupOnlineApplicantAdapter(onlineApplicantsList: ArrayList<Record>) {

        try {
            binding.rvOfferLetters.setHasFixedSize(true)

            adapter = OfferLetterAdapter(
                requireContext(),
                onlineApplicantsList, viewModel,childFragmentManager
            )


            binding.rvOfferLetters.layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.VERTICAL,
                false
            )

            binding.rvOfferLetters.adapter = adapter
        } catch (e: Exception) {

        }

    }

    private fun loadMoreData(page: Int) {

        loader.show()
        isLoading = true

        val model = SortDirectionGetOnlineCandidate(
            applicantFilter = emptyList(),
            jobId = com.example.envagemobileapplication.Utils.Global.GlobalJobID!!,
            pageIndex = page,
            pageSize = 25,
            searchText = "",
            sortBy = "CreatedDate",
            sortDirection = 1
        )
        try {
            lifecycleScope.launch {
                try {
                    ApiUtils.getAPIService(requireContext()).getAllOfferLetters(
                        model,
                        token.toString(),
                    )
                        .enqueue(object : Callback<GetAllOfferLetterResp> {
                            override fun onResponse(
                                call: Call<GetAllOfferLetterResp>,
                                response: Response<GetAllOfferLetterResp>
                            ) {
                                if (response.body() != null) {

                                    loader.hide()
                                    for (i in 0 until response.body()!!.data.records.size) {
                                        totalOfferLetterList.add(
                                            response.body()!!.data.records.get(
                                                i
                                            )
                                        )
                                    }
                                    //  currentPage++
                                    isLoading = false
                                    adapter.notifyDataSetChanged()

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


        } catch (ex: java.lang.Exception) {
            loader.hide()
            Log.i("exception", ex.toString())
        }
    }
}