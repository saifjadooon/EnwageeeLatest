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
import com.example.envagemobileapplication.Activities.Candidates.CandidateProfileSummary
import com.example.envagemobileapplication.Models.RequestModels.ReconsiderCandidateRequestModel
import com.example.envagemobileapplication.Oauth.TokenManager
import com.example.envagemobileapplication.R
import com.example.envagemobileapplication.Utils.Constants
import com.example.envagemobileapplication.Utils.Global
import com.example.envagemobileapplication.Utils.Loader
import com.example.envagemobileapplication.ViewModels.JobSummaryViewModel
import com.example.envagemobileapplication.databinding.BsheetJobDropCandidateOptionsBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class BottomsheetJobCandidatesDropOptions : BottomSheetDialogFragment() {


    var global = com.example.envagemobileapplication.Utils.Global
    lateinit var binding: BsheetJobDropCandidateOptionsBinding
    val viewModel: JobSummaryViewModel by viewModels()

    lateinit var tokenManager: TokenManager
    lateinit var token: String
    lateinit var loader: Loader

    var StatusName:String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.AppBottomSheetDialogTheme);
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        tokenManager = TokenManager(requireContext())
        token = tokenManager.getAccessToken()!!
        loader = Loader(requireContext())

        binding = BsheetJobDropCandidateOptionsBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvNewStatus.setOnClickListener{
            StatusName = "new"
            reconsiderCandidate(StatusName)
        }

        binding.tvLastStatus.setOnClickListener{
            StatusName = "last"
            reconsiderCandidate(StatusName)
        }


        viewModel.LDreconsiderCandidateResponse?.observe(viewLifecycleOwner) { response ->
            loader.hide()
            try {
                if (response?.data != null) {
                    Toast.makeText(context, response.data.message.toString(), Toast.LENGTH_SHORT)
                        .show()
                    val intent = Intent(requireContext(), CandidateProfileSummary::class.java)
                    startActivity(intent)
                    dismiss()
                    (context as Activity).finish()
                } else {

                    Toast.makeText(context, "No data found", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {

                Log.e("jkbhdjhsbdhjsbhjd", "Error in LDsendAssessmentResponse observer", e)
            }
        }


    }

    private fun reconsiderCandidate(statusName: String) {
        val requestModel = Global.candidateIdForOfferLetter?.let { listOf(it.toInt()) }?.let {
            ReconsiderCandidateRequestModel(
                status = statusName,
                candidateId = it,
                jobId = Constants.AssessmentJobId?.toInt() ?: 0
            )
        }

        if (requestModel != null) {
            loader.show()
            viewModel.reconsiderCandidate(
                requireContext(),
                token,
                requestModel
            )

        }

//        dismiss()
    }




}
