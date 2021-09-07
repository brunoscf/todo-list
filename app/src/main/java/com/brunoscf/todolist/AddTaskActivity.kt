package com.brunoscf.todolist

import android.app.Activity
import android.database.DatabaseErrorHandler
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import com.brunoscf.todolist.model.Task
import com.google.android.material.button.MaterialButton
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.text.SimpleDateFormat
import java.util.*

class AddTaskActivity : AppCompatActivity() {
    lateinit var etTitle: TextInputEditText
    lateinit var etDate: TextInputEditText
    lateinit var etTime: TextInputEditText
    lateinit var btnCancel: MaterialButton
    lateinit var btnAddTask: MaterialButton
    lateinit var oldTask: Task

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_task)

        initData()

        if (intent.hasExtra(TASK_ID)) {
            val taskName = intent.getStringExtra(TASK_ID)
            oldTask = DataSource.list.find { it.name == taskName }!!
            etTitle.setText(oldTask?.name)
            etDate.setText(oldTask?.date)
            etTime.setText(oldTask?.time)
            
        }

        initListeners()
    }

    fun initData(){
        etTitle = findViewById(R.id.it_title)
        etDate = findViewById(R.id.it_date)
        etTime = findViewById(R.id.it_time)
        btnCancel = findViewById(R.id.btn_cancel)
        btnAddTask = findViewById(R.id.btn_add_task)
    }

    fun initListeners(){
        etDate.setOnClickListener {
            val datePicker = MaterialDatePicker.Builder
                    .datePicker()
                    .build()

            datePicker.addOnPositiveButtonClickListener { date ->
                val timeZone = TimeZone.getDefault()
                val offset = timeZone.getOffset(Date().time) * (-1)
                val formattedDate = SimpleDateFormat("dd/MM/yy", Locale("pt", "BR")).format(Date(date + offset))
                etDate.setText(formattedDate)
            }

            datePicker.show(supportFragmentManager, "DATE_PICKER_TAG")
        }

        etTime.setOnClickListener {
            val timePicker = MaterialTimePicker.Builder()
                    .setTimeFormat(TimeFormat.CLOCK_24H)
                    .build()

            timePicker.addOnPositiveButtonClickListener {
                val formattedTime = "${timePicker.hour.toString().padStart(2, '0')}:${timePicker.minute.toString().padStart(2, '0')}"
                etTime.setText(formattedTime)
            }

            timePicker.show(supportFragmentManager, null)
        }

        btnCancel.setOnClickListener {
            finish()
        }

        btnAddTask.setOnClickListener {
            val newTask = Task(etTitle.text.toString(), etDate.text.toString(), etTime.text.toString())

            if (intent.hasExtra(TASK_ID)) {
                val index = DataSource.findByName(oldTask.name)
                DataSource.list[index] = newTask
            } else {
                DataSource.list.add(newTask)
            }
            finish()
        }
    }

    companion object{
        const val TASK_ID = "task_id"
    }
}