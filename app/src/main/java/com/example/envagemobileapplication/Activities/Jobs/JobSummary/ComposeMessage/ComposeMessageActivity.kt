package com.example.envagemobileapplication.Activities.Jobs.JobSummary.ComposeMessage

import BaseActivity
import android.R
import android.app.AlertDialog
import android.graphics.Color
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.util.Patterns
import android.view.KeyEvent
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.envagemobileapplication.Adapters.customadapter
import com.example.envagemobileapplication.Models.RequestModels.sendMesageReqModel
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.MessageTemplatesRes.Datum
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.clientHedrSumryRsp.ClientHeaderSummaryResponse
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.getTwilioConfig.Data
import com.example.envagemobileapplication.Oauth.TokenManager
import com.example.envagemobileapplication.Utils.Loader
import com.example.envagemobileapplication.ViewModels.SendOfferLetterViewModel
import com.example.envagemobileapplication.databinding.ActivityComposeMessageBinding
import com.ezshifa.aihealthcare.network.ApiUtils
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat

class ComposeMessageActivity : BaseActivity() {

    private var twilioResp: Data? = null
    private var replacecontent: String = ""
    private var clientHeaderSummaryResp: ClientHeaderSummaryResponse? = null
    var descriptiontext: String = ""
    private var jobdeetails: com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.getjobbyid.Data? =
        null
    var firstname = ""
    var lastname = ""
    var joiningdate = ""
    var salary = ""
    var offerletterlink = ""
    var clientname = ""
    var clientindustry = ""
    var clientwebsite = ""
    var clientFacebook = ""
    var clientInstagram = ""
    var clientLinkedin = ""
    var clientAddress = ""
    var clientLocation = ""
    var clientCountry = ""
    var clientCity = ""
    var clientState = ""
    var clientZipCode = ""
    var clientPhoneNumber = ""
    var senderName = ""
    var senderDesignation = ""
    var positionname = ""
    var jobIndustry = ""
    var jobType = ""
    var jobNature = ""
    var jobFrequency = ""
    var jobweekDays = ""
    var jobestimatedhours = ""
    var jobaddress = ""
    var jobCountry = ""
    var jobcity = ""
    var jobstate = ""
    var jobzipcode = ""
    var joblocation = ""

