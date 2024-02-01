package com.example.envagemobileapplication.Utils

import android.R
import android.app.Activity
import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Context
import android.graphics.Color
import android.graphics.PorterDuff
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.widget.ProgressBar

import com.example.envagemobileapplication.Models.RequestModels.AddJobRequestModels.AddJobAdressDetailsReqModel
import com.example.envagemobileapplication.Models.RequestModels.AddJobRequestModels.AddJobBasicDetailRequestModel
import com.example.envagemobileapplication.Models.RequestModels.AddJobRequestModels.AddJobDetailsReqModel
import com.example.envagemobileapplication.Models.RequestModels.AddJobRequestModels.AddJobSalaryDetailReqModel
import com.example.envagemobileapplication.Models.RequestModels.JobSkill
import com.example.envagemobileapplication.Models.RequestModels.SkillsRequestModels
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.CndidteJobRsp.CandidateJobGetJobResponse
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.CompanyOnboardingRes.Datum
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.GetAllSkillsResponse.GetAllSkillsResponse
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.GetJobByJobIdResponse.GetJobByJobIdResponse
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.GetJobHeaderSummary.GetJobHeaderSummary
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.OnlineApplicantsResponse.Data
import okhttp3.MultipartBody
import java.text.SimpleDateFormat

public class Global {

    companion object {


        var message: String? = ""
        var too: String? =""
        var from: String? = ""
        var selectedJObGuid: String? = ""
        var clietidforBulkMsg: Int? = 0
        var clientforbulkmsgs: String? = ""
        var twilioResp:com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.getTwilioConfig.Data? = null
        var phonenumberlist: ArrayList<String> = ArrayList()
        var editreqjobSkills: ArrayList<com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.JobRequestSkills.Datum> ?= null
        var jobReqbyJobid: com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.GetJobRequestResp.Data? = null
        var isfromEditJobRequisition: Boolean = false
        var jobReqStatusesList: ArrayList<com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.JobRequsitionStatusResp.Datum>? = null
        var jobRequisitonPosition: Int = 0
        var isbackfromjobsummary: Boolean = false
        var assesmentStatus: String = ""
        var clientAssesmentFormid: Int? = 0
        var composeMessagePhoneNumber: String? = ""
        var offerlettlink: MultipartBody.Part? = null
        var ln: String? = ""
        var fn: String? = ""
        var validtill: String = ""
        var showofferLetter: Boolean = false
        var offerlettertemplateid: String = ""
        var descriptionforOfferLetter: MultipartBody.Part? = null
        var lastnameforofferletter: String? = ""
        var firstnameforofferletter: String? = ""
        var jobGuidforOfferLetter: String? = ""
        var candidateEmailAdress: String? = null
        var loggedinuserDetails: com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.GtLogdinUserDetailsRsp.Data? =
            null
        var jobidForOfferLetter: Any? = ""
        var candidateIdForOfferLetter: Int? = 0
        var index: Int = 0
        var jobtypelist: ArrayList<String> = ArrayList()
        var isfirstTimeFragmentLoaded: Boolean = true
        var getcandidatjobResp: CandidateJobGetJobResponse = CandidateJobGetJobResponse()
        var getjobbyjoid: GetJobByJobIdResponse = GetJobByJobIdResponse()
        var arraylistSelectedSkills: ArrayList<JobSkill> = ArrayList()
        var estimatedhours: String = ""
        var jobstatius: String = ""
        var jobtype: String = ""
        var jobskilllslist: ArrayList<SkillsRequestModels> = ArrayList()
        var descriptiontext: String = ""
        var noofWorkingDays: String = ""
        var enddate: String = ""
        var jobtitle: String? = ""
        var jobHeaderSummary: GetJobHeaderSummary? = null
        var jobGuid: String? = ""
        var AllOnlineSkillsPosition: Int = 0
        var position: Int = 0
        var GlobalJobID: Int? = 0
        var AllOnlineSkills: GetAllSkillsResponse? = null
        var onlineApplicantDetails: Data? = null
        var jobId: String = "0"
        var applicantid: String = "0"
        var isfrombackpress: Boolean = false
        var startdate: String = ""
        var headcount: String = ""
        var weekdaysConcatinated: String = ""
        var jobskills: JobSkill? = null
        var addjobSalaryDetailReqModel: AddJobSalaryDetailReqModel? = null
        var addJobAddressDetailModel: AddJobAdressDetailsReqModel? = null
        var addJobDetailModel: AddJobDetailsReqModel? = null
        var basicDetailReqModel: AddJobBasicDetailRequestModel? = null
        var filterclickedStatusItemList: ArrayList<String> = ArrayList()
        var onBoardingStatusList: ArrayList<Datum> = ArrayList()
        var JobStatusList: ArrayList<com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.GetJobsStatusesResponse.Datum> =
            ArrayList()
        var progressDialog: ProgressDialog? = null

        var context: Context? = null
        fun showProgressDialog(activity: Activity?, message: String?) {
            progressDialog = ProgressDialog(activity)
            progressDialog!!.setMessage(message)
            progressDialog!!.setCancelable(false)
            progressDialog!!.show()
            val progressbar = progressDialog!!.findViewById<ProgressBar>(R.id.progress)
            progressbar.indeterminateDrawable.setColorFilter(
                Color.parseColor("#d32e33"),
                PorterDuff.Mode.SRC_IN
            )
        }

        fun formatDate(inputDate: String): String {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
            val outputFormat = SimpleDateFormat("MM/dd/yyyy")

            try {
                val date = inputFormat.parse(inputDate)
                return outputFormat.format(date)
            } catch (e: Exception) {
                e.printStackTrace()
                return "" // Handle parsing error here
            }
        }
        fun hideProgressDialog() {
            if (progressDialog!!.isShowing) {
                progressDialog!!.hide()
            }
        }

          fun showDialog(context:Context,activity :Activity) {
            val alertDialogBuilder = AlertDialog.Builder(context)

            // Set the dialog title and message
            alertDialogBuilder.setTitle("Discard Changes")
            alertDialogBuilder.setMessage("Are you sure you want to discard changes?")

            // Add positive button and its click listener
            alertDialogBuilder.setPositiveButton("Yes") { dialog, which ->
                // Handle the OK button click (if needed)
                dialog.dismiss()
                activity.finish()
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


        fun formatHintWithRedAsterisk(hint: String): CharSequence {
            val spannable = SpannableStringBuilder(hint)
            val indexOfAsterisk = hint.indexOf('*')

            if (indexOfAsterisk >= 0) {
                spannable.setSpan(
                    ForegroundColorSpan(Color.RED),
                    indexOfAsterisk,
                    indexOfAsterisk + 1,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            }

            return spannable
        }
    }






}