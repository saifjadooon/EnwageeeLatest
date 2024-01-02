package com.example.envagemobileapplication.Fragments.BottomSheet


import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.envagemobileapplication.Activities.Candidates.CandidateProfileSummary
import com.example.envagemobileapplication.Adapters.CandidateJobStatusAdapter
import com.example.envagemobileapplication.Models.RequestModels.UpdateJobStatusPayload
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.CompanyOnboardingRes.Datum
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.UpdateCJobStatusRes.UpdateCJobStatusRes
import com.example.envagemobileapplication.Oauth.TokenManager
import com.example.envagemobileapplication.R
import com.example.envagemobileapplication.Utils.Constants
import com.example.envagemobileapplication.ViewModels.CandidatesProfileSumViewModel
import com.example.envagemobileapplication.databinding.FragmentBottomSheetBinding
import com.ezshifa.aihealthcare.network.ApiUtils
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BSCandidateJobStatusF : BottomSheetDialogFragment() {

    val viewModel: CandidatesProfileSumViewModel by viewModels()

    lateinit var binding: FragmentBottomSheetBinding
    var statusList: ArrayList<Datum> = ArrayList()
    private lateinit var adapter: CandidateJobStatusAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.AppBottomSheetDialogTheme);
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentBottomSheetBinding.inflate(inflater, container, false)
        /*    statusList =
                requireArguments().getSerializable("statusListData") as ArrayList<Datum>*/

        setUpDatAdapter()
        observers()
        return binding.root
    }

    private fun observers() {
        viewModel.LDChangeCandidateStatusToDrop.observe(this) {
            if (it.data != null) {

                changeStatusToDroped()


            } else {
                Toast.makeText(context, "no data", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun changeStatusToDroped() {
        val payload = UpdateJobStatusPayload(
            jobId = Constants.jobId, //ok
            candidateGUID = Constants.candidateId.toString(),
            statusId = Constants.candidateJobDropedId.toString(),
            joiningDate = "",
            OfferedSalary = "",
            payRate = "",
            billRate = "",
            overtimePayRate = "",
            overtimeBillRate = "",
            doublePayRate = "0.00",
            doubleBillRate = "0.00",
        )

        var tokenmanager: TokenManager = TokenManager(requireContext())
        var token = tokenmanager.getAccessToken()
        try {

            ApiUtils.getAPIService(requireContext()).updateCandidateJobStatus(
                token.toString(),
                payload
            )
                .enqueue(object : Callback<UpdateCJobStatusRes> {
                    override fun onResponse(
                        call: Call<UpdateCJobStatusRes>,
                        response: Response<UpdateCJobStatusRes>
                    ) {
                        if (response.body() != null) {

                            try {


                                // Finish the current activity (optional)
                                (context as? Activity)?.finish()


                                val intent = Intent(context, CandidateProfileSummary::class.java)
                                // Add any extra data if needed
                                // intent.putExtra("key", "value")
                                context?.startActivity(intent)
                                dismiss()

//                                (context as Activity).finish()


                            } catch (e: Exception) {

                            }

                        }
                    }

                    override fun onFailure(call: Call<UpdateCJobStatusRes>, t: Throwable) {
                        Log.i("exceptionddsfdsfds", t.toString())

                    }
                })
        } catch (ex: java.lang.Exception) {
            Log.i("exceptionddsfdsfds", ex.toString())
        }
    }

    private fun setUpDatAdapter() {

        binding!!.rvStatusList.setHasFixedSize(true)
        val mLayoutManager = LinearLayoutManager(
            context
        )
        mLayoutManager.reverseLayout = true
        mLayoutManager.stackFromEnd = true
        binding!!.rvStatusList.layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.VERTICAL,
            false
        )

        adapter = CandidateJobStatusAdapter(
            requireContext(),
            Constants.candidateJobStatusList, viewModel
        )
        binding!!.rvStatusList.adapter = adapter

    }
}