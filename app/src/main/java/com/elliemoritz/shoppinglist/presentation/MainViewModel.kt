package com.elliemoritz.shoppinglist.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.elliemoritz.shoppinglist.data.ShopListRepositoryImpl
import com.elliemoritz.shoppinglist.domain.DeleteShopItemUseCase
import com.elliemoritz.shoppinglist.domain.EditShopItemUseCase
import com.elliemoritz.shoppinglist.domain.GetShopListUseCase
import com.elliemoritz.shoppinglist.domain.ShopItem
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = ShopListRepositoryImpl(application)

    private val getShopListUseCase = GetShopListUseCase(repository)
    private val editShopItemUseCase = EditShopItemUseCase(repository)
    private val deleteShopItemUseCase = DeleteShopItemUseCase(repository)

    var shopList = getShopListUseCase.getShopList()

    fun changeEnableState(shopItem: ShopItem) {
        viewModelScope.launch {
            val shopItemCopy = shopItem.copy(isActive = !shopItem.isActive)
            editShopItemUseCase.editShopItem(shopItemCopy)
        }
    }

    fun deleteShopItem(shopItem: ShopItem) {
        viewModelScope.launch {
            deleteShopItemUseCase.deleteShopItem(shopItem)
        }
    }
}