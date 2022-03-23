package com.mvvm.todo.ui.fragment.add

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.amel.mvvmsample.R
import com.amel.mvvmsample.databinding.FragmentAddCategoryBinding
import com.mvvm.todo.model.CategoryTask
import com.mvvm.todo.viewmodel.CategoryViewModel

class AddCategoryFragment : Fragment() {

    private val viewCategoryModel: CategoryViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentAddCategoryBinding.inflate(inflater)

        binding.apply {
            btnSimpan.setOnClickListener {
                if (TextUtils.isEmpty(edtNamaKelas.text)) {
                    Toast.makeText(
                        requireContext(),
                        "Please Enter the Category of the Task",
                        Toast.LENGTH_SHORT
                    ).show()
                    return@setOnClickListener
                }

                val title = edtNamaKelas.text.toString()
                val categoryTask = CategoryTask(
                    0,
                    title
                )
                viewCategoryModel.insertData(categoryTask)
                Toast.makeText(requireContext(), "Successfully Added!", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_addCategoryFragment_to_taskFragment)
            }
        }

        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().navigate(R.id.action_addCategoryFragment_to_taskFragment)
            }
        })

        return binding.root
    }
}