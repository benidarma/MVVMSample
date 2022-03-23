package com.mvvm.todo.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.amel.mvvmsample.R
import com.amel.mvvmsample.databinding.FragmentTaksByCategoryBinding
import com.mvvm.todo.adapter.TaskAdapter
import com.mvvm.todo.model.TaskEntry
import com.mvvm.todo.util.Constant.myGlobalVarIdCat
import com.mvvm.todo.util.Constant.myGlobalVarTitleCat
import com.mvvm.todo.viewmodel.TaskViewModel

class TaksByCategoryFragment : Fragment(R.layout.fragment_taks_by_category), TaskAdapter.OnItemClickListener {

    private val viewModel: TaskViewModel by viewModels()
    private lateinit var adapter: TaskAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentTaksByCategoryBinding.inflate(inflater)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        val id_cat = myGlobalVarIdCat;
        val title_cat = myGlobalVarTitleCat

        adapter = TaskAdapter(this)
        viewModel.getAllTaksByCat(id_cat).observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

        binding.apply {

            binding.recyclerView.adapter = adapter

            floatingActionButton.setOnClickListener {
                findNavController().navigate(R.id.action_taksByCategoryFragment_to_addFragment)
            }
        }

        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().navigate(R.id.action_taksByCategoryFragment_to_taskFragment)
            }
        })

        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onItemClick(taskEntry: TaskEntry) {
        viewModel.onTaskSelected(taskEntry)

        val builder = context?.let { AlertDialog.Builder(it) }
        builder?.setTitle(taskEntry.title)
        builder?.setMessage("Silakan Pilih Aksi")

        builder?.setPositiveButton("Hapus") { dialog, which ->
            viewModel.delete(taskEntry)
            dialog.cancel()
        }

        /*builder?.setNegativeButton("Edit") { dialog, which ->
            findNavController().navigate(R.id.action_taksByCategoryFragment_to_addFragment)
        }*/

        builder?.setNeutralButton("Cancel") { dialog, which ->
            dialog.cancel()
        }
        builder?.show()
    }

    override fun onCheckBoxClick(taskEntry: TaskEntry, isChecked: Boolean) {
        viewModel.onTaskCheckedChanged(taskEntry, isChecked)
    }
}