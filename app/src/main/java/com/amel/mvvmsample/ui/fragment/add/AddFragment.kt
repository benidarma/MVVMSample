package com.mvvm.todo.ui.fragment.add

import android.annotation.SuppressLint
import android.app.Activity
import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.app.TimePickerDialog
import android.app.TimePickerDialog.OnTimeSetListener
import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.amel.mvvmsample.R
import com.amel.mvvmsample.databinding.FragmentAddBinding
import com.mvvm.todo.model.TaskEntry
import com.mvvm.todo.util.Constant.myGlobalVarIdCat
import com.mvvm.todo.viewmodel.TaskViewModel
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class AddFragment : Fragment() {

    private val viewModel: TaskViewModel by viewModels()
    private var datePicker: DatePickerDialog? = null
    private var timePicker: TimePickerDialog? = null
    private var datePick: String = ""

    @SuppressLint("ShowToast", "NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentAddBinding.inflate(inflater)

        setupDateTime(this)

        binding.apply {
            timeBut.setOnClickListener {
                hideKeyboard()
                datePicker?.show()
            }
            setDate.setOnClickListener {
                if (TextUtils.isEmpty(etEnterTask.text)) {
                    Toast.makeText(
                        requireContext(),
                        "Please Enter the Title of the Task",
                        Toast.LENGTH_SHORT
                    ).show()
                    return@setOnClickListener
                }
                if (datePick == "") {
                    Toast.makeText(
                        requireContext(),
                        "Please select date time",
                        Toast.LENGTH_SHORT
                    ).show()
                    return@setOnClickListener
                }

                val title = etEnterTask.text.toString()
                val category = myGlobalVarIdCat
                val taskEntry = TaskEntry(
                    0,
                    title,
                    category,
                    datePick
                )
                viewModel.insertData(taskEntry)
                Toast.makeText(requireContext(), "Successfully Added! $datePick", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_addFragment_to_taksByCategoryFragment)
            }
        }

        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().navigate(R.id.action_addFragment_to_taksByCategoryFragment)
            }
        })

        return binding.root
    }

    fun Fragment.hideKeyboard() {
        view?.let { activity?.hideKeyboard(it) }
    }

    fun Context.hideKeyboard(view: View) {
        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    fun setupDateTime(application: AddFragment) {
        val calendar: Calendar = Calendar.getInstance()
        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

        try {
            val current = simpleDateFormat.format(Date())
            calendar.time = simpleDateFormat.parse(current)
        } catch (e: ParseException) {

        }

        datePicker = DatePickerDialog(application.requireActivity(),
            OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                setDateTimeFieldTimePicker(
                    year, monthOfYear, dayOfMonth, calendar.get(Calendar.HOUR_OF_DAY),
                    calendar.get(Calendar.MINUTE), calendar.get(Calendar.SECOND),
                    application
                )
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
    }


    private fun setDateTimeFieldTimePicker(
        year: Int, monthOfYear: Int, dayOfMonth: Int, dayOfHOUR: Int, dayOfMINUTE: Int,
        dayOfSECOND: Int, application: AddFragment
    ) {
        var newDate = Calendar.getInstance()
        timePicker = TimePickerDialog(application.requireActivity(),
            OnTimeSetListener { view, hourOfDay, minuteOfDay ->
                newDate[year, monthOfYear, dayOfMonth, hourOfDay] = minuteOfDay
                val startDate = newDate.time
                val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                datePick = simpleDateFormat.format(startDate)
            }, dayOfHOUR, dayOfMINUTE, DateFormat.is24HourFormat(application.requireActivity())
        )
        timePicker!!.show()
    }
}