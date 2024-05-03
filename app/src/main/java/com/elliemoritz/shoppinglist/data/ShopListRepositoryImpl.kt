package com.elliemoritz.shoppinglist.data

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.elliemoritz.shoppinglist.domain.ShopItem
import com.elliemoritz.shoppinglist.domain.ShopListRepository

class ShopListRepositoryImpl(
    application: Application
) : ShopListRepository {

    private val shopListDao = AppDatabase.getInstance(application).shopListDao()
    private val mapper = ShopListMapper()

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
        val shopItemDbModel = mapper.mapEntityToDbModel(shopItem)
        shopListDao.addShopItem(shopItemDbModel)
    }

    override suspend fun editShopItem(shopItem: ShopItem) {
        val shopItemDbModel = mapper.mapEntityToDbModel(shopItem)
        shopListDao.addShopItem(shopItemDbModel)
    }

    override suspend fun deleteShopItem(shopItem: ShopItem) {
        shopListDao.deleteShopItem(shopItem.id)
    }
}