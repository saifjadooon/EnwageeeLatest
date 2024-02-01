package com.example.envagemobileapplication.Activities.DashBoard.BulkMessages.BulkMessagesFragments

import android.R
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
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.envagemobileapplication.Adapters.FilteredCandidatesAdapter
import com.example.envagemobileapplication.Adapters.StatusAdapterWithCheckbox
import com.example.envagemobileapplication.Adapters.customadapter
import com.example.envagemobileapplication.Models.RequestModels.GetFltrDtaBulkMsgRm
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.getBulkMsgFilterdResp.GetBulkMsgFilterdResp
import com.example.envagemobileapplication.Oauth.TokenManager
import com.example.envagemobileapplication.Utils.Loader
import com.example.envagemobileapplication.ViewModels.BulkMessagesViewModel
import com.example.envagemobileapplication.databinding.FragmentFilterRecipetentsBinding
import com.ezshifa.aihealthcare.network.ApiUtils
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class FilterRecipetentsF : Fragment(), StatusAdapterWithCheckbox.OnItemSelectedListener {
    private var isfirsttimesearched: Boolean = false
    private val handler = Handler(Looper.getMainLooper())
    private var totalfilteredCandidate: Int? = 0
    private var selectedJobid: Int? = 0
    private var searchviewtext: String = ""
    private var concatinatedStatusString: String = ""
    private lateinit var statusAdapter: StatusAdapterWithCheckbox
    var global = com.example.envagemobileapplication.Utils.Global
    lateinit var clientList: ArrayList<com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.SearchClientByNameResp.Datum>
    lateinit var clientJobList: ArrayList<com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.getClientJobsResp.Datum>
    lateinit var jobstatusesList: ArrayList<com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.CandidateJobStatusRes.Datum>
    lateinit var filteredCandidateList: ArrayList<com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.getBulkMsgFilterdResp.Record>
    lateinit var loader: Loader
    lateinit var tokenManager: TokenManager
    lateinit var token: String
    val viewModel: BulkMessagesViewModel by activityViewModels()
    lateinit var binding: FragmentFilterRecipetentsBinding
    lateinit var adapter: FilteredCandidatesAdapter
    var statusList: ArrayList<String> = ArrayList()
    private var isLoading = false
    private var currentPage = 1
    var spinnercandidatelist: ArrayList<String> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentFilterRecipetentsBinding.inflate(inflater, container, false)
        initviews()
        clicklisteners()
        observers()
        networkCalls()
        return binding.root
    }

    private fun networkCalls() {
        viewModel.searhClientbyName(requireContext(), token)
    }

    private fun observers() {
        viewModel.LDSearchClientByName.observe(requireActivity()) {
            loader.hide()
            if (it.data != null) {

                clientList =
                    ArrayList()
                for (i in 0 until it.data.size) {
                    clientList.add(it.data.get(i))
                }

                var items: ArrayList<String> = ArrayList()
                items.add("Select Client")
                for (i in 0 until clientList.size) {
                    items.add(clientList.get(i).name)
                }

                try {
                    val adapter = customadapter(
                        requireContext(),
                        R.layout.simple_spinner_item,
                        items
                    )
                    binding.spinnerClient.setAdapter(adapter)
                    binding.spinnerClient.setSelection(0)

                } catch (e: Exception) {

                }
            }
        }
        viewModel.LDGetClientJobs.observe(requireActivity()) {
            loader.hide()
            if (it.data != null) {

                clientJobList =
                    ArrayList()
                for (i in 0 until it.data.size) {
                    clientJobList.add(it.data.get(i))
                }

                var items: ArrayList<String> = ArrayList()
                items.add("Select Job")
                for (i in 0 until clientJobList.size) {
                    items.add(clientJobList.get(i).positionName)
                }


                try {
                    val adapter = customadapter(
                        requireContext(),
                        R.layout.simple_spinner_item,
                        items
                    )
                    binding.spinnerjobs.setAdapter(adapter)
                    binding.spinnerjobs.setSelection(0)

                } catch (e: Exception) {

                }

                binding.spinnerjobs.setOnItemClickListener { _, _, position, _ ->

                    var selectedText = binding.spinnerjobs.text.toString()

                    for (i in 0 until clientJobList.size) {

                        if (clientJobList.get(i).positionName.equals(selectedText)) {


                            selectedJobid = clientJobList.get(i).jobId
                            global.selectedJObGuid = clientJobList.get(i).guid
                        }
                    }
                    binding.Tijob.error = null
                    viewModel.getCandidateJobStatuses(requireContext(), token)

                }
            }
        }
        viewModel.LDgetCandidateJobStatuses.observe(requireActivity()) {
            loader.hide()

            jobstatusesList = ArrayList()
            if (it.data != null) {

                jobstatusesList =
                    ArrayList()
                for (i in 0 until it.data.size) {
                    jobstatusesList.add(it.data.get(i))
                }

                var items: ArrayList<String> = ArrayList()

                for (i in 0 until jobstatusesList.size) {
                    items.add(jobstatusesList.get(i).statusName)
                }


                try {

                    statusAdapter = StatusAdapterWithCheckbox(
                        requireContext(),
                        com.example.envagemobileapplication.R.layout.status_with_checkbox_item,
                        items
                    )
                    statusAdapter.onItemSelectedListener = this
                    binding.spinnerStatus.setAdapter(statusAdapter)

                    binding.spinnerStatus.setOnItemClickListener { _, _, position, _ ->
                        val selectedItemText = statusAdapter.getItem(position)
                        // Handle the selected item as needed


                        binding.Tijob.error = null
                    }

                    // Example: Get selected items when needed (e.g., on a button click)
                    val selectedItems = statusAdapter.getSelectedItems()
                    for (item in selectedItems) {
                        // Handle each selected item as needed
                        Log.i("Selected Item", item)
                    }


                } catch (e: Exception) {

                }
            }
        }
        viewModel.LDGetBulkMsgFilterdResp.observe(requireActivity()) {
            loader.hide()
            binding.swipeRefreshLayout.isRefreshing = false
            filteredCandidateList = ArrayList()

            if (it.data != null) {
                totalfilteredCandidate = it.data.totalRecords
                filteredCandidateList =
                    ArrayList()
                for (i in 0 until it.data.records.size) {
                    filteredCandidateList.add(it.data.records.get(i))
                }

                try {

                    binding.ccRv.visibility = View.VISIBLE
                    setupFilteredCandidateAdapter(filteredCandidateList, requireContext())

                } catch (e: Exception) {

                }
            }
        }
    }

    private fun clicklisteners() {
        binding.spinnerSendTo.setOnTouchListener(View.OnTouchListener { v, event ->
            if (binding.spinnerSendTo.isPopupShowing) {
                binding.spinnerSendTo.dismissDropDown()
            } else {
                binding.spinnerSendTo.showDropDown()
            }
            false
        })
        binding.btnNext.setOnClickListener {

            var client = binding.spinnerClient.text
            var job = binding.spinnerjobs.text
            global.clientforbulkmsgs = client.toString()
            if (concatinatedStatusString != "" && job != null && client != null) {
                if (global.phonenumberlist!!.size > 0) {
                    replaceFragment(SendBulkMessageF())
                } else {
                    val rootView = binding.root
                    val duration = Snackbar.LENGTH_SHORT
                    val snackbar = Snackbar.make(rootView, "No Candidate selected", duration)
                    snackbar.setActionTextColor(
                        ContextCompat.getColor(
                            requireContext(),
                            com.example.envagemobileapplication.R.color.red
                        )
                    )
                    snackbar.show()
                }
            } else {
                if (client.equals(null)) {
                    binding.Ticlient.error = "Client is Required"
                }
                if (job.equals(null)) {
                    binding.Tijob.error = "Job is Required"
                }

                if (concatinatedStatusString.equals("")) {
                    binding.TIStatus.error = "Status is Required"
                }

            }

        }
        binding.btnCancel.setOnClickListener {
            global.showDialog(requireContext(), requireActivity())

        }


        binding.selectAllcheckbox.setOnCheckedChangeListener { buttonView, isChecked ->

            if (isChecked) {
                // in this checkbox i want to select all the checkboxes that are in the the adapter item how is it possible ?
                adapter.selectAll(true)
            } else {
                adapter.selectAll(false)
// in this i want to uncheck all the checkoxes
            }

        }

        binding.spinnerClient.setOnTouchListener(View.OnTouchListener { v, event ->
            if (binding.spinnerClient.isPopupShowing) {
                binding.spinnerClient.dismissDropDown()
            } else {
                binding.spinnerClient.showDropDown()
            }
            false
        })

        binding.spinnerClient.setOnItemClickListener { _, _, position, _ ->
            var selectedText = binding.spinnerClient.text.toString()
            binding.Ticlient.error = null
            var clientid = 0
            for (i in 0 until clientList.size) {
                if (selectedText.equals(clientList.get(i).name)) {
                    clientid = clientList.get(i).clientId
                    global.clietidforBulkMsg = clientid
                }
            }
            viewModel.getclientJobs(requireContext(), token, clientid)
        }

        binding.spinnerjobs.setOnTouchListener(View.OnTouchListener { v, event ->
            if (binding.spinnerjobs.isPopupShowing) {
                binding.spinnerjobs.dismissDropDown()
            } else {
                binding.spinnerjobs.showDropDown()
            }
            false
        })


        binding.spinnerStatus.setOnTouchListener(View.OnTouchListener { v, event ->
            if (binding.spinnerStatus.isPopupShowing) {
                binding.spinnerStatus.dismissDropDown()
            } else {
                binding.spinnerStatus.showDropDown()
            }
            false
        })

        binding.tvSelectedStatus.setOnClickListener {
            if (binding.spinnerStatus.isPopupShowing) {
                binding.spinnerStatus.dismissDropDown()
            } else {
                binding.spinnerStatus.showDropDown()
            }
        }
        binding.applyFilter.setOnClickListener {

            loader.show()
            val model = GetFltrDtaBulkMsgRm(
                pageIndex = 1,
                pageSize = 15,
                sortBy = "CreatedDate",
                sortDirection = 1,
                searchText = "",
                tileStatusIds = concatinatedStatusString,
                candidateFilters = listOf(), // Provide actual values or an empty list based on your requirements
                jobId = selectedJobid!!
            )
            viewModel.getMultiStatusCandidateData(requireContext(), token, model)

        }
        binding.rvFilered.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager?
                val totalItemCount = layoutManager!!.itemCount
                val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()
                if (!isLoading && lastVisibleItemPosition == totalItemCount - 1) {
                    currentPage++
                    loadMoreData(currentPage)
                }
            }
        })

        binding.swipeRefreshLayout.setOnRefreshListener {
            val model = GetFltrDtaBulkMsgRm(
                pageIndex = 1,
                pageSize = 15,
                sortBy = "CreatedDate",
                sortDirection = 1,
                searchText = "",
                tileStatusIds = concatinatedStatusString,
                candidateFilters = listOf(), // Provide actual values or an empty list based on your requirements
                jobId = selectedJobid!!
            )
            viewModel.getMultiStatusCandidateData(requireContext(), token, model)
        }


        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
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
        binding.searchView.queryHint = hint

        binding.searchView.setOnQueryTextFocusChangeListener { view, hasFocus ->
            if (hasFocus) {
                // Clear the hint when the SearchView gains focus
                binding.searchView.queryHint = null
            } else {
                // Set the hint when the SearchView loses focus
                binding.searchView.queryHint = hint
            }
        }
    }

    private fun initviews() {
        loader = Loader(requireContext())
        tokenManager = TokenManager(requireContext())
        token = tokenManager.getAccessToken().toString()
        val client = "Client *"
        val formattedclient = global.formatHintWithRedAsterisk(client)
        binding.Ticlient.hint = formattedclient

        val job = "Job *"
        val formattedjob = global.formatHintWithRedAsterisk(job)
        binding.Tijob.hint = formattedjob

        val Sendto = "Send To *"
        val formattedSendto = global.formatHintWithRedAsterisk(Sendto)
        binding.TISendto.hint = formattedSendto

        val Status = "Status *"
        val formattedStatus = global.formatHintWithRedAsterisk(Status)
        binding.TIStatus.hint = formattedStatus

        spinnercandidatelist.add("Candidate")
        try {
            val adapter = customadapter(
                requireContext(),
                R.layout.simple_spinner_item,
                spinnercandidatelist
            )
            binding.spinnerSendTo.setAdapter(adapter)


        } catch (e: Exception) {

        }


    }

    fun setupFilteredCandidateAdapter(
        cLientList: ArrayList<com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.getBulkMsgFilterdResp.Record>,
        requireContext: Context,

        ) {

        binding!!.rvFilered.setHasFixedSize(true)
        val mLayoutManager = LinearLayoutManager(
            requireContext
        )
        mLayoutManager.reverseLayout = true
        mLayoutManager.stackFromEnd = true
        binding!!.rvFilered.layoutManager = LinearLayoutManager(
            requireContext,
            LinearLayoutManager.VERTICAL,
            false
        )
        adapter = FilteredCandidatesAdapter(
            requireContext, cLientList
        )
        binding!!.rvFilered.setHasFixedSize(true);
        binding!!.rvFilered.adapter = adapter
    }

    fun replaceFragment(fragment: Fragment) {
        val fragmentManager: FragmentManager = requireActivity().supportFragmentManager
        val transaction: FragmentTransaction = fragmentManager.beginTransaction()
        transaction.replace(com.example.envagemobileapplication.R.id.nav_bulk_messages, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    override fun onItemSelected(item: String) {


        for (i in 0 until jobstatusesList.size) {
            if (jobstatusesList.get(i).statusName.equals(item)) {
                statusList.add(jobstatusesList.get(i).candidateStatusId.toString())
                Log.i("Deselected Item", item)
                updateConcatenatedString()
            }
        }
        if (statusList.size >= 1) {
            binding.tvSelectedStatus.visibility = View.VISIBLE
            binding.tvSelectedStatus.setText(statusList.size.toString() + " Selected")
        } else {
            binding.tvSelectedStatus.visibility = View.GONE
        }


    }

    override fun onItemDeselected(item: String) {

        for (i in 0 until jobstatusesList.size) {
            if (jobstatusesList.get(i).statusName.equals(item)) {
                statusList.remove(jobstatusesList.get(i).candidateStatusId.toString())
                Log.i("Deselected Item", item)
                updateConcatenatedString()
            }
            if (statusList.size >= 1) {
                binding.tvSelectedStatus.visibility = View.VISIBLE
                binding.tvSelectedStatus.setText(statusList.size.toString() + " Selected")
            } else {
                binding.tvSelectedStatus.visibility = View.GONE
            }
        }

        // Handle the deselected item in your fragment


    }

    private fun updateConcatenatedString() {


        val concatenatedString = statusList.joinToString(", ") // Customize the separator if needed
        Log.i("Concatenated String", concatenatedString)
        concatinatedStatusString = concatenatedString

        // Use the concatenatedString as needed (e.g., display in a TextView)
    }

    private fun loadMoreData(page: Int) {

        isLoading = true
        var page = page

        val model = GetFltrDtaBulkMsgRm(
            pageIndex = page,
            pageSize = 15,
            sortBy = "CreatedDate",
            sortDirection = 1,
            searchText = "",
            tileStatusIds = concatinatedStatusString,
            candidateFilters = listOf(), // Provide actual values or an empty list based on your requirements
            jobId = selectedJobid!!
        )
        try {
            ApiUtils.getAPIService(requireContext())
                .getBulkMsgFiltered(
                    token.toString(), model
                )
                .enqueue(object : Callback<GetBulkMsgFilterdResp> {
                    override fun onResponse(
                        call: Call<GetBulkMsgFilterdResp>,
                        response: Response<GetBulkMsgFilterdResp>
                    ) {
                        if (response.body() != null) {

                            if (response.body() != null) {

                                isLoading = false
                                if (response.body()!!.data != null) {

                                    for (i in 0 until response.body()!!.data.records.size) {
                                        filteredCandidateList.add(
                                            response.body()!!.data.records.get(
                                                i
                                            )
                                        )
                                    }
                                    try {
                                        adapter.notifyDataSetChanged()
                                    } catch (e: Exception) {

                                    }
                                }

                            }
                        }
                    }

                    override fun onFailure(call: Call<GetBulkMsgFilterdResp>, t: Throwable) {

                    }
                })
        } catch (ex: java.lang.Exception) {

        }

    }

    private fun fetchData(query: String) {


        loader.show()
        // Use Coroutines to perform the API call
        CoroutineScope(Dispatchers.IO).launch {
            try {
                var model: GetFltrDtaBulkMsgRm
                // Make your API call here and handle the response
                if (query.length < 3) {
                    model = GetFltrDtaBulkMsgRm(
                        pageIndex = 1,
                        pageSize = 15,
                        sortBy = "CreatedDate",
                        sortDirection = 1,
                        searchText = query,
                        tileStatusIds = concatinatedStatusString,
                        candidateFilters = listOf(), // Provide actual values or an empty list based on your requirements
                        jobId = selectedJobid!!
                    )


                } else {

                    model = GetFltrDtaBulkMsgRm(
                        pageIndex = 1,
                        pageSize = 15,
                        sortBy = "CreatedDate",
                        sortDirection = 1,
                        searchText = query,
                        tileStatusIds = concatinatedStatusString,
                        candidateFilters = listOf(), // Provide actual values or an empty list based on your requirements
                        jobId = selectedJobid!!
                    )
                }
                viewModel.getMultiStatusCandidateData(requireContext(), token, model)

            } catch (e: Exception) {
                loader.hide()
                Toast.makeText(requireContext(), e.toString(), Toast.LENGTH_LONG).show()
                e.printStackTrace()

            }
        }
    }
}