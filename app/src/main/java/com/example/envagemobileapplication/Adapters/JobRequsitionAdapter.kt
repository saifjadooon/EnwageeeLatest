package com.example.envagemobileapplication.Adapters

import android.content.Context
import android.content.Intent
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
import com.example.envagemobileapplication.Activities.Client.AddClient.ClientSummary.JobRequisition.ClientJobReqJobSummaryActivity
import com.example.envagemobileapplication.Activities.DashBoard.DashboardFragments.BottomSheet.BottomSheetEditJob
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.GetJobRequests.Record
import com.example.envagemobileapplication.R
import com.example.envagemobileapplication.Utils.Constants
import java.text.SimpleDateFormat


class JobRequsitionAdapter(
    var context: Context,
    onlineList: ArrayList<Record>,
    childFragmentManager: FragmentManager
) :
    RecyclerView.Adapter<JobRequsitionAdapter.MyViewHolder>() {

    var global = com.example.envagemobileapplication.Utils.Global
    var cfm = childFragmentManager
    var dataList: ArrayList<Record> =
        onlineList

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.job_requisition_item, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        initdata(holder, position)
        clicklistenrs(holder, position)
    }

    private fun clicklistenrs(holder: JobRequsitionAdapter.MyViewHolder, position: Int) {
        holder.itemLayout.setOnClickListener {

            Constants.jobRequestid = dataList.get(position).jobRequestId
            com.example.envagemobileapplication.Utils.Global.jobRequisitonPosition = position
            val intent = Intent(context, ClientJobReqJobSummaryActivity::class.java)
            context.startActivity(intent)
        }

        holder.kebabmenu.setOnClickListener{
            Constants.jobRequestid = dataList.get(position).jobRequestId
            global.isfromEditJobRequisition = true
            val bottomSheetFragment = BottomSheetEditJob()
            bottomSheetFragment.show(cfm, bottomSheetFragment.tag)
        }
    }

    private fun initdata(holder: JobRequsitionAdapter.MyViewHolder, position: Int) {


        if (dataList.get(position).colorCode != null) {
            val hexColorCode = dataList.get(position).colorCode
            holder.tvapproved.setTextColor(Color.parseColor(hexColorCode))
            parseBackgroundColor(holder.tvapproved, hexColorCode)
        }

        if (dataList.get(position).jobNature != null) {
            val hexColorCode = "#001329"
            holder.tvonsite.setTextColor(Color.parseColor(hexColorCode))
            parseBackgroundColor(holder.tvonsite, hexColorCode)
        }

        if (dataList.get(position).jobType != null) {
            val hexColorCode = "#AF6606"
            holder.tvJobstatus.setTextColor(Color.parseColor(hexColorCode))
            parseBackgroundColor(holder.tvJobstatus, hexColorCode)
        }

        if (dataList.get(position).positionName != null) {
            holder.tv_Name.setText(dataList.get(position).positionName)
        } else {
            holder.tv_Name.setText("Not provided")
        }
        if (dataList.get(position).jobType != null) {
            holder.tvJobstatus.setText(dataList.get(position).jobType)
        } else {
            holder.tvJobstatus.setText("Not provided")
        }

        if (dataList.get(position).jobNature != null) {
            holder.tvonsite.setText(dataList.get(position).jobNature)
        } else {
            holder.tvonsite.setText("Not provided")
        }

        if (dataList.get(position).status != null) {
            holder.tvapproved.setText(dataList.get(position).status)
        } else {
            holder.tvapproved.setText("Not provided")
        }
        if (dataList.get(position).ownerName != null) {
            holder.tvCreator.setText("Creator:" + dataList.get(position).ownerName)
        } else {
            holder.tvCreator.setText("Not provided")
        }

        if (dataList.get(position).minPayRate != null) {
            holder.tvMinPayRate.setText("Min.Pay Rate:" + dataList.get(position).minPayRate + " USD")
        } else {
            holder.tvMinPayRate.setText("Not provided")
        }

        if (dataList.get(position).createdDate != null) {

            val inputDate = dataList.get(position).createdDate.toString()
            val formattedDate = formatDate(inputDate)
            holder.tvRequestedat.setText("Requested at:" + formattedDate)
        } else {
            holder.tvRequestedat.setText("Not provided")
        }

        if (dataList.get(position).maxPayRate != null) {
            holder.tvMaxPayRate.setText("Max.Pay Rate:" + dataList.get(position).maxPayRate + " USD")
        } else {
            holder.tvMaxPayRate.setText("Not provided")
        }
        if (dataList.get(position).targetPayRate != null) {
            holder.tvTargetPayRate.setText("Target Pay Rate:" + dataList.get(position).targetPayRate + " USD")
        } else {
            holder.tvTargetPayRate.setText("Not provided")
        }

        if (dataList.get(position).frequency != null) {
            holder.tvFrequency.setText("Frequency:" + dataList.get(position).frequency + " USD")
        } else {
            holder.tvFrequency.setText("Not provided")
        }

        holder.tv_Name.setOnLongClickListener {
            if (dataList.get(position).positionName != null) {
                val toast = Toast.makeText(
                    context,
                    dataList.get(position).positionName,
                    Toast.LENGTH_LONG
                )
                toast.show()
            }

            true
        }

        holder.tvJobstatus.setOnLongClickListener {
            if (dataList.get(position).jobType != null) {
                val toast = Toast.makeText(
                    context,
                    dataList.get(position).jobType,
                    Toast.LENGTH_LONG
                )
                toast.show()
            }

            true
        }

        holder.tvonsite.setOnLongClickListener {
            if (dataList.get(position).jobNature != null) {
                val toast = Toast.makeText(
                    context,
                    dataList.get(position).jobNature,
                    Toast.LENGTH_LONG
                )
                toast.show()
            }

            true
        }

        holder.tvapproved.setOnLongClickListener {
            if (dataList.get(position).status != null) {
                val toast = Toast.makeText(
                    context,
                    dataList.get(position).status,
                    Toast.LENGTH_LONG
                )
                toast.show()
            }

            true
        }
        holder.tvCreator.setOnLongClickListener {
            if (dataList.get(position).ownerName != null) {
                val toast = Toast.makeText(
                    context,
                    "Creator:" + dataList.get(position).ownerName,
                    Toast.LENGTH_LONG
                )
                toast.show()
            }

            true
        }
        holder.tvRequestedat.setOnLongClickListener {
            if (dataList.get(position).createdDate != null) {

                val inputDate = dataList.get(position).createdDate.toString()
                val formattedDate = formatDate(inputDate)
                val toast = Toast.makeText(
                    context,
                    "Requested at:" + formattedDate,
                    Toast.LENGTH_LONG
                )
                toast.show()
            }

            true
        }

        holder.tvMinPayRate.setOnLongClickListener {
            if (dataList.get(position).minPayRate != null) {


                val toast = Toast.makeText(
                    context,
                    "Min.Pay Rate:" + dataList.get(position).minPayRate + " USD",
                    Toast.LENGTH_LONG
                )
                toast.show()
            }

            true
        }

        holder.tvMaxPayRate.setOnLongClickListener {
            if (dataList.get(position).maxPayRate != null) {


                val toast = Toast.makeText(
                    context,
                    "Max.Pay Rate:" + dataList.get(position).maxPayRate + " USD",
                    Toast.LENGTH_LONG
                )
                toast.show()
            }

            true
        }

        holder.tvTargetPayRate.setOnLongClickListener {
            if (dataList.get(position).targetPayRate != null) {


                val toast = Toast.makeText(
                    context,
                    "Target Pay Rate:" + dataList.get(position).targetPayRate + " USD",
                    Toast.LENGTH_LONG
                )
                toast.show()
            }

            true
        }

        holder.tvFrequency.setOnLongClickListener {
            if (dataList.get(position).frequency != null) {

                val toast = Toast.makeText(
                    context,
                    "Frequency:" + dataList.get(position).frequency,
                    Toast.LENGTH_LONG
                )
                toast.show()
            }

            true
        }

    }


    override fun getItemCount(): Int {
        return dataList.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tv_Name: TextView = itemView.findViewById(R.id.tv_Name)
        var tvJobstatus: TextView = itemView.findViewById(R.id.tvJobstatus)
        var tvonsite: TextView = itemView.findViewById(R.id.tvonsite)
        var tvapproved: TextView = itemView.findViewById(R.id.tvapproved)
        var tvCreator: TextView = itemView.findViewById(R.id.tvCreator)
        var tvRequestedat: TextView = itemView.findViewById(R.id.tvRequestedat)
        var tvMinPayRate: TextView = itemView.findViewById(R.id.tvMinPayRate)
        var tvMaxPayRate: TextView = itemView.findViewById(R.id.tvMaxPayRate)
        var tvTargetPayRate: TextView = itemView.findViewById(R.id.tvTargetPayRate)
        var tvFrequency: TextView = itemView.findViewById(R.id.tvFrequency)
        var kebabmenu: ImageView = itemView.findViewById(R.id.kebabmenu)
        var itemLayout: ConstraintLayout = itemView.findViewById(R.id.itemLayout)


    }

    fun formatDate(inputDate: String): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS")
        val outputFormat = SimpleDateFormat("MM-dd-yyyy")

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