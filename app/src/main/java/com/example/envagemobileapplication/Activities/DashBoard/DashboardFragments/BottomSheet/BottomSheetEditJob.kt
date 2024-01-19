package com.example.envagemobileapplication.Activities.DashBoard.DashboardFragments.BottomSheet

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.envagemobileapplication.Activities.Client.AddClient.ClientSummary.JobRequisition.EditJobRequisitionActivity
import com.example.envagemobileapplication.Activities.Login.LoginActivty
import com.example.envagemobileapplication.R
import com.example.envagemobileapplication.databinding.BottomSheetEditJobBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSheetEditJob : BottomSheetDialogFragment() {
    var global = com.example.envagemobileapplication.Utils.Global
    lateinit var binding: BottomSheetEditJobBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.AppBottomSheetDialogTheme);
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = BottomSheetEditJobBinding.inflate(inflater, container, false)
        if (global.isfromEditJobRequisition){
            binding.ivEditjobREq.visibility= View.VISIBLE
            binding.ccEditJob.visibility = View.INVISIBLE
        }
        else {
            binding.ivEditjobREq.visibility= View.INVISIBLE
            binding.ccEditJob.visibility = View.VISIBLE
        }


        binding.ivEditjobREq.setOnClickListener{
            val intent = Intent(requireContext(),EditJobRequisitionActivity ::class.java)
            startActivity(intent)
        }
        return binding.root


    }

}
