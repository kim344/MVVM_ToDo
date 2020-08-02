package com.kim344.mvvmtodo.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.kim344.mvvmtodo.model.TodoModel
import com.kim344.mvvmtodo.repository.TodoRepository

class TodoViewModel(application: Application) : AndroidViewModel(application) {
    private val mTodoRepository : TodoRepository
    private val mTodoItems : LiveData<List<TodoModel>>

    init {
        mTodoRepository = TodoRepository(application)
        mTodoItems = mTodoRepository.getTodoList()
    }

    fun getTodoList() : LiveData<List<TodoModel>> {
        return mTodoItems
    }

    fun insertTodo(todoModel: TodoModel) {
        mTodoRepository.insertTodo(todoModel)
    }

}