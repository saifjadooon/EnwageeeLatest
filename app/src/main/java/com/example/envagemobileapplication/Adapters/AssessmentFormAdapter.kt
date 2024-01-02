package com.example.envagemobileapplication.Adapters

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.example.envagemobileapplication.Models.RequestModels.SendAssessmentRequestModel
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.GetAssesmentForms.Datum
import com.example.envagemobileapplication.Oauth.TokenManager
import com.example.envagemobileapplication.R
import com.example.envagemobileapplication.Utils.Constants
import com.example.envagemobileapplication.Utils.Loader
import com.example.envagemobileapplication.ViewModels.JobSummaryViewModel


class AssessmentFormAdapter(
    var context: Context,
    datalist: ArrayList<Datum>,
    childFragmentManager: FragmentManager,
    viewModel: JobSummaryViewModel
) :
    RecyclerView.Adapter<AssessmentFormAdapter.MyViewHolder>() {

    var assessmentList = datalist
    var loader = Loader(context)
    lateinit var token: String
    lateinit var tokenManager: TokenManager
    var viewmodel = viewModel


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AssessmentFormAdapter.MyViewHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.assessmentform_item, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        tokenManager = TokenManager(context)
        token = tokenManager.getAccessToken().toString()

        try {

            holder.assessmentName.setOnClickListener {

                val payload = Constants.AssessmentCandidateId?.let { it1 ->
                    Constants.AssessmentJobId?.let { it2 ->
                        Constants.AssessmentClientId?.let { it3 ->
                            SendAssessmentRequestModel(
                                clientId = it3,
                                jobId = it2,
                                clientAssessmentFormId = assessmentList.get(position).clientAssessmentFormId,
                                candidateId = it1,
                                status = "Pending",
                                baseUrl = Constants.BASE_URL
                            )
                        }
                    }
                }


//                sendAssessment(payload)
                showConfirmationDialog(payload)

            }

            holder.assessmentName.text = assessmentList.get(position).name.toString()

        }catch (e:Exception){
            Toast.makeText(context, "Exception is "+e.toString(), Toast.LENGTH_SHORT).show()
        }

    }

    private fun sendAssessment(payload: SendAssessmentRequestModel?) {
        if (payload != null) {
            loader.show()
            viewmodel.sendAssessmentForm(context,token,payload)
            loader.hide()
        }
    }

    private fun showConfirmationDialog(payload: SendAssessmentRequestModel?) {
        if (payload != null) {
            val builder = AlertDialog.Builder(context)
            builder.setTitle("Confirmation")
            builder.setMessage("Do you want to send the assessment?")

            builder.setPositiveButton("Yes") { _, _ ->
                // User clicked Yes
                sendAssessment(payload)
            }

            builder.setNegativeButton("No") { dialog, _ ->
                // User clicked No
                dialog.dismiss() // Dismiss the dialog
            }

            val dialog = builder.create()
            dialog.show()
        } else {
            Toast.makeText(context, "Payload is null", Toast.LENGTH_SHORT).show()
        }
    }


    override fun getItemCount(): Int {
        return assessmentList.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var assessmentName: TextView = itemView.findViewById(R.id.tv_assessment_name)
    }

}
