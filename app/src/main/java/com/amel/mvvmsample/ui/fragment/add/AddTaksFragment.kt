package com.amel.mvvmsample.ui.fragment.add

import android.app.Activity
import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.app.TimePickerDialog
import android.content.Context
import android.os.Bundle
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.amel.mvvmsample.R
import com.amel.mvvmsample.databinding.FragmentAddTaksBinding
import com.amel.mvvmsample.util.Constant
import com.amel.mvvmsample.viewmodel.TaskViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class AddTaksFragment : Fragment() {

    private val viewModel: TaskViewModel by viewModels()
    private var datePicker: DatePickerDialog? = null
    private var timePicker: TimePickerDialog? = null
    private var datePick: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentAddTaksBinding.inflate(inflater)

        setupDateTime(activity)

        binding.apply {
            if (!Cons.INSERT) {
                etEnterTask.setText(Constant.myGlobalVarTitleTaks)
                datePick = Constant.myGlobalVarTimeTaks
            }
            etEnterTask.addTextChangedListener {
                viewModel.taTitle = it.toString()
            }
            if (etEnterTask.text.isBlank()) {
                saveTaks.text = "Add"
            } else {
                saveTaks.text = "Edit"
            }
            timeSet.setOnClickListener {
                hideKeyboard()
                datePicker?.show()
            }
            saveTaks.setOnClickListener {
                when {
                    viewModel.onSaveClick() -> findNavController().navigate(R.id.action_addFragment_to_taksByCategoryFragment)
                }
            }
        }

        viewModel.message.observe(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let { its ->
                Toast.makeText(activity, its, Toast.LENGTH_LONG).show()
            }
        }

        activity?.onBackPressedDispatcher?.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    findNavController().navigate(R.id.action_addFragment_to_taksByCategoryFragment)
                }
            })

        return binding.root
    }

    private fun Fragment.hideKeyboard() {
        view?.let { activity?.hideKeyboard(it) }
    }

    private fun Context.hideKeyboard(view: View) {
        val inputMethodManager =
            getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    private fun setupDateTime(application: FragmentActivity?) {
        val calendar: Calendar = Calendar.getInstance()
        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

        try {
            val current = simpleDateFormat.format(Date())
            calendar.time = simpleDateFormat.parse(current)
        } catch (e: ParseException) {

        }

        datePicker = application?.let {
            DatePickerDialog(
                it,
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
    }

    private fun setDateTimeFieldTimePicker(
        year: Int, monthOfYear: Int, dayOfMonth: Int, dayOfHOUR: Int, dayOfMINUTE: Int,
        dayOfSECOND: Int, application: FragmentActivity?
    ) {
        val newDate = Calendar.getInstance()
        timePicker = TimePickerDialog(
            application,
            { _, hourOfDay, minuteOfDay ->
                newDate[year, monthOfYear, dayOfMonth, hourOfDay] = minuteOfDay
                val startDate = newDate.time
                val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                datePick = simpleDateFormat.format(startDate)
                viewModel.taTime = datePick
            }, dayOfHOUR, dayOfMINUTE, DateFormat.is24HourFormat(application)
        )
        timePicker!!.show()
    }
}