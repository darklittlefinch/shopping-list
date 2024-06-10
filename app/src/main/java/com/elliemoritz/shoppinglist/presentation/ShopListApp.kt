package com.elliemoritz.shoppinglist.presentation

import android.app.Application
import com.elliemoritz.shoppinglist.di.DaggerApplicationComponent

class ShopListApp: Application() {

    val component by lazy {
        DaggerApplicationComponent.factory().create(this)
    }
}