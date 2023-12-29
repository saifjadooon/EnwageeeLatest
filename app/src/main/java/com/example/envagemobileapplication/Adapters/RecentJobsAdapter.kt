package com.example.envagemobileapplication.Adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.envagemobileapplication.Models.RequestModels.AssignJobRequestModel
import com.example.envagemobileapplication.R
import com.example.envagemobileapplication.Utils.Constants

class RecentJobsAdapter(
    private val context: Context,
    private val onlineList: ArrayList<com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.GetRecentJobsRes.Datum>,
    private val childFragmentManager: FragmentManager
) :
    RecyclerView.Adapter<RecentJobsAdapter.MyViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_candidatesummary_recent_job, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        holder.tvJobName.setOnLongClickListener {
            onlineList.getOrNull(position)?.let {
                if (!it.positionName.isNullOrBlank()) {
                    Toast.makeText(context, it.positionName, Toast.LENGTH_SHORT).show()
                }
            }
            true
        }

        holder.tvJobLocation.setOnLongClickListener {
            onlineList.getOrNull(position)?.let {
                if (!it.location.isNullOrBlank()) {
                    Toast.makeText(context, it.location.toString(), Toast.LENGTH_SHORT).show()
                }
            }
            true
        }

        holder.cb_job_item.setOnCheckedChangeListener { _, isChecked ->
            onlineList.getOrNull(position)?.let { currentItem ->

                if (isChecked) {
                    // If CheckBox is checked, add the item to the selected list
                    Constants.selectedJobslist.add(
                        AssignJobRequestModel(
                            0,
                            currentItem.companyId,
                            0,
                            currentItem.jobId,
                            currentItem.positionName.orEmpty(),
                            currentItem.jobStatusId,
                            currentItem.jobStatus.orEmpty(),
                            currentItem.colorCode.orEmpty(),
                            currentItem.clientId,
                            currentItem.clientProfilePicture.orEmpty(),
                            currentItem.clientName.orEmpty(),
                            currentItem.jobType.orEmpty(),
                            currentItem.country.orEmpty(),
                            null,
                            null,
                            null,
                            null,
                            currentItem.jobNature.orEmpty(),
                            null,
                            null,
                            null,
                            null,
                            null,
                            null,
                            null,
                            null,
                            null,
                            null,
                            currentItem.isDeleted,
                            currentItem.offerSent,
                            null,
                            null,
                            null,
                            null,
                            null,
                            null,
                            null,
                            currentItem.jobGuid.orEmpty(),
                            Constants.candidateId.toString(),
                            true
                        )
                    )
                } else {
                    // If CheckBox is unchecked, remove the item from the selected list
                    Constants.selectedJobslist.removeIf { it.jobId == currentItem.jobId }
                }

                // Do something with the updated selectedList...

            }
        }

        try {
            onlineList.getOrNull(position)?.let { currentItem ->
                holder.tvJobName.text = currentItem.positionName ?: "Not Provided"

                holder.tvJobType.text =
                    "${currentItem.clientName.orEmpty()} - ${currentItem.jobType.orEmpty()}"

                holder.tvJobStatus.text = currentItem.status ?: "Not Provided"

                holder.tvJobLocation.text =
                    "${currentItem.location.orEmpty()}, ${currentItem.country.orEmpty()}"

                if (currentItem.clientProfilePicture.isNotBlank()) {
                    Glide.with(context)
                        .load(currentItem.clientProfilePicture)
                        .circleCrop()
                        .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                        .into(holder.ivCandidateJobProfile)
                }
            }
        } catch (e: Exception) {
            Log.d("CandidateJobsExce", e.toString())
        }
    }

    override fun getItemCount(): Int {
        return onlineList.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvJobName: TextView = itemView.findViewById(R.id.tvJobName)
        var tvJobType: TextView = itemView.findViewById(R.id.tvJobType)
        var tvJobLocation: TextView = itemView.findViewById(R.id.tvJobLocation)
        var tvJobStatus: TextView = itemView.findViewById(R.id.tvJobStatus)
        var ivCandidateJobProfile: ImageView = itemView.findViewById(R.id.candidateJobProfile)
        var cr_jobPill: CardView = itemView.findViewById(R.id.cr_jobPill)
        var job_item: ConstraintLayout = itemView.findViewById(R.id.job_item)
        var cb_job_item: CheckBox = itemView.findViewById(R.id.cb_job_item)
    }
}