package com.example.coincapapp.utils

import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.formatter.ValueFormatter
import java.text.DecimalFormat

class DecimalValueFormatter : ValueFormatter() {
    private val format = DecimalFormat("#.###")

    override fun getPointLabel(entry: Entry?): String {
        return format.format(entry?.y ?: 0f)
    }

    override fun getAxisLabel(value: Float, axis: AxisBase?): String {
        return super.getAxisLabel(value, axis)
    }
}