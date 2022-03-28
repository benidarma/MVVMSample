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
import com.amel.mvvmsample.adapter.TaskRecyclerViewAdapter
import com.amel.mvvmsample.databinding.FragmentTaksByCategoryBinding
import com.amel.mvvmsample.model.TaskEntry
import com.amel.mvvmsample.ui.fragment.add.Cons
import com.amel.mvvmsample.util.Constant.myGlobalVarIdCat
import com.amel.mvvmsample.util.Constant.myGlobalVarIdTaks
import com.amel.mvvmsample.util.Constant.myGlobalVarTimeTaks
import com.amel.mvvmsample.util.Constant.myGlobalVarTitleCat
import com.amel.mvvmsample.util.Constant.myGlobalVarTitleTaks
import com.amel.mvvmsample.viewmodel.TaskViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TaksByCategoryFragment : Fragment(R.layout.fragment_taks_by_category) {

    private val viewModel: TaskViewModel by viewModels()
    private lateinit var taksAdapter: TaskRecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentTaksByCategoryBinding.inflate(inflater)

        binding.lifecycleOwner = this

        val id_cat = myGlobalVarIdCat;
        val title_cat = myGlobalVarTitleCat

        taksAdapter = TaskRecyclerViewAdapter({ selectedItem: TaskEntry ->
            taksTitleClicked(selectedItem)
        }, { selectedItem: TaskEntry ->
            taksRadioClicked(selectedItem)
        })
        viewModel.getAllTaksByCat(id_cat).observe(viewLifecycleOwner) {
            taksAdapter.setList(it)
        }
        viewModel.message.observe(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let { its ->
                Toast.makeText(activity, its, Toast.LENGTH_LONG).show()
            }
        }

        binding.apply {

            binding.recyclerView.adapter = taksAdapter

            floatingActionButton.setOnClickListener {
                Cons.INSERT = true
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

    private fun taksTitleClicked(taskEntry: TaskEntry) {
        activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setTitle(taskEntry.title)
            builder.setMessage("Select action")
            builder.setPositiveButton("Update") { dialog, _ ->
                dialog.dismiss()
                myGlobalVarIdTaks = taskEntry.id
                myGlobalVarTitleTaks = taskEntry.title
                myGlobalVarTimeTaks = taskEntry.time
                Cons.INSERT = false
                findNavController().navigate(R.id.action_taksByCategoryFragment_to_addFragment)
            }
            builder.setNegativeButton("Delete") { dialog, _ ->
                dialog.dismiss()
                viewModel.callDelete(taskEntry)
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
}