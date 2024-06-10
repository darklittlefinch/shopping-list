package com.elliemoritz.shoppinglist.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elliemoritz.shoppinglist.domain.DeleteShopItemUseCase
import com.elliemoritz.shoppinglist.domain.EditShopItemUseCase
import com.elliemoritz.shoppinglist.domain.GetShopListUseCase
import com.elliemoritz.shoppinglist.domain.ShopItem
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val getShopListUseCase: GetShopListUseCase,
    private val editShopItemUseCase: EditShopItemUseCase,
    private val deleteShopItemUseCase: DeleteShopItemUseCase
) : ViewModel() {

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