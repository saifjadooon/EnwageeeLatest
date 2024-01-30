package com.example.envagemobileapplication.Adapters

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.envagemobileapplication.R
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class MessagesAdapter(
    var context: Context,
    onlineList: ArrayList<com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.GetAllSmsResp.Record>

) :
    RecyclerView.Adapter<MessagesAdapter.MyViewHolder>() {


    var dataList: ArrayList<com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.GetAllSmsResp.Record> = onlineList


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.messages_item, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
         try {
               if (!dataList.get(position).to.isNullOrEmpty()) {

                   holder.tvTo.text = "To: "+dataList.get(position).to
               }/* else {
                   holder.tvTo.visibility = View.GONE
               }*/

           } catch (e: java.lang.Exception) {
           }
           try {
               if (!dataList.get(position).from.isNullOrEmpty()) {
                   holder.tvFrom.text = dataList.get(position).from
               }

           } catch (e: java.lang.Exception) {
           }
           try {
               if (!dataList.get(position).dateCreated.isNullOrEmpty()) {
                 /*  var formatedDate = com.example.envagemobileapplication.Utils.Global.formatDate(dataList.get(position).dateCreated)
                   holder.tvDate.text = formatedDate*/
                   val originalDateTime = dataList.get(position).dateCreated
                   val formattedDateTime = convertDateTimeFormat(originalDateTime)

                   holder.tvDate.text = formattedDateTime
               } else {
                   holder.tvDate.text = "Not provided"
               }

           } catch (e: java.lang.Exception) {
           }
           try {
               if (!dataList.get(position).body.isNullOrEmpty()) {
                   val originalText = dataList.get(position).body
                   val textWithoutSpaces = originalText.replace("  ", "")
                   holder.tvMsg.text =    textWithoutSpaces
               } else {

               }

           } catch (e: java.lang.Exception) {
           }

        holder.tvMsg.setOnLongClickListener {

            val toast = Toast.makeText(
                context,
                "sfd",
                Toast.LENGTH_LONG
            )

            toast.show()
            true
        }
        holder.itemlayout.setOnClickListener {

        }

    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvFrom: TextView = itemView.findViewById(R.id.tvFrom)
        var tvDate: TextView = itemView.findViewById(R.id.tvDate)
        var tvTo: TextView = itemView.findViewById(R.id.tvToo)
        var tvMsg: TextView = itemView.findViewById(R.id.tvMsg)
        var itemlayout: ConstraintLayout = itemView.findViewById(R.id.itemlayout)
    }

    private fun convertDateTimeFormat(originalDateTime: String): String {
        val inputFormat = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX", Locale.US)
        } else {
            TODO("VERSION.SDK_INT < N")
        }
        val outputDateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.US)

        val outputTimeFormat = SimpleDateFormat("HH:mm", Locale.US)

        try {
            val date = inputFormat.parse(originalDateTime)
            val formattedDate = outputDateFormat.format(date!!)
            val formattedTime = outputTimeFormat.format(date)

            return "$formattedDate $formattedTime"
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return ""
    }
}