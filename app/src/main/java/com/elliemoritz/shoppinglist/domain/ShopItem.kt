package com.elliemoritz.shoppinglist.domain

data class ShopItem (
    val id: Int,
    var name: String,
    var count: Int,
    var isActive: Boolean
)
