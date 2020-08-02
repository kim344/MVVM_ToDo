package com.kim344.mvvmtodo.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.kim344.mvvmtodo.db.TodoDAO
import com.kim344.mvvmtodo.db.TodoDatabase
import com.kim344.mvvmtodo.model.TodoModel

class TodoRepository(application: Application) {
    private var mTodoDatabase : TodoDatabase
    private var mTodoDAO : TodoDAO
    private var mTodoItems : LiveData<List<TodoModel>>

    init {
        mTodoDatabase = TodoDatabase.getInstance(application)
        mTodoDAO = mTodoDatabase.todoDao()
        mTodoItems = mTodoDAO.getTodoList()
    }

    fun getTodoList() : LiveData<List<TodoModel>> {
        return mTodoItems
    }

    fun insertTodo(todoModel: TodoModel) {
        Thread(Runnable {
            mTodoDAO.insertTodo(todoModel)
        }).start()
    }
}