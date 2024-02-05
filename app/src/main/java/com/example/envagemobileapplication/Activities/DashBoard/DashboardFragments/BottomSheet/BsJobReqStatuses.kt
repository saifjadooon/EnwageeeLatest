package com.example.envagemobileapplication.Activities.DashBoard.DashboardFragments.BottomSheet

import android.app.AlertDialog
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.example.envagemobileapplication.Models.RequestModels.JobPublishSetting
import com.example.envagemobileapplication.Models.RequestModels.JobReqApprovedReqModel
import com.example.envagemobileapplication.Models.RequestModels.JobReqUpdateModel
import com.example.envagemobileapplication.Models.RequestModels.JobStatus
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.ApprovedjobRespModel.ApprovedjobRespModel
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.CheckpayrateResp.CheckpayrateResp
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.JobReqUpdateStatusResp.JobReqUpdateStatusResp
import com.example.envagemobileapplication.Oauth.TokenManager
import com.example.envagemobileapplication.R
import com.example.envagemobileapplication.Utils.Constants
import com.example.envagemobileapplication.Utils.Loader
import com.example.envagemobileapplication.ViewModels.ClientSummaryViewModel
import com.example.envagemobileapplication.databinding.BsJobreqStatusesBinding
import com.ezshifa.aihealthcare.network.ApiUtils
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class BsJobReqStatuses : BottomSheetDialogFragment() {
    private var json: String? = ""
    val viewModel: ClientSummaryViewModel by activityViewModels()
    private var clickedjobRequestStatusName: String? = ""
    private var clickedStatusId: Int? = 0
    var global = com.example.envagemobileapplication.Utils.Global
    lateinit var binding: BsJobreqStatusesBinding
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
        binding = BsJobreqStatusesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        tokenManager = TokenManager(requireContext())
        token = tokenManager.getAccessToken().toString()
        loader = Loader(requireContext())
        clicklisteners()
        setupHorizontalScrollView()
        val gson = Gson()

        var jobskilllist = global.skilllistJobReq
        val listOfLinkedHashMaps = jobskilllist?.map { jobSkill ->
            linkedMapOf(
                "skillId" to jobSkill.skillId,
                "name" to jobSkill.name,
                "skillRating" to jobSkill.skillRating,
                "IsDeleted" to jobSkill.IsDeleted
            )
        }

        val json = gson.toJson(listOfLinkedHashMaps)
        Log.i("jsonstringofskills", json)

    }

    private fun clicklisteners() {

        binding.ivcross.setOnClickListener {
            showDialog()
        }
        binding.ivCancel.setOnClickListener {
            showDialog()
        }

        binding.btnsave.setOnClickListener {
            var remarks = binding.etRemarks.text.toString()
            var statusid = clickedStatusId
            var jobRequestid =
                Constants.jobReqData!!.records.get(global.jobRequisitonPosition).jobRequestId
            var statusname = clickedjobRequestStatusName

            if (remarks.isNotEmpty()) {
                if (statusname!!.equals("Approved")) {
                    checkmarkuppercentage(token, remarks, statusid, jobRequestid, statusname)
                } else {
                    updateJobReqStatus(token, remarks, statusid, jobRequestid, statusname)
                }
            }

        }
    }

    private fun checkmarkuppercentage(
        token: String,
        remarks: String,
        statusid: Int?,
        jobRequestid: Int?,
        statusname: String?
    ) {

        val gson = Gson()

        var jobskilllist = global.skilllistJobReq
        val listOfLinkedHashMaps = jobskilllist?.map { jobSkill ->
            linkedMapOf(
                "skillId" to jobSkill.skillId,
                "name" to jobSkill.name,
                "skillRating" to jobSkill.skillRating,
                "IsDeleted" to jobSkill.IsDeleted
            )
        }

        val json = gson.toJson(listOfLinkedHashMaps)
        Log.i("jsonstringofskills", json)

        val jobRequestmodel = JobReqUpdateModel(
            jobRequestId = jobRequestid!!,
            statusId = statusid!!,
            jobRequestStatusName = statusname!!,
            jobSkills = json,
            remarks = remarks
        )
        try {
            ApiUtils.getAPIService(requireContext()).checkPayRate(
                jobRequestmodel, token
            )
                .enqueue(object : Callback<CheckpayrateResp> {
                    override fun onResponse(
                        call: Call<CheckpayrateResp>,
                        response: Response<CheckpayrateResp>
                    ) {
                        loader.hide()
                        if (response.body() != null) {
                            Log.i("respMessage", response.body()!!.data.message.toString())
                            if (response.body()!!.data.message.toString().equals("MarkupNull")) {
                                Toast.makeText(
                                    requireContext(),
                                    "Please define the Markup Percentage for the job first.",
                                    Toast.LENGTH_LONG
                                ).show()
                            } else {
                                callApproveJobReqApi()
                            }

                        }
                    }

                    override fun onFailure(
                        call: Call<CheckpayrateResp>,
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

    private fun callApproveJobReqApi() {
        var remarks = binding.etRemarks.text.toString()
        var statusid = clickedStatusId
        var jobRequestid =
            Constants.jobReqData!!.records.get(global.jobRequisitonPosition).jobRequestId
        var statusname = clickedjobRequestStatusName

        val gson = Gson()

        var jobskilllist = global.skilllistJobReq
        val listOfLinkedHashMaps = jobskilllist?.map { jobSkill ->
            linkedMapOf(
                "skillId" to jobSkill.skillId,
                "name" to jobSkill.name,
                "skillRating" to jobSkill.skillRating,
                "IsDeleted" to jobSkill.IsDeleted
            )
        }

        val json = gson.toJson(listOfLinkedHashMaps)
        Log.i("jsonstringofskills", json)
        val jobStatus = JobStatus(
            jobRequestId = jobRequestid,
            statusId = statusid!!,
            jobRequestStatusName = statusname!!,
            remarks = remarks,
            jobSkills = json // Assuming jobSkills is a list of strings
        )

        // Create an instance of JobPublishSetting
        val jobPublishSetting = JobPublishSetting(
            applicationFormId = "1047",
            showSalary = false,
            showNature = false,
            showClient = false,
            showIndustry = false,
            showAddress = false,
            showType = false,
            showSkills = false,
            showShift = false,
            isPublish = false
        )

        // Create an instance of JobRequest and set the jobStatus and jobPublishSetting
        val jobRequest = JobReqApprovedReqModel(
            jobStatus = jobStatus,
            jobPublishSetting = jobPublishSetting
        )

        try {
            ApiUtils.getAPIService(requireContext()).updateJobReqApprovedStatus(
                jobRequest, token
            )
                .enqueue(object : Callback<ApprovedjobRespModel> {
                    override fun onResponse(
                        call: Call<ApprovedjobRespModel>,
                        response: Response<ApprovedjobRespModel>
                    ) {
                        loader.hide()
                        viewModel.closeChangeStatusBottomsheet(statusname)
                    }

                    override fun onFailure(
                        call: Call<ApprovedjobRespModel>,
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

    private fun updateJobReqStatus(
        token: String,
        remarks: String,
        statusid: Int?,
        jobRequestid: Int,
        statusname: String?
    ) {

        val gson = Gson()

        var jobskilllist = global.skilllistJobReq
        val listOfLinkedHashMaps = jobskilllist?.map { jobSkill ->
            linkedMapOf(
                "skillId" to jobSkill.skillId,
                "name" to jobSkill.name,
                "skillRating" to jobSkill.skillRating,
                "IsDeleted" to jobSkill.IsDeleted
            )
        }

        val json = gson.toJson(listOfLinkedHashMaps)
        Log.i("jsonstringofskills", json)
        val jobRequestmodel = JobReqUpdateModel(
            jobRequestId = jobRequestid,
            statusId = statusid!!,
            jobRequestStatusName = statusname!!,
            jobSkills = json, // You can populate this list with actual skills
            remarks = remarks
        )

        try {
            ApiUtils.getAPIService(requireContext()).updateJobReqStatus(
                jobRequestmodel, token
            )
                .enqueue(object : Callback<JobReqUpdateStatusResp> {
                    override fun onResponse(
                        call: Call<JobReqUpdateStatusResp>,
                        response: Response<JobReqUpdateStatusResp>
                    ) {
                        loader.hide()
                        if (response.body() != null) {

                            viewModel.closeChangeStatusBottomsheet(statusname)

                        }
                    }

                    override fun onFailure(
                        call: Call<JobReqUpdateStatusResp>,
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

    private fun setupHorizontalScrollView() {


        var itemList = global.jobReqStatusesList

        for (itemText in itemList!!) {
            val itemView = layoutInflater.inflate(R.layout.itemview_jobreq_status, null)

            var colorcode = itemText.colorCode
            val hexColorCode = colorcode
            val colore = Color.parseColor(hexColorCode)
            val textView = itemView.findViewById<TextView>(R.id.tvTitle)
            val textViewdescription = itemView.findViewById<TextView>(R.id.tvDescription)
            val radioButton = itemView.findViewById<RadioButton>(R.id.radioButton)
            textView.text = itemText.statusName
            textView.setTextColor(colore)
            if (itemText.statusName.equals("Require More Detail")) {
                textViewdescription.text =
                    "A request will be made to the creator of the requisition for him to add more details."
            } else if (itemText.statusName.equals("On hold")) {
                textViewdescription.text =
                    "Moving to on Hold will keep the requisition in a waiting state for you to take actions."
            } else if (itemText.statusName.equals("Approved")) {
                textViewdescription.text =
                    "Approving the requisition will subsequently create a new job."
            } else if (itemText.statusName.equals("Cancelled")) {
                textViewdescription.text =
                    "Cancelling the requisition will make it impossible for anymore to edit or approve the requisition in the future."
            } else if (itemText.statusName.equals("In Review")) {
                textViewdescription.text =
                    "The requisition will remain in review until its status is further modified."
            }

            itemView.setOnClickListener(object : View.OnClickListener {
                override fun onClick(v: View) {

                    val clickedText = itemText
                    binding.tvHeading.text = clickedText.statusName
                    binding.ccRemarks.visibility = View.VISIBLE
                    binding.ccList.visibility = View.INVISIBLE
                    val left = itemView.left
                    binding.horizontalScrollView.scrollTo(left, 0)
                    itemView.requestFocus()
                    clickedStatusId = itemText.jobRequestStatusId
                    clickedjobRequestStatusName = itemText.statusName

                }
            })

            radioButton.setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked) {
                    val clickedText = itemText
                    binding.tvHeading.text = clickedText.statusName
                    binding.ccRemarks.visibility = View.VISIBLE
                    binding.ccList.visibility = View.INVISIBLE
                    val left = radioButton.left
                    binding.horizontalScrollView.scrollTo(left, 0)
                    radioButton.requestFocus()
                    clickedStatusId = itemText.jobRequestStatusId
                    clickedjobRequestStatusName = itemText.statusName
                }
            }

            binding.linearlist.addView(itemView)
        }
    }

    private fun showDialog() {
        val alertDialogBuilder = AlertDialog.Builder(requireContext())

        // Set the dialog title and message
        alertDialogBuilder.setTitle("Discard Changes")
        alertDialogBuilder.setMessage("Are you sure you want to discard changes?")

        // Add positive button and its click listener
        alertDialogBuilder.setPositiveButton("Yes") { dialog, which ->
            // Handle the OK button click (if needed)
            dialog.dismiss()

            viewModel.closeChangeStatusBottomsheet("truee")
            // finish()// Dismiss the dialog
        }

        // Add negative button and its click listener (optional)
        alertDialogBuilder.setNegativeButton("No") { dialog, which ->
            // Handle the Cancel button click (if needed)
            dialog.dismiss() // Dismiss the dialog
        }

        // Create and show the alert dialog
        val alertDialog: AlertDialog = alertDialogBuilder.create()
        alertDialog.show()

    }
}