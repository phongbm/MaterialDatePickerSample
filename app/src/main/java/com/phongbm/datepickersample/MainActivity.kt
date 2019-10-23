package com.phongbm.datepickersample

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.util.TypedValue
import androidx.annotation.AttrRes
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.MaterialDatePicker
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnShowDatePickerDialog.setOnClickListener {
            showDatePickerDialog()
        }
    }

    @SuppressLint("PrivateResource")
    private fun showDatePickerDialog() {
        val dialogTheme = resolveOrThrow(this, R.attr.materialCalendarTheme)

        val minDate = Calendar.getInstance().apply {
            set(2019, 10, 5) // 2019-11-05
        }.timeInMillis

        val maxDate = Calendar.getInstance().apply {
            set(2019, 11, 25) // 2019-12-25
        }.timeInMillis

        val calendarConstraints = CalendarConstraints.Builder()
                .setStart(minDate)
                .setEnd(maxDate)
                .setValidator(MinMaxDateValidator(minDate, maxDate))
                .build()

        val dialog = MaterialDatePicker.Builder.dateRangePicker()
                .setTheme(dialogTheme)
                .setTitleText("Select Date Range")
                .setCalendarConstraints(calendarConstraints)
                .build()
        dialog.show(supportFragmentManager, null)
    }

    private fun resolveOrThrow(context: Context, @AttrRes attributeResId: Int): Int {
        val typedValue = TypedValue()
        if (context.theme.resolveAttribute(attributeResId, typedValue, true)) {
            return typedValue.data
        }
        throw IllegalArgumentException(context.resources.getResourceName(attributeResId))
    }

    data class MinMaxDateValidator(var minDate: Long = 0,
                                   var maxDate: Long = 0) : CalendarConstraints.DateValidator {
        companion object CREATOR : Parcelable.Creator<MinMaxDateValidator> {
            override fun createFromParcel(parcel: Parcel): MinMaxDateValidator {
                return MinMaxDateValidator()
            }

            override fun newArray(size: Int): Array<MinMaxDateValidator?> {
                return arrayOfNulls(size)
            }
        }

        override fun isValid(date: Long) = date in minDate..maxDate

        override fun writeToParcel(parcel: Parcel, flags: Int) {
        }

        override fun describeContents(): Int {
            return 0
        }
    }

}