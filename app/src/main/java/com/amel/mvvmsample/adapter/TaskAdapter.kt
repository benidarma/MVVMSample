package com.mvvm.todo.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.amel.mvvmsample.databinding.ItemTaskBinding
import com.mvvm.todo.model.TaskEntry

class TaskAdapter(private val listener: OnItemClickListener) :
    ListAdapter<TaskEntry, TaskAdapter.ViewHolder>(TaskDiffCallback) {

    companion object TaskDiffCallback : DiffUtil.ItemCallback<TaskEntry>() {
        override fun areItemsTheSame(oldItem: TaskEntry, newItem: TaskEntry) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: TaskEntry, newItem: TaskEntry) = oldItem == newItem
    }

    inner class ViewHolder(val binding: ItemTaskBinding) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.apply {
                root.setOnClickListener {
                    val position = adapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        val taskEntry = getItem(position)
                        listener.onItemClick(taskEntry)
                    }
                }
                itemTodo.setOnClickListener {
                    val position = adapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        val taskEntry = getItem(position)
                        listener.onCheckBoxClick(taskEntry, itemTodo.isChecked)
                    }
                }
            }
        }

        fun bind(taskEntry: TaskEntry) {
            binding.apply {
                itemTodo.isChecked = taskEntry.complete
                tvJudul.text = taskEntry.title
                tvJudul.paint.isStrikeThruText = taskEntry.complete
            }
            binding.taskEntry = taskEntry
            binding.executePendingBindings()
        }
    }

    interface OnItemClickListener {
        fun onItemClick(taskEntry: TaskEntry)
        fun onCheckBoxClick(taskEntry: TaskEntry, isChecked: Boolean)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemTaskBinding.inflate(
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

