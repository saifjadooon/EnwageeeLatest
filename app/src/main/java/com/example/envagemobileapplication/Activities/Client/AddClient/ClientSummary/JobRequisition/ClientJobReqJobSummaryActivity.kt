package com.example.envagemobileapplication.Activities.Client.AddClient.ClientSummary.JobRequisition

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.text.Spanned
import android.view.View
import android.view.ViewGroup
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.drawable.DrawableCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.envagemobileapplication.Activities.DashBoard.DashboardFragments.BottomSheet.BsJobReqStatuses
import com.example.envagemobileapplication.Activities.Jobs.JobSummary.JobSummaryFragments.JobsSummaryFragment
import com.example.envagemobileapplication.Adapters.BillingDetailJobSummaryAdapter
import com.example.envagemobileapplication.Models.RequestModels.JobSkill
import com.example.envagemobileapplication.Models.RequestModels.skillmodelforJobreq
import com.example.envagemobileapplication.Oauth.TokenManager
import com.example.envagemobileapplication.R
import com.example.envagemobileapplication.Utils.Constants
import com.example.envagemobileapplication.Utils.Loader
import com.example.envagemobileapplication.ViewModels.ClientSummaryViewModel
import com.example.envagemobileapplication.databinding.ActivityClientJobReqJobSummaryBinding
import com.google.android.flexbox.FlexboxLayout
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.text.SimpleDateFormat

