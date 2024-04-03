package com.elliemoritz.shoppinglist.presentation

import androidx.lifecycle.LiveData
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

    var shopList = getShopListUseCase.getShopList()

    fun changeEnableState(shopItem: ShopItem) {
        val shopItemCopy = shopItem.copy(isActive = !shopItem.isActive)
        editShopItemUseCase.editShopItem(shopItemCopy)
    }

    fun deleteShopItem(shopItem: ShopItem) {
        deleteShopItemUseCase.deleteShopItem(shopItem)
    }
}