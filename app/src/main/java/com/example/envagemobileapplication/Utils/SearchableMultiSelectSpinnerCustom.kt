package com.example.envagemobileapplication.Utils

import android.content.Context
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager

import com.devstune.searchablemultiselectspinner.SelectionCompleteListener
import com.example.envagemobileapplication.Adapters.SearchableAdapterCustom
import com.example.envagemobileapplication.R


class SearchableMultiSelectSpinnerCustom {
    companion object {
        fun show(
            context: Context,
            title: String,
            doneButtonText:String,
            searchableItems: MutableList<SearchableItemsCustom>,
            selectionCompleteListener: SelectionCompleteListenerCustom
        ) {
            val alertDialog = AlertDialog.Builder(context)
            val inflater = LayoutInflater.from(context)
            val convertView = inflater.inflate(R.layout.searchable_list_layout, null)

            alertDialog.setView(convertView)
            alertDialog.setTitle(title)

            val searchView = convertView.findViewById<SearchView>(R.id.searchView)
            val recyclerView =
                convertView.findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.recyclerView)
            val mLayoutManager = LinearLayoutManager(context)
            val adapter =
                SearchableAdapterCustom(
                    context,
                    searchableItems,
                    searchableItems,
                    object : SearchableAdapterCustom.ItemClickListener {
                        override fun onItemClicked(
                            item: SearchableItemsCustom,
                            position: Int,
                            b: Boolean
                        ) {
                            for (i in searchableItems.indices) {
                                if (searchableItems[i].code == item.code) {
                                    searchableItems[i].isSelected = b
                                    break
                                }
                            }
                        }

                    },false)
            recyclerView.itemAnimator = null
            recyclerView.layoutManager = mLayoutManager
            recyclerView.adapter = adapter

            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String): Boolean {
                    // do something on text submit
                    return false
                }

                override fun onQueryTextChange(newText: String): Boolean {
                    // do something when text changes
                    adapter.filter.filter(newText)
                    return false
                }
            })

            alertDialog.setPositiveButton(doneButtonText) { dialogInterface, i ->
                dialogInterface.dismiss()
                val resultList=ArrayList<SearchableItemsCustom>()
                for (index in searchableItems.indices) {
                    if (searchableItems[index].isSelected) {
                        resultList.add(searchableItems[index])
                    }
                }

                selectionCompleteListener.onCompleteSelection(resultList)
            }

            alertDialog.show()
        }
    }


}