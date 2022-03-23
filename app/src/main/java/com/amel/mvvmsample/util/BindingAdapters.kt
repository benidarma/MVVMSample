package com.mvvm.todo.util

import android.widget.CheckBox
import androidx.databinding.BindingAdapter
import com.amel.mvvmsample.R

@BindingAdapter("android:setColorCategory")
fun setColorCategory(view: CheckBox, category: Int) {
    when (category) {
        0 -> {
            view.setBackgroundResource(R.drawable.ic_baseline_filter_1_24)
        }
        1 -> {
            view.setBackgroundResource(R.drawable.ic_baseline_filter_2_24)
        }
        2 -> {
            view.setBackgroundResource(R.drawable.ic_baseline_filter_3_24)
        }
        3 -> {
            view.setBackgroundResource(R.drawable.ic_baseline_filter_1_24)
        }
        4 -> {
            view.setBackgroundResource(R.drawable.ic_baseline_filter_2_24)
        }
        5 -> {
            view.setBackgroundResource(R.drawable.ic_baseline_filter_2_24)
        }
        6 -> {
            view.setBackgroundResource(R.drawable.ic_baseline_filter_1_24)
        }
        7 -> {
            view.setBackgroundResource(R.drawable.ic_baseline_filter_3_24)
        }
    }
}
