package com.example.envagemobileapplication.Adapters

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.envagemobileapplication.Activities.Candidates.CandidateJobStatusChange
import com.example.envagemobileapplication.R
import com.example.envagemobileapplication.Utils.Constants


class EmployeeSummaryPlacementsAdapter(
    var context: Context,
    onlineList: ArrayList<com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.GetPlacementResponse.Datum>,
    childFragmentManager: FragmentManager
) :
    RecyclerView.Adapter<EmployeeSummaryPlacementsAdapter.MyViewHolder>() {

    var cfm = childFragmentManager
    var dataList: ArrayList<com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.GetPlacementResponse.Datum> = onlineList


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_employeesummary_placements, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {


        holder.tvPlacementName.setOnLongClickListener{
            if(!dataList?.get(position)?.positionName.isNullOrBlank()){
                Toast.makeText(context, dataList.get(position).positionName, Toast.LENGTH_SHORT).show()
            }
            true
        }


        holder.job_item.setOnClickListener{
//            Constants.jobId = dataList.get(position).jobId
//            Constants.CandidateJobSelectedStatus = dataList.get(position).status
//            Constants.CandidateJobSelectedStatusId = dataList.get(position).statusId
//            val intent = Intent(context, CandidateJobStatusChange::class.java)
//            context.startActivity(intent)
        }

        try{

            if(dataList.get(position).positionName !=null){
                holder.tvPlacementName.text = dataList.get(position).positionName
            }else{
                holder.tvPlacementName.text = "Not Provided"
            }

            if(dataList.get(position).clientName != null &&  dataList.get(position).jobType != null){
                holder.tvPlacementType.text = dataList.get(position).clientName + " - " + dataList.get(position).jobType
            } else if(dataList.get(position).clientName != null &&  dataList.get(position).jobType == null){
                holder.tvPlacementType.text = dataList.get(position).clientName
            }else if(dataList.get(position).clientName == null &&  dataList.get(position).jobType != null){
                holder.tvPlacementType.text = dataList.get(position).jobType
            }else{
                holder.tvPlacementType.text = "Not Provided"
            }


            if(dataList.get(position).employeeStatusName !=null){
                holder.tvPlacementStatus.text = dataList.get(position).employeeStatusName.toString()
            }else{
                holder.tvPlacementStatus.text = "Not Provided"
            }




            if(dataList.get(position).clientProfilePicture !=""){
                Glide.with(context)
                    .load(dataList?.get(position)?.clientProfilePicture)
                    .circleCrop()
                    .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                    .into(holder.employeeJobProfile)
            }

        }
        catch (e:Exception){
            Log.d("CandidateJobsExce",e.toString())
        }


//        holder.tvJobType.text = dataList.get(position)

    }


    override fun getItemCount(): Int {
        return dataList.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvPlacementName: TextView = itemView.findViewById(R.id.tvPlacementName)
        var tvPlacementType: TextView = itemView.findViewById(R.id.tvPlacementType)
        var tvPlacementStatus: TextView = itemView.findViewById(R.id.tvPlacementStatus)
        var employeeJobProfile :ImageView = itemView.findViewById(R.id.employeeJobProfile)
        var cr_placementPill :CardView = itemView.findViewById(R.id.cr_placementPill)
        var job_item :ConstraintLayout = itemView.findViewById(R.id.job_item)

    }


}