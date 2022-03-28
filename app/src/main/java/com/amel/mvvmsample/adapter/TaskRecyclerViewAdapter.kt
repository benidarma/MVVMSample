package com.amel.mvvmsample.adapter

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.amel.mvvmsample.R
import com.amel.mvvmsample.databinding.ItemTaskBinding
import com.amel.mvvmsample.model.TaskEntry

class TaskRecyclerViewAdapter(
    private val clickListener: (TaskEntry) -> Unit,
    private val clickRadioListener: (TaskEntry) -> Unit
): RecyclerView.Adapter<TaskByCategoryViewHolder>() {

    private val taskList = ArrayList<TaskEntry>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskByCategoryViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ItemTaskBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.item_task, parent, false)
        return TaskByCategoryViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return taskList.size
    }

    override fun onBindViewHolder(holder: TaskByCategoryViewHolder, position: Int) {
        holder.bind(taskList[position], clickListener, clickRadioListener)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setList(task: List<TaskEntry>) {
        taskList.clear()
        taskList.addAll(task)
        notifyDataSetChanged()
    }
}

class TaskByCategoryViewHolder(private val binding: ItemTaskBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(taks: TaskEntry, clickListener: (TaskEntry) -> Unit, clickRadioListener: (TaskEntry) -> Unit) {
        binding.tvTitleTaks.text = taks.title
        binding.tvTitleTaks.setOnClickListener {
            clickListener(taks)
        }
        binding.radiobuttonTaks.setOnClickListener {
            clickRadioListener(taks)
        }
        if (taks.complete) {
            binding.radiobuttonTaks.isChecked = true
            // strikethrough
            binding.tvTitleTaks.paintFlags = binding.tvTitleTaks.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        } else {
            binding.radiobuttonTaks.isChecked = false
            binding.tvTitleTaks.paintFlags = Paint.ANTI_ALIAS_FLAG
        }
        when (taks.category) {
            1 -> {
                binding.radiobuttonTaks.buttonTintList = ColorStateList.valueOf(ContextCompat.getColor(binding.root.context, R.color.rand))
            }
            2 -> {
                binding.radiobuttonTaks.buttonTintList = ColorStateList.valueOf(ContextCompat.getColor(binding.root.context, R.color.purple_700))
            }
            3 -> {
                binding.radiobuttonTaks.buttonTintList = ColorStateList.valueOf(ContextCompat.getColor(binding.root.context, R.color.darkred))
            }
            4 -> {
                binding.radiobuttonTaks.buttonTintList = ColorStateList.valueOf(ContextCompat.getColor(binding.root.context, R.color.darkorange))
            }
            5 -> {
                binding.radiobuttonTaks.buttonTintList = ColorStateList.valueOf(ContextCompat.getColor(binding.root.context, R.color.darkgreen))
            }
            6 -> {
                binding.radiobuttonTaks.buttonTintList = ColorStateList.valueOf(ContextCompat.getColor(binding.root.context, R.color.red))
            }
            7 -> {
                binding.radiobuttonTaks.buttonTintList = ColorStateList.valueOf(ContextCompat.getColor(binding.root.context, R.color.orange))
            }
            8 -> {
                binding.radiobuttonTaks.buttonTintList = ColorStateList.valueOf(ContextCompat.getColor(binding.root.context, R.color.green))
            }
            9 -> {
                binding.radiobuttonTaks.buttonTintList = ColorStateList.valueOf(ContextCompat.getColor(binding.root.context, R.color.teal_700))
            }
            10 -> {
                binding.radiobuttonTaks.buttonTintList = ColorStateList.valueOf(ContextCompat.getColor(binding.root.context, R.color.rang))
            }
        }
    }
}

