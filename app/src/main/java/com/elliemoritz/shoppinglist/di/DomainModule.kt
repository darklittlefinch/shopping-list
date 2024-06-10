package com.elliemoritz.shoppinglist.di

import com.elliemoritz.shoppinglist.data.ShopListRepositoryImpl
import com.elliemoritz.shoppinglist.domain.ShopListRepository
import dagger.Binds
import dagger.Module

@Module
interface DomainModule {

    @ApplicationScope
    @Binds
    fun bindRepository(impl: ShopListRepositoryImpl): ShopListRepository
}