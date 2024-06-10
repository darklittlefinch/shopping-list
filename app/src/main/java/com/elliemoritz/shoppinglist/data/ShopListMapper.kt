package com.elliemoritz.shoppinglist.data

import com.elliemoritz.shoppinglist.domain.ShopItem
import javax.inject.Inject

class ShopListMapper @Inject constructor() {

    fun mapEntityToDbModel(shopItem: ShopItem) = ShopItemDbModel(
        id = shopItem.id,
        name = shopItem.name,
        count = shopItem.count,
        isActive = shopItem.isActive
    )

    fun mapDbModelToEntity(shopItemDbModel: ShopItemDbModel) = ShopItem(
        id = shopItemDbModel.id,
        name = shopItemDbModel.name,
        count = shopItemDbModel.count,
        isActive = shopItemDbModel.isActive
    )

    fun mapDbModelListToEntityList(shopList: List<ShopItemDbModel>) = shopList.map {
        mapDbModelToEntity(it)
    }
}