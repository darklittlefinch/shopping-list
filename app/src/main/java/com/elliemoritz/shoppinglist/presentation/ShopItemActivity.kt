package com.elliemoritz.shoppinglist.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.elliemoritz.shoppinglist.R
import com.elliemoritz.shoppinglist.databinding.ActivityShopItemBinding
import com.elliemoritz.shoppinglist.domain.ShopItem

class ShopItemActivity : AppCompatActivity() {

    private lateinit var binding: ActivityShopItemBinding

    private var shopItemId: Int = ShopItem.UNDEFINED_ID

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupBinding()
        setShopItemId()
        if (savedInstanceState == null) {
            launchFragment()
        }
    }

    private fun launchFragment() {
        val fragment = ShopItemFragment.newInstance(shopItemId)
        supportFragmentManager.beginTransaction()
            .replace(R.id.shopItemContainer, fragment)
            .commit()
    }

    private fun setupBinding() {
        binding = ActivityShopItemBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }

    private fun setShopItemId() {
        shopItemId = intent.getIntExtra(EXTRA_ID, ShopItem.UNDEFINED_ID)
    }

    companion object {
        private const val EXTRA_ID = "id"

        fun newIntentAddItem(context: Context): Intent {
            return Intent(context, ShopItemActivity::class.java)
        }

        fun newIntentEditItem(context: Context, id: Int): Intent {
            val intent = Intent(context, ShopItemActivity::class.java)
            intent.putExtra(EXTRA_ID, id)
            return intent
        }
    }
}