package com.example.envagemobileapplication.Adapters

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.getBulkMsgFilterdResp.Record
import com.example.envagemobileapplication.R
import de.hdodenhof.circleimageview.CircleImageView

class FilteredCandidatesAdapter(
    var context: Context,
    onlineList: ArrayList<Record>
) :
    RecyclerView.Adapter<FilteredCandidatesAdapter.MyViewHolder>() {

    var dataList: ArrayList<Record> = onlineList
    var global = com.example.envagemobileapplication.Utils.Global

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.filter_candidate_item, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        // ... existing code

        try {
            if (dataList.get(position).firstName != null && dataList.get(position).lastName != null) {
                holder.tvCandidateName.setText(
                    dataList.get(position).firstName + " " + dataList.get(
                        position
                    ).lastName
                )
            } else if (dataList.get(position).firstName != null && dataList.get(position).lastName == null) {
                holder.tvCandidateName.setText(dataList.get(position).firstName.toString())
            } else if (dataList.get(position).firstName == null && dataList.get(position).lastName != null) {
                holder.tvCandidateName.setText(dataList.get(position).lastName.toString())
            }
        } catch (e: Exception) {
        }

        if (dataList.get(position).status != null) {

            holder.tvStatus.setText(dataList.get(position).status.toString())
            val hexColorCode = (dataList.get(position).colorCode.toString())
            holder.tvStatus.setTextColor(Color.parseColor(hexColorCode))
            parseBackgroundColor(holder.tvStatus, hexColorCode)
        }

        if (dataList.get(position).phoneNumber != null) {
            holder.tvContact.setText(dataList.get(position).phoneNumber.toString())
        }
        try {
            // Set the state of the checkbox based on the selected property
            holder.checkBox2.isChecked = dataList[position].selected

            // Add an OnCheckedChangeListener to update the selected property
        }
        catch (e: Exception) {
            // Handle any exceptions here
        }

        holder.checkBox2.setOnCheckedChangeListener { _, isChecked ->
            dataList[position].selected = isChecked

            val phonenumberlist: ArrayList<String> = ArrayList()

            if (isChecked) {
                // CheckBox is checked, add phone number to the list
                if (dataList[position].phoneNumber != null) {
                    val phoneNumber = dataList[position].phoneNumber.toString()
                    phonenumberlist.add(phoneNumber)
                    Log.i("numberlistsize", phonenumberlist.size.toString())
                }
            }
            else {
                // CheckBox is unchecked, remove phone number from the list
                if (dataList[position].phoneNumber != null) {
                    val phoneNumber = dataList[position].phoneNumber.toString()
                    phonenumberlist.remove(phoneNumber)
                    Log.i("numberlistsize", phonenumberlist.size.toString())
                }
            }

            // Update the global phone number list
            global.phonenumberlist = phonenumberlist
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var iv_profile_pic: CircleImageView = itemView.findViewById(R.id.iv_profile_pic)
        var tvCandidateName: TextView = itemView.findViewById(R.id.tvCandidateName)
        var tvStatus: TextView = itemView.findViewById(R.id.tvStatus)
        var tvContact: TextView = itemView.findViewById(R.id.tvContact)
        var checkBox2: CheckBox = itemView.findViewById(R.id.checkBox2)
        var itemlayout: ConstraintLayout = itemView.findViewById(R.id.itemlayout)
    }

    private fun parseBackgroundColor(tvJobstatus: TextView, hexColorCode: String) {
        val currentTextColor = Color.parseColor(hexColorCode)
        val adjustedAlpha = (Color.alpha(currentTextColor) * 0.1).toInt()
        val adjustedColor = Color.argb(
            adjustedAlpha,
            Color.red(currentTextColor),
            Color.green(currentTextColor),
            Color.blue(currentTextColor)
        )
        val cornerRadius = 20f
        val gradientDrawable = GradientDrawable()
        gradientDrawable.cornerRadius = cornerRadius
        gradientDrawable.setColor(adjustedColor)
        tvJobstatus.background = gradientDrawable
    }

    // Method to handle Select All
    fun selectAll(isChecked: Boolean) {
        val phonenumberlist: ArrayList<String> = ArrayList()

        for (position in 0 until itemCount) {
            val phoneNumber = dataList[position].phoneNumber.toString()
            dataList[position].selected = isChecked

            if (isChecked) {
                phonenumberlist.add(phoneNumber)
            } else {
                phonenumberlist.remove(phoneNumber)
            }
        }

        notifyDataSetChanged()
        global.phonenumberlist = phonenumberlist
    }
}