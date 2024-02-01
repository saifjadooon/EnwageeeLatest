package com.example.envagemobileapplication.Activities.DashBoard.BulkMessages.BulkMessagesFragments

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.envagemobileapplication.Adapters.customadapter
import com.example.envagemobileapplication.Models.RequestModels.sendMesageReqModel
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.GetJobHeaderSummary.GetJobHeaderSummary
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.MessageTemplatesRes.Datum
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.clientHedrSumryRsp.ClientHeaderSummaryResponse
import com.example.envagemobileapplication.Oauth.TokenManager
import com.example.envagemobileapplication.R
import com.example.envagemobileapplication.Utils.Loader
import com.example.envagemobileapplication.ViewModels.BulkMessagesViewModel
import com.example.envagemobileapplication.databinding.FragmentSendBulkMessageBinding
import com.ezshifa.aihealthcare.network.ApiUtils
import com.google.android.flexbox.FlexboxLayout
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SendBulkMessageF : Fragment() {
    var descriptiontext: String = ""
    private var replacecontent: String = ""
    private var templateId: String = ""
    private var messageTemplatesResponse: MutableList<Datum>? = null
    lateinit var loader: Loader
    lateinit var tokenManager: TokenManager
    lateinit var token: String
    val viewModel: BulkMessagesViewModel by activityViewModels()
    lateinit var binding: FragmentSendBulkMessageBinding
    var global = com.example.envagemobileapplication.Utils.Global
    private var messageTemplatesList: ArrayList<Datum>? = null
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


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSendBulkMessageBinding.inflate(inflater, container, false)
        initviews()
        clicklisteners()
        observers()
        networkCalls()
        return binding.root
    }


    private fun networkCalls() {
        viewModel.getCustomTemplatesForMessages(requireContext(), tokenManager.getAccessToken())
        getClientHeaderSummary()
        getJobHeaderSummary()
    }

    fun getJobHeaderSummary() {

        try {
            ApiUtils.getAPIService(requireContext()).GetJobHeaderSummary(

                token,
                global.selectedJObGuid!!
            )
                .enqueue(object : Callback<GetJobHeaderSummary> {
                    override fun onResponse(
                        call: Call<GetJobHeaderSummary>,
                        response: Response<GetJobHeaderSummary>
                    ) {
                        if (response.body() != null) {
                            if (response.body()!!.data.jobInfo.positionName != null) {
                                positionname = response.body()!!.data.jobInfo.positionName
                            }

                            if (response.body()!!.data.jobInfo.industryName != null) {
                                jobIndustry = response.body()!!.data.jobInfo.industryName.toString()
                            }

                            if (response.body()!!.data.jobInfo.jobType != null) {
                                jobType = response.body()!!.data.jobInfo.jobType
                            }

                            if (response.body()!!.data.jobInfo.jobNature != null) {
                                jobNature = response.body()!!.data.jobInfo.jobNature
                            }

                            if (response.body()!!.data.jobInfo.jobFrequency != null) {
                                jobFrequency = response.body()!!.data.jobInfo.jobFrequency
                            }

                            if (response.body()!!.data.jobInfo.address1 != null && response.body()!!.data.jobInfo.address2 != null) {
                                jobaddress =
                                    response.body()!!.data.jobInfo.address1 + " " + response.body()!!.data.jobInfo.address2
                            } else if (response.body()!!.data.jobInfo.address1 != null && response.body()!!.data.jobInfo.address2 == null) {
                                jobaddress =
                                    response.body()!!.data.jobInfo.address1
                            } else if (response.body()!!.data.jobInfo.address1 == null && response.body()!!.data.jobInfo.address2 != null) {
                                jobaddress =
                                    response.body()!!.data.jobInfo.address2
                            }
                            if (response.body()!!.data.jobInfo.city != null) {
                                jobcity = response.body()!!.data.jobInfo.city.toString()
                            }


                            if (response.body()!!.data.jobInfo.state != null) {
                                jobstate = response.body()!!.data.jobInfo.state.toString()
                            }


                            if (response.body()!!.data.jobInfo.location != null) {
                                joblocation = response.body()!!.data.jobInfo.location.toString()
                            }




                            if (response.body()!!.data.jobInfo.country != null) {
                                jobCountry = response.body()!!.data.jobInfo.country.toString()
                            }

                            if (response.body()!!.data.jobInfo.zipcode != null) {
                                jobzipcode = response.body()!!.data.jobInfo.zipcode.toString()
                            }
                        }
                    }

                    override fun onFailure(call: Call<GetJobHeaderSummary>, t: Throwable) {
                        Log.i("exceptionddsfdsfds", t.toString())

                    }
                })
        } catch (ex: java.lang.Exception) {
            Log.i("exceptionddsfdsfds", ex.toString())
        }
    }

    private fun getClientHeaderSummary() {
        loader.show()
        var tokenmanager: TokenManager = TokenManager(requireContext())
        var token = tokenmanager.getAccessToken()

        val clientId =
            global.clietidforBulkMsg
        try {
            ApiUtils.getAPIService(requireContext()).GetClientHeaderSummary(
                token.toString(), clientId!!
            )
                .enqueue(object : Callback<ClientHeaderSummaryResponse> {
                    override fun onResponse(
                        call: Call<ClientHeaderSummaryResponse>,
                        response: Response<ClientHeaderSummaryResponse>
                    ) {
                        if (isAdded) {
                            if (response.body() != null) {
                                if (response.body()!!.data.clientInfo.name != null) {
                                    clientname = response.body()!!.data.clientInfo.name
                                }
                                if (response.body()!!.data.clientInfo.industryName != null) {
                                    clientindustry = response.body()!!.data.clientInfo.industryName
                                }
                                if (response.body()!!.data.clientInfo.websiteUrl != null) {
                                    clientwebsite = response.body()!!.data.clientInfo.websiteUrl
                                }
                                if (response.body()!!.data.clientInfo.primaryAddress1 != null) {
                                    clientAddress =
                                        response.body()!!.data.clientInfo.primaryAddress1
                                }
                                if (response.body()!!.data.clientInfo.location != null) {
                                    clientLocation = response.body()!!.data.clientInfo.location
                                }
                                if (response.body()!!.data.clientInfo.country != null) {
                                    clientCountry = response.body()!!.data.clientInfo.country
                                }
                                if (response.body()!!.data.clientInfo.city != null) {
                                    clientCity = response.body()!!.data.clientInfo.city
                                }
                                if (response.body()!!.data.clientInfo.state != null) {
                                    clientState = response.body()!!.data.clientInfo.state
                                }
                                if (response.body()!!.data.clientInfo.zipcode != null) {
                                    clientZipCode = response.body()!!.data.clientInfo.zipcode
                                }
                                if (response.body()!!.data.clientInfo.phone != null) {
                                    clientPhoneNumber = response.body()!!.data.clientInfo.phone
                                }

                                if (response.body()!!.data.clientSocialMedia != null) {
                                    if (response.body()!!.data.clientSocialMedia.size > 0) {
                                        for (i in 0 until response.body()!!.data.clientSocialMedia.size) {

                                            if (response.body()!!.data.clientSocialMedia.get(i).name.equals(
                                                    "Facebook"
                                                )
                                            ) {
                                                if (response.body()!!.data.clientSocialMedia.get(i).url != null) {
                                                    clientFacebook =
                                                        response.body()!!.data.clientSocialMedia.get(
                                                            i
                                                        ).url.toString()
                                                }
                                            }

                                            if (response.body()!!.data.clientSocialMedia.get(i).name.equals(
                                                    "LinkedIn"
                                                )
                                            ) {
                                                if (response.body()!!.data.clientSocialMedia.get(i).url != null) {
                                                    clientLinkedin =
                                                        response.body()!!.data.clientSocialMedia.get(
                                                            i
                                                        ).url.toString()
                                                }
                                            }

                                            if (response.body()!!.data.clientSocialMedia.get(i).name.equals(
                                                    "Instagram"
                                                )
                                            ) {
                                                if (response.body()!!.data.clientSocialMedia.get(i).url != null) {
                                                    clientInstagram =
                                                        response.body()!!.data.clientSocialMedia.get(
                                                            i
                                                        ).url.toString()
                                                }
                                            }
                                        }
                                    }

                                }

                            }
                        }
                    }

                    override fun onFailure(call: Call<ClientHeaderSummaryResponse>, t: Throwable) {
                        Log.i("exceptions", t.toString())

                    }
                })
        } catch (ex: java.lang.Exception) {
            Toast.makeText(requireContext(), ex.toString(), Toast.LENGTH_LONG).show()
        }

    }

    private fun observers() {
        viewModel.LDMessageTemplate.observe(requireActivity()) {
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
                        requireContext(),
                        android.R.layout.simple_spinner_item,
                        items
                    )
                    binding.spinnerSelectTemplate.setAdapter(adapter)
                    binding.spinnerSelectTemplate.setSelection(0)

                } catch (e: Exception) {

                }
            }
        }
        viewModel.LDSendMessage.observe(requireActivity()) {
            loader.hide()
            if (it != null) {

                Toast.makeText(
                    requireContext(),
                    "Message has been sent successfully.",
                    Toast.LENGTH_LONG
                )
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
                            requireContext(),
                            e.toString(),
                            Toast.LENGTH_LONG
                        ).show()
                    }

                } else {
                    // binding.tvDescription.text = "No Description to Show"
                }
            }
        }
        binding.btnSend.setOnClickListener {
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
                var model = sendMesageReqModel(global.phonenumberlist, dcontent)
                viewModel.sendMessageResponse(requireContext(), token, model, binding)
            } else {
                if (descriptiontext.isNullOrEmpty() || descriptiontext.equals("<p></p>")) {
                    binding.ccDescription.error = "body is Required."
                    binding.ccDescription.errorIconDrawable = null// Set the error message
                    binding.ccDescription.setErrorTextAppearance(com.example.envagemobileapplication.R.style.ErrorText)
                }
            }
        }
    }

    private fun initviews() {
        loader = Loader(requireContext())
        tokenManager = TokenManager(requireContext())
        token = tokenManager.getAccessToken().toString()
        val hintSubject = "Body *"
        val formattedhintSubject = global.formatHintWithRedAsterisk(hintSubject)
        binding.textView37.text = formattedhintSubject
     /*   setUpRtf()*/
        try {
            binding.tvEmailFrom.setText(global.twilioResp!!.number.toString())
        } catch (e: Exception) {
        }
        try {

            setupScrollViews(global.phonenumberlist)

        } catch (e: Exception) {
        }
        if (global.clientforbulkmsgs != "") {
            global.clientforbulkmsgs = clientname
        }


    }

    private fun setupScrollViews(dataList: List<String>) {
        for (item in dataList) {
            val textView = TextView(requireContext())
            textView.text = item
            textView.background = this.resources.getDrawable(R.drawable.gray_bg)
            textView.setPadding(8, 8, 8, 8)

            val layoutParams = createFlexboxLayoutParams()
            layoutParams.topMargin = 8 // Set top margin
            layoutParams.bottomMargin = 8 // Set bottom margin
            textView.layoutParams = layoutParams

            binding.flexlayout.addView(textView)
        }

    }

    private fun createFlexboxLayoutParams(): FlexboxLayout.LayoutParams {
        var layoutParamss = FlexboxLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        layoutParamss.marginEnd = 16 // Example: Add margin
        // Example: Add margin
        return layoutParamss
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
            .replace(
                "[Joining Date]",
                if (joiningdate.isNotEmpty()) joiningdate else "[Joining Date]"
            )
            .replace("[Salary]", if (salary.isNotEmpty()) salary else "[Salary]")
            .replace(
                "[Offer Letter Link]",
                if (offerletterlink.isNotEmpty()) offerletterlink else "[Offer Letter Link]"
            )
            .replace("[Client Name]", if (clientname.isNotEmpty()) clientname else "[Client Name]")
            .replace(
                "[Client Industry]",
                if (clientindustry.isNotEmpty()) clientindustry else "[Client Industry]"
            )
            .replace(
                "[Client Website]",
                if (clientwebsite.isNotEmpty()) clientwebsite else "[Client Website]"
            )
            .replace(
                "[Client Address]",
                if (clientAddress.isNotEmpty()) clientAddress else "[Client Address]"
            )
            .replace(
                "[Client Location]",
                if (clientLocation.isNotEmpty()) clientLocation else "[Client Location]"
            )
            .replace(
                "[Client Country]",
                if (clientCountry.isNotEmpty()) clientCountry else "[Client Country]"
            )
            .replace("[Client City]", if (clientCity.isNotEmpty()) clientCity else "[Client City]")
            .replace(
                "[Client State]",
                if (clientState.isNotEmpty()) clientState else "[Client State]"
            )
            .replace(
                "[Client Zip Code]",
                if (clientZipCode.isNotEmpty()) clientZipCode else "[Client Zip Code]"
            )
            .replace(
                "[Client Phone Number]",
                if (clientPhoneNumber.isNotEmpty()) clientPhoneNumber else "[Client Phone Number]"
            )
            .replace("[Sender Name]", if (senderName.isNotEmpty()) senderName else "[Sender Name]")
            .replace(
                "[Sender Designation]",
                if (senderDesignation.isNotEmpty()) senderDesignation else "[Sender Designation]"
            )
            .replace(
                "[Job Position Name]",
                if (positionname.isNotEmpty()) positionname else "[Job Position Name]"
            )
            .replace("[Job Industry]", if (jobIndustry.isNotEmpty()) jobIndustry else "")
            .replace("[Job Type]", if (jobType.isNotEmpty()) jobType else "[Job Type]")
            .replace("[Job Nature]", if (jobNature.isNotEmpty()) jobNature else "[Job Nature]")
            .replace(
                "[Job Frequency]",
                if (jobFrequency.isNotEmpty()) jobFrequency else "[Job Frequency]"
            )
            .replace(
                "[Job Weekdays]",
                if (jobweekDays.isNotEmpty()) jobweekDays else "[Job Weekdays]"
            )
            .replace(
                "[Job Estimated Hours]",
                if (jobestimatedhours.isNotEmpty()) jobestimatedhours else "[Job Estimated Hours]"
            )
            .replace("[Job Address]", if (jobaddress.isNotEmpty()) jobaddress else "[Job Address]")
            .replace("[Job Country]", if (jobCountry.isNotEmpty()) jobCountry else "[Job Country]")
            .replace("[Job City]", if (jobcity.isNotEmpty()) jobcity else "[Job City]")
            .replace("[Job State]", if (jobstate.isNotEmpty()) jobstate else "[Job State]")
            .replace(
                "[Job Zip Code]",
                if (jobzipcode.isNotEmpty()) jobzipcode else "[Job Zip Code]"
            )
            .replace(
                "[Job Location]",
                if (joblocation.isNotEmpty()) joblocation else "[Job Location]"
            )
            .replace(
                "[Client Facebook]",
                if (clientFacebook.isNotEmpty()) clientFacebook else "[Client Facebook]"
            )
            .replace(
                "[Client Instagram]",
                if (clientInstagram.isNotEmpty()) clientInstagram else "[Client Instagram]"
            )
            .replace(
                "[Client LinkedIn]",
                if (clientLinkedin.isNotEmpty()) clientLinkedin else "[Client LinkedIn]"
            )


        return replacedContent
    }
}