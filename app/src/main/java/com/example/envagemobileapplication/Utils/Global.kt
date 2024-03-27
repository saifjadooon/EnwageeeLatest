package com.example.envagemobileapplication.Utils

import android.Manifest
import android.R
import android.app.Activity
import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.*
import android.graphics.drawable.GradientDrawable
import android.media.ExifInterface
import android.os.Build
import android.text.InputFilter
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.widget.ProgressBar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.envagemobileapplication.Models.RequestModels.AddJobRequestModels.AddJobAdressDetailsReqModel
import com.example.envagemobileapplication.Models.RequestModels.AddJobRequestModels.AddJobBasicDetailRequestModel
import com.example.envagemobileapplication.Models.RequestModels.AddJobRequestModels.AddJobDetailsReqModel
import com.example.envagemobileapplication.Models.RequestModels.AddJobRequestModels.AddJobSalaryDetailReqModel
import com.example.envagemobileapplication.Models.RequestModels.GuestsData
import com.example.envagemobileapplication.Models.RequestModels.JobSkill
import com.example.envagemobileapplication.Models.RequestModels.SkillsRequestModels
import com.example.envagemobileapplication.Models.RequestModels.skillmodelforJobreq
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.CandidateSummaryJobsRes.CandidateJobsResponse
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.CndidteJobRsp.CandidateJobGetJobResponse
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.CompanyOnboardingRes.Datum
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.GetAllSkillsResponse.GetAllSkillsResponse
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.GetJobByJobIdResponse.GetJobByJobIdResponse
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.GetJobHeaderSummary.GetJobHeaderSummary
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.OnlineApplicantsResponse.Data
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.SearchClientByNameResp.SearchClientByNameResp
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.SearchJobsClientsResp.SearchJobsClientsResp
import okhttp3.MultipartBody
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileWriter
import java.io.IOException
import java.text.SimpleDateFormat

public class Global {

    companion object {
        var verifyotpTOken: String? = ""
        var emailforgotpassword: String = ""
        var imagefile: File? = null
        var descriptiontextguest: String? = ""
        var descriptionFinalForAddGuest: MultipartBody.Part ?= null
        var profilePicGuest: MultipartBody.Part? = null
        var guestData: GuestsData = GuestsData()
        val maxLengthFilter = InputFilter.LengthFilter(5)
        val zipcodefilterl = InputFilter { source, start, end, dest, dstart, dend ->
            for (i in start until end) {
                if (Character.isWhitespace(source[i])) {
                    return@InputFilter ""
                }
            }
            null
        }
        val filter = InputFilter { source, start, end, dest, dstart, dend ->
            if (dstart == 0 && end > start && source[start] == ' ') {
                // Block leading space
                return@InputFilter ""
            }
            null
        }

        fun isValidUrl(url: String): Boolean {
            val customPattern =
                "^(https?://(www\\.)?|www\\.)[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}(/.*)?$"
            return url.matches(customPattern.toRegex())
        }

        val emailRegex = Regex("^([a-zA-Z0-9_\\.-]+)@([a-zA-Z0-9\\.-]+)\\.([a-zA-Z]{2,})$")


        fun exifToDegrees(rotation: Int): Int {
            if (rotation == ExifInterface.ORIENTATION_ROTATE_90) return 90 else if (rotation == ExifInterface.ORIENTATION_ROTATE_180) return 180 else if (rotation == ExifInterface.ORIENTATION_ROTATE_270) return 270
            return 0
        }

        fun getBitmapRotatedByDegree(bitmap: Bitmap, rotationDegree: Int): Bitmap {
            val matrix = Matrix()
            matrix.preRotate(rotationDegree.toFloat())
            return Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
        }

        fun getImageRotation(imageFile: File): Int {
            var exif: ExifInterface? = null
            var exifRotation = 0
            try {
                exif = ExifInterface(imageFile.path)
                exifRotation = exif.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL
                )
            } catch (e: IOException) {
                e.printStackTrace()
            }
            return if (exif == null) 0 else exifToDegrees(exifRotation)
        }

        fun getStreamByteFromImage(imageFile: File): ByteArray? {
            var photoBitmap = BitmapFactory.decodeFile(imageFile.path)
            val stream = ByteArrayOutputStream()
            val imageRotation = getImageRotation(imageFile)
            if (imageRotation != 0) photoBitmap =
                getBitmapRotatedByDegree(photoBitmap, imageRotation)
            photoBitmap.compress(Bitmap.CompressFormat.JPEG, 70, stream)
            return stream.toByteArray()
        }

        fun removeDashesFromPhoneNumber(phoneNumber: String): String {
            // Use replace to remove dashes
            return phoneNumber.replace("-", "")
        }

