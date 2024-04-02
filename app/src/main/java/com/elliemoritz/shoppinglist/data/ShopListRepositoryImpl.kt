package com.elliemoritz.shoppinglist.data

import com.elliemoritz.shoppinglist.domain.ShopItem
import com.elliemoritz.shoppinglist.domain.ShopListRepository
import java.lang.RuntimeException

object ShopListRepositoryImpl : ShopListRepository {

    private val shopList = mutableListOf<ShopItem>()
    private var autoIncrementId = 0;

    override fun getShopList(): List<ShopItem> {
        return shopList.toList()
    }

    override fun getShopItem(id: Int): ShopItem {
        return shopList.find { it.id == id }
            ?: throw RuntimeException("Unable to get shop item: " +
                    "element with id $id not found")
    }

    override fun addShopItem(shopItem: ShopItem) {
        if (shopItem.id == ShopItem.UNDEFINED_ID) {
            shopItem.id = autoIncrementId
            shopList.add(shopItem)
            autoIncrementId++
        }
    }

    override fun editShopItem(shopItem: ShopItem) {
        val oldShopItem = shopList.find { it.id == shopItem.id }
            ?: throw RuntimeException("Unable to edit shop item: " +
                    "element with id ${shopItem.id} not found")
        shopList.remove(oldShopItem)
        addShopItem(shopItem)
    }

    override fun deleteShopItem(shopItem: ShopItem) {
        shopList.remove(shopItem)
    }

}