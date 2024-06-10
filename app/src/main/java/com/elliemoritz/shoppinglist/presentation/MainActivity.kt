package com.elliemoritz.shoppinglist.presentation

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchHelper.SimpleCallback
import androidx.recyclerview.widget.RecyclerView
import com.elliemoritz.shoppinglist.R
import com.elliemoritz.shoppinglist.databinding.ActivityMainBinding
import com.elliemoritz.shoppinglist.domain.ShopItem
import javax.inject.Inject

class MainActivity : AppCompatActivity(), ShopItemFragment.OnEditingFinishedListener {

    private val component by lazy {
        (application as ShopListApp).component
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: ShopListAdapter
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        component.inject(this)
        setupBinding()

        setupRecyclerView()

        viewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]
        viewModel.shopList.observe(this) {
            adapter.submitList(it)
        }

        setupAddButton()
    }

    private fun setupBinding() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }

    private fun setupRecyclerView() {
        adapter = ShopListAdapter()
        binding.rvShopList.adapter = adapter

        binding.rvShopList.recycledViewPool.setMaxRecycledViews(
            ShopListAdapter.ACTIVATED_VIEW_TYPE, ShopListAdapter.MAX_POOL_SIZE
        )

        binding.rvShopList.recycledViewPool.setMaxRecycledViews(
            ShopListAdapter.DEACTIVATED_VIEW_TYPE, ShopListAdapter.MAX_POOL_SIZE
        )

        setupLongClickListener()
        setupClickListener()
        setupSwipeListener(binding.rvShopList)
    }

    private fun setupLongClickListener() {
        adapter.onShopItemLongClickListener = {
            viewModel.changeEnableState(it)
        }
    }

    private fun setupClickListener() {
        adapter.onShopItemClickListener = {
            if (isOrientationLandscape()) {
                launchFragment(it.id)
            } else {
                val intent = ShopItemActivity.newIntentEditItem(this, it.id)
                startActivity(intent)
            }
        }
    }

    private fun setupSwipeListener(rv: RecyclerView) {
        val callback = object : SimpleCallback(
            0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val shopItem = adapter.currentList[viewHolder.adapterPosition]
                viewModel.deleteShopItem(shopItem)
            }
        }

        val itemTouchHelper = ItemTouchHelper(callback)
        itemTouchHelper.attachToRecyclerView(rv)
    }

    private fun isOrientationLandscape(): Boolean {
        return binding.shopItemContainer != null
    }

    private fun launchFragment(id: Int = ShopItem.UNDEFINED_ID) {
        supportFragmentManager.popBackStack()
        val fragment = ShopItemFragment.newInstance(id)
        supportFragmentManager.beginTransaction()
            .replace(R.id.shopItemContainer, fragment)
            .addToBackStack(null)
            .commit()
    }

    private fun setupAddButton() {
        binding.fabAddNewShopItem.setOnClickListener {
            if (isOrientationLandscape()) {
                launchFragment()
            } else {
                val intent = ShopItemActivity.newIntentAddItem(this)
                startActivity(intent)
            }
        }
    }

    override fun onEditingFinished() {
        Toast.makeText(this, getString(R.string.success), Toast.LENGTH_SHORT).show()
        supportFragmentManager.popBackStack()
    }
}