    var global = com.example.envagemobileapplication.Utils.Global
    private var templateId: String = ""
    private var messageTemplatesList: ArrayList<Datum>? = null
    private var messageTemplatesResponse: MutableList<Datum>? = null
    lateinit var loader: Loader
    lateinit var tokenManager: TokenManager
    lateinit var token: String
    val viewModel: SendOfferLetterViewModel by viewModels()
    lateinit var binding: ActivityComposeMessageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityComposeMessageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initviews()
        clicklisteners()
        observers()
        networkCalls()


    }

    private fun networkCalls() {
        loader.show()

        viewModel.getCustomTemplatesForMessages(this, tokenManager.getAccessToken())
        var jobguid = global.jobHeaderSummary!!.data.jobInfo.guid!!
        viewModel.getjobbyJobid(this, tokenManager.getAccessToken(), jobguid)

        viewModel.getTwilioConfigration(this, tokenManager.getAccessToken())
    }

    private fun observers() {
        viewModel.LDGetTwilioConfig.observe(this) {
            loader.hide()
            twilioResp = it.data
            try {
                binding.tvEmailFrom.setText(it.data.number.toString())
            }
            catch (e:Exception){}


        }
        viewModel.LDMessageTemplate.observe(this) {
            loader.hide()

            messageTemplatesResponse = it.data
            if (it.data != null) {

                messageTemplatesList =
                    ArrayList()
                for (i in 0 until it.data.size) {
                    messageTemplatesList!!.add(it.data.get(i))
                }

                var items: ArrayList<String> = ArrayList()
                items.add("Select Template")
                for (i in 0 until messageTemplatesList!!.size) {
                    items.add(messageTemplatesList!!.get(i).templateName)
                }


                try {
                    val adapter = customadapter(
                        this,
                        R.layout.simple_spinner_item,
                        items
                    )
                    binding.spinnerSelectTemplate.setAdapter(adapter)
                    binding.spinnerSelectTemplate.setSelection(0)

                } catch (e: Exception) {

                }
            }
        }
        viewModel.LDGetJobByJobId.observe(this) {
            loader.hide()

            jobdeetails = it.data
            if (jobdeetails != null) {
                val inputDate = jobdeetails!!.startDate.toString()
                val formattedDate = formatDate(inputDate)
                joiningdate = formattedDate
                salary = jobdeetails!!.minimumSalary.toString()
                jobweekDays = jobdeetails!!.workingDays
                jobestimatedhours = jobdeetails!!.estimatedHours.toString()
                var clientid = jobdeetails!!.clientId

                getClientHeaderSummary(clientid)
            }
        }

        viewModel.LDSendMessage.observe(this) {
            loader.hide()

            if (it != null) {
                finish()
                Toast.makeText(this, "Message has been sent successfully.", Toast.LENGTH_LONG)
                    .show()
            }
        }
    }

    private fun clicklisteners() {
        binding.etDescription!!.setOnTextChangeListener { text ->
            binding.ccDescription.error = null
            if (text.contains("<")) {
                descriptiontext = text
            } else {
                descriptiontext = "<p>" + text + "</p>"
            }


        }
        binding.btnAddClient.setOnClickListener {
            if (binding.etDescription.html != null) {
                descriptiontext = binding.etDescription.html.toString()
                if (!descriptiontext.isNullOrBlank()) {
                    val htmlContent = descriptiontext
                    val mediaType = "text/html".toMediaTypeOrNull()
                    val descriptionBody = RequestBody.create(mediaType, htmlContent)
                    val descriptionPart =
                        MultipartBody.Part.createFormData(
                            "body",
                            "description.html",
                            descriptionBody
                        )
                } else {

                    val htmlContent = "<p></p>"
                    val mediaType = "text/html".toMediaTypeOrNull()
                    val descriptionBody = RequestBody.create(mediaType, htmlContent)
                    val descriptionPart =
                        MultipartBody.Part.createFormData(
                            "body",
                            "description.html",
                            descriptionBody
                        )
                }


                var phonenumber = global.composeMessagePhoneNumber!!
                var toolist: ArrayList<String> = ArrayList()
                toolist.add(phonenumber)
                var dcontent = binding.etDescription.html.toString()
                var model = sendMesageReqModel(toolist, dcontent)
                viewModel.sendMessageResponse(this, token, model,binding)
            } else {
                if (descriptiontext.isNullOrEmpty() || descriptiontext.equals("<p></p>")) {
                    binding.ccDescription.error = "body is Required."
                    binding.ccDescription.errorIconDrawable = null// Set the error message
                    binding.ccDescription.setErrorTextAppearance(com.example.envagemobileapplication.R.style.ErrorText)
                }
            }

        }
        binding.ivcCross.setOnClickListener { showDialog() }
        binding.ivCancel.setOnClickListener { showDialog() }
        binding.TISelectTemplate.setOnTouchListener(View.OnTouchListener { v, event ->
            if (binding.spinnerSelectTemplate.isPopupShowing) {
                binding.spinnerSelectTemplate.dismissDropDown()
            } else {
                binding.spinnerSelectTemplate.showDropDown()
            }
            false
        })
        binding.spinnerSelectTemplate.setOnTouchListener(View.OnTouchListener { v, event ->
            if (binding.spinnerSelectTemplate.isPopupShowing) {
                binding.spinnerSelectTemplate.dismissDropDown()
            } else {
                binding.spinnerSelectTemplate.showDropDown()
            }
            false
        })
        binding.spinnerSelectTemplate.setOnItemClickListener { _, _, position, _ ->
            var selectedText = binding.spinnerSelectTemplate.text.toString()
            binding.TISelectTemplate.error = null
            for (i in 0 until messageTemplatesResponse!!.size) {
                if (selectedText == messageTemplatesResponse!!.get(i).templateName.toString()) {
                    templateId = messageTemplatesResponse!!.get(i).messageTemplateId.toString()

                    var filename =
                        messageTemplatesResponse!!.get(i).templatePath.toString()


                    try {
                        val replacedHtmlContent = replacePlaceholders(filename)

                        replacecontent = replacedHtmlContent
                        // Set the modified HTML content to HtmlTextView
                        binding.etDescription.html = replacedHtmlContent
                        // loadJobDescriptionContent(baseurlnew)
                    } catch (e: Exception) {
                        Toast.makeText(
                            this,
                            e.toString(),
                            Toast.LENGTH_LONG
                        ).show()
                    }

                } else {
                    // binding.tvDescription.text = "No Description to Show"
                }
            }
        }

    }

    private fun initviews() {
        loader = Loader(this)
        tokenManager = TokenManager(this)
        token = tokenManager.getAccessToken().toString()
        setUpRtf()
        firstname = global.fn.toString()
        lastname = global.ln.toString()

        val hintSubject = "Body *"
        val formattedhintSubject = formatHintWithRedAsterisk(hintSubject)
        binding.textView37.text = formattedhintSubject
        try {

            binding.tvEmailTo.setText(global.composeMessagePhoneNumber)
            if (senderName != null) {
                senderName =
                    global.loggedinuserDetails!!.firstName + " " + global.loggedinuserDetails!!.lastName
            }

            if (senderDesignation != null) {
                senderDesignation =
                    global.loggedinuserDetails!!.designation.designationName.toString()
            }

            if (positionname != null) {
                positionname = global.jobHeaderSummary!!.data.jobInfo.positionName
            }

            if (jobIndustry != null) {
                jobIndustry = global.jobHeaderSummary!!.data.jobInfo.industryName.toString()
            }

            if (jobType != null) {
                jobType = global.jobHeaderSummary!!.data.jobInfo.jobType
            }

            if (jobType != null) {
                jobNature = global.jobHeaderSummary!!.data.jobInfo.jobNature
            }

            if (jobFrequency != null) {
                jobFrequency = global.jobHeaderSummary!!.data.jobInfo.jobFrequency
            }

            if (jobaddress != null) {
                jobaddress =
                    global.jobHeaderSummary!!.data.jobInfo.address1 + " " + global.jobHeaderSummary!!.data.jobInfo.address2
            }


            if (jobcity != null) {
                jobcity = global.jobHeaderSummary!!.data.jobInfo.city.toString()
            }


            if (jobstate != null) {
                jobstate = global.jobHeaderSummary!!.data.jobInfo.state.toString()
            }


            if (joblocation != null) {
                joblocation = global.jobHeaderSummary!!.data.jobInfo.location.toString()
            }



            offerletterlink = "offer letter link"
            clientFacebook = "www.facebook.com"
            clientInstagram = "www.instagram.com"
            clientLinkedin = "www.facebook.com"
            if (jobCountry != null) {
                jobCountry = global.jobHeaderSummary!!.data.jobInfo.country.toString()
            }

            if (jobzipcode != null) {
                jobzipcode = global.jobHeaderSummary!!.data.jobInfo.zipcode.toString()
            }
        } catch (e: Exception) {
        }
    }

    private fun showDialog() {
        val alertDialogBuilder = AlertDialog.Builder(this)

        // Set the dialog title and message
        alertDialogBuilder.setTitle("Discard Changes")
        alertDialogBuilder.setMessage("Are you sure you want to discard changes?")

        // Add positive button and its click listener
        alertDialogBuilder.setPositiveButton("Yes") { dialog, which ->
            // Handle the OK button click (if needed)
            dialog.dismiss()
            this.finish()// Dismiss the dialog
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

    private fun setUpRtf() {
        binding.etDescription!!.setEditorHeight(110)

        binding.etDescription!!.setEditorFontSize(14)

        binding.etDescription!!.setEditorFontColor(Color.BLACK)

        binding.etDescription!!.setPadding(0, 10, 10, 10)

        binding.etDescription.setOnKeyListener(View.OnKeyListener { _, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                val enteredText = binding.etDescription.html  // Get the HTML content

                // Check if the entered text looks like a URL (you might want to improve this validation)
                if (Patterns.WEB_URL.matcher(enteredText).matches()) {
                    // Manually insert the link
                    val url = enteredText
                    val linkText = "Offer Letter Link"
                    binding.etDescription.insertLink(url, linkText)

                    // Clear the editor to avoid duplicate entries
                    binding.etDescription.html = ""

                    // Return true to consume the key event
                    return@OnKeyListener true
                }
            }

            // Return false to allow the default action to be performed
            false
        })

        binding.actionUndo.setOnClickListener { binding.etDescription!!.undo() }

        binding.actionRedo.setOnClickListener { binding.etDescription!!.redo() }

        binding.actionBold.setOnClickListener { binding.etDescription!!.setBold() }

        binding.actionItalic.setOnClickListener { binding.etDescription!!.setItalic() }

        binding.actionStrikethrough.setOnClickListener { binding.etDescription!!.setStrikeThrough() }

        binding.actionUnderline.setOnClickListener { binding.etDescription!!.setUnderline() }

        binding.actionTxtColor.setOnClickListener(object : View.OnClickListener {
            private var isChanged = false
            override fun onClick(v: View) {
                binding.etDescription!!.setTextColor(if (isChanged) Color.BLACK else Color.RED)
                isChanged = !isChanged
            }
        })

        binding.actionBgColor.setOnClickListener(
            object : View.OnClickListener {
                private var isChanged = false
                override fun onClick(v: View) {
                    binding.etDescription!!.setTextBackgroundColor(if (isChanged) Color.TRANSPARENT else Color.YELLOW)
                    isChanged = !isChanged
                }
            })

        binding.actionAlignLeft.setOnClickListener { binding.etDescription!!.setAlignLeft() }

        binding.actionAlignCenter.setOnClickListener { binding.etDescription!!.setAlignCenter() }

        binding.actionAlignRight.setOnClickListener { binding.etDescription!!.setAlignRight() }


        binding.actionInsertBullets.setOnClickListener { binding.etDescription!!.setBullets() }

        binding.actionInsertNumbers.setOnClickListener { binding.etDescription!!.setNumbers() }

        binding.actionInsertLink.setOnClickListener {
            binding.etDescription!!.insertLink(
                "https://github.com/wasabeef",
                "wasabeef"
            )
        }
    }

    fun replacePlaceholders(htmlContent: String): String {
        // Replace placeholders with your desired values
        var replacedContent = htmlContent
            .replace("[First Name]", if (firstname.isNotEmpty()) firstname else "[First Name]")
            .replace("[Last Name]", if (lastname.isNotEmpty()) lastname else "[Last Name]")
            .replace("[Joining Date]", if (joiningdate.isNotEmpty()) joiningdate else "[Joining Date]")
            .replace("[Salary]", if (salary.isNotEmpty()) salary else "[Salary]")
            .replace("[Offer Letter Link]", if (offerletterlink.isNotEmpty()) offerletterlink else "[Offer Letter Link]")
            .replace("[Client Name]", if (clientname.isNotEmpty()) clientname else "[Client Name]")
            .replace("[Client Industry]", if (clientindustry.isNotEmpty()) clientindustry else "[Client Industry]")
            .replace("[Client Website]", if (clientwebsite.isNotEmpty()) clientwebsite else "[Client Website]")
            .replace("[Client Address]", if (clientAddress.isNotEmpty()) clientAddress else "[Client Address]")
            .replace("[Client Location]", if (clientLocation.isNotEmpty()) clientLocation else "[Client Location]")
            .replace("[Client Country]", if (clientCountry.isNotEmpty()) clientCountry else "[Client Country]")
            .replace("[Client City]", if (clientCity.isNotEmpty()) clientCity else "[Client City]")
            .replace("[Client State]", if (clientState.isNotEmpty()) clientState else "[Client State]")
            .replace("[Client Zip Code]", if (clientZipCode.isNotEmpty()) clientZipCode else "[Client Zip Code]")
            .replace("[Client Phone Number]", if (clientPhoneNumber.isNotEmpty()) clientPhoneNumber else "[Client Phone Number]")
            .replace("[Sender Name]", if (senderName.isNotEmpty()) senderName else "[Sender Name]")
            .replace("[Sender Designation]", if (senderDesignation.isNotEmpty()) senderDesignation else "[Sender Designation]")
            .replace("[Job Position Name]", if (positionname.isNotEmpty()) positionname else "[Job Position Name]")
            .replace("[Job Industry]", if (jobIndustry.isNotEmpty()) jobIndustry else "")
            .replace("[Job Type]", if (jobType.isNotEmpty()) jobType else "[Job Type]")
            .replace("[Job Nature]", if (jobNature.isNotEmpty()) jobNature else "[Job Nature]")
            .replace("[Job Frequency]", if (jobFrequency.isNotEmpty()) jobFrequency else "[Job Frequency]")
            .replace("[Job Weekdays]", if (jobweekDays.isNotEmpty()) jobweekDays else "[Job Weekdays]")
            .replace("[Job Estimated Hours]", if (jobestimatedhours.isNotEmpty()) jobestimatedhours else "[Job Estimated Hours]")
            .replace("[Job Address]", if (jobaddress.isNotEmpty()) jobaddress else "[Job Address]")
            .replace("[Job Country]", if (jobCountry.isNotEmpty()) jobCountry else "[Job Country]")
            .replace("[Job City]", if (jobcity.isNotEmpty()) jobcity else "[Job City]")
            .replace("[Job State]", if (jobstate.isNotEmpty()) jobstate else "[Job State]")
            .replace("[Job Zip Code]", if (jobzipcode.isNotEmpty()) jobzipcode else "[Job Zip Code]")
            .replace("[Job Location]", if (joblocation.isNotEmpty()) joblocation else "[Job Location]")


        return replacedContent
    }

    private fun getClientHeaderSummary(clientid: Int) {
        loader.show()
        var tokenmanager: TokenManager = TokenManager(this)
        var token = tokenmanager.getAccessToken()

        val clientId = clientid
        try {
            ApiUtils.getAPIService(this).GetClientHeaderSummary(
                token.toString(), clientId
            )
                .enqueue(object : Callback<ClientHeaderSummaryResponse> {
                    override fun onResponse(
                        call: Call<ClientHeaderSummaryResponse>,
                        response: Response<ClientHeaderSummaryResponse>
                    ) {

                        if (response.body() != null) {
                            loader.hide()
                            clientHeaderSummaryResp = response.body()!!
                            try {
                                clientname = clientHeaderSummaryResp!!.data.clientInfo.name
                                clientindustry =
                                    clientHeaderSummaryResp!!.data.clientInfo.industryName
                                clientwebsite =
                                    clientHeaderSummaryResp!!.data.clientInfo.websiteUrl
                                clientAddress =
                                    clientHeaderSummaryResp!!.data.clientInfo.primaryAddress1 + " " + clientHeaderSummaryResp!!.data.clientInfo.primaryAddress2
                                clientLocation =
                                    clientHeaderSummaryResp!!.data.clientInfo.location
                                clientCountry =
                                    clientHeaderSummaryResp!!.data.clientInfo.country
                                clientCity =
                                    clientHeaderSummaryResp!!.data.clientInfo.primaryAddressCity
                                clientState =
                                    clientHeaderSummaryResp!!.data.clientInfo.primaryAddressState
                                clientZipCode =
                                    clientHeaderSummaryResp!!.data.clientInfo.primaryAddressZipcode
                                clientPhoneNumber =
                                    clientHeaderSummaryResp!!.data.clientInfo.phone
                            } catch (e: Exception) {
                            }

                        }

                    }

                    override fun onFailure(call: Call<ClientHeaderSummaryResponse>, t: Throwable) {
                        Log.i("exceptions", t.toString())

                    }
                })
        } catch (ex: java.lang.Exception) {
            Toast.makeText(this, ex.toString(), Toast.LENGTH_LONG).show()
        }

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