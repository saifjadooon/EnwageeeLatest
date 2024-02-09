package com.example.envagemobileapplication.Activities.DashBoard.DashboardFragments.Dashboard

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.envagemobileapplication.Activities.DashBoard.BulkMessages.BulkMessagesActivty
import com.example.envagemobileapplication.Adapters.MessagesAdapter
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.GetAllSmsResp.GetAllSmsResp
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.GetAllSmsResp.Record
import com.example.envagemobileapplication.Oauth.TokenManager
import com.example.envagemobileapplication.Utils.Loader
import com.example.envagemobileapplication.ViewModels.MainActivityViewModel
import com.example.envagemobileapplication.databinding.FragmentMessagesBinding
import com.ezshifa.aihealthcare.network.ApiUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MessagesF : Fragment() {
    lateinit var twillioToken: String
    lateinit var messagesList: ArrayList<com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.GetAllSmsResp.Record>
    private var currentPage = 0
    lateinit var loader: Loader
    private var isLoading = false
    lateinit var adapter: MessagesAdapter
    lateinit var tokenManager: TokenManager
    lateinit var token: String
    lateinit var binding: FragmentMessagesBinding
    val viewModel: MainActivityViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentMessagesBinding.inflate(inflater, container, false)

        return binding.getRoot()
    }


    private fun clicklisteners() {

        binding.composeMessage.setOnClickListener {
            val intent = Intent(requireContext(), BulkMessagesActivty::class.java)
            startActivity(intent)

        }
        binding.swipeRefreshLayout.setOnRefreshListener {
            twillioToken = ""
            viewModel.getAllSms(requireContext(), token.toString())
        }

        binding.rvMessages.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager?
                val totalItemCount = layoutManager!!.itemCount
                val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()
                if (!isLoading && lastVisibleItemPosition == totalItemCount - 1) {
                    // Load more data here

                    if (twillioToken != null) {
                        currentPage++
                        loadMoreData(currentPage)
                    }

                }
            }
        })
    }

    private fun observers() {
        viewModel.LDgetAllSms.observe(requireActivity()) {
            loader.hide()
            binding.swipeRefreshLayout.isRefreshing = false
            if (it.data != null) {

                messagesList =
                    ArrayList()
                for (i in 0 until it.data.records.size) {
                    messagesList.add(it.data.records.get(i))
                }

                var nextpageurl = it.data.nextPageUrl
                try {
                    twillioToken = extractPageToken(nextpageurl)!!
                } catch (e: Exception) {

                }

                try {
                    setUpJobsAdapter(messagesList, requireContext())
                } catch (e: Exception) {

                }

            }
        }
    }

    private fun networkCalls() {
        loader.show()
        viewModel.getAllSms(requireContext(), token.toString())
    }

    private fun initViews() {
        loader = Loader(requireContext())
        tokenManager = TokenManager(requireContext())
        token = tokenManager.getAccessToken()!!
    }

    fun setUpJobsAdapter(
        messagesList: ArrayList<Record>,
        requireContext: Context

    ) {

        binding!!.rvMessages.setHasFixedSize(true)
        val mLayoutManager = LinearLayoutManager(
            requireContext
        )
        mLayoutManager.reverseLayout = true
        mLayoutManager.stackFromEnd = true
        binding!!.rvMessages.layoutManager = LinearLayoutManager(
            requireContext,
            LinearLayoutManager.VERTICAL,
            false
        )

        adapter = MessagesAdapter(
            requireContext, messagesList
        )
        binding!!.rvMessages.adapter = adapter
    }

    private fun loadMoreData(page: Int) {
        // loader.show()
        isLoading = true
        var pagesize = "25"
        var page = page
        var twillliotoken = twillioToken
        loader.show()
        try {
            ApiUtils.getAPIService(requireContext()).getAllSmsPagginated(
                token.toString(), pagesize, page.toString(), twillliotoken
            )
                .enqueue(object : Callback<GetAllSmsResp> {
                    override fun onResponse(
                        call: Call<GetAllSmsResp>,
                        response: Response<GetAllSmsResp>
                    ) {
                        if (response.body() != null) {
                            loader.hide()
                            for (i in 0 until response.body()!!.data.records.size) {
                                messagesList.add(response.body()!!.data.records.get(i))
                            }

                            isLoading = false
                            adapter.notifyDataSetChanged()

                            var nextpageurl = response.body()!!.data.nextPageUrl
                            try {
                                twillioToken = extractPageToken(nextpageurl)!!
                            } catch (e: Exception) {
                            }

                        }
                    }

                    override fun onFailure(call: Call<GetAllSmsResp>, t: Throwable) {
                        Log.i("exceptionddsfdsfds", t.toString())
                        loader.hide()
                    }
                })
        } catch (ex: java.lang.Exception) {
            Log.i("exceptionddsfdsfds", ex.toString())
            loader.hide()
        }

    }

    fun extractPageToken(url: String): String? {
        val pageTokenQueryParam = "PageToken="
        val startIndex = url.indexOf(pageTokenQueryParam)

        if (startIndex != -1) {
            val endIndex = url.indexOf('&', startIndex + pageTokenQueryParam.length)
            return if (endIndex != -1) {
                url.substring(startIndex + pageTokenQueryParam.length, endIndex)
            } else {
                url.substring(startIndex + pageTokenQueryParam.length)
            }
        }

        return null
    }

    override fun onResume() {
        super.onResume()
        initViews()
        networkCalls()
        observers()
        clicklisteners()
    }
}