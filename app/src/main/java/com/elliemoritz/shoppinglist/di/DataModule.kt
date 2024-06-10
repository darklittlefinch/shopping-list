package com.elliemoritz.shoppinglist.di

import android.app.Application
import com.elliemoritz.shoppinglist.data.AppDatabase
import com.elliemoritz.shoppinglist.data.ShopListDao
import dagger.Module
import dagger.Provides

@Module
class DataModule {

    @ApplicationScope
    @Provides
    fun provideDao(application: Application): ShopListDao {
        return AppDatabase.getInstance(application).shopListDao()
    }
}