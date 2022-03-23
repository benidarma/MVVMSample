package com.mvvm.todo.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.amel.mvvmsample.R
import com.amel.mvvmsample.databinding.FragmentTaskBinding
import com.mvvm.todo.adapter.CategoryAdapter
import com.mvvm.todo.adapter.TaskAdapter
import com.mvvm.todo.model.CategoryTask
import com.mvvm.todo.model.TaskEntry
import com.mvvm.todo.viewmodel.CategoryViewModel
import com.mvvm.todo.viewmodel.TaskViewModel

class TaskFragment : Fragment(R.layout.fragment_task), TaskAdapter.OnItemClickListener,
    CategoryAdapter.OnItemClickListener {

    private val viewModel: TaskViewModel by viewModels()
    private val categoryViewModel: CategoryViewModel by viewModels()
    private lateinit var adapter: TaskAdapter
    private lateinit var categoryAdapter: CategoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentTaskBinding.inflate(inflater)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        adapter = TaskAdapter(this)
        viewModel.getTaskToday().observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

        binding.categoryViewModel = categoryViewModel
        /*when(categoryViewModel.getAllCategory.value) {
             null -> findNavController().navigate(R.id.action_taskFragment_to_addCategoryFragment2)
        }*/
        categoryAdapter = CategoryAdapter(this, this)
        categoryViewModel.getAllCategory.observe(viewLifecycleOwner) {
            categoryAdapter.submitList(it)
        }

        binding.apply {

            binding.recyclerView.adapter = adapter
            binding.rvCategory.adapter = categoryAdapter

            floatingActionButton.setOnClickListener {
                findNavController().navigate(R.id.action_taskFragment_to_addCategoryFragment)
            }
        }

        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                activity!!.finish()
            }
        })

        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onItemClick(taskEntry: TaskEntry) {
        viewModel.onTaskSelected(taskEntry)
    }

    override fun onCheckBoxClick(taskEntry: TaskEntry, isChecked: Boolean) {
        viewModel.onTaskCheckedChanged(taskEntry, isChecked)
    }

    override fun onItemClickCategory(categoryTask: CategoryTask) {
        categoryViewModel.onCategoryVSelected(categoryTask)
        findNavController().navigate(R.id.action_taskFragment_to_taksByCategoryFragment)
    }

    fun onBackPressed() {
    }
}