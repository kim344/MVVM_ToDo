package com.kim344.mvvmtodo.view.adapter

import android.util.Log
import androidx.recyclerview.widget.DiffUtil
import com.kim344.mvvmtodo.model.TodoModel

class TodoListDiffCallback(private val oldTodoList : List<TodoModel>, private val newTodoList : List<TodoModel>) : DiffUtil.Callback() {
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldTodoList[oldItemPosition].id == newTodoList[newItemPosition].id
    }

    override fun getOldListSize(): Int {
        return oldTodoList.size
    }

    override fun getNewListSize(): Int {
        return newTodoList.size
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return newTodoList[newItemPosition].equals(oldTodoList[oldItemPosition])
    }
}