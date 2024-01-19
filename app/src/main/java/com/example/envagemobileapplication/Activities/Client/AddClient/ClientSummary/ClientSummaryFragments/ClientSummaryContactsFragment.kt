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
import com.example.envagemobileapplication.Adapters.ClientContactsAdapter
import com.example.envagemobileapplication.Models.RequestModels.SortDirectionClientContacts
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.GetClientContactsResponse.GetClientContactsResponse
import com.example.envagemobileapplication.Oauth.TokenManager
import com.example.envagemobileapplication.Utils.Constants
import com.example.envagemobileapplication.Utils.Loader
import com.example.envagemobileapplication.ViewModels.ClientSummaryViewModel
import com.example.envagemobileapplication.databinding.FragmentClientSummaryContactsBinding
import com.ezshifa.aihealthcare.network.ApiUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ClientSummaryContactsFragment : Fragment() {
    lateinit var contactlist: ArrayList<com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.GetClientContactsResponse.Record>
    private var currentPage = 1
    private var isLoading = false
    private var searchviewtext: String = ""
    private var isfirsttimesearched: Boolean = false
    lateinit var model: SortDirectionClientContacts
    lateinit var loader: Loader
    private val handler = Handler(Looper.getMainLooper())
    private var isFromSwipeRefresh: Boolean = false
    lateinit var binding: FragmentClientSummaryContactsBinding
    val viewModel: ClientSummaryViewModel by activityViewModels()
    lateinit var tokenmanager: TokenManager
    lateinit var adapter: ClientContactsAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentClientSummaryContactsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        observers()
        clickListeners()
    }


    private fun clickListeners() {

        binding.rvClientContacts.addOnScrollListener(object : RecyclerView.OnScrollListener() {
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
            getClientContacts()
        }

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

        val hint = "Search"
        binding.searchView2.queryHint = hint
        binding.searchView2.setOnQueryTextFocusChangeListener { view, hasFocus ->
            if (hasFocus) {
                // Clear the hint when the SearchView gains focus
                binding.searchView2.queryHint = null
            } else {
                // Set the hint when the SearchView loses focus
                binding.searchView2.queryHint = hint
            }
        }
    }

    private fun initViews() {
        loader = Loader(context as Activity)
        tokenmanager = TokenManager(requireContext())
        binding.searchView2.queryHint = "Search"
        getClientContacts()
    }

    private fun observers() {

        viewModel.LDgetContacts.observe(requireActivity()) {

            currentPage = 1
            binding.swipeRefreshLayout.isRefreshing = false

            if (it.data != null) {
                loader.hide()
                if (it.data.records.size != 0) {
                    binding.rvClientContacts.visibility = View.VISIBLE
                    binding.tvNoJobs.visibility = View.GONE
                    contactlist = ArrayList()

                    for (i in 0 until it.data.records.size) {
                        contactlist.add(it.data.records.get(i))
                    }

                    try {
                        setUpContactsAdapter(contactlist, requireContext())

                    } catch (e: Exception) {
                    }

                } else {
                    binding.rvClientContacts.visibility = View.INVISIBLE
                    binding.tvNoJobs.visibility = View.VISIBLE
                }


            }


        }
    }

    private fun getClientContacts() {

        loader.show()

        if (!searchviewtext.equals("")) {
            model = SortDirectionClientContacts(
                clientId = Constants.clientid.toString(),
                clientContactFilters = emptyList(),
                pageIndex = 1,
                pageSize = 25,
                searchText = searchviewtext,
                sortBy = "CreatedDate",
                sortDirection = 1
            )
        } else {
            model = SortDirectionClientContacts(
                clientId = Constants.clientid.toString(),
                clientContactFilters = emptyList(),
                pageIndex = 1,
                pageSize = 25,
                searchText = "",
                sortBy = "CreatedDate",
                sortDirection = 1
            )
        }
        viewModel.getContacts(
            requireActivity(),
            tokenmanager.getAccessToken(),
            model
        )

    }

    fun setUpContactsAdapter(
        contactlist: ArrayList<com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.GetClientContactsResponse.Record>,
        requireContext: Context
    ) {

        binding!!.rvClientContacts.setHasFixedSize(true)
        val mLayoutManager = LinearLayoutManager(
            requireContext
        )
        mLayoutManager.reverseLayout = true
        mLayoutManager.stackFromEnd = true
        binding!!.rvClientContacts.layoutManager = LinearLayoutManager(
            requireContext,
            LinearLayoutManager.VERTICAL,
            false
        )

        adapter = ClientContactsAdapter(
            requireContext,
            contactlist,
            childFragmentManager

        )
        binding!!.rvClientContacts.adapter = adapter

        try {
            binding.swipeRefreshLayout.isRefreshing = false
        } catch (e: Exception) {
        }
    }

    private fun fetchData(query: String) {


        loader.show()
        // Use Coroutines to perform the API call
        CoroutineScope(Dispatchers.IO).launch {
            try {
                // Make your API call here and handle the response
                if (query.length < 3) {
                    model = SortDirectionClientContacts(
                        clientId = Constants.clientid.toString(),
                        clientContactFilters = emptyList(),
                        pageIndex = 1,
                        pageSize = 25,
                        searchText = query,
                        sortBy = "CreatedDate",
                        sortDirection = 1,
                    )
                } else {
                    model = SortDirectionClientContacts(
                        clientId = Constants.clientid.toString(),
                        clientContactFilters = emptyList(),
                        pageIndex = 1,
                        pageSize = 25,
                        searchText = query,
                        sortBy = "CreatedDate",
                        sortDirection = 1,
                    )
                }




                viewModel.getContacts(
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


    private fun loadMoreData(page: Int) {
        loader.show()
        isLoading = true
        model = SortDirectionClientContacts(
            clientId = Constants.clientid.toString(),
            clientContactFilters = emptyList(),
            pageIndex = page,
            pageSize = 25,
            searchText = "",
            sortBy = "CreatedDate",
            sortDirection = 1
        )


        try {
            ApiUtils.getAPIService(requireContext()).getClientContacts(
                model,
                tokenmanager.getAccessToken().toString(),
            )
                .enqueue(object : Callback<GetClientContactsResponse> {
                    override fun onResponse(
                        call: Call<GetClientContactsResponse>,
                        response: Response<GetClientContactsResponse>
                    ) {
                        loader.hide()
                        if (response.body() != null) {


                            for (i in 0 until response.body()!!.data.records.size) {
                                contactlist.add(response.body()!!.data.records.get(i))
                            }

                            isLoading = false
                            adapter.notifyDataSetChanged()
                        }
                    }

                    override fun onFailure(
                        call: Call<GetClientContactsResponse>,
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

}