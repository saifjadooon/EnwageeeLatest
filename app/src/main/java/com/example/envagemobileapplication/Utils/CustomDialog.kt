package com.example.envagemobileapplication.Utils

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.DropCandidateResponse.DropCandidateResponse
import com.example.envagemobileapplication.Oauth.TokenManager
import com.example.envagemobileapplication.R
import com.example.envagemobileapplication.Utils.Global.Companion.descriptiontext
import com.example.envagemobileapplication.ViewModels.CandidatesProfileSumViewModel
import com.example.envagemobileapplication.databinding.CustomDialogLayoutBinding
import com.ezshifa.aihealthcare.network.ApiUtils
import jp.wasabeef.richeditor.RichEditor
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CustomDialog(context: Context, viewModel: CandidatesProfileSumViewModel) : Dialog(context) {

    private lateinit var richEditor: RichEditor
    private var mEditor: RichEditor? = null
    private var selectedReason: String? = null
    private var descriptiontextforbackpress: String? = ""
    lateinit var description: MultipartBody.Part
    lateinit var loader: Loader

    private var cViewModel = viewModel
    lateinit var token: String
    lateinit var tokenManager: TokenManager

    init {
        setCancelable(true)
        // You can customize other properties of the dialog here
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = CustomDialogLayoutBinding.inflate(LayoutInflater.from(context))
        setContentView(binding.root)
        richEditor = binding.etClientDescription

        loader = Loader(context)


        tokenManager = TokenManager(context)
        token = tokenManager.getAccessToken().toString()

        setUpRtf()


        val layoutParams = WindowManager.LayoutParams()
        layoutParams.copyFrom(window?.attributes)
        layoutParams.width = (context.resources.displayMetrics.widthPixels * 0.9).toInt()
        layoutParams.height = (context.resources.displayMetrics.heightPixels * 0.5).toInt()
        window?.attributes = layoutParams


        binding.btnCancel.setOnClickListener {
            Toast.makeText(context, "clicked", Toast.LENGTH_SHORT).show()
        }

        binding.btnSave.setOnClickListener {
            if (descriptiontextforbackpress?.length ?: 0 > 0) {
                dropStatusApi()
            } else {
                Toast.makeText(context, " Description can not be empty", Toast.LENGTH_SHORT).show()
            }
        }
        binding.etClientDescription!!.setOnTextChangeListener { text ->

            descriptiontextforbackpress = text
            if (text.contains("<")) {
                descriptiontext = text
            } else {
                descriptiontext = "<p>" + text + "</p>"
            }
        }


        // Set up the spinner with a static list of names
        val names = arrayOf(
            "Above Budget",
            "Accepted another offer",
            "Cultural unfit",
            "Did not attend an interview",
            "Not Available",
            "Not Qualified",
            "Overqualified",
            "Rejected the offer",
            "Technical test failed"
        )
        val adapter = ArrayAdapter(context, android.R.layout.simple_spinner_dropdown_item, names)
        binding.spinner.adapter = adapter


        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                // Update the global variable with the selected item
                selectedReason = names[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Handle case where nothing is selected if needed
            }
        }

    }

    private fun dropStatusApi() {
        loader.show()
        if (!descriptiontext.isNullOrBlank()) {
            val htmlContent = descriptiontext
            val mediaType = "text/html".toMediaTypeOrNull()
            val descriptionBody = RequestBody.create(mediaType, htmlContent)
            val descriptionPart =
                MultipartBody.Part.createFormData(
                    "description",
                    "description.html",
                    descriptionBody
                )
            description = descriptionPart
        } else {

            val htmlContent = "<p></p>"
            val mediaType = "text/html".toMediaTypeOrNull()
            val descriptionBody = RequestBody.create(mediaType, htmlContent)
            val descriptionPart =
                MultipartBody.Part.createFormData(
                    "description",
                    "description.html",
                    descriptionBody
                )
            description = descriptionPart
        }

        val candidateIdPart =
            MultipartBody.Part.createFormData("candidateId", Constants.candidateIDNumber.toString())
        val candidateNotesIdPart = MultipartBody.Part.createFormData("candidateNotesId", "")
        val jobIdPart = MultipartBody.Part.createFormData("jobId", Constants.jobId.toString())
        val reasonPart = MultipartBody.Part.createFormData("reason", selectedReason ?: "")


//        cViewModel.changeCandidateStatusToDrop(
//            context,
//            token,
//            description,
//            candidateIdPart,
//            candidateNotesIdPart,
//            jobIdPart,
//            reasonPart
//        )


        try {
            ApiUtils.getAPIService(context)
                .changeCandidateStatusToDrop(
                    token,
                    description,
                    candidateIdPart,
                    candidateNotesIdPart,
                    jobIdPart,
                    reasonPart

                )
                .enqueue(object : Callback<DropCandidateResponse> {
                    override fun onResponse(
                        call: Call<DropCandidateResponse>,
                        response: Response<DropCandidateResponse>
                    ) {
                        loader.hide()
                        if (response.body() != null) {

                            if(response?.body()?.success == true ){
                                cViewModel.postDataToViewModel(response.body()!!)
                                dismiss()
                            }
                        }
                    }

                    override fun onFailure(call: Call<DropCandidateResponse>, t: Throwable) {
                        Log.d("hhhkhk1", t.toString())
                    }
                })
        } catch (ex: java.lang.Exception) {
            Log.d("assignjobsres", ex.toString())
        }

    }

    private fun setUpRtf() {
        mEditor = findViewById<View>(R.id.et_client_description) as RichEditor
        mEditor!!.setEditorHeight(110)
        mEditor!!.setEditorFontSize(14)
        mEditor!!.setEditorFontColor(Color.BLACK)
        //mEditor.setEditorBackgroundColor(Color.BLUE);
        //mEditor.setBackgroundColor(Color.BLUE);
        //mEditor.setBackgroundResource(R.drawable.bg);
        mEditor!!.setPadding(0, 10, 10, 10)
        //mEditor.setBackground("https://raw.githubusercontent.com/wasabeef/art/master/chip.jpg");
        mEditor!!.setPlaceholder("Description")
        //mEditor.setInputEnabled(false);
        //  mPreview = findViewById<View>(R.id.preview) as TextView
        //   mEditor!!.setOnTextChangeListener { text -> mPreview!!.text = text }
        findViewById<View>(R.id.action_undo).setOnClickListener { mEditor!!.undo() }
        findViewById<View>(R.id.action_redo).setOnClickListener { mEditor!!.redo() }
        findViewById<View>(R.id.action_bold).setOnClickListener { mEditor!!.setBold() }
        findViewById<View>(R.id.action_italic).setOnClickListener { mEditor!!.setItalic() }

        findViewById<View>(R.id.action_strikethrough).setOnClickListener { mEditor!!.setStrikeThrough() }
        findViewById<View>(R.id.action_underline).setOnClickListener { mEditor!!.setUnderline() }

        findViewById<View>(R.id.action_txt_color).setOnClickListener(object : View.OnClickListener {
            private var isChanged = false
            override fun onClick(v: View) {
                mEditor!!.setTextColor(if (isChanged) Color.BLACK else Color.RED)
                isChanged = !isChanged
            }
        })
        findViewById<View>(R.id.action_bg_color).setOnClickListener(object : View.OnClickListener {
            private var isChanged = false
            override fun onClick(v: View) {
                mEditor!!.setTextBackgroundColor(if (isChanged) Color.TRANSPARENT else Color.YELLOW)
                isChanged = !isChanged
            }
        })

        findViewById<View>(R.id.action_align_left).setOnClickListener { mEditor!!.setAlignLeft() }
        findViewById<View>(R.id.action_align_center).setOnClickListener { mEditor!!.setAlignCenter() }
        findViewById<View>(R.id.action_align_right).setOnClickListener { mEditor!!.setAlignRight() }

        findViewById<View>(R.id.action_insert_bullets).setOnClickListener { mEditor!!.setBullets() }
        findViewById<View>(R.id.action_insert_numbers).setOnClickListener { mEditor!!.setNumbers() }

        findViewById<View>(R.id.action_insert_link).setOnClickListener {
            mEditor!!.insertLink(
                "https://github.com/wasabeef",
                "wasabeef"
            )
        }
    }


}