        val MULTIPLE_PERMISSIONS = 10
        var permissions = arrayOf(
            Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.RECORD_AUDIO
        )

        fun checkPermissions(): Boolean {

            if (Build.VERSION.SDK_INT >= 33) {
                permissions = arrayOf(
                    Manifest.permission.CAMERA,
                    Manifest.permission.RECORD_AUDIO,
                    Manifest.permission.READ_MEDIA_IMAGES,
                    Manifest.permission.READ_MEDIA_AUDIO,
                    Manifest.permission.READ_MEDIA_VIDEO
                )
            }

            var result: Int
            val listPermissionsNeeded: MutableList<String> = ArrayList()
            for (p in permissions) {
                result = ContextCompat.checkSelfPermission(context as Activity, p)
                if (result != PackageManager.PERMISSION_GRANTED) {
                    listPermissionsNeeded.add(p)
                }

            }
            if (listPermissionsNeeded.isNotEmpty()) {
                ActivityCompat.requestPermissions(
                    context as Activity,
                    listPermissionsNeeded.toTypedArray(),
                    MULTIPLE_PERMISSIONS
                )
                return false
            }
            return true
        }

        fun formatPhoneNumber(phoneNumber: String): String {
            val cleanedNumber = phoneNumber.replace("\\D".toRegex(), "") // Remove non-digits
            return if (cleanedNumber.length == 10) {
                "${cleanedNumber.substring(0, 3)}-${
                    cleanedNumber.substring(
                        3,
                        6
                    )
                }-${cleanedNumber.substring(6)}"
            } else {
                cleanedNumber // Return the cleaned number if it doesn't have 10 digits
            }
        }

        fun parseBackgroundColor(tvJobstatus: ConstraintLayout, hexColorCode: String) {
            val currentTextColor = Color.parseColor(hexColorCode)

            // Adjust the alpha component
            val adjustedAlpha = (Color.alpha(currentTextColor) * 0.1).toInt()

            // Create the new color with adjusted alpha
            val adjustedColor = Color.argb(
                adjustedAlpha,
                Color.red(currentTextColor),
                Color.green(currentTextColor),
                Color.blue(currentTextColor)
            )

            val cornerRadius = 20f // You can adjust this value based on your preference

            // Create a GradientDrawable
            val gradientDrawable = GradientDrawable()
            gradientDrawable.cornerRadius = cornerRadius
            gradientDrawable.setColor(adjustedColor)

            // Set the background drawable with corner radius to the TextView
            tvJobstatus.background = gradientDrawable
        }

        var changeStatusClicked: Boolean = false
        var isFirsttimeStatusChange: Boolean = true
        var joiningDate: String = ""
        var jobStatusid: Int? = 0
        var offeredSalary: String = ""
        var offerLetterLinkExpiryDate: String = ""
        var jobCount: String = ""
        var clientbranchName: String = ""
        var clientGroupname: String = ""
        var headcountexprequired: String = ""
        var searchedJobData: SearchJobsClientsResp? = null
        var searchedclientdata: SearchClientByNameResp? = null
        var datalistt: ArrayList<String> = ArrayList()
        var candidatesJobResp: CandidateJobsResponse? = null
        var jobfrequency: String? = ""
        var skilllistJobReq: ArrayList<skillmodelforJobreq>? = null
        var htmlcontent: String = ""
        var jobreqlist: ArrayList<com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.GetJobRequests.Record> =
            ArrayList()
        var message: String? = ""
        var too: String? = ""
        var from: String? = ""
        var selectedJObGuid: String? = ""
        var clietidforBulkMsg: Int? = 0
        var clientforbulkmsgs: String? = ""
        var twilioResp: com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.getTwilioConfig.Data? =
            null
        var phonenumberlist: ArrayList<String> = ArrayList()
        var editreqjobSkills: ArrayList<com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.JobRequestSkills.Datum>? =
            null
        var jobReqbyJobid: com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.GetJobRequestResp.Data? =
            null
        var isfromEditJobRequisition: Boolean = false
        var jobReqStatusesList: ArrayList<com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.JobRequsitionStatusResp.Datum>? =
            null
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

        fun showDialog(context: Context, activity: Activity) {
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

        fun htmlToFile(context: Context, htmlContent: String, fileName: String): File? {
            try {
                // Create a temporary file
                val file = File(context.cacheDir, fileName)

                // Write HTML content to the file
                FileWriter(file).use { writer ->
                    writer.write(htmlContent)
                }

                return file
            } catch (e: IOException) {
                e.printStackTrace()
                return null
            }
        }
    }


}