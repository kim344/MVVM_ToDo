package com.kim344.mvvmtodo.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.kim344.mvvmtodo.model.TodoModel

@Dao
interface TodoDAO {

    @Query("SELECT * FROM Todo ORDER BY createdDate ASC")
    fun getTodoList() : LiveData<List<TodoModel>>

    @Insert
    fun insertTodo(todoModel: TodoModel)

    @Update
    fun updateTodo(todoModel: TodoModel)

    @Delete
    fun deleteTodo(todoModel: TodoModel)
}