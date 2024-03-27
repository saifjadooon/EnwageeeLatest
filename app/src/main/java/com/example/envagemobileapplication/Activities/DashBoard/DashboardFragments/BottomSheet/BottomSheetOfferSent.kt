package com.example.envagemobileapplication.Activities.DashBoard.DashboardFragments.BottomSheet

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.example.envagemobileapplication.Models.RequestModels.SortDirectionGetOnlineCandidate
import com.example.envagemobileapplication.Models.RequestModels.UpdateStatusPayload
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.UpdateJobsStatusResponse.UpdateJobsStatusResponse
import com.example.envagemobileapplication.Oauth.TokenManager
import com.example.envagemobileapplication.R
import com.example.envagemobileapplication.Utils.Constants
import com.example.envagemobileapplication.Utils.Global
import com.example.envagemobileapplication.Utils.Loader
import com.example.envagemobileapplication.ViewModels.JobSummaryCandidateViewModel
import com.example.envagemobileapplication.databinding.BottomSheetOfferSentBinding
import com.ezshifa.aihealthcare.network.ApiUtils
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BottomSheetOfferSent : BottomSheetDialogFragment() {
    lateinit var payloadList: MutableList<UpdateStatusPayload>
    val viewModel: JobSummaryCandidateViewModel by activityViewModels()
    var global = com.example.envagemobileapplication.Utils.Global
    lateinit var binding: BottomSheetOfferSentBinding
    lateinit var tokenManager: TokenManager
    lateinit var loader: Loader
    lateinit var token: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.AppBottomSheetDialogTheme);
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = BottomSheetOfferSentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        tokenManager = TokenManager(requireContext())
        token = tokenManager.getAccessToken().toString()
        loader = Loader(requireContext())
        setupHorizontalScrollView()
    }

    private fun setupHorizontalScrollView() {


        var datalist: ArrayList<String> = ArrayList()
        datalist.add("Accepted")
        datalist.add("Rejected")
        var itemList = datalist

        for (itemText in itemList!!) {
            val itemView = layoutInflater.inflate(R.layout.item_offer_send, null)
            val textView = itemView.findViewById<TextView>(R.id.tvTitle)
            val radioButton = itemView.findViewById<RadioButton>(R.id.radioButton)
            textView.text = itemText
            itemView.setOnClickListener(object : View.OnClickListener {
                override fun onClick(v: View) {

                    val clickedText = itemText

                    if (clickedText.equals("Accepted")) {
                        val name = UpdateStatusPayload("add", "/status", "Accepted")
                        callUpdateStatusApi(name)

                    } else if (clickedText.equals("Rejected")) {
                        val name = UpdateStatusPayload("add", "/status", "Rejected")
                        callUpdateStatusApi(name)

                    }
                    itemView.requestFocus()
                }
            })

            radioButton.setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked) {
                    val clickedText = itemText
                    if (clickedText.equals("Accepted")) {
                        val name = UpdateStatusPayload("add", "/status", "Accepted")
                        callUpdateStatusApi(name)

                    } else if (clickedText.equals("Rejected")) {
                        val name = UpdateStatusPayload("add", "/status", "Rejected")
                        callUpdateStatusApi(name)

                    }
                    radioButton.requestFocus()

                }
            }

            binding.linearlist.addView(itemView)
        }
    }

    private fun callUpdateStatusApi(patch: UpdateStatusPayload) {
        try {

            loader.show()

            payloadList = ArrayList()
            payloadList.add(patch)


            var tokenmanager: TokenManager = TokenManager(requireContext())
            var token = tokenmanager.getAccessToken()
            var offerLetterId = Constants.offerLetterId

            ApiUtils.getAPIService(requireContext()).UpdateOfferLetterStatusClient(
                token.toString(), payloadList, offerLetterId!!
            )
                .enqueue(object : Callback<UpdateJobsStatusResponse> {
                    override fun onResponse(
                        call: Call<UpdateJobsStatusResponse>,
                        response: Response<UpdateJobsStatusResponse>
                    ) {
                        if (response.body() != null) {

                            loader.hide()
                            val model = SortDirectionGetOnlineCandidate(
                                applicantFilter = emptyList(),
                                jobId = Global.GlobalJobID!!,
                                pageIndex = 1,
                                pageSize = 25,
                                searchText = "",
                                sortBy = "CreatedDate",
                                sortDirection = 1
                            )
                           // loader.show()

                            viewModel.getallOfferLetters(requireActivity(), token, model)
                            val toast = Toast.makeText(
                                requireContext(),
                                "Offer Letter status updated successfully",
                                Toast.LENGTH_LONG
                            )
                            toast.show()

                            dismiss()
                        }


                    }

                    override fun onFailure(
                        call: Call<UpdateJobsStatusResponse>,
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