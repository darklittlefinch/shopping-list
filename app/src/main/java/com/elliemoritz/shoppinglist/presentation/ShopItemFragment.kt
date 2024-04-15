package com.elliemoritz.shoppinglist.presentation

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.elliemoritz.shoppinglist.R
import com.elliemoritz.shoppinglist.databinding.FragmentShopItemBinding
import com.elliemoritz.shoppinglist.domain.ShopItem

class ShopItemFragment : Fragment() {
    private var _binding: FragmentShopItemBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: ShopItemViewModel

    private var shopItemId = ShopItem.UNDEFINED_ID

    private lateinit var onEditingFinishedListener: OnEditingFinishedListener

    override fun onAttach(context: Context) {
        Log.d("LIFECYCLE", "onAttach")
        super.onAttach(context)
        if (context is OnEditingFinishedListener) {
            onEditingFinishedListener = context
        } else {
            throw java.lang.RuntimeException("Activity must implement OnEditingFinishedListener")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("LIFECYCLE", "onCreate")
        super.onCreate(savedInstanceState)
        setShopItemId()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("LIFECYCLE", "onCreateView")
        _binding = FragmentShopItemBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.d("LIFECYCLE", "onViewCreated")
        super.onViewCreated(view, savedInstanceState)
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[ShopItemViewModel::class.java]
        observeViewModel()

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

    override fun onStart() {
        Log.d("LIFECYCLE", "onStart")
        super.onStart()
    }

    override fun onResume() {
        Log.d("LIFECYCLE", "onResume")
        super.onResume()
    }

    override fun onPause() {
        Log.d("LIFECYCLE", "onPause")
        super.onPause()
    }

    override fun onStop() {
        Log.d("LIFECYCLE", "onStop")
        super.onStop()
    }

    override fun onDestroyView() {
        Log.d("LIFECYCLE", "onDestroyView")
        super.onDestroyView()
    }

    override fun onDestroy() {
        Log.d("LIFECYCLE", "onDestroy")
        super.onDestroy()
    }

    override fun onDetach() {
        Log.d("LIFECYCLE", "onDetach")
        super.onDetach()
    }

    private fun observeViewModel() {
        viewModel.shopItem.observe(viewLifecycleOwner) {
            binding.etName.setText(it.name)
            binding.etCount.setText(it.count.toString())
        }

        viewModel.errorInputName.observe(viewLifecycleOwner) {
            binding.tilName.error = if (it) {
                getString(R.string.error_incorrect_name)
            } else {
                null
            }
        }
        setupResetErrorInputName()

        viewModel.errorInputCount.observe(viewLifecycleOwner) {
            binding.tilCount.error = if (it) {
                getString(R.string.error_incorrect_count)
            } else {
                null
            }
        }
        setupResetErrorInputCount()

        viewModel.shouldCloseScreen.observe(viewLifecycleOwner) {
            onEditingFinishedListener.onEditingFinished()
        }
    }

    private fun setShopItemId() {
        val args = requireArguments()
        if (!args.containsKey(SHOP_ITEM_ID)) {
            throw RuntimeException("Param shop item id is absent")
        }
        shopItemId = args.getInt(SHOP_ITEM_ID, ShopItem.UNDEFINED_ID)
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
        private const val SHOP_ITEM_ID = "id"

        fun newInstance(id: Int): ShopItemFragment {
            return ShopItemFragment().apply {
                arguments = Bundle().apply {
                    putInt(SHOP_ITEM_ID, id)
                }
            }
        }
    }

    interface OnEditingFinishedListener {
        fun onEditingFinished()
    }
}