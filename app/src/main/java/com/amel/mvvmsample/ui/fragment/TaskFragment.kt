package com.amel.mvvmsample.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.amel.mvvmsample.R
import com.amel.mvvmsample.adapter.CategoriesRecyclerViewAdapter
import com.amel.mvvmsample.adapter.TaskRecyclerViewAdapter
import com.amel.mvvmsample.databinding.FragmentTaskBinding
import com.amel.mvvmsample.model.CategoryData
import com.amel.mvvmsample.model.TaskEntry
import com.amel.mvvmsample.ui.fragment.add.Cons.INSERT
import com.amel.mvvmsample.util.Constant.myGlobalVarIdCat
import com.amel.mvvmsample.util.Constant.myGlobalVarTitleCat
import com.amel.mvvmsample.viewmodel.CategoryViewModel
import com.amel.mvvmsample.viewmodel.TaskViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TaskFragment : Fragment(R.layout.fragment_task) {

    private val viewModel: TaskViewModel by viewModels()
    private val categoryViewModel: CategoryViewModel by viewModels()
    private lateinit var taksAdapter: TaskRecyclerViewAdapter
    private lateinit var categoryAdapter: CategoriesRecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentTaskBinding.inflate(inflater)

        binding.lifecycleOwner = this

        taksAdapter = TaskRecyclerViewAdapter({ selectedItem: TaskEntry ->
            taksTitleClicked(selectedItem)
        }, { selectedItem: TaskEntry ->
            taksRadioClicked(selectedItem)
        })
        viewModel.taksToday.observe(viewLifecycleOwner) {
            taksAdapter.setList(it)
        }
        viewModel.message.observe(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let { its ->
                Toast.makeText(activity, its, Toast.LENGTH_LONG).show()
            }
        }

        categoryAdapter = CategoriesRecyclerViewAdapter({ selectedItem: CategoryData ->
            listItemClicked(selectedItem)
        }, { selectedLongItem: CategoryData ->
            listItemLongClicked(selectedLongItem)
        })
        categoryViewModel.message.observe(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let { its ->
                Toast.makeText(activity, its, Toast.LENGTH_LONG).show()
            }
        }
        categoryViewModel.getAllCategories().observe(viewLifecycleOwner) {
            categoryViewModel.loadCategory(it)
        }
        categoryViewModel.category.observe(viewLifecycleOwner) {
            categoryAdapter.setList(it)
        }

        binding.apply {

            binding.recyclerView.adapter = taksAdapter
            binding.rvCategory.adapter = categoryAdapter

            floatingActionButton.setOnClickListener {
                INSERT = true
                findNavController().navigate(R.id.action_taskFragment_to_addCategoryFragment)
            }
        }

        activity?.onBackPressedDispatcher?.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    activity!!.finish()
                }
            })

        // Inflate the layout for this fragment
        return binding.root
    }

    private fun taksTitleClicked(taskEntry: TaskEntry) {
        val builder = AlertDialog.Builder(requireActivity())

        with(builder)
        {
            setTitle("Taks anda")
            setMessage(taskEntry.title)
            show()
        }
    }

    private fun taksRadioClicked(taskEntry: TaskEntry) {
        activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setTitle(taskEntry.title)
            builder.setMessage("Apakah taks sudah selesai?")
            builder.setCancelable(false)
            builder.setPositiveButton("Iya") { dialog, _ ->
                viewModel.onTaskCheckedChanged(taskEntry, true)
                dialog.dismiss()
            }
            builder.setNegativeButton("Belum") { dialog, _ ->
                viewModel.onTaskCheckedChanged(taskEntry, false)
                dialog.dismiss()
            }
            val dialog = builder.create()
            dialog.show()
            dialog.getButton(AlertDialog.BUTTON_NEGATIVE).isAllCaps = false
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).isAllCaps = false
            dialog.getButton(AlertDialog.BUTTON_NEUTRAL).isAllCaps = false
        }
    }

    private fun listItemClicked(categoryData: CategoryData) {
        myGlobalVarIdCat = categoryData.category_id
        myGlobalVarTitleCat = categoryData.category_name
        findNavController().navigate(R.id.action_taskFragment_to_taksByCategoryFragment)
    }

    private fun listItemLongClicked(categoryData: CategoryData): Boolean {
        activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setTitle(categoryData.category_name)
            builder.setMessage("Select action")
            builder.setPositiveButton("Update") { dialog, _ ->
                dialog.dismiss()
                myGlobalVarIdCat = categoryData.category_id
                myGlobalVarTitleCat = categoryData.category_name
                INSERT = false
                findNavController().navigate(R.id.action_taskFragment_to_addCategoryFragment)
            }
            builder.setNegativeButton("Delete") { dialog, _ ->
                dialog.dismiss()
                categoryViewModel.callDelete(categoryData)

                TODO() // masih error jika category di delete
                viewModel.deleteByCatId(categoryData.category_id)
            }
            builder.setNeutralButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
            val dialog = builder.create()
            dialog.show()
            dialog.getButton(AlertDialog.BUTTON_NEGATIVE).isAllCaps = false
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).isAllCaps = false
            dialog.getButton(AlertDialog.BUTTON_NEUTRAL).isAllCaps = false
        }
        return true
    }
}