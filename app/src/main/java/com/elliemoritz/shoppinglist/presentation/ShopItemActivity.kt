package com.elliemoritz.shoppinglist.presentation

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.lifecycle.ViewModelProvider
import com.elliemoritz.shoppinglist.R
import com.elliemoritz.shoppinglist.databinding.ActivityShopItemBinding
import com.elliemoritz.shoppinglist.domain.ShopItem

class ShopItemActivity : AppCompatActivity() {

    private lateinit var viewModel: ShopItemViewModel
    private lateinit var binding: ActivityShopItemBinding

    private var shopItemId: Int = ShopItem.UNDEFINED_ID

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupBinding()
        viewModel = ViewModelProvider(this)[ShopItemViewModel::class.java]

        observeViewModel()
        setShopItemId()

        if (shopItemId != ShopItem.UNDEFINED_ID) {
            viewModel.getShopItem(shopItemId)
        }

        binding.buttonSave.setOnClickListener {
            val name = binding.etName.text?.toString()
            val count = binding.etCount.text?.toString()
            if (shopItemId == ShopItem.UNDEFINED_ID) {
                viewModel.addShopItem(name, count)
            } else {
                viewModel.editShopItem(shopItemId, name, count)
            }
        }
    }

    private fun setupBinding() {
        binding = ActivityShopItemBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }

    private fun setShopItemId() {
        shopItemId = intent.getIntExtra(EXTRA_ID, ShopItem.UNDEFINED_ID)
    }

    private fun observeViewModel() {
        viewModel.shopItem.observe(this) {
            binding.etName.setText(it.name)
            binding.etCount.setText(it.count.toString())
        }

        viewModel.errorInputName.observe(this) {
            binding.tilName.error = if (it) {
                getString(R.string.error_incorrect_name)
            } else {
                null
            }
        }
        setupResetErrorInputName()

        viewModel.errorInputCount.observe(this) {
            binding.tilCount.error = if (it) {
                getString(R.string.error_incorrect_count)
            } else {
                null
            }
        }
        setupResetErrorInputCount()

        viewModel.shouldCloseScreen.observe(this) {
            finish()
        }
    }

    private fun setupResetErrorInputName() {
        binding.etName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewModel.resetErrorInputName()
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        })
    }

    private fun setupResetErrorInputCount() {
        binding.etCount.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewModel.resetErrorInputCount()
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        })
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