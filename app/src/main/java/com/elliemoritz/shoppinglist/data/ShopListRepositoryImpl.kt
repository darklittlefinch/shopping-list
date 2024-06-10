package com.elliemoritz.shoppinglist.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.elliemoritz.shoppinglist.domain.ShopItem
import com.elliemoritz.shoppinglist.domain.ShopListRepository
import javax.inject.Inject

class ShopListRepositoryImpl @Inject constructor(
    private val shopListDao: ShopListDao,
    private val mapper: ShopListMapper
) : ShopListRepository {

    override fun getShopList(): LiveData<List<ShopItem>> {
        return shopListDao.getShopList().map {
            mapper.mapDbModelListToEntityList(it)
        }
    }

    override suspend fun getShopItem(id: Int): ShopItem {
        val shopItemDbModel = shopListDao.getSHopItem(id)
        return mapper.mapDbModelToEntity(shopItemDbModel)
    }

    override suspend fun addShopItem(shopItem: ShopItem) {
        insertShopItemToDb(shopItem)
    }

    override suspend fun editShopItem(shopItem: ShopItem) {
        insertShopItemToDb(shopItem)
    }

    override suspend fun deleteShopItem(shopItem: ShopItem) {
        shopListDao.deleteShopItem(shopItem.id)
    }

    private suspend fun insertShopItemToDb(shopItem: ShopItem) {
        val shopItemDbModel = mapper.mapEntityToDbModel(shopItem)
        shopListDao.addShopItem(shopItemDbModel)
    }
}