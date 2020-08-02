package com.kim344.mvvmtodo.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.kim344.mvvmtodo.R
import com.kim344.mvvmtodo.model.TodoModel
import com.kim344.mvvmtodo.view.adapter.TodoListAdapter
import com.kim344.mvvmtodo.viewmodel.TodoViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.dialog_add_todo.view.*
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var mTodoListAdapter: TodoListAdapter
    private val mTodoItems: ArrayList<TodoModel> = ArrayList()

    private lateinit var mTodoViewModel : TodoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initAddButton()
        initRecyclerView()
    }

    private fun initViewModel(){
        mTodoViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(application).create(TodoViewModel::class.java)
        mTodoViewModel.getTodoList().observe(this, androidx.lifecycle.Observer {
            mTodoListAdapter.setTodoItems(it)
        })
    }

    private fun initRecyclerView() {
        mTodoListAdapter = TodoListAdapter()
        rl_todo_list.run {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = mTodoListAdapter
        }
    }

    private fun initAddButton() {
        btn_add_todo.setOnClickListener {
            openAddTodoDialog()
        }
    }

    private fun openAddTodoDialog() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_add_todo, null)
        val dialog = AlertDialog.Builder(this)
            .setTitle("추가하기")
            .setView(dialogView)
            .setPositiveButton("확인") { _, _ ->
                val title = dialogView.et_todo_title.text.toString()
                val description = dialogView.et_todo_description.text.toString()
                val createdDate = Date().time

                val todoModel = TodoModel(null,title, description, createdDate)
                mTodoViewModel.insertTodo(todoModel)
            }
            .setNegativeButton("취소",null)
            .create()
        dialog.show()
    }
}