class ClientJobReqJobSummaryActivity : AppCompatActivity() {
    private var industryglobal: String = ""
    private var isfromstatuschange: Boolean = false
    var jobCurrentStatus = ""
    lateinit var bottomSheetFragment: BsJobReqStatuses
    private var statusesList: ArrayList<com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.JobRequsitionStatusResp.Datum> =
        ArrayList()
    private var statuslistfiletered: ArrayList<com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.JobRequsitionStatusResp.Datum> =
        ArrayList()
    private var weekdaysList: List<String> = ArrayList()
    var global = com.example.envagemobileapplication.Utils.Global
    val salaryDetailsData: ArrayList<JobsSummaryFragment.KeyValueData> = ArrayList()
    val viewModel: ClientSummaryViewModel by viewModels()
    lateinit var binding: ActivityClientJobReqJobSummaryBinding
    lateinit var token: String
    lateinit var tokenManager: TokenManager
    lateinit var loader: Loader
    val skillsList: ArrayList<String> = ArrayList()
    val skillsListwithmodel: ArrayList<skillmodelforJobreq> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityClientJobReqJobSummaryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initviews()
        networkCalls()
        observers()
        clicklisteners()
    }

    private fun clicklisteners() {
        binding.tvjobNature.setOnClickListener {
            if (bottomSheetFragment.isAdded()) {
                return@setOnClickListener
            } else {
                bottomSheetFragment.show(supportFragmentManager, bottomSheetFragment.tag)
            }
        }
    }

    private fun setupScrollViews(dataList: List<String>) {
        for (item in dataList) {
            val textView = TextView(this)
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

    private fun observers() {
        viewModel.LDgetIndustry.observe(this) {
            loader.hide()
            if (it.data != null) {
                for (i in 0 until com.example.envagemobileapplication.Utils.Global.jobreqlist.size) {
                    if (com.example.envagemobileapplication.Utils.Global.jobreqlist.get(global.jobRequisitonPosition).industryId != null) {
                        var indutryid =
                            com.example.envagemobileapplication.Utils.Global.jobreqlist.get(global.jobRequisitonPosition).industryId
                        for (i in 0 until it.data.size) {
                            if (it.data.get(i).industryId == indutryid) {
                                industryglobal = it.data.get(i).name.toString()
                                binding.tvIndustry.setText(it.data.get(i).name.toString())
                            }
                        }
                    }
                }
            }
        }

        viewModel.LDgetJobSkills.observe(this) {
            loader.hide()
            if (it.data != null) {

                for (i in 0 until it.data.size) {
                    skillsList.add(it.data.get(i).name)
                    val jobskill =
                        skillmodelforJobreq(it.data.get(i).skillId, it.data.get(i).name, "1", false)
                    skillsListwithmodel.add(jobskill)

                }
                if (skillsList.size >= 1) {
                    binding.tvNoskills.visibility = View.INVISIBLE
                    setupScrollViews(skillsList)
                    global.skilllistJobReq = skillsListwithmodel
                } else {
                    binding.tvNoskills.visibility = View.VISIBLE
                }

                //     setupgridlayout(skillsList)
            }
        }

        viewModel.LDgetJobRequsitionStatusResp.observe(this) {
            loader.hide()
            if (it.data != null) {
                statusesList.clear()
                try {
                    global.jobReqStatusesList!!.clear()
                } catch (e: Exception) {

                }

                for (i in 0 until it.data.size) {
                    if (it.data.get(i).statusName.equals(jobCurrentStatus)) {
                        val hexColorCode =
                            it.data.get(i).colorCode
                        binding.tvjobNature.setTextColor(Color.parseColor(hexColorCode))
                        parseBackgroundColor(binding.tvjobNature, hexColorCode)
                        binding.tvjobNature.setText(jobCurrentStatus)
                    }
                    statusesList.add(it.data.get(i))
                }
                if (jobCurrentStatus.equals("Require More Detail")) {

                    for (i in 0 until statusesList.size) {
                        if (statusesList.get(i).statusName.equals("Cancelled")) {
                            statuslistfiletered.add(statusesList.get(i))
                        }
                    }
                } else if (jobCurrentStatus.equals("On hold")) {
                    for (i in 0 until statusesList.size) {
                        if (statusesList.get(i).statusName.equals("In Review")) {
                            statuslistfiletered.add(statusesList.get(i))
                        }
                    }

                } else if (jobCurrentStatus.equals("Approved")) {
                    for (i in 0 until statusesList.size) {
                        if (statusesList.get(i).statusName.equals("Cancelled")) {
                            statuslistfiletered.add(statusesList.get(i))
                        }
                    }
                } else if (jobCurrentStatus.equals("Cancelled")) {
                    for (i in 0 until statusesList.size) {
                        if (statusesList.get(i).statusName.equals("In Review")) {
                            statuslistfiletered.add(statusesList.get(i))
                        }
                    }

                } else if (jobCurrentStatus.equals("In Review")) {
                    for (i in 0 until statusesList.size) {
                        if (statusesList.get(i).statusName.equals("Require More Detail")) {
                            statuslistfiletered.add(statusesList.get(i))
                        }
                        if (statusesList.get(i).statusName.equals("On hold")) {
                            statuslistfiletered.add(statusesList.get(i))
                        }
                        if (statusesList.get(i).statusName.equals("Approved")) {
                            statuslistfiletered.add(statusesList.get(i))
                        }
                        if (statusesList.get(i).statusName.equals("Cancelled")) {
                            statuslistfiletered.add(statusesList.get(i))
                        }
                    }
                }

                global.jobReqStatusesList = statuslistfiletered
            }
        }

        viewModel.LDcloseChangeStatusBs.observe(this) {
            loader.hide()
            if (it != null) {

                if (it != "truee") {
                    jobCurrentStatus = it
                    bottomSheetFragment.dismiss()
                    Toast.makeText(
                        this,
                        "Status has been updated successfully.",
                        Toast.LENGTH_LONG
                    ).show()
                    var jobrequestid =
                        com.example.envagemobileapplication.Utils.Global.jobreqlist.get(global.jobRequisitonPosition).jobRequestId
                    viewModel.getjobReqStatuses(this, token, jobrequestid)
                    isfromstatuschange = true

                } else {
                    bottomSheetFragment.dismiss()
                }


            }
        }

    }

    private fun networkCalls() {
        var jobrequestid =
            com.example.envagemobileapplication.Utils.Global.jobreqlist.get(global.jobRequisitonPosition).jobRequestId
        viewModel.getindustry(this, token)
        viewModel.getjobskills(this, token, jobrequestid)
        viewModel.getjobReqStatuses(this, token, jobrequestid)

    }

    private fun initviews() {
        bottomSheetFragment = BsJobReqStatuses()
        tokenManager = TokenManager(this)
        token = tokenManager.getAccessToken().toString()
        loader = Loader(this)


        if (com.example.envagemobileapplication.Utils.Global.jobreqlist.get(global.jobRequisitonPosition).jobRequestBillingDetail != null) {
            if (com.example.envagemobileapplication.Utils.Global.jobreqlist.get(global.jobRequisitonPosition).jobRequestBillingDetail.markup != null) {
                salaryDetailsData.add(
                    JobsSummaryFragment.KeyValueData(
                        "Markup Percentage",
                        com.example.envagemobileapplication.Utils.Global.jobreqlist.get(global.jobRequisitonPosition).jobRequestBillingDetail.markup.toString()
                    )
                )
            } else {
                salaryDetailsData.add(
                    JobsSummaryFragment.KeyValueData(
                        "Markup Percentage",
                        "-"
                    )
                )
            }

            if (com.example.envagemobileapplication.Utils.Global.jobreqlist.get(global.jobRequisitonPosition).jobRequestBillingDetail.minPayRate != null) {
                salaryDetailsData.add(
                    JobsSummaryFragment.KeyValueData(
                        "Min. Pay Rate",
                        com.example.envagemobileapplication.Utils.Global.jobreqlist.get(global.jobRequisitonPosition).jobRequestBillingDetail.minPayRate.toString()
                    )
                )
            } else {
                salaryDetailsData.add(
                    JobsSummaryFragment.KeyValueData(
                        "Min. Pay Rate",
                        "-"
                    )
                )
            }
            if (com.example.envagemobileapplication.Utils.Global.jobreqlist.get(global.jobRequisitonPosition).jobRequestBillingDetail.minBillRate != null) {
                salaryDetailsData.add(
                    JobsSummaryFragment.KeyValueData(
                        "Min. Bill Rate",
                        com.example.envagemobileapplication.Utils.Global.jobreqlist.get(global.jobRequisitonPosition).jobRequestBillingDetail.minBillRate.toString()
                    )
                )
            } else {
                salaryDetailsData.add(
                    JobsSummaryFragment.KeyValueData(
                        "Min. Bill Rate",
                        "-"
                    )
                )
            }
            if (com.example.envagemobileapplication.Utils.Global.jobreqlist.get(global.jobRequisitonPosition).jobRequestBillingDetail.maxPayRate != null) {
                salaryDetailsData.add(
                    JobsSummaryFragment.KeyValueData(
                        "Max. Pay Rate",
                        com.example.envagemobileapplication.Utils.Global.jobreqlist.get(global.jobRequisitonPosition).jobRequestBillingDetail.maxPayRate.toString()
                    )
                )
            } else {
                salaryDetailsData.add(
                    JobsSummaryFragment.KeyValueData(
                        "Max. Pay Rate",
                        "-"
                    )
                )
            }
            if (com.example.envagemobileapplication.Utils.Global.jobreqlist.get(global.jobRequisitonPosition).jobRequestBillingDetail.maxBillRate != null) {
                salaryDetailsData.add(
                    JobsSummaryFragment.KeyValueData(
                        "Max. Bill Rate",
                        com.example.envagemobileapplication.Utils.Global.jobreqlist.get(global.jobRequisitonPosition).jobRequestBillingDetail.maxBillRate.toString()
                    )
                )
            } else {
                salaryDetailsData.add(
                    JobsSummaryFragment.KeyValueData(
                        "Max. Bill Rate",
                        "-"
                    )
                )
            }
            if (com.example.envagemobileapplication.Utils.Global.jobreqlist.get(global.jobRequisitonPosition).jobRequestBillingDetail.targetPayRate != null) {
                salaryDetailsData.add(
                    JobsSummaryFragment.KeyValueData(
                        "Target Pay Rate",
                        com.example.envagemobileapplication.Utils.Global.jobreqlist.get(global.jobRequisitonPosition).jobRequestBillingDetail.targetPayRate.toString()
                    )
                )
            } else {
                salaryDetailsData.add(
                    JobsSummaryFragment.KeyValueData(
                        "Target Pay Rate",
                        "-"
                    )
                )
            }
            if (com.example.envagemobileapplication.Utils.Global.jobreqlist.get(global.jobRequisitonPosition).jobRequestBillingDetail.targetBillRate != null) {

                salaryDetailsData.add(
                    JobsSummaryFragment.KeyValueData(
                        "Target Bill Rate",
                        com.example.envagemobileapplication.Utils.Global.jobreqlist.get(global.jobRequisitonPosition).jobRequestBillingDetail.targetBillRate.toString()
                    )
                )
            } else {
                salaryDetailsData.add(
                    JobsSummaryFragment.KeyValueData(
                        "Target Bill Rate",
                        "-"
                    )
                )
            }
            if (com.example.envagemobileapplication.Utils.Global.jobreqlist.get(global.jobRequisitonPosition).jobRequestBillingDetail.overtimeType != null) {

                if (com.example.envagemobileapplication.Utils.Global.jobreqlist.get(global.jobRequisitonPosition).jobRequestBillingDetail.overtimeType.equals(
                        "Yes"
                    )
                ) {
                    salaryDetailsData.add(
                        JobsSummaryFragment.KeyValueData(
                            "Overtime Type",
                            "Paid and Billed"
                        )
                    )
                } else {
                    salaryDetailsData.add(
                        JobsSummaryFragment.KeyValueData(
                            "Overtime Type",
                            "None"
                        )
                    )
                }

            } else {
                salaryDetailsData.add(
                    JobsSummaryFragment.KeyValueData(
                        "Overtime Type",
                        "-"
                    )
                )
            }
            if (com.example.envagemobileapplication.Utils.Global.jobreqlist.get(global.jobRequisitonPosition).jobRequestBillingDetail.overtimeMarkup != null) {
                salaryDetailsData.add(
                    JobsSummaryFragment.KeyValueData(
                        "Overtime Markup Percentage",
                        com.example.envagemobileapplication.Utils.Global.jobreqlist.get(global.jobRequisitonPosition).jobRequestBillingDetail.overtimeMarkup.toString()
                    )
                )
            } else {
                salaryDetailsData.add(
                    JobsSummaryFragment.KeyValueData(
                        "Overtime Markup Percentage",
                        "-"
                    )
                )
            }


            if (com.example.envagemobileapplication.Utils.Global.jobreqlist.get(global.jobRequisitonPosition).jobRequestBillingDetail.overtimePayRate != null) {
                salaryDetailsData.add(
                    JobsSummaryFragment.KeyValueData(
                        "Overtime Pay Rate",
                        com.example.envagemobileapplication.Utils.Global.jobreqlist.get(global.jobRequisitonPosition).jobRequestBillingDetail.overtimePayRate.toString()
                    )
                )
            } else {
                salaryDetailsData.add(
                    JobsSummaryFragment.KeyValueData(
                        "Overtime Pay Rate",
                        "-"
                    )
                )
            }
            if (com.example.envagemobileapplication.Utils.Global.jobreqlist.get(global.jobRequisitonPosition).jobRequestBillingDetail.overtimeBillRate != null) {
                salaryDetailsData.add(
                    JobsSummaryFragment.KeyValueData(
                        "Overtime Bill Rate",
                        com.example.envagemobileapplication.Utils.Global.jobreqlist.get(global.jobRequisitonPosition).jobRequestBillingDetail.overtimeBillRate.toString()
                    )
                )
            } else {
                salaryDetailsData.add(
                    JobsSummaryFragment.KeyValueData(
                        "Overtime Bill Rate",
                        "-"
                    )
                )
            }
            if (com.example.envagemobileapplication.Utils.Global.jobreqlist.get(global.jobRequisitonPosition).jobRequestBillingDetail.doubletimeType != null) {

                if (com.example.envagemobileapplication.Utils.Global.jobreqlist.get(global.jobRequisitonPosition).jobRequestBillingDetail.doubletimeType.equals(
                        "Yes"
                    )
                ) {
                    salaryDetailsData.add(
                        JobsSummaryFragment.KeyValueData(
                            "Double-time Type",
                            "Paid and Billed"
                        )
                    )
                } else {
                    salaryDetailsData.add(
                        JobsSummaryFragment.KeyValueData(
                            "Double-time Type",
                            "None"
                        )
                    )
                }


            } else {
                salaryDetailsData.add(
                    JobsSummaryFragment.KeyValueData(
                        "Double-time Type",
                        "-"
                    )
                )
            }
            if (com.example.envagemobileapplication.Utils.Global.jobreqlist.get(global.jobRequisitonPosition).jobRequestBillingDetail.doubletimeMarkup != null) {
                salaryDetailsData.add(
                    JobsSummaryFragment.KeyValueData(
                        "Double-Time Markup Percentage",
                        com.example.envagemobileapplication.Utils.Global.jobreqlist.get(global.jobRequisitonPosition).jobRequestBillingDetail.doubletimeMarkup.toString()
                    )
                )
            } else {
                salaryDetailsData.add(
                    JobsSummaryFragment.KeyValueData(
                        "Double-Time Markup Percentage",
                        "-"
                    )
                )
            }
            if (com.example.envagemobileapplication.Utils.Global.jobreqlist.get(global.jobRequisitonPosition).jobRequestBillingDetail.doubletimePayRate != null) {
                salaryDetailsData.add(
                    JobsSummaryFragment.KeyValueData(
                        "Double-Time Pay Rate",
                        com.example.envagemobileapplication.Utils.Global.jobreqlist.get(global.jobRequisitonPosition).jobRequestBillingDetail.doubletimePayRate.toString()
                    )
                )
            } else {
                salaryDetailsData.add(
                    JobsSummaryFragment.KeyValueData(
                        "Double-Time Pay Rate",
                        "-"
                    )
                )
            }
            if (com.example.envagemobileapplication.Utils.Global.jobreqlist.get(global.jobRequisitonPosition).jobRequestBillingDetail.doubletimeBillRate != null) {
                salaryDetailsData.add(
                    JobsSummaryFragment.KeyValueData(
                        "Double-Time Bill Rate",
                        com.example.envagemobileapplication.Utils.Global.jobreqlist.get(global.jobRequisitonPosition).jobRequestBillingDetail.doubletimeBillRate.toString()
                    )
                )
            } else {
                salaryDetailsData.add(
                    JobsSummaryFragment.KeyValueData(
                        "Double-Time Bill Rate",
                        "-"
                    )
                )
            }
            if (com.example.envagemobileapplication.Utils.Global.jobreqlist.get(global.jobRequisitonPosition).jobRequestBillingDetail.frequency != null) {
                salaryDetailsData.add(
                    JobsSummaryFragment.KeyValueData(
                        "Frequency",
                        com.example.envagemobileapplication.Utils.Global.jobreqlist.get(global.jobRequisitonPosition).jobRequestBillingDetail.frequency.toString()
                    )
                )
            } else {
                salaryDetailsData.add(
                    JobsSummaryFragment.KeyValueData(
                        "Frequency",
                        "-"
                    )
                )
            }

            setupBillingDetailAdapter(salaryDetailsData)
        }


        try {
            val weekdaysString =
                com.example.envagemobileapplication.Utils.Global.jobreqlist.get(global.jobRequisitonPosition).workingDays.toString()

            if (weekdaysList.isEmpty()) {

                weekdaysList = weekdaysString.split(",")
                setupHorizontalScrollView(weekdaysList)
            }

        } catch (e: Exception) {
        }
        try {
            val hexColorCode =
                com.example.envagemobileapplication.Utils.Global.jobreqlist.get(global.jobRequisitonPosition).colorCode
            binding.tvjobNature.setTextColor(Color.parseColor(hexColorCode))
            parseBackgroundColor(binding.tvjobNature, hexColorCode)

            jobCurrentStatus =
                com.example.envagemobileapplication.Utils.Global.jobreqlist.get(global.jobRequisitonPosition).status
            val drawable: Drawable? =
                binding.tvjobNature.compoundDrawables[2] // Assuming your drawable is on the right

            var colorrr = Color.parseColor(hexColorCode)
            if (drawable != null) {
                val wrappedDrawable = DrawableCompat.wrap(drawable)
                DrawableCompat.setTint(wrappedDrawable, colorrr)
                binding.tvjobNature.setCompoundDrawablesWithIntrinsicBounds(
                    null,
                    null,
                    wrappedDrawable,
                    null
                )
            }

            val jobtypehexcolor = "#AF6606"
            binding.tvJobstatus.setTextColor(Color.parseColor(jobtypehexcolor))
            parseBackgroundColor(binding.tvJobstatus, jobtypehexcolor)

            val blackcolor = "#000000"
            parseBackgroundColor(binding.tvjobtype, blackcolor)
        } catch (e: Exception) {
        }
        try {
            if (com.example.envagemobileapplication.Utils.Global.jobreqlist.get(global.jobRequisitonPosition).positionName != null) {
                binding.tvName.setText(
                    com.example.envagemobileapplication.Utils.Global.jobreqlist.get(
                        global.jobRequisitonPosition
                    ).positionName
                )
                binding.tvJobName.setText(
                    com.example.envagemobileapplication.Utils.Global.jobreqlist.get(
                        global.jobRequisitonPosition
                    ).positionName
                )
            } else {
                binding.tvJobName.setText("Not Provided")
            }
            if (com.example.envagemobileapplication.Utils.Global.jobreqlist.get(global.jobRequisitonPosition).clientName != null) {
                binding.clientname.setText(
                    com.example.envagemobileapplication.Utils.Global.jobreqlist.get(
                        global.jobRequisitonPosition
                    ).clientName
                )
            } else {
                binding.clientname.setText("Not Provided")
            }


            if (com.example.envagemobileapplication.Utils.Global.jobreqlist.get(global.jobRequisitonPosition).jobType != null) {
                binding.tvJobstatus.setText(
                    com.example.envagemobileapplication.Utils.Global.jobreqlist.get(
                        global.jobRequisitonPosition
                    ).jobType
                )
            } else {
                binding.tvJobstatus.setText("Not Provided")
            }

            if (com.example.envagemobileapplication.Utils.Global.jobreqlist.get(global.jobRequisitonPosition).jobNature != null) {
                binding.tvjobtype.setText(
                    com.example.envagemobileapplication.Utils.Global.jobreqlist.get(
                        global.jobRequisitonPosition
                    ).jobNature
                )
            } else {
                binding.tvjobtype.setText("Not Provided")
            }

            if (com.example.envagemobileapplication.Utils.Global.jobreqlist.get(global.jobRequisitonPosition).status != null) {
                binding.tvjobNature.setText(
                    com.example.envagemobileapplication.Utils.Global.jobreqlist.get(
                        global.jobRequisitonPosition
                    ).status
                )
            } else {
                binding.tvjobNature.setText("Not Provided")
            }

            if (com.example.envagemobileapplication.Utils.Global.jobreqlist.get(global.jobRequisitonPosition).payGroupName != null) {
                binding.payGroup.setText(
                    "Pay Group: " + com.example.envagemobileapplication.Utils.Global.jobreqlist.get(
                        global.jobRequisitonPosition
                    ).payGroupName.toString()
                )
            } else {
                binding.payGroup.setText("Pay Group:Not Provided")
            }

            if (com.example.envagemobileapplication.Utils.Global.jobreqlist.get(global.jobRequisitonPosition).headcount != null) {
                binding.tvHeadCount.setText(
                    "Headcount: " + Constants.jobReqData!!.records.get(
                        global.jobRequisitonPosition
                    ).headcount.toString()
                )
            } else {
                binding.tvHeadCount.setText("Headcount:Not Provided")
            }

            if (com.example.envagemobileapplication.Utils.Global.jobreqlist.get(global.jobRequisitonPosition).startDate != null) {
                val formattedDate =
                    formatDate(
                        com.example.envagemobileapplication.Utils.Global.jobreqlist.get(
                            global.jobRequisitonPosition
                        ).startDate
                    )
                binding.tvStartDate.setText("Start Date: " + formattedDate)
            } else {
                binding.tvStartDate.setText("Start Date: Not Provided")
            }

            if (com.example.envagemobileapplication.Utils.Global.jobreqlist.get(global.jobRequisitonPosition).endDate != null) {
                val formattedDate =
                    formatDate(
                        com.example.envagemobileapplication.Utils.Global.jobreqlist.get(
                            global.jobRequisitonPosition
                        ).endDate.toString()
                    )
                binding.tvStartDate2.setText("End Date: " + formattedDate)
            } else {
                binding.tvStartDate2.setText("End Date: Not Provided")
            }

            if (com.example.envagemobileapplication.Utils.Global.jobreqlist.get(global.jobRequisitonPosition).workingDaysNo != null) {
                if (com.example.envagemobileapplication.Utils.Global.jobreqlist.get(global.jobRequisitonPosition).workingDaysNo.equals(
                        1
                    )
                ) {
                    binding.tvTotalDays.setText(
                        com.example.envagemobileapplication.Utils.Global.jobreqlist.get(
                            global.jobRequisitonPosition
                        ).workingDaysNo.toString() + " Day"
                    )
                } else {
                    binding.tvTotalDays.setText(
                        com.example.envagemobileapplication.Utils.Global.jobreqlist.get(
                            global.jobRequisitonPosition
                        ).workingDaysNo.toString() + " Days"
                    )

                }
            } else {
                binding.tvTotalDays.setText("Days:Not Provided")
            }

            if (com.example.envagemobileapplication.Utils.Global.jobreqlist.get(global.jobRequisitonPosition).address1 != null && Constants.jobReqData!!.records.get(
                    global.jobRequisitonPosition
                ).address2 != null
            ) {
                binding.tvHomeAdress.setText(
                    com.example.envagemobileapplication.Utils.Global.jobreqlist.get(global.jobRequisitonPosition).address1 + ", " + Constants.jobReqData!!.records.get(
                        global.jobRequisitonPosition
                    ).address2
                )
            } else if (com.example.envagemobileapplication.Utils.Global.jobreqlist.get(global.jobRequisitonPosition).address1 != null && Constants.jobReqData!!.records.get(
                    global.jobRequisitonPosition
                ).address2 == null
            ) {
                binding.tvHomeAdress.setText(
                    com.example.envagemobileapplication.Utils.Global.jobreqlist.get(
                        global.jobRequisitonPosition
                    ).address1
                )
            } else if (com.example.envagemobileapplication.Utils.Global.jobreqlist.get(global.jobRequisitonPosition).address1 == null && Constants.jobReqData!!.records.get(
                    global.jobRequisitonPosition
                ).address2 != null
            ) {
                binding.tvHomeAdress.setText(
                    com.example.envagemobileapplication.Utils.Global.jobreqlist.get(
                        global.jobRequisitonPosition
                    ).address2
                )
            } else {
                binding.tvHomeAdress.setText("Not Provided")
            }

            if (com.example.envagemobileapplication.Utils.Global.jobreqlist.get(global.jobRequisitonPosition).zipcode != null) {
                binding.postalcode.setText(
                    com.example.envagemobileapplication.Utils.Global.jobreqlist.get(
                        global.jobRequisitonPosition
                    ).zipcode.toString()
                )
            } else {
                binding.postalcode.setText("Not Provided")
            }
            if ((com.example.envagemobileapplication.Utils.Global.jobreqlist.get(global.jobRequisitonPosition).city != null)) {
                binding.country.setText(
                    com.example.envagemobileapplication.Utils.Global.jobreqlist.get(
                        global.jobRequisitonPosition
                    ).city.toString()
                )
            } else {
                binding.country.setText("Not Provided")
            }

            if ((com.example.envagemobileapplication.Utils.Global.jobreqlist.get(global.jobRequisitonPosition).state != null)) {
                binding.city.setText(
                    com.example.envagemobileapplication.Utils.Global.jobreqlist.get(
                        global.jobRequisitonPosition
                    ).state.toString()
                )
            } else {
                binding.city.setText("Not Provided")
            }

            if ((com.example.envagemobileapplication.Utils.Global.jobreqlist.get(global.jobRequisitonPosition).location != null)) {

                binding.location.setText(
                    com.example.envagemobileapplication.Utils.Global.jobreqlist.get(
                        global.jobRequisitonPosition
                    ).location.toString()
                )
            } else {
                binding.location.setText("Not Provided")
            }

            if ((com.example.envagemobileapplication.Utils.Global.jobreqlist.get(global.jobRequisitonPosition).estimatedHours != null)) {

                binding.tvestimatedHours.setText(
                    "Estimated Hours:" + Constants.jobReqData!!.records.get(
                        global.jobRequisitonPosition
                    ).estimatedHours.toString()
                )
            } else {
                binding.tvestimatedHours.setText("Not Provided")
            }



            if (com.example.envagemobileapplication.Utils.Global.jobreqlist.get(global.jobRequisitonPosition).jobDescription != null) {
                binding.webView.visibility = View.VISIBLE

                binding.tvNodescription.visibility = View.INVISIBLE
                var filename =
                    com.example.envagemobileapplication.Utils.Global.jobreqlist.get(global.jobRequisitonPosition).jobDescription
                var baseurlnew =
                    "https://staginggateway.enwage.com/api/v1/AzureStorage/download?filename=" + filename

                try {
                    loadJobDescriptionContent(baseurlnew)
                } catch (e: Exception) {
                }

            } else {
                binding.webView.visibility = View.INVISIBLE

                binding.tvNodescription.visibility = View.VISIBLE
            }


        } catch (e: Exception) {
        }

        binding.postalcode.setOnLongClickListener {

            if (com.example.envagemobileapplication.Utils.Global.jobreqlist.get(global.jobRequisitonPosition).zipcode != null) {
                val toast = Toast.makeText(
                    this,
                    com.example.envagemobileapplication.Utils.Global.jobreqlist.get(global.jobRequisitonPosition).zipcode,
                    Toast.LENGTH_LONG
                )

                toast.show()
            }


            true
        }
        binding.city.setOnLongClickListener {

            if ((com.example.envagemobileapplication.Utils.Global.jobreqlist.get(global.jobRequisitonPosition).state != null)) {
                val toast = Toast.makeText(
                    this,
                    com.example.envagemobileapplication.Utils.Global.jobreqlist.get(global.jobRequisitonPosition).state,
                    Toast.LENGTH_LONG
                )

                toast.show()
            }

            true
        }
        binding.country.setOnLongClickListener {

            if ((com.example.envagemobileapplication.Utils.Global.jobreqlist.get(global.jobRequisitonPosition).city != null)) {
                val toast = Toast.makeText(
                    this,
                    com.example.envagemobileapplication.Utils.Global.jobreqlist.get(global.jobRequisitonPosition).city.toString(),
                    Toast.LENGTH_LONG
                )

                toast.show()
            }
            true
        }
        binding.tvJobName.setOnLongClickListener {
            if (com.example.envagemobileapplication.Utils.Global.jobreqlist.get(global.jobRequisitonPosition).positionName != null) {
                val toast = Toast.makeText(
                    this,
                    com.example.envagemobileapplication.Utils.Global.jobreqlist.get(global.jobRequisitonPosition).positionName,
                    Toast.LENGTH_LONG
                )

                toast.show()
            }

            true
        }
        binding.tvIndustry.setOnLongClickListener {

            if (industryglobal != null) {
                val toast = Toast.makeText(
                    this,
                    industryglobal.toString(),
                    Toast.LENGTH_LONG
                )

                toast.show()
            }

            true
        }
        binding.tvHomeAdress.setOnLongClickListener {
            if (com.example.envagemobileapplication.Utils.Global.jobreqlist.get(global.jobRequisitonPosition).address1 != null && Constants.jobReqData!!.records.get(
                    global.jobRequisitonPosition
                ).address2 != null
            ) {
                val toast = Toast.makeText(
                    this,
                    com.example.envagemobileapplication.Utils.Global.jobreqlist.get(global.jobRequisitonPosition).address1 + ", " + Constants.jobReqData!!.records.get(
                        global.jobRequisitonPosition
                    ).address2,
                    Toast.LENGTH_LONG
                )
                toast.show()

            } else if (com.example.envagemobileapplication.Utils.Global.jobreqlist.get(global.jobRequisitonPosition).address1 != null && Constants.jobReqData!!.records.get(
                    global.jobRequisitonPosition
                ).address2 == null
            ) {
                val toast = Toast.makeText(
                    this,
                    com.example.envagemobileapplication.Utils.Global.jobreqlist.get(global.jobRequisitonPosition).address1,
                    Toast.LENGTH_LONG
                )

                toast.show()

            } else if (com.example.envagemobileapplication.Utils.Global.jobreqlist.get(global.jobRequisitonPosition).address1 == null && Constants.jobReqData!!.records.get(
                    global.jobRequisitonPosition
                ).address2 != null
            ) {
                val toast = Toast.makeText(
                    this,
                    com.example.envagemobileapplication.Utils.Global.jobreqlist.get(global.jobRequisitonPosition).address2,
                    Toast.LENGTH_LONG
                )
                toast.show()

            }

            true
        }
        binding.location.setOnLongClickListener {
            if (com.example.envagemobileapplication.Utils.Global.jobreqlist.get(global.jobRequisitonPosition).location != null) {
                val toast = Toast.makeText(
                    this,
                    com.example.envagemobileapplication.Utils.Global.jobreqlist.get(global.jobRequisitonPosition).location.toString(),
                    Toast.LENGTH_LONG
                )

                toast.show()
            }

            true
        }
        binding.payGroup.setOnLongClickListener {


            if (com.example.envagemobileapplication.Utils.Global.jobreqlist.get(global.jobRequisitonPosition).payGroupName != null) {
                val toast = Toast.makeText(
                    this,
                    "Pay Group: " + com.example.envagemobileapplication.Utils.Global.jobreqlist.get(
                        global.jobRequisitonPosition
                    ).payGroupName.toString(),
                    Toast.LENGTH_LONG
                )

                toast.show()
            }

            true
        }
    }

    private fun setupHorizontalScrollView(weekdaysList: List<String>) {
        for (itemText in weekdaysList) {
            val itemView = layoutInflater.inflate(R.layout.weekdays_item, null)
            val textView = itemView.findViewById<TextView>(R.id.tvWeekday)

            val stringWithoutBrackets = itemText.replace(Regex("[\\[\\]]"), "")
            textView.text = stringWithoutBrackets
            binding.linearlist.addView(itemView)
        }
    }

    private fun setupBillingDetailAdapter(billdata: ArrayList<JobsSummaryFragment.KeyValueData>) {

        try {
            binding.rvBillingInformation.setHasFixedSize(true)

            val adapter = BillingDetailJobSummaryAdapter(this, billdata)

            val layoutManager = object : LinearLayoutManager(
                this,
                LinearLayoutManager.VERTICAL,
                false
            ) {
                override fun canScrollVertically(): Boolean {
                    return false
                }
            }
            binding.rvBillingInformation.layoutManager = layoutManager
            binding.rvBillingInformation.adapter = adapter

        } catch (e: Exception) {
        }

    }

    private fun parseBackgroundColor(tvJobstatus: TextView, hexColorCode: String) {
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

    fun loadJobDescriptionContent(url: String) {
        Thread {
            try {
                val urlConnection = URL(url).openConnection() as HttpURLConnection
                urlConnection.requestMethod = "GET"

                val inputStream = urlConnection.inputStream
                val reader = BufferedReader(InputStreamReader(inputStream))
                val response = StringBuilder()
                var line: String?

                while (reader.readLine().also { line = it } != null) {
                    response.append(line).append("\n") // Append a newline to separate lines
                }

                val htmlContent = response.toString()

                this.runOnUiThread {
                    binding.webView.settings.javaScriptEnabled = true
                    binding.webView.webViewClient = object : WebViewClient() {
                        override fun shouldOverrideUrlLoading(
                            view: WebView?,
                            request: WebResourceRequest?
                        ): Boolean {
                            // Intercept URL clicks, making them unclickable
                            return true
                        }
                    }

                    binding.webView.loadDataWithBaseURL(
                        null,
                        htmlContent,
                        "text/html",
                        "UTF-8",
                        null
                    )

                    val spannedText: Spanned =
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            Html.fromHtml(htmlContent, Html.FROM_HTML_MODE_LEGACY)
                        } else {
                            Html.fromHtml(htmlContent)
                        }

                    val description: String = spannedText.toString()

                    if (description.isNullOrEmpty()) {
                        binding.tvNodescription.visibility = View.VISIBLE
                        binding.webView.visibility = View.INVISIBLE
                    } else {
                        binding.tvDescription.text = spannedText
                    }
                }

                inputStream.close()
                urlConnection.disconnect()

            } catch (e: Exception) {
                e.printStackTrace()
                // Handle errors here
            }
        }.start()
    }

    fun formatDate(inputDate: String): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
        val outputFormat = SimpleDateFormat("MM-dd-yyyy")

        try {
            val date = inputFormat.parse(inputDate)
            return outputFormat.format(date)
        } catch (e: Exception) {
            e.printStackTrace()
            return "" // Handle parsing error here
        }
    }

}