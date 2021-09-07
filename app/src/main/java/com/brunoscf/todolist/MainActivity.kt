package com.brunoscf.todolist

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import com.brunoscf.todolist.model.Task
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText

class MainActivity : AppCompatActivity() {
    var listTasks = mutableListOf<Task>()

    lateinit var rvTasks: RecyclerView
    var adapter = TaskAdapter(DataSource.list)
    lateinit var btnAddTask: FloatingActionButton


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initComponents()
        initListeners()
    }

    fun initComponents(){
        rvTasks = findViewById(R.id.rv_tasks)
        btnAddTask = findViewById(R.id.add_task)

        rvTasks.adapter = adapter

    }

    fun initListeners(){
        btnAddTask.setOnClickListener {
            val intent = Intent(this, AddTaskActivity::class.java)
            startActivityForResult(intent, ADD_TASK_TAG)
        }

        adapter.editListener = {
            val intent = Intent(this, AddTaskActivity::class.java)
            intent.putExtra(AddTaskActivity.TASK_ID, it.name)
            startActivityForResult(intent, ADD_TASK_TAG)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == ADD_TASK_TAG){
            adapter.updateList(DataSource.list)
        }
    }

    companion object{
        const val ADD_TASK_TAG = 120
    }
}