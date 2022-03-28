package com.amel.mvvmsample.ui.fragment.add

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.amel.mvvmsample.R
import com.amel.mvvmsample.databinding.FragmentAddCategoryBinding
import com.amel.mvvmsample.ui.fragment.add.Cons.INSERT
import com.amel.mvvmsample.util.Constant.myGlobalVarTitleCat
import com.amel.mvvmsample.viewmodel.CategoryViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddCategoryFragment : Fragment() {

    private val viewCategoryModel: CategoryViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentAddCategoryBinding.inflate(inflater)

        binding.apply {
            if (!INSERT) {
                etNameCat.setText(myGlobalVarTitleCat)
            }
            etNameCat.addTextChangedListener {
                viewCategoryModel.catName = it.toString()
            }
            if (etNameCat.text.isBlank()) {
                btnSaveCat.text = "Add"
            } else {
                btnSaveCat.text = "Edit"
            }
            btnSaveCat.setOnClickListener {
                when {
                    viewCategoryModel.onSaveClick() -> findNavController().navigate(R.id.action_addCategoryFragment_to_taskFragment)
                }
            }
        }

        viewCategoryModel.message.observe(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let { its ->
                Toast.makeText(activity, its, Toast.LENGTH_LONG).show()
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

object Cons {
    var INSERT = false
}