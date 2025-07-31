package com.example.listifyjetapp.utils

import android.util.Log
import com.example.listifyjetapp.model.ListModel

fun filterListItems(input: String, listItems: List<ListModel>): List<ListModel> {

    return if (input.isEmpty()) {
        listItems
    } else {
        listItems.filter { it.name.contains(input, ignoreCase = true) }
    }

}
