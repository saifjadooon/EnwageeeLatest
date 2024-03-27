package com.example.envagemobileapplication.Activities.Assesments

import BaseActivity
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.GetSpecificAssesmentRsp.GetspecificAssesmentResp
import com.example.envagemobileapplication.Oauth.TokenManager
import com.example.envagemobileapplication.R
import com.example.envagemobileapplication.Utils.Loader
import com.ezshifa.aihealthcare.network.ApiUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AssesmentDetailActivity : BaseActivity() {
    private var currentSectionIndex = 0

    var global = com.example.envagemobileapplication.Utils.Global
    lateinit var tokenmanager: TokenManager
    lateinit var token: String
    lateinit var loader: Loader
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_assesment_detail)

        tokenmanager = TokenManager(this)
        token = tokenmanager.getAccessToken().toString()
        loader = Loader(this)
        getAssignment()

    }

    private fun getAssignment() {
        try {
            loader.show()

            var assesmentid = global.clientAssesmentFormid!!
            ApiUtils.getAPIService(this).GetAssesmentForms(
                token, assesmentid
            )
                .enqueue(object : Callback<GetspecificAssesmentResp> {
                    override fun onResponse(
                        call: Call<GetspecificAssesmentResp>,
                        response: Response<GetspecificAssesmentResp>
                    ) {
                        loader.hide()
                        if (response.body() != null) {

                            // Create a ScrollView to wrap the layout
                            val scrollView = ScrollView(this@AssesmentDetailActivity)
                            scrollView.layoutParams = ViewGroup.LayoutParams(
                                ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.MATCH_PARENT
                            )

                            // Create a LinearLayout as the main layout
                            val mainLayout = LinearLayout(this@AssesmentDetailActivity)
                            mainLayout.layoutParams = ViewGroup.LayoutParams(
                                ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.MATCH_PARENT
                            )
                            mainLayout.orientation = LinearLayout.VERTICAL

                            // Add Heading Text
                            val headingTextView = TextView(this@AssesmentDetailActivity)
                            try {
                                headingTextView.text = response.body()!!.data.name
                            } catch (e: Exception) {
                            }
                            headingTextView.setTypeface(null, Typeface.BOLD)
                            headingTextView.setTextSize(
                                TypedValue.COMPLEX_UNIT_SP,
                                24f
                            ) // Set text size to 24sp
                            headingTextView.setTextColor(
                                ContextCompat.getColor(
                                    this@AssesmentDetailActivity,
                                    android.R.color.black
                                )
                            )
                            headingTextView.gravity = Gravity.CENTER

// Add top margin to the headingTextView
                            val layoutParams = LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                            )
                            val topmargin =
                                resources.getDimensionPixelSize(R.dimen.top_margin) // Change R.dimen.top_margin to your desired dimension resource
                            val bottommargin =
                                resources.getDimensionPixelSize(R.dimen.bottom_margin) // Change R.dimen.top_margin to your desired dimension resource
                            layoutParams.setMargins(0, topmargin, 0, bottommargin)
                            headingTextView.layoutParams = layoutParams

// Add heading TextView to the main layout
                            mainLayout.addView(headingTextView)

                            // Add Test Information in a horizontal LinearLayout
                            val testInfoLayout = LinearLayout(this@AssesmentDetailActivity)
                            testInfoLayout.layoutParams = ViewGroup.LayoutParams(
                                ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.MATCH_PARENT
                            )
                            testInfoLayout.orientation = LinearLayout.HORIZONTAL


                            // Test Time TextView
                            val testTimeTextView = TextView(this@AssesmentDetailActivity)
                            try {
                                testTimeTextView.text = response.body()!!.data.timer.toString()
                            } catch (e: Exception) {

                            }
                            // Replace with actual test time
                            testTimeTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f)
                            testTimeTextView.setCompoundDrawablesWithIntrinsicBounds(
                                R.drawable.ic_clock, // Replace with your clock drawable
                                0,
                                0,
                                0
                            )
                            testTimeTextView.compoundDrawablePadding = 8

                            val testviewSPaceempty = TextView(this@AssesmentDetailActivity)
                            testviewSPaceempty.text =
                                "  " // Replace with actual test time
                            testviewSPaceempty.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f)

                            testviewSPaceempty.compoundDrawablePadding = 8

                            val testStatusTextView = TextView(this@AssesmentDetailActivity)

                            if (global.assesmentStatus == "Done") {

                                val jobtypehexcolor = "#0D824B"
                                testStatusTextView.setTextColor(Color.parseColor(jobtypehexcolor))
                                parseBackgroundColor(testStatusTextView, jobtypehexcolor)
                                testStatusTextView.text = global.assesmentStatus
                            } else {
                                val jobtypehexcolor = "#AF6606"
                                testStatusTextView.setTextColor(Color.parseColor(jobtypehexcolor))
                                parseBackgroundColor(testStatusTextView, jobtypehexcolor)
                                testStatusTextView.text = global.assesmentStatus
                            }
                            val cardPaddingInDp = 8
                            val cardPaddingInPx = TypedValue.applyDimension(
                                TypedValue.COMPLEX_UNIT_DIP,
                                cardPaddingInDp.toFloat(),
                                resources.displayMetrics
                            ).toInt()
                            testStatusTextView.setPadding(
                                cardPaddingInPx,
                                0,
                                cardPaddingInPx,
                                0
                            )
                            testStatusTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f)


                            testInfoLayout.addView(testTimeTextView)
                            testInfoLayout.addView(testviewSPaceempty)
                            testInfoLayout.addView(testStatusTextView)

                            testInfoLayout.gravity = Gravity.CENTER

                            mainLayout.addView(testInfoLayout)

                            val passingcriterialayout = LinearLayout(this@AssesmentDetailActivity)
                            passingcriterialayout.layoutParams = ViewGroup.LayoutParams(
                                ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.MATCH_PARENT
                            )
                            passingcriterialayout.orientation = LinearLayout.HORIZONTAL

                            val passingcriteriatextview = TextView(this@AssesmentDetailActivity)
                            try {
                                passingcriteriatextview.text =
                                    "Passing Criteria >= " + response.body()!!.data.passingCriteria + "%"
                            } catch (e: Exception) {

                            }
                            // Replace with actual test time
                            passingcriteriatextview.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f)

                            passingcriteriatextview.setTextColor(resources.getColor(R.color.green_color))


                            val testviewSPace = TextView(this@AssesmentDetailActivity)
                            testviewSPace.text =
                                " | " // Replace with actual test time
                            testviewSPace.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f)

                            testviewSPace.compoundDrawablePadding = 8

                            val lineexpiryTextview = TextView(this@AssesmentDetailActivity)
                            try {
                                lineexpiryTextview.text =
                                    "Link Expiry Time:" + response.body()!!.data.urlExpiryTime
                            } catch (e: Exception) {
                            }
                            // Replace with actual test time
                            lineexpiryTextview.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f)
                            lineexpiryTextview.setTextColor(resources.getColor(R.color.red))

                            passingcriterialayout.addView(passingcriteriatextview)
                            passingcriterialayout.addView(testviewSPace)
                            passingcriterialayout.addView(lineexpiryTextview)
                            passingcriterialayout.gravity = Gravity.CENTER



                            mainLayout.addView(passingcriterialayout)

                            var sectionNumber = 1

                            val dividerView = View(this@AssesmentDetailActivity)
                            val dividerHeight =
                                resources.getDimensionPixelSize(R.dimen.divider_height) // Change R.dimen.divider_height to your desired dimension resource
                            dividerView.setBackgroundColor(
                                ContextCompat.getColor(
                                    this@AssesmentDetailActivity,
                                    android.R.color.darker_gray
                                )
                            )

