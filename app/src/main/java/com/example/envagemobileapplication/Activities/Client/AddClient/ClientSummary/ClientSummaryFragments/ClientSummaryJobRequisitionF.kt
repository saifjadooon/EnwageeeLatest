package com.example.envagemobileapplication.Activities.Client.AddClient.ClientSummary.ClientSummaryFragments

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.envagemobileapplication.Adapters.JobRequsitionAdapter
import com.example.envagemobileapplication.Models.RequestModels.SortDirectionJobRequisition
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.GetJobRequests.GetJobRequests
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.GetJobRequests.Record
import com.example.envagemobileapplication.Oauth.TokenManager
import com.example.envagemobileapplication.R
import com.example.envagemobileapplication.Utils.Constants
import com.example.envagemobileapplication.Utils.Loader
import com.example.envagemobileapplication.ViewModels.ClientSummaryViewModel
import com.example.envagemobileapplication.databinding.FragmentClientSummaryContactsBinding
import com.example.envagemobileapplication.databinding.FragmentClientSummaryJobRequisitionBinding
import com.ezshifa.aihealthcare.network.ApiUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ClientSummaryJobRequisitionF : Fragment() {
    private val handler = Handler(Looper.getMainLooper())
    private var isfirsttimesearched: Boolean = false
    lateinit var adapter: JobRequsitionAdapter
    lateinit var jobreqList: ArrayList<com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.GetJobRequests.Record>
    lateinit var model: SortDirectionJobRequisition
    private var searchviewtext: String = ""
    lateinit var loader: Loader
    lateinit var binding: FragmentClientSummaryJobRequisitionBinding
    val viewModel: ClientSummaryViewModel by activityViewModels()
    lateinit var tokenmanager: TokenManager
    private var isLoading = false
    private var currentPage = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentClientSummaryJobRequisitionBinding.inflate(inflater, container, false)
        return binding.root
        // Inflate the layout for this fragment
     }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        observers()
        clickListeners()
    }



    private fun clickListeners() {

        binding.swipeRefreshLayout.setOnRefreshListener {
            currentPage = 1
            getJobRequests()
        }

        binding.rvJobRequisition.addOnScrollListener(object : RecyclerView.OnScrollListener() {
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

        binding.searchView2.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                // Handle search here when the user submits the query
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {

                // Implement the debounce mechanism here
                newText?.let {
                    // Remove leading spaces from the input
                    val trimmedText = newText.trimStart()
                    searchviewtext = trimmedText

                    // Check if the length of the trimmed input is at least 3 characters
                    if (trimmedText.length >= 3) {
                        // Cancel previous requests if any
                        handler.removeCallbacksAndMessages(null)

                        // Delay the API call to ensure efficient network usage
                        handler.postDelayed({
                            isfirsttimesearched = true
                            // Call your API with trimmedText as the search query
                            fetchData(trimmedText)
                        }, 500) // Adjust the delay time as needed
                    }

                    if (newText.length < 3) {

                        if (isfirsttimesearched) {
                            isfirsttimesearched = false
                            // Call your API with newText as the search query (even if length is less than 3)
                            handler.postDelayed({
                                // Call your API with newText as the search query
                                fetchData(newText)
                            }, 500)
                        }

                    }

                    if (newText.equals("")) {
                        handler.postDelayed({
                            searchviewtext = ""
                            // Call your API with newText as the search query
                            fetchData(newText)
                        }, 500)

                    }
                }
                return true
            }
        })
    }

    private fun initViews() {
        loader = Loader(context as Activity)
        tokenmanager = TokenManager(requireContext())
        binding.searchView2.queryHint = "Search"
        getJobRequests()
    }


    private fun observers() {

        viewModel.LDgetJobReqs.observe(requireActivity()) {


            binding.swipeRefreshLayout.isRefreshing = false

            if (it.data != null) {
                loader.hide()
                if (it.data.records.size != 0) {
                  Constants.jobReqData = it.data!!

                    binding.rvJobRequisition.visibility = View.VISIBLE
                    binding.tvNoJobs.visibility = View.GONE
                    jobreqList = ArrayList()

                    for (i in 0 until it.data.records.size) {
                        jobreqList.add(it.data.records.get(i))
                    }

                    try {
                        setupJobReqAdapter(jobreqList, requireContext())

                    } catch (e: Exception) {
                    }

                } else {
                    binding.rvJobRequisition.visibility = View.INVISIBLE
                    binding.tvNoJobs.visibility = View.VISIBLE
                }


            }


        }
    }

    private fun getJobRequests() {

        loader.show()

        if (!searchviewtext.equals("")) {
            model = SortDirectionJobRequisition(
                clientId = Constants.clientid.toString(),
                jobRequestFilters = emptyList(),
                pageIndex = 1,
                pageSize = 25,
                searchText = searchviewtext,
                sortBy = "CreatedDate",
                sortDirection = 1,
                tileStatusId = -1
            )
        } else {
            model = SortDirectionJobRequisition(
                clientId = Constants.clientid.toString(),
                jobRequestFilters = emptyList(),
                pageIndex = 1,
                pageSize = 25,
                searchText = "",
                sortBy = "CreatedDate",
                sortDirection = 1,
                tileStatusId = -1
            )
        }
        viewModel.getJobRequests(
            requireActivity(),
            tokenmanager.getAccessToken(),
            model
        )

    }

    fun setupJobReqAdapter(
        jobReqlist: ArrayList<Record>,
        requireContext: Context
    ) {

        binding!!.rvJobRequisition.setHasFixedSize(true)
        val mLayoutManager = LinearLayoutManager(
            requireContext
        )
        mLayoutManager.reverseLayout = true
        mLayoutManager.stackFromEnd = true
        binding!!.rvJobRequisition.layoutManager = LinearLayoutManager(
            requireContext,
            LinearLayoutManager.VERTICAL,
            false
        )

        adapter = JobRequsitionAdapter(
            requireContext,
            jobReqlist,
            childFragmentManager

        )
        binding!!.rvJobRequisition.adapter = adapter

        try {
            binding.swipeRefreshLayout.isRefreshing = false
        } catch (e: Exception) {
        }
    }

    private fun loadMoreData(page: Int) {
        loader.show()
        isLoading = true

        model = SortDirectionJobRequisition(
            clientId = Constants.clientid.toString(),
            jobRequestFilters = emptyList(),
            pageIndex = page,
            pageSize = 25,
            searchText = "",
            sortBy = "CreatedDate",
            sortDirection = 1,
            tileStatusId = -1
        )


        try {
            ApiUtils.getAPIService(requireContext()).getJobRequests(
                model,
                tokenmanager.getAccessToken().toString(),
            )
                .enqueue(object : Callback<GetJobRequests> {
                    override fun onResponse(
                        call: Call<GetJobRequests>,
                        response: Response<GetJobRequests>
                    ) {
                        loader.hide()
                        if (response.body() != null) {

                            for (i in 0 until response.body()!!.data.records.size) {
                                jobreqList.add(response.body()!!.data.records.get(i))
                            }
                            isLoading = false
                            adapter.notifyDataSetChanged()
                        }
                    }

                    override fun onFailure(
                        call: Call<GetJobRequests>,
                        t: Throwable
                    ) {
                        loader.hide()
                        Log.i("exceptionddsfdsfds", t.toString())

                    }
                })
        } catch (ex: java.lang.Exception) {
            loader.hide()
            Log.i("exceptionddsfdsfds", ex.toString())
        }

    }

    private fun fetchData(query: String) {

        loader.show()
        // Use Coroutines to perform the API call
        CoroutineScope(Dispatchers.IO).launch {
            try {
                // Make your API call here and handle the response
                if (query.length < 3) {

                    model = SortDirectionJobRequisition(
                        clientId = Constants.clientid.toString(),
                        jobRequestFilters = emptyList(),
                        pageIndex = 1,
                        pageSize = 25,
                        searchText = query,
                        sortBy = "CreatedDate",
                        sortDirection = 1,
                        tileStatusId = -1
                    )

                } else {
                    model = SortDirectionJobRequisition(
                        clientId = Constants.clientid.toString(),
                        jobRequestFilters = emptyList(),
                        pageIndex = 1,
                        pageSize = 25,
                        searchText = query,
                        sortBy = "CreatedDate",
                        sortDirection = 1,
                        tileStatusId = -1
                    )
                }
                viewModel.getJobRequests(
                    requireActivity(),
                    tokenmanager.getAccessToken(),
                    model
                )

            } catch (e: Exception) {
                Log.i("searcherrorrrr", e.toString())
                Toast.makeText(requireContext(), e.toString(), Toast.LENGTH_LONG).show()
                e.printStackTrace()
                // Handle errors, e.g., show an error message to the user
            }
        }
    }

    override fun onResume() {
        super.onResume()
        currentPage = 1
        getJobRequests()
    }

}