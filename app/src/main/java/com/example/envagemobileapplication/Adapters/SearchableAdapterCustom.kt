package com.example.envagemobileapplication.Adapters



import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.envagemobileapplication.R
import com.example.envagemobileapplication.Utils.SearchableItemsCustom
import java.util.*

class SearchableAdapterCustom(
    internal var context: Context,
    private val mValues: List<SearchableItemsCustom>,
    private var filteredList: List<SearchableItemsCustom>,
    clickListener: ItemClickListener,
    var singleSelection:Boolean=false
) : Filterable, RecyclerView.Adapter<SearchableAdapterCustom.ViewHolder>() {
    private var itemClickListener: ItemClickListener = clickListener



    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        internal var titleTextView = mView.findViewById<TextView>(R.id.titleTextView)
        internal var checkBox = mView.findViewById<CheckBox>(R.id.checkBox)

        var mItem: SearchableItemsCustom? = null
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_multi_select_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.mItem = filteredList[holder.adapterPosition]

        holder.checkBox.setOnCheckedChangeListener(null)
        holder.titleTextView.text = holder.mItem!!.skillname
        holder.checkBox.isChecked = holder.mItem!!.isSelected
        if(singleSelection){
            holder.checkBox.visibility=View.GONE
        }else{
            holder.checkBox.visibility=View.VISIBLE
        }
        var productPosition = 0
        for (i in mValues.indices) {
            if (mValues[i].code.equals(holder.mItem!!.code)) {
                productPosition = i
            }
        }

        holder.checkBox.setOnCheckedChangeListener { buttonView, isChecked ->
            itemClickListener.onItemClicked(
                filteredList[holder.adapterPosition],
                productPosition,
                isChecked
            )
        }

        holder.mView.setOnClickListener { view ->
            holder.checkBox.isChecked = !holder.checkBox.isChecked
        }

    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence): Filter.FilterResults {
                val charString = charSequence.toString()
                if (charString.isEmpty()) {
                    filteredList = mValues
                } else {
                    val tempList = ArrayList<SearchableItemsCustom>()
                    for (row in mValues) {
                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.skillname.toLowerCase(Locale.getDefault())
                                .contains(charString.toLowerCase(Locale.getDefault()))
                        ) {
                            tempList.add(row)
                        }
                    }

                    filteredList = tempList
                }

                val filterResults = Filter.FilterResults()
                filterResults.values = filteredList
                return filterResults
            }

            override fun publishResults(
                charSequence: CharSequence,
                filterResults: Filter.FilterResults
            ) {
                filteredList = filterResults.values as ArrayList<SearchableItemsCustom>

                // refresh the list with filtered data
                notifyDataSetChanged()
            }
        }
    }

    override fun getItemCount(): Int {
        return filteredList.size
    }

    companion object {
        lateinit var itemClickListener: ItemClickListener
    }


    interface ItemClickListener {
        fun onItemClicked(item: SearchableItemsCustom, position: Int, b: Boolean)
    }
}