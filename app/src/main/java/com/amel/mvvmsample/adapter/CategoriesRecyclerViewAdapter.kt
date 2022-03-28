package com.amel.mvvmsample.adapter

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.amel.mvvmsample.R
import com.amel.mvvmsample.databinding.ItemCategoryBinding
import com.amel.mvvmsample.model.CategoryData

class CategoriesRecyclerViewAdapter(
    private val clickListener: (CategoryData) -> Unit,
    private val clickLongListener: (CategoryData) -> Boolean
) : RecyclerView.Adapter<CategoriesViewHolder>() {

    private val categoriesList = ArrayList<CategoryData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriesViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ItemCategoryBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.item_category, parent, false)
        return CategoriesViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return categoriesList.size
    }

    override fun onBindViewHolder(holder: CategoriesViewHolder, position: Int) {
        holder.bind(categoriesList[position], clickListener, clickLongListener)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setList(categoryData: List<CategoryData>) {
        categoriesList.clear()
        if (categoryData.size == 1) {
            categoriesList.addAll(categoryData)
            notifyDataSetChanged()
            return
        }
        val list = categoryData.sortedByDescending {
            it.category_id
        }
        categoriesList.addAll(list)
        notifyDataSetChanged()
    }
}

class CategoriesViewHolder(
    private val binding: ItemCategoryBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(
        categoryData: CategoryData,
        clickListener: (CategoryData) -> Unit,
        clickLongListener: (CategoryData) -> Boolean
    ) {
        binding.totalTask.text = "${categoryData.category_use} taks"
        binding.tvNameCat.text = categoryData.category_name
        binding.progressbarCat.progress = categoryData.category_use
        binding.mainCard.setOnClickListener {
            clickListener(categoryData)
        }
        binding.mainCard.setOnLongClickListener {
            clickLongListener(categoryData)
        }
        when (categoryData.category_id) {
            1 -> {
                binding.progressbarCat.progressTintList =
                    ColorStateList.valueOf(ContextCompat.getColor(binding.root.context, R.color.rand))
            }
            2 -> {
                binding.progressbarCat.progressTintList =
                    ColorStateList.valueOf(ContextCompat.getColor(binding.root.context, R.color.purple_700))
            }
            3 -> {
                binding.progressbarCat.progressTintList =
                    ColorStateList.valueOf(ContextCompat.getColor(binding.root.context, R.color.darkred))
            }
            4 -> {
                binding.progressbarCat.progressTintList =
                    ColorStateList.valueOf(ContextCompat.getColor(binding.root.context, R.color.darkorange))
            }
            5 -> {
                binding.progressbarCat.progressTintList =
                    ColorStateList.valueOf(ContextCompat.getColor(binding.root.context, R.color.darkgreen))
            }
            6 -> {
                binding.progressbarCat.progressTintList =
                    ColorStateList.valueOf(ContextCompat.getColor(binding.root.context, R.color.red))
            }
            7 -> {
                binding.progressbarCat.progressTintList =
                    ColorStateList.valueOf(ContextCompat.getColor(binding.root.context, R.color.orange))
            }
            8 -> {
                binding.progressbarCat.progressTintList =
                    ColorStateList.valueOf(ContextCompat.getColor(binding.root.context, R.color.green))
            }
            9 -> {
                binding.progressbarCat.progressTintList =
                    ColorStateList.valueOf(ContextCompat.getColor(binding.root.context, R.color.teal_700))
            }
            10 -> {
                binding.progressbarCat.progressTintList =
                    ColorStateList.valueOf(ContextCompat.getColor(binding.root.context, R.color.rang))
            }
        }
    }
}