package com.elliemoritz.shoppinglist.domain

data class ShopItem (
    var name: String,
    var count: Int,
    var isActive: Boolean,
    var id: Int = UNDEFINED_ID
) {
    companion object {
        const val UNDEFINED_ID = -1
    }
}
