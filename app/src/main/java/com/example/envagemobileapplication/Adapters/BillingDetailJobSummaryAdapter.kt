package com.example.envagemobileapplication.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.envagemobileapplication.Activities.Jobs.JobSummary.JobSummaryFragments.JobsSummaryFragment
import com.example.envagemobileapplication.R


class BillingDetailJobSummaryAdapter(
    var context: Context,
    billingdatalist: ArrayList<JobsSummaryFragment.KeyValueData>

) :
    RecyclerView.Adapter<BillingDetailJobSummaryAdapter.MyViewHolder>() {
    var doubletimetypenone = false
    var isPaidNotBilled: Boolean = false
    var isNone: Boolean = false
    var isDoubleTimeType: Boolean = false
    var doubleTimeTypeValue: String? = null
    var ispaidnotbilled = false
    var dataList: ArrayList<JobsSummaryFragment.KeyValueData> = billingdatalist


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.billing_detail_item, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

// Inside your function or adapter
        val data = dataList[position]

        holder.tvKey.text = data.key

        if (data.value != "null") {
            if (data.value.equals("Paid Not Billed")) {
                isPaidNotBilled = true
            } else if (data.value.equals("None")) {
                isNone = true
            }

            if (isPaidNotBilled) {
                if (data.key.equals("Overtime Markup Percentage")) {
                    holder.tvValue.text = "-"
                    isPaidNotBilled = false
                } else if (data.key.equals("Double-time Type")) {
                    // Check for "Double-Time Type" and its corresponding value is "Paid Not Billed"
                    isDoubleTimeType = true
                    doubleTimeTypeValue = data.value.toString()
                    holder.tvValue.text = data.value.toString()

                } else if (isDoubleTimeType && data.key.equals("Double-Time Markup Percentage")) {
                    // Check for "Double-time markup percentage" when "Double-Time Type" was previously found
                    holder.tvValue.text = "-"
                    isDoubleTimeType = false
                } else {
                    holder.tvValue.text = data.value.toString()
                }
            }
            else if (isNone) {
                if (data.key.equals("Overtime Markup Percentage")) {
                    holder.tvValue.text = "-"
                } else if (data.key.equals("Overtime Multiplier")) {
                    holder.tvValue.text = "-"
                } else if (data.key.equals("Overtime Pay Rate")) {
                    holder.tvValue.text = "-"
                } else if (data.key.equals("Overtime Bill Rate")) {
                    holder.tvValue.text = "-"
                } else {
                    holder.tvValue.text = data.value.toString()
                }
            } else {
                holder.tvValue.text = data.value.toString()
            }

            if (data.key.equals("Double-time Type") && data.value.equals("None")){

                doubletimetypenone = true

            }
            if (doubletimetypenone){
                if (data.key.equals("Double-Time Multiplier")) {
                    holder.tvValue.text = "-"
                }
            }


        }


        else {
            holder.tvValue.text = "-"
        }





    }


    override fun getItemCount(): Int {
        return dataList.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvKey: TextView = itemView.findViewById(R.id.tvKey)
        var tvValue: TextView = itemView.findViewById(R.id.tvValue)


    }
}