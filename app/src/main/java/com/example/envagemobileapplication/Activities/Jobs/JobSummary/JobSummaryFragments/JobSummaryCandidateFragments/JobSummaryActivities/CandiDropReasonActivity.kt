package com.example.envagemobileapplication.Activities.Jobs.JobSummary.JobSummaryFragments.JobSummaryCandidateFragments.JobSummaryActivities

import BaseActivity
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.text.Spanned
import android.util.Log
import android.view.View
import android.webkit.WebViewClient
import com.example.envagemobileapplication.R
import com.example.envagemobileapplication.Utils.Constants
import com.example.envagemobileapplication.databinding.ActivityCandiDropReasonBinding
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class CandiDropReasonActivity : BaseActivity() {

    lateinit var binding: ActivityCandiDropReasonBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_candi_drop_reason)

        binding = ActivityCandiDropReasonBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.btnCrossReason.setOnClickListener {
            finish()
        }

        var filename =
            Constants.descriptionText.toString()
        var baseurlnew =
            "https://staginggateway.enwage.com/api/v1/AzureStorage/download?filename=" + filename
        try {
            loadJobDescriptionContent(baseurlnew)
        } catch (e: Exception) {
            Log.d("hsdhsdhsgd",e.toString())
        }


//        loadJobDescriptionContent(Constants.BASE_URL_STAGING+"/"+Constants.descriptionText.toString())

//        if(!Constants.descriptionText.isNullOrEmpty()){
//            binding.etReason.text = Constants.descriptionText
//        }else{
//            binding.etReason.text = "Not Provided"
//        }

    }



    fun loadJobDescriptionContent(url: String) {

        //val htmlTextView = HtmlTextView(requireContext())
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
                    val spannedText: Spanned =
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            binding.webView.settings.javaScriptEnabled =
                                true // Enable JavaScript if needed
                            binding.webView.webViewClient = WebViewClient()
                            binding.webView.loadDataWithBaseURL(
                                null,
                                htmlContent,
                                "text/html",
                                "UTF-8",
                                null
                            )
                            Html.fromHtml(htmlContent, Html.FROM_HTML_MODE_LEGACY)
                        } else {
                            binding.webView.settings.javaScriptEnabled =
                            true // Enable JavaScript if needed
                            binding.webView.webViewClient = WebViewClient()

                            binding.webView.loadDataWithBaseURL(
                                null,
                                htmlContent,
                                "text/html",
                                "UTF-8",
                                null
                            )
                            Html.fromHtml(htmlContent)
                        }
                    val description: String = spannedText.toString()

                    if (description.isNullOrEmpty()) {

//                        binding.tvNodescription.visibility = View.VISIBLE
                        binding.webView.visibility = View.INVISIBLE
                    } else {

//                        binding.tvDescription.text = spannedText
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

}