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
import com.example.envagemobileapplication.Activities.DashBoard.DashboardFragments.BottomSheet.BottomsheetViewAssesment
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.GetAssesmentResp.Record
import com.example.envagemobileapplication.R
import com.example.envagemobileapplication.Utils.CircleTransformation
import com.squareup.picasso.Picasso


class CandidateAssesmentsAdapter(
    var context: Context,
    onlineList: ArrayList<Record>,
    childFragmentManager: FragmentManager
) :
    RecyclerView.Adapter<CandidateAssesmentsAdapter.MyViewHolder>() {
    var cfm = childFragmentManager
    var global = com.example.envagemobileapplication.Utils.Global
    var dataList: ArrayList<com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.GetAssesmentResp.Record> =
        onlineList


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.candidate_assesment_item, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        try {
            holder.tv_Name.text = dataList.get(position).client.name.toString()
        } catch (e: Exception) {

        }


        holder.tv_Name.setOnLongClickListener {
            val toast = Toast.makeText(
                context,
                dataList.get(position).client.name.toString(),
                Toast.LENGTH_LONG
            )

            toast.show()
            true
        }

        holder.tvJobName.setOnLongClickListener {
            val toast = Toast.makeText(
                context,
                dataList.get(position).job.positionName.toString(),
                Toast.LENGTH_LONG
            )

            toast.show()
            true
        }

        holder.tvtestname.setOnLongClickListener {
            val toast = Toast.makeText(
                context,
                dataList.get(position).clientAssessmentForm.name.toString(),
                Toast.LENGTH_LONG
            )

            toast.show()
            true
        }
        if (!dataList.get(position).job.positionName.isNullOrEmpty()) {

            holder.tvJobName.text = dataList.get(position).job.positionName
        } else {
            holder.tvJobName.setText("Not provided")
        }
        if (!dataList.get(position).clientAssessmentForm.name.isNullOrEmpty()) {
            holder.tvtestname.setText(dataList.get(position).clientAssessmentForm.name)
        } else {
            holder.tvtestname.setText("Not provided")
        }

        if (dataList.get(position).achievedMarks != null || dataList.get(position).achievedMarks.toString() != "null") {
            val jobtypehexcolor = "#0D824B"
            holder.tvachievedmarks.setTextColor(Color.parseColor(jobtypehexcolor))
            parseBackgroundColor(holder.tvachievedmarks, jobtypehexcolor)
            holder.tvachievedmarks.setText("Achieved marks:" + dataList.get(position).achievedMarks.toString())
        } else {
            holder.tvachievedmarks.visibility = View.GONE
        }

        if (dataList.get(position).passed != null) {
            if (!dataList.get(position).passed.equals("true")) {
                val jobtypehexcolor = "#0D824B"
                holder.tvtestresultstatus.setTextColor(Color.parseColor(jobtypehexcolor))
                parseBackgroundColor(holder.tvtestresultstatus, jobtypehexcolor)
                holder.tvtestresultstatus.setText("Passed")
            } else {
                holder.tvtestresultstatus.visibility = View.GONE
            }
        } else {
            holder.tvtestresultstatus.visibility = View.GONE
        }
        if (!dataList.get(position).client.profilePicture.isNullOrEmpty()) {
            Picasso.get().load(dataList.get(position).client.profilePicture)
                .placeholder(R.drawable.upload_pic_bg)
                .transform(CircleTransformation()).into(holder.iv_profile_pic)

        }
        if (!dataList.get(position).status.isNullOrEmpty()) {
            holder.tvtesttime.setText("Test Time:" + dataList.get(position).clientAssessmentForm.urlExpiryTime)
        } else {
            holder.tvtesttime.setText("Not Provided")
        }

        if (!dataList.get(position).status.isNullOrEmpty()) {

            if (dataList.get(position).status == "Done") {

                val jobtypehexcolor = "#0D824B"
                holder.iv_deal_status.setTextColor(Color.parseColor(jobtypehexcolor))
                parseBackgroundColor(holder.iv_deal_status, jobtypehexcolor)
                holder.iv_deal_status.text = dataList.get(position).status
            } else {
                val jobtypehexcolor = "#AF6606"
                holder.iv_deal_status.setTextColor(Color.parseColor(jobtypehexcolor))
                parseBackgroundColor(holder.iv_deal_status, jobtypehexcolor)
                holder.iv_deal_status.text = dataList.get(position).status
            }
        }

        holder.itemLayout.setOnClickListener {
            global.assesmentStatus = dataList.get(position).status
            global.clientAssesmentFormid = dataList.get(position).clientAssessmentFormId

        }

        holder.menu.setOnClickListener {
            global.assesmentStatus = dataList.get(position).status
            global.clientAssesmentFormid = dataList.get(position).clientAssessmentFormId
            val bottomSheetFragment = BottomsheetViewAssesment()
            bottomSheetFragment.show(cfm, bottomSheetFragment.tag)
        }

    }


    override fun getItemCount(): Int {
        return dataList.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tv_Name: TextView = itemView.findViewById(R.id.tv_Name)
        var tvJobName: TextView = itemView.findViewById(R.id.tvJobName)
        var tvtestname: TextView = itemView.findViewById(R.id.tvtestname)
        var tvtesttime: TextView = itemView.findViewById(R.id.tvtesttime)
        var tvachievedmarks: TextView = itemView.findViewById(R.id.tvachievedmarks)
        var tvtestresultstatus: TextView = itemView.findViewById(R.id.tvtestresultstatus)
        var iv_profile_pic: ImageView = itemView.findViewById(R.id.iv_profile_pic)
        var iv_deal_status: TextView = itemView.findViewById(R.id.iv_deal_status)
        var itemLayout: ConstraintLayout = itemView.findViewById(R.id.itemLayout)
        var menu: ImageView = itemView.findViewById(R.id.menu)
    }

    // Function to adjust color brightness
    private fun adjustColorBrightness(color: Int, factor: Float): Int {
        val alpha = Color.alpha(color)
        val red = Math.min(Math.round(Color.red(color) * factor), 255)
        val green = Math.min(Math.round(Color.green(color) * factor), 255)
        val blue = Math.min(Math.round(Color.blue(color) * factor), 255)
        return Color.argb(alpha, red, green, blue)
    }

    private fun formatPhoneNumber(phoneNumber: String): String {
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