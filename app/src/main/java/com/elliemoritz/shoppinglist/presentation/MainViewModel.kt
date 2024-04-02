package com.elliemoritz.shoppinglist.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

import com.elliemoritz.shoppinglist.data.ShopListRepositoryImpl

import com.elliemoritz.shoppinglist.domain.DeleteShopItemUseCase
import com.elliemoritz.shoppinglist.domain.EditShopItemUseCase
import com.elliemoritz.shoppinglist.domain.GetShopListUseCase
import com.elliemoritz.shoppinglist.domain.ShopItem

class MainViewModel : ViewModel() {
    private val repository = ShopListRepositoryImpl

    private val getShopListUseCase = GetShopListUseCase(repository)
    private val editShopItemUseCase = EditShopItemUseCase(repository)
    private val deleteShopItemUseCase = DeleteShopItemUseCase(repository)

    private val shopList = MutableLiveData<List<ShopItem>>()

    fun getShopList() {
        val list = getShopListUseCase.getShopList()
        shopList.value = list
    }

    fun changeEnableState(shopItem: ShopItem) {
        val shopItemCopy = shopItem.copy(isActive = !shopItem.isActive)
        editShopItemUseCase.editShopItem(shopItemCopy)
        getShopList()
    }

    fun deleteShopItem(shopItem: ShopItem) {
        deleteShopItemUseCase.deleteShopItem(shopItem)
        getShopList()
    }
}