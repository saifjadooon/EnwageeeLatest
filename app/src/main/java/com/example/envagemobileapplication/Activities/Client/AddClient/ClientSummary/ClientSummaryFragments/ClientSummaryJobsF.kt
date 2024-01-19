package com.example.envagemobileapplication.Activities.Client.AddClient.ClientSummary.ClientSummaryFragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.envagemobileapplication.Adapters.ClientJobsAdapter
import com.example.envagemobileapplication.Models.RequestModels.SortDirectionJobs
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.GetJobsResponse.GetJobsResponse
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.GetJobsResponse.Record
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.GetJobsStatusesResponse.Datum
import com.example.envagemobileapplication.Oauth.TokenManager
import com.example.envagemobileapplication.Utils.Constants
import com.example.envagemobileapplication.Utils.Loader
import com.example.envagemobileapplication.ViewModels.ClientSummaryViewModel
import com.example.envagemobileapplication.databinding.FragmentClientSummaryJobsBinding
import com.ezshifa.aihealthcare.network.ApiUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ClientSummaryJobsF : Fragment() {
    private var isLoading = false
    private var currentPage = 1
    lateinit var jobsList: ArrayList<com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.GetJobsResponse.Record>
    lateinit var binding: FragmentClientSummaryJobsBinding
    val viewModel: ClientSummaryViewModel by activityViewModels()
    lateinit var tokenmanager: TokenManager
    var statusesidsList: ArrayList<String> =
        ArrayList() // list in which all statuses ids are populated
    var jobsStatusesList: ArrayList<com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.GetJobsStatusesResponse.Datum> =
        ArrayList()
    lateinit var adapter: ClientJobsAdapter
    lateinit var loader: Loader
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment#
        binding = FragmentClientSummaryJobsBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        // getClientJobs()
        initViews()
        observers()
        clickListeners()
    }

    private fun clickListeners() {

        binding.rvClientJobs.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager?
                val totalItemCount = layoutManager!!.itemCount
                val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()
                if (!isLoading && lastVisibleItemPosition == totalItemCount - 1) {
                    // Load more data here

                    currentPage++
                    loadMoreData(currentPage)
                }
            }
        })
        binding.swipeRefreshLayout.setOnRefreshListener {
            currentPage = 1
            getClientJobs()
        }
    }

    private fun initViews() {
        tokenmanager = TokenManager(requireContext())
        loader = Loader(requireContext())
        viewModel.getallJobStatuses(
            requireActivity(),
            tokenmanager.getAccessToken().toString()
        )
    }

    private fun observers() {
        viewModel.LDgetJobStatusResponse.observe(requireActivity())
        {
            jobsStatusesList.clear()
            for (i in 0 until it.data.size) {
                jobsStatusesList.add(it.data.get(i))

                statusesidsList.add(it.data.get(i).jobStatusId.toString())
            }
            try {


                com.example.envagemobileapplication.Utils.Global.JobStatusList =
                    jobsStatusesList

                getClientJobs()

            } catch (e: Exception) {

            }

        }
        viewModel.LDgetJobs.observe(requireActivity()) {

            if (it.data != null) {
                loader.hide()
                if (it.data.records.size != 0) {
                    binding.rvClientJobs.visibility = View.VISIBLE
                    binding.tvNoJobs.visibility = View.GONE
                    jobsList = ArrayList()
                    for (i in 0 until it.data.records.size) {
                        jobsList.add(it.data.records.get(i))
                    }

                    try {
                        setUpJobsAdapter(jobsList, requireContext(), jobsStatusesList)

                    } catch (e: Exception) {
                        // Toast.makeText(requireContext(), e.toString(), Toast.LENGTH_LONG).show()
                    }

                } else {
                    binding.rvClientJobs.visibility = View.INVISIBLE
                    binding.tvNoJobs.visibility = View.VISIBLE
                }
            }


        }
    }


    private fun getClientJobs() {

        loader.show()
        var isfromJobBottomSheet = false
        val model = SortDirectionJobs(
            clientId = Constants.clientid.toString(),
            jobFilters = emptyList(),
            pageIndex = 1,
            pageSize = 25,
            searchText = "",
            sortBy = "CreatedDate",
            sortDirection = 1,
            tileStatusId = -1
        )
        viewModel.getJobs(
            requireActivity(),
            tokenmanager.getAccessToken(),
            model,
            isfromJobBottomSheet
        )

    }

    fun setUpJobsAdapter(
        jobslist: ArrayList<Record>,
        requireContext: Context,
        onBoardingStatusList: ArrayList<Datum>
    ) {

        binding!!.rvClientJobs.setHasFixedSize(true)
        val mLayoutManager = LinearLayoutManager(
            requireContext
        )
        mLayoutManager.reverseLayout = true
        mLayoutManager.stackFromEnd = true
        binding!!.rvClientJobs.layoutManager = LinearLayoutManager(
            requireContext,
            LinearLayoutManager.VERTICAL,
            false
        )

        adapter = ClientJobsAdapter(
            requireContext,
            jobslist,
            childFragmentManager,
            onBoardingStatusList
        )
        binding!!.rvClientJobs.adapter = adapter

        try {
            binding.swipeRefreshLayout.isRefreshing = false
        } catch (e: Exception) {
        }
    }


    private fun loadMoreData(page: Int) {
        loader.show()
        isLoading = true


        val model = SortDirectionJobs(
            clientId = Constants.clientid.toString(),
            jobFilters = emptyList(),
            pageIndex = page,
            pageSize = 25,
            searchText = "",
            sortBy = "CreatedDate",
            sortDirection = 1,
            tileStatusId = -1
        )

        try {
            ApiUtils.getAPIService(requireContext()).getJobs(
                model,
                tokenmanager.getAccessToken().toString(),
            )
                .enqueue(object : Callback<GetJobsResponse> {
                    override fun onResponse(
                        call: Call<GetJobsResponse>,
                        response: Response<GetJobsResponse>
                    ) {
                        loader.hide()
                        if (response.body() != null) {

                            for (i in 0 until response.body()!!.data.records.size) {
                                jobsList.add(response.body()!!.data.records.get(i))
                            }

                            adapter.notifyDataSetChanged()
                        }
                    }

                    override fun onFailure(call: Call<GetJobsResponse>, t: Throwable) {
                        Log.i("exceptionddsfdsfds", t.toString())
                        loader.hide()
                    }
                })
        } catch (ex: java.lang.Exception) {
            Log.i("exceptionddsfdsfds", ex.toString())
            loader.hide()
        }

    }
}