// Add top margin to the dividerView
                            val dividerLayoutParams = LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                dividerHeight
                            )
                            val topDividerMargin =
                                resources.getDimensionPixelSize(R.dimen.top_margin) // Change R.dimen.top_divider_margin to your desired dimension resource
                            dividerLayoutParams.setMargins(0, topDividerMargin, 0, 0)
                            dividerView.layoutParams = dividerLayoutParams

// Add the dividerView to the main layout
                            mainLayout.addView(dividerView)

                            // Iterate through sections
                            for (section in response.body()!!.data.clientAssessmentFormSections) {
                                // Create a TextView for the section name outside the CardView
                                val sectionNameTextView = TextView(this@AssesmentDetailActivity)
                                sectionNameTextView.text = section.name
                                sectionNameTextView.setTypeface(null, Typeface.BOLD)
                                sectionNameTextView.setTextColor(
                                    ContextCompat.getColor(
                                        this@AssesmentDetailActivity,
                                        android.R.color.black
                                    )
                                )
                                sectionNameTextView.setTextSize(
                                    TypedValue.COMPLEX_UNIT_SP,
                                    22f
                                ) // Set text size to 20sp
                                sectionNameTextView.gravity = Gravity.CENTER
                                // Add section name TextView to the main layout
                                val layoutParams = LinearLayout.LayoutParams(
                                    LinearLayout.LayoutParams.MATCH_PARENT,
                                    LinearLayout.LayoutParams.WRAP_CONTENT
                                )
                                val topmargin =
                                    resources.getDimensionPixelSize(R.dimen.top_margin) // Change R.dimen.top_margin to your desired dimension resource
                                val bottommargin =
                                    resources.getDimensionPixelSize(R.dimen.bottom_margin) // Change R.dimen.top_margin to your desired dimension resource
                                layoutParams.setMargins(0, topmargin, 0, bottommargin)
                                sectionNameTextView.layoutParams = layoutParams
                                mainLayout.addView(sectionNameTextView)

                                // Create a CardView for each section
                                val cardView = CardView(this@AssesmentDetailActivity)
                                val cardParams = LinearLayout.LayoutParams(
                                    ViewGroup.LayoutParams.MATCH_PARENT,
                                    ViewGroup.LayoutParams.WRAP_CONTENT
                                )
                                val cardMarginInDp = 20
                                val cardMarginInPx = TypedValue.applyDimension(
                                    TypedValue.COMPLEX_UNIT_DIP,
                                    cardMarginInDp.toFloat(),
                                    resources.displayMetrics
                                ).toInt()
                                cardParams.setMargins(
                                    cardMarginInPx,
                                    cardMarginInPx,
                                    cardMarginInPx,
                                    cardMarginInPx
                                ) // Set left, top, right, and bottom margins
                                cardView.layoutParams = cardParams
                                cardView.radius = 30f // Set corner radius

                                cardView.cardElevation = 20f
                                // Set padding for the CardView content
                                val cardPaddingInDp = 12
                                val cardPaddingInPx = TypedValue.applyDimension(
                                    TypedValue.COMPLEX_UNIT_DIP,
                                    cardPaddingInDp.toFloat(),
                                    resources.displayMetrics
                                ).toInt()
                                cardView.setPadding(
                                    cardPaddingInPx,
                                    cardPaddingInPx,
                                    cardPaddingInPx,
                                    cardPaddingInPx
                                )

                                val sectionLayout = LinearLayout(this@AssesmentDetailActivity)

                                // Set margins for sectionLayout
                                val sectionLayoutParams = LinearLayout.LayoutParams(
                                    ViewGroup.LayoutParams.MATCH_PARENT,
                                    ViewGroup.LayoutParams.MATCH_PARENT
                                )
                                val sectionMarginInDp = 12
                                val sectionMarginInPx = TypedValue.applyDimension(
                                    TypedValue.COMPLEX_UNIT_DIP,
                                    sectionMarginInDp.toFloat(),
                                    resources.displayMetrics
                                ).toInt()
                                sectionLayoutParams.setMargins(
                                    sectionMarginInPx,
                                    sectionMarginInPx,
                                    sectionMarginInPx,
                                    sectionMarginInPx
                                )
                                sectionLayout.layoutParams = sectionLayoutParams

                                sectionLayout.orientation = LinearLayout.VERTICAL

                                // Create a TextView for the weightage
                                val weightageTextView = TextView(this@AssesmentDetailActivity)
                                weightageTextView.text =
                                    "Weightage: ${section.weightage}%" // Assuming 'weightage' is a property of your Section model
                                weightageTextView.setTextSize(
                                    TypedValue.COMPLEX_UNIT_SP,
                                    13f
                                ) // Set text size to 13sp
                                weightageTextView.setTextColor(
                                    ContextCompat.getColor(
                                        this@AssesmentDetailActivity,
                                        android.R.color.black
                                    )
                                )

                                // Set margins for weightageTextView
                                val weightageLayoutParams = LinearLayout.LayoutParams(
                                    ViewGroup.LayoutParams.WRAP_CONTENT,
                                    ViewGroup.LayoutParams.WRAP_CONTENT
                                )
                                val weightageMarginInDp = 12
                                val weightageMarginInPx = TypedValue.applyDimension(
                                    TypedValue.COMPLEX_UNIT_DIP,
                                    weightageMarginInDp.toFloat(),
                                    resources.displayMetrics
                                ).toInt()
                                weightageLayoutParams.setMargins(
                                    0,
                                    weightageMarginInPx,
                                    0,
                                    weightageMarginInPx
                                )
                                weightageTextView.layoutParams = weightageLayoutParams

                                // Add weightage TextView to the section layout
                                sectionLayout.addView(weightageTextView)

                                var questionNumber = 1

                                // Iterate through questions
                                /*  for (question in section.clientAssessmentFormQuestions) {
                                      val questionContentTextView =
                                          TextView(this@AssesmentDetailActivity)
                                      questionContentTextView.text = question.content
                                      questionContentTextView.setTextColor(
                                          ContextCompat.getColor(
                                              this@AssesmentDetailActivity,
                                              android.R.color.black
                                          )
                                      )
                                      questionContentTextView.setTypeface(null, Typeface.BOLD)
                                      questionContentTextView.setTextSize(
                                          TypedValue.COMPLEX_UNIT_SP,
                                          16f
                                      )

                                      // Set top margin for questionContentTextView
                                      val layoutParams = LinearLayout.LayoutParams(
                                          ViewGroup.LayoutParams.MATCH_PARENT,
                                          ViewGroup.LayoutParams.WRAP_CONTENT
                                      )
                                      val topMarginValueInDp = 12
                                      val topMarginInPx = TypedValue.applyDimension(
                                          TypedValue.COMPLEX_UNIT_DIP,
                                          topMarginValueInDp.toFloat(),
                                          resources.displayMetrics
                                      ).toInt()
                                      layoutParams.setMargins(0, topMarginInPx, 0, 0)

                                      // Apply layout parameters to questionContentTextView
                                      questionContentTextView.layoutParams = layoutParams

                                      // Add question content TextView to the section layout
                                      sectionLayout.addView(questionContentTextView)

                                      // Create a RadioGroup for options
                                      val radioGroup = RadioGroup(this@AssesmentDetailActivity)
                                      radioGroup.orientation = RadioGroup.VERTICAL

                                      // Iterate through options
                                      for (option in question.clientAssessmentFormOptions) {
                                          // Create a RadioButton for the option
                                          val radioButton = RadioButton(this@AssesmentDetailActivity)
                                          radioButton.text = option.content
                                          radioButton.setTextColor(
                                              ContextCompat.getColor(
                                                  this@AssesmentDetailActivity,
                                                  android.R.color.black
                                              )
                                          )
                                          radioButton.setBackgroundResource(R.drawable.blackbg)

                                          val topMarginValueInDp =
                                              15 // Set your desired top margin value
                                          val topMarginInPx = TypedValue.applyDimension(
                                              TypedValue.COMPLEX_UNIT_DIP,
                                              topMarginValueInDp.toFloat(),
                                              resources.displayMetrics
                                          ).toInt()
                                          val radioLayoutParams = RadioGroup.LayoutParams(
                                              ViewGroup.LayoutParams.WRAP_CONTENT,
                                              ViewGroup.LayoutParams.WRAP_CONTENT
                                          )
                                          radioLayoutParams.setMargins(
                                              0,
                                              topMarginInPx,
                                              0,
                                              0
                                          ) // Set only top margin

                                          // Set the layout parameters for the RadioButton
                                          radioButton.layoutParams = radioLayoutParams
                                          // Add RadioButton to the RadioGroup
                                          radioGroup.addView(radioButton)
                                      }

                                      // Add RadioGroup to the section layout
                                      sectionLayout.addView(radioGroup)

                                      // Increment the question number
                                      questionNumber++
                                  }*/

                                for (question in section.clientAssessmentFormQuestions) {
                                    val questionContentTextView =
                                        TextView(this@AssesmentDetailActivity)
                                    questionContentTextView.text = question.content
                                    questionContentTextView.setTextColor(
                                        ContextCompat.getColor(
                                            this@AssesmentDetailActivity,
                                            android.R.color.black
                                        )
                                    )
                                    questionContentTextView.setTypeface(null, Typeface.BOLD)
                                    questionContentTextView.setTextSize(
                                        TypedValue.COMPLEX_UNIT_SP,
                                        16f
                                    )

                                    // Set top margin for questionContentTextView
                                    val layoutParams = LinearLayout.LayoutParams(
                                        ViewGroup.LayoutParams.MATCH_PARENT,
                                        ViewGroup.LayoutParams.WRAP_CONTENT
                                    )
                                    val topMarginValueInDp = 12
                                    val topMarginInPx = TypedValue.applyDimension(
                                        TypedValue.COMPLEX_UNIT_DIP,
                                        topMarginValueInDp.toFloat(),
                                        resources.displayMetrics
                                    ).toInt()
                                    layoutParams.setMargins(0, topMarginInPx, 0, 0)

                                    // Apply layout parameters to questionContentTextView
                                    questionContentTextView.layoutParams = layoutParams

                                    // Add question content TextView to the section layout
                                    sectionLayout.addView(questionContentTextView)

                                    // Create a RadioGroup for options
                                    val radioGroup = RadioGroup(this@AssesmentDetailActivity)
                                    radioGroup.orientation = RadioGroup.VERTICAL

                                    // Iterate through options
                                    for (option in question.clientAssessmentFormOptions) {
                                        // Create a RadioButton for the option
                                        val radioButton = RadioButton(this@AssesmentDetailActivity)
                                        radioButton.text = option.content
                                        radioButton.setTextColor(
                                            ContextCompat.getColor(
                                                this@AssesmentDetailActivity,
                                                android.R.color.black
                                            )
                                        )
                                        radioButton.setBackgroundResource(R.drawable.blackbg)

                                        val topMarginValueInDp =
                                            15 // Set your desired top margin value
                                        val topMarginInPx = TypedValue.applyDimension(
                                            TypedValue.COMPLEX_UNIT_DIP,
                                            topMarginValueInDp.toFloat(),
                                            resources.displayMetrics
                                        ).toInt()
                                        val radioLayoutParams = RadioGroup.LayoutParams(
                                            ViewGroup.LayoutParams.WRAP_CONTENT,
                                            ViewGroup.LayoutParams.WRAP_CONTENT
                                        )
                                        radioLayoutParams.setMargins(
                                            0,
                                            topMarginInPx,
                                            0,
                                            0
                                        ) // Set only top margin

                                        // Set the layout parameters for the RadioButton
                                        radioButton.layoutParams = radioLayoutParams
                                        // Add RadioButton to the RadioGroup
                                        radioGroup.addView(radioButton)
                                    }

                                    // Add RadioGroup to the section layout
                                    sectionLayout.addView(radioGroup)

                                    // Add expected answer TextView with top margin
                                    val expectedAnswerTextView =
                                        TextView(this@AssesmentDetailActivity)
                                    expectedAnswerTextView.text =
                                        "Expected Answer:Option ${question.expectedAnswer}"


                                    // Set top margin for expectedAnswerTextView
                                    val expectedAnswerLayoutParams = LinearLayout.LayoutParams(
                                        ViewGroup.LayoutParams.WRAP_CONTENT,
                                        ViewGroup.LayoutParams.WRAP_CONTENT
                                    )
                                    val expectedAnswerTopMarginValueInDp =
                                        8 // Set your desired top margin value
                                    val expectedAnswerTopMarginInPx = TypedValue.applyDimension(
                                        TypedValue.COMPLEX_UNIT_DIP,
                                        expectedAnswerTopMarginValueInDp.toFloat(),
                                        resources.displayMetrics
                                    ).toInt()
                                    expectedAnswerLayoutParams.setMargins(
                                        0,
                                        expectedAnswerTopMarginInPx,
                                        0,
                                        0
                                    )

                                    // Apply layout parameters to expectedAnswerTextView
                                    expectedAnswerTextView.layoutParams = expectedAnswerLayoutParams

                                    sectionLayout.addView(expectedAnswerTextView)

                                    // Increment the question number
                                    questionNumber++
                                }

                                // Add the LinearLayout for each section to the CardView
                                cardView.addView(sectionLayout)

                                // Add the CardView to the main layout
                                mainLayout.addView(cardView)

                                // Increment the section number
                                sectionNumber++
                            }

                            // Add the main layout to the ScrollView
                            scrollView.addView(mainLayout)

                            // Set the ScrollView as the content view
                            setContentView(scrollView)
                        }
                    }


                    override fun onFailure(call: Call<GetspecificAssesmentResp>, t: Throwable) {
                        loader.hide()
                        Log.i("exc", t.toString())

                    }
                })
        } catch (ex: java.lang.Exception) {
            loader.hide()
            Log.i("exc", ex.toString())
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

}