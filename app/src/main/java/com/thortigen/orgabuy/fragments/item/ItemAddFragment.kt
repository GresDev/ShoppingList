package com.thortigen.orgabuy.fragments.item

import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.thortigen.orgabuy.R
import com.thortigen.orgabuy.data.models.CatalogItem
import com.thortigen.orgabuy.data.models.ShopListItem
import com.thortigen.orgabuy.databinding.FragmentItemAddBinding
import com.thortigen.orgabuy.utils.ItemFullName
import com.thortigen.orgabuy.utils.hideKeyboard
import com.thortigen.orgabuy.viewmodels.CatalogViewModel
import com.thortigen.orgabuy.viewmodels.ItemAddViewModel
import com.thortigen.orgabuy.viewmodels.ShopListViewModel
import kotlinx.coroutines.delay
import java.util.*

class ItemAddFragment : Fragment() {

    private var _binding: FragmentItemAddBinding? = null
    private val binding get() = _binding!!

    private val mItemAddViewModel: ItemAddViewModel by viewModels()

    private val mShopListViewModel: ShopListViewModel by viewModels()

    private val mCatalogViewModel: CatalogViewModel by viewModels()

    private val args by navArgs<ItemAddFragmentArgs>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentItemAddBinding.inflate(inflater, container, false)

        binding.searchString = args.searchString

        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_item_add_actionbar, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_add_item) {
            insertCatalogItemToDatabase()
        }
        view?.hideKeyboard()
        return super.onOptionsItemSelected(item)
    }

    private fun insertCatalogItemToDatabase() {

        val mName = binding.tiEtItemName.text.toString().replaceFirstChar {
            if (it.isLowerCase()) it.titlecase(
                Locale.getDefault()
            ) else it.toString()
        }

        val mDescription = binding.tiEtItemDescription.text.toString().replaceFirstChar {
            if (it.isLowerCase()) it.titlecase(
                Locale.getDefault()
            ) else it.toString()
        }

        if (inputIsCorrect(mName)) {
            val newCatalogItem = CatalogItem(
                0,
                mName,
                mDescription
            )
            addNewItem(newCatalogItem, R.id.action_itemAddFragment_to_catalogFragment)

        } else {
            showToast("Поле \"Наименование\" не заполнено!")
        }
    }

    private fun addNewItem(newCatalogItem: CatalogItem, action: Int) {

        val itemFullName = ItemFullName(newCatalogItem.name, newCatalogItem.description)

        val itemFullNameList = mItemAddViewModel.getFullNameItems()

        if (itemFullName !in itemFullNameList) {
            mItemAddViewModel.insertItem(newCatalogItem)

            if (binding.swAutoAddNewItem.isChecked) {

                mShopListViewModel.insertItem(
                    ShopListItem(
                        newCatalogItem.id,
                        newCatalogItem.name,
                        newCatalogItem.description,
                        0
                    )
                )

            }

            showToast("Новая запись добавлена")
            findNavController().navigate(action)

        }

        else {

            showToast("Такая запись уже есть")

        }
    }

        private fun inputIsCorrect(itemName: String) = itemName.isNotEmpty()

        private fun showToast(message: String) {
            val toast =
                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT)
            toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0)
            toast.show()
        }

        override fun onDestroyView() {
            super.onDestroyView()
            _binding = null
        }

    }