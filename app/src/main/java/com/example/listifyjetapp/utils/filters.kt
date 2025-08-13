package com.example.listifyjetapp.utils

import com.example.listifyjetapp.model.ListModel

fun filterLists(input: String, lists: List<ListModel>): List<ListModel> {

    return if (input.isEmpty()) {
        lists
    } else {
        lists.filter { it.name.contains(input, ignoreCase = true) }
    }

}
