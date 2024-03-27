package com.example.envagemobileapplication.Adapters
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.envagemobileapplication.R
import com.leo.searchablespinner.interfaces.OnItemSelectListener

internal class SpinnerCheckboxRecyclerAdapter(
    private val context: Context,
    private val list: ArrayList<String>,
    private val onItemSelectListener: OnItemSelectListener
) :
    RecyclerView.Adapter<SpinnerCheckboxRecyclerAdapter.SpinnerHolder>() {
    private var selectedItem: String = ""

    var highlightSelectedItem: Boolean = true

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpinnerHolder {
        return SpinnerHolder(
            LayoutInflater.from(context).inflate(
                R.layout.list_item_searchale_spinner_with_checkbox,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: SpinnerHolder, position: Int) {
        val currentString = list[position]
        holder.textViewSpinnerItem.text = currentString

        val colorDrawable: ColorDrawable = if (currentString == selectedItem) {
            ColorDrawable(ContextCompat.getColor(context, R.color.gray))
        } else {
            ColorDrawable(ContextCompat.getColor(context, android.R.color.white))
        }
        holder.textViewSpinnerItem.background = colorDrawable

        holder.checkbox.isChecked = currentString == selectedItem

        holder.textViewSpinnerItem.setOnClickListener {
            holder.checkbox.isChecked = !holder.checkbox.isChecked
            selectedItem = if (holder.checkbox.isChecked) currentString else ""
            notifyDataSetChanged()
            onItemSelectListener.setOnItemSelectListener(getOriginalItemPosition(currentString), currentString)
        }
    }

    class SpinnerHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewSpinnerItem: TextView = itemView.findViewById(R.id.textViewSpinnerItem)
        val checkbox: CheckBox = itemView.findViewById(R.id.checkBox3)
    }

    fun filter(query: CharSequence?) {
        val filteredNames: ArrayList<String> = ArrayList()
        if (query.isNullOrEmpty()) {
            filterList(list)
        } else {
            for (s in list) {
                if (s.contains(query, true)) {
                    filteredNames.add(s)
                }
            }
            filterList(filteredNames)
        }
    }

    private fun filterList(filteredNames: ArrayList<String>) {
        list.clear()
        list.addAll(filteredNames)
        notifyDataSetChanged()
    }

    private fun getOriginalItemPosition(selectedString: String): Int {
        return list.indexOf(selectedString)
    }
}