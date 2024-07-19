package com.example.movimientosbancarios.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.movimientosbancarios.data.Movement
import com.example.movimientosbancarios.data.MovementDAO
import com.example.movimientosbancarios.databinding.ActivityNewMovementBinding
import com.google.android.material.datepicker.MaterialDatePicker.Builder.datePicker
import java.util.Calendar


class NewMovementActivity : AppCompatActivity() {

    lateinit var binding: ActivityNewMovementBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewMovementBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.saveButton.setOnClickListener {
            val quantity = binding.quantityEditText.text.toString().toFloat()
            val calendar = Calendar.getInstance()
            calendar.set(Calendar.DAY_OF_MONTH, binding.datePicker.dayOfMonth)
            calendar.set(Calendar.MONTH, binding.datePicker.month)
            calendar.set(Calendar.YEAR, binding.datePicker.year)

            val date = calendar.timeInMillis

            val movement = Movement(-1, quantity, date)
            MovementDAO(this).insert(movement)

            finish()
        }
    }
}