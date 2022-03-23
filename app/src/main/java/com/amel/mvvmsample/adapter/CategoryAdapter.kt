package com.mvvm.todo.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.amel.mvvmsample.databinding.ItemCategoryBinding
import com.mvvm.todo.model.CategoryTask
import com.mvvm.todo.ui.fragment.TaskFragment


class CategoryAdapter(
    private val listener: OnItemClickListener,
    private val listener2: TaskFragment
) :
    ListAdapter<CategoryTask, CategoryAdapter.ViewHolder>(TaskDiffCallback) {

    companion object TaskDiffCallback : DiffUtil.ItemCallback<CategoryTask>() {
        override fun areItemsTheSame(oldItem: CategoryTask, newItem: CategoryTask) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: CategoryTask, newItem: CategoryTask) =
            oldItem == newItem
    }

    fun sData(string: String) {

    }

    inner class ViewHolder(private val binding: ItemCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.apply {
                root.setOnClickListener {
                    /*if (position != RecyclerView.NO_POSITION) {
                        val categoryTask = getItem(position)
                        listener.onItemClickCategory(categoryTask)
                    }*/
                }
                mainCard.setOnClickListener {
                    val position = adapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        val categoryTask = getItem(position)
                        listener.onItemClickCategory(categoryTask)
                    }
                }
            }
        }

        fun bind(categoryTask: CategoryTask) {
            //listener2.ngantukCuk(categoryTask)
            binding.apply {
                //totalTask
            }
            binding.categoryTask = categoryTask
            binding.executePendingBindings()
        }
    }

    interface OnItemClickListener {
        fun onItemClickCategory(categoryTask: CategoryTask)
    }

    interface OndataBeruba {
        fun ngantukCuk(categoryTask: CategoryTask);
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemCategoryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current)
    }
}
