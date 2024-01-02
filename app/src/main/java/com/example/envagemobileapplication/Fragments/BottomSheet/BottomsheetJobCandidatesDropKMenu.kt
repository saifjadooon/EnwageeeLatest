package com.example.envagemobileapplication.Fragments.BottomSheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.envagemobileapplication.R
import com.example.envagemobileapplication.ViewModels.JobSummaryViewModel
import com.example.envagemobileapplication.databinding.BsheetJobDropCandidateKebabMenuBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class BottomsheetJobCandidatesDropKMenu : BottomSheetDialogFragment() {


    var global = com.example.envagemobileapplication.Utils.Global
    lateinit var binding: BsheetJobDropCandidateKebabMenuBinding
    val viewModel: JobSummaryViewModel by viewModels()
    var bottomsheetJobCandidatesDropOptions: BottomsheetJobCandidatesDropOptions = BottomsheetJobCandidatesDropOptions()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.AppBottomSheetDialogTheme);
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = BsheetJobDropCandidateKebabMenuBinding.inflate(inflater, container, false)
        binding.btnReconsiderCandidate.setOnClickListener{
            if (bottomsheetJobCandidatesDropOptions.isAdded()) {
                return@setOnClickListener
            } else {
                bottomsheetJobCandidatesDropOptions.show(childFragmentManager, bottomsheetJobCandidatesDropOptions.tag)
            }
        }

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        viewModel.LDreconsiderCandidateResponse?.observe(viewLifecycleOwner) { response ->
//            try {
//
//                if (response?.data != null) {
//                    Toast.makeText(context, response.data.message.toString(), Toast.LENGTH_SHORT)
//                        .show()
//                    (context as Activity).finish()
//                    dismiss()
//                } else {
//
//                    Toast.makeText(context, "No data found", Toast.LENGTH_SHORT).show()
//                }
//            } catch (e: Exception) {
//
//                Log.e("jkbhdjhsbdhjsbhjd", "Error in LDsendAssessmentResponse observer", e)
//            }
//        }

    }

}
