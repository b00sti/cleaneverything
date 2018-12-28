package com.b00sti.cleaneverything.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import com.b00sti.cleaneverything.AppExecutors
import com.b00sti.cleaneverything.databinding.CategoryItemBinding
import com.b00sti.cleaneverything.ui.common.DataBoundListAdapter
import com.b00sti.cleaneverything.vo.CategoryItem
import com.b00sti.cleaneverything.R

class CategoryListAdapter(
    private val dataBindingComponent: DataBindingComponent,
    appExecutors: AppExecutors,
    private val repoClickCallback: ((CategoryItem) -> Unit)?
) : DataBoundListAdapter<CategoryItem, CategoryItemBinding>(
    appExecutors = appExecutors,
    diffCallback = object : DiffUtil.ItemCallback<CategoryItem>() {
        override fun areItemsTheSame(oldItem: CategoryItem, newItem: CategoryItem): Boolean {
            return oldItem.title == newItem.title
        }

        override fun areContentsTheSame(oldItem: CategoryItem, newItem: CategoryItem): Boolean {
            return oldItem.title == newItem.title && oldItem.isDone == newItem.isDone
        }
    }
) {
    override fun bind(binding: CategoryItemBinding, item: CategoryItem) {
        binding.item = item
        binding.vCheckbox.isChecked = item.isDone
    }

    override fun createBinding(parent: ViewGroup): CategoryItemBinding {
        val binding = DataBindingUtil.inflate<CategoryItemBinding>(
            LayoutInflater.from(parent.context),
            R.layout.category_item,
            parent,
            false,
            dataBindingComponent
        )
        binding.root.setOnClickListener {
            binding.item?.let {
                repoClickCallback?.invoke(it)
            }
        }
        return binding
    }
}