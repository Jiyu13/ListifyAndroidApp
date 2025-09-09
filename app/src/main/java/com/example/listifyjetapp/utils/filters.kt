package com.example.listifyjetapp.utils

import com.example.listifyjetapp.model.ListItem
import com.example.listifyjetapp.model.ListModel

fun filterLists(input: String, lists: List<ListModel>): List<ListModel> {

    return if (input.isEmpty()) {
        lists
    } else {
        lists.filter { it.name.contains(input, ignoreCase = true) }
    }

}

fun filterListItems(input: String, items: List<ListItem>): List<ListItem> {
    return if (input.isEmpty()) {
        items
    } else {
        items.filter { it.description.contains(input, ignoreCase = true) }
    }
}