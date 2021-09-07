package com.brunoscf.todolist

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.brunoscf.todolist.model.Task

class TaskAdapter(var listTasks: MutableList<Task>) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {
    var editListener: (Task) -> Unit = {}
    fun updateList(list: MutableList<Task>){
        listTasks = list
        notifyDataSetChanged()
    }

    class TaskViewHolder(itemView: View) : ViewHolder(itemView){
        val tvName: TextView
        val tvDate: TextView
        val tvTime: TextView
        val ivMenu: ImageView

        init {
            tvName = itemView.findViewById(R.id.task_name)
            tvDate = itemView.findViewById(R.id.task_date)
            tvTime = itemView.findViewById(R.id.task_time)
            ivMenu = itemView.findViewById(R.id.btn_menu)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_task, parent, false)
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = listTasks[position]

        holder.tvName.text = task.name
        holder.tvDate.text = task.date
        holder.tvTime.text = task.time

        holder.ivMenu.setOnClickListener{
            showPopup(task, holder.ivMenu)
        }
    }

    override fun getItemCount(): Int {
        return listTasks.size
    }

    private fun showPopup(task: Task, ivMenu: ImageView) {
        val popupMenu = PopupMenu(ivMenu.context, ivMenu)
        popupMenu.menuInflater.inflate(R.menu.menu_popup, popupMenu.menu)
        popupMenu.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.edit -> {
                    editListener(task)
                    true
                }

                R.id.delete -> {
                    DataSource.list.remove(task)
                    notifyDataSetChanged()
                    true
                }

                else -> {
                    true
                }
            }
        }

        popupMenu.show()
    }
}