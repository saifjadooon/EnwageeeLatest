package com.example.envagemobileapplication.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.CheckBox
import android.widget.TextView
import com.example.envagemobileapplication.R


class StatusAdapterWithCheckbox(
    context: Context,
    private val resource: Int,
    private val items: List<String>
) : ArrayAdapter<String>(context, resource, items) {

    private val selectedItems = mutableSetOf<String>()

    interface OnItemSelectedListener {
        fun onItemSelected(item: String)
        fun onItemDeselected(item: String)
    }

    var onItemSelectedListener: OnItemSelectedListener? = null

    fun getSelectedItems(): Set<String> {
        return selectedItems
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return getCustomView(position, convertView, parent)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return getCustomView(position, convertView, parent)
    }

    private fun getCustomView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater = LayoutInflater.from(context)
        val view = convertView ?: inflater.inflate(resource, parent, false)

        val checkBox = view.findViewById<CheckBox>(R.id.checkBox)
        val textView = view.findViewById<TextView>(R.id.textView)

        val item = getItem(position)
        if (item != null) {
            textView.text = item
            checkBox.isChecked = selectedItems.contains(item)

            checkBox.setOnClickListener {
                handleCheckBoxClick(position)
            }
        }

        return view
    }

    private fun handleCheckBoxClick(position: Int) {
        val clickedItem = getItem(position)
        if (clickedItem != null) {
            if (selectedItems.contains(clickedItem)) {
                selectedItems.remove(clickedItem)
                onItemSelectedListener?.onItemDeselected(clickedItem)
            } else {
                selectedItems.add(clickedItem)
                onItemSelectedListener?.onItemSelected(clickedItem)
            }
           notifyDataSetChanged() // Update the UI
        }
    }


}