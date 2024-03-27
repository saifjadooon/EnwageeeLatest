package com.example.envagemobileapplication.Adapters

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.example.envagemobileapplication.Activities.DashBoard.DashboardFragments.BottomSheet.BottomSheetDownloadOfferLetter
import com.example.envagemobileapplication.Activities.DashBoard.DashboardFragments.BottomSheet.BottomSheetOfferSent
import com.example.envagemobileapplication.Oauth.TokenManager
import com.example.envagemobileapplication.R
import com.example.envagemobileapplication.Utils.Constants
import com.example.envagemobileapplication.Utils.Loader
import com.example.envagemobileapplication.ViewModels.JobSummaryCandidateViewModel
import java.text.SimpleDateFormat


class OfferLetterAdapter(
    var context: Context,

    datalist: ArrayList<com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.GetAllOfferLetterResp.Record>,
    viewModel: JobSummaryCandidateViewModel,
    childFragmentManager: FragmentManager
) :
    RecyclerView.Adapter<OfferLetterAdapter.MyViewHolder>() {
    var cfm = childFragmentManager
    lateinit var bottomSheetFragment: BottomSheetOfferSent
    lateinit var bottomSheetdownloadOfferLetterFragment: BottomSheetDownloadOfferLetter
    var global = com.example.envagemobileapplication.Utils.Global
    var loader = Loader(context)
    lateinit var token: String
    lateinit var tokenManager: TokenManager
    var viewmodel = viewModel
    var onlineApplicantsDataList = datalist


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): OfferLetterAdapter.MyViewHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.offer_sent_item, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        bottomSheetFragment = BottomSheetOfferSent()
        bottomSheetdownloadOfferLetterFragment = BottomSheetDownloadOfferLetter()
        tokenManager = TokenManager(context)
        token = tokenManager.getAccessToken().toString()

        holder.tvCandidateName.setOnLongClickListener {

            val toast = Toast.makeText(
                context,
                onlineApplicantsDataList.get(position).candidateName,
                Toast.LENGTH_LONG
            )

            toast.show()
            true
        }

        if (!onlineApplicantsDataList.get(position).candidateName.toString().isNullOrEmpty()) {
            holder.tvCandidateName.setText(
                onlineApplicantsDataList.get(position).candidateName
            )
        } else {
            holder.tvCandidateName.text = "Not Provided"
        }

        if (!onlineApplicantsDataList.get(position).userName.toString().isNullOrEmpty()) {
            holder.tv_createdby.setText("Created By: " + onlineApplicantsDataList.get(position).userName)
        } else {
            holder.tv_createdby.text = "Not Provided"
        }

        holder.tv_createdOn.setOnLongClickListener {

            val toast = Toast.makeText(
                context,
                onlineApplicantsDataList.get(position).createdDate,
                Toast.LENGTH_LONG
            )
            toast.show()
            true
        }


        if (!onlineApplicantsDataList.get(position).createdDate.toString().isNullOrEmpty()) {

            val inputDate = onlineApplicantsDataList.get(position).createdDate.toString()
            val formattedDate = formatDate(inputDate)
            holder.tv_createdOn.setText("Created On: " + formattedDate)
        } else {
            holder.tv_createdOn.text = "Not Provided"
        }

        holder.kebabMenu.setOnClickListener {

            Constants.offerLetterUrl = onlineApplicantsDataList.get(position).bodyPath
            if (bottomSheetdownloadOfferLetterFragment.isAdded()) {
                return@setOnClickListener
            } else {

                bottomSheetdownloadOfferLetterFragment.show(cfm, bottomSheetdownloadOfferLetterFragment.tag)
            }
        }

        holder.tvStatus.setOnClickListener {

            Constants.offerLetterId = onlineApplicantsDataList.get(position).candidateOfferLetterId
            if (bottomSheetFragment.isAdded()) {
                return@setOnClickListener
            } else {

                bottomSheetFragment.show(cfm, bottomSheetFragment.tag)
            }
        }


        if (!onlineApplicantsDataList.get(position).status.toString().isNullOrEmpty()) {
            var status = onlineApplicantsDataList.get(position).status.toString()

            if (status.equals("Pending")) {
                val jobtypehexcolor = "#48505E"
                holder.tvStatus.setTextColor(Color.parseColor(jobtypehexcolor))
                parseBackgroundColor(holder.tvStatus, jobtypehexcolor)
                holder.tvStatus.text = onlineApplicantsDataList.get(position).status

            } else if (status.equals("Accepted")) {
                val jobtypehexcolor = "#0D824B"
                holder.tvStatus.setTextColor(Color.parseColor(jobtypehexcolor))
                parseBackgroundColor(holder.tvStatus, jobtypehexcolor)
                holder.tvStatus.text = onlineApplicantsDataList.get(position).status
            } else if (status.equals("Rejected")) {
                val jobtypehexcolor = "#AA3028"
                holder.tvStatus.setTextColor(Color.parseColor(jobtypehexcolor))
                parseBackgroundColor(holder.tvStatus, jobtypehexcolor)
                holder.tvStatus.text = onlineApplicantsDataList.get(position).status

            }

        } else {
            holder.tv_createdOn.text = "Not Provided"
        }

    }


    override fun getItemCount(): Int {
        return onlineApplicantsDataList.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvCandidateName: TextView = itemView.findViewById(R.id.tvCandidateName)
        var tvStatus: TextView = itemView.findViewById(R.id.tvStatus)
        var tv_createdby: TextView = itemView.findViewById(R.id.tv_createdby)
        var tv_createdOn: TextView = itemView.findViewById(R.id.tv_createdOn)
        var kebabMenu: ImageView = itemView.findViewById(R.id.KebabMenu)
        var itemLayout: ConstraintLayout = itemView.findViewById(R.id.itemLayout)
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
