package com.example.envagemobileapplication.Fragments.BottomSheet

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.envagemobileapplication.Activities.Assesments.AssesmentDetailActivity
import com.example.envagemobileapplication.R
import com.example.envagemobileapplication.databinding.BottomsheetAssignmentDetailsBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class BottomsheetViewAssesment : BottomSheetDialogFragment() {

    lateinit var binding: BottomsheetAssignmentDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.AppBottomSheetDialogTheme);
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = BottomsheetAssignmentDetailsBinding.inflate(inflater, container, false)
        binding.viewForm.setOnClickListener {}
        binding.assesmentDetails.setOnClickListener {
            val intent = Intent(context, AssesmentDetailActivity::class.java)
            requireContext().startActivity(intent)
        }
        return binding.root


    }

}
