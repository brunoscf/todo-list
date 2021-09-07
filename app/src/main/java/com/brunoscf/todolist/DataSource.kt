package com.brunoscf.todolist

import com.brunoscf.todolist.model.Task

object DataSource {
    var list: MutableList<Task> = mutableListOf()

    fun findByName(name: String) : Int {
        return list.indexOfFirst {
            it.name == name
        }
    }
}