package com.kim344.mvvmtodo.view

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
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

    private lateinit var mTodoViewModel: TodoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initAddButton()
        initRecyclerView()
    }

    private fun initViewModel() {
        mTodoViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(application)
            .create(TodoViewModel::class.java)
        mTodoViewModel.getTodoList().observe(this, androidx.lifecycle.Observer {
            mTodoListAdapter.setTodoItems(it)
        })
    }

    private fun initRecyclerView() {

        mTodoListAdapter = TodoListAdapter().apply {
            listener = object : TodoListAdapter.OnTodoItemClickListener {
                override fun onTodoItemClick(position: Int) {
                    openModifyTodoDialog(getItem(position))
                }

                override fun onTodoItemLongClick(position: Int) {
                    openDeleteTodoDialog(getItem(position))
                }

            }
        }

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

                val todoModel = TodoModel(null, title, description, createdDate)
                mTodoViewModel.insertTodo(todoModel)
            }
            .setNegativeButton("취소", null)
            .create()
        dialog.show()
    }

    private fun openModifyTodoDialog(todoModel: TodoModel) {
        val dialogView = layoutInflater.inflate(R.layout.dialog_add_todo, null)
        dialogView.et_todo_title.setText(todoModel.title)
        dialogView.et_todo_description.setText(todoModel.description)

        val dialog = AlertDialog.Builder(this).setTitle("수정하기").setView(dialogView)
            .setPositiveButton(
                "확인"
            ) { _, _ ->
                val title = dialogView.et_todo_title.text.toString()
                val description =
                    dialogView.et_todo_description.text.toString()
                todoModel.description = description
                todoModel.title = title
                mTodoViewModel.updateTodo(todoModel)
            }.setNegativeButton("취소", null).create()
        dialog.show()
    }

    private fun openDeleteTodoDialog(todoModel: TodoModel){
        val dialog = AlertDialog.Builder(this)
            .setTitle("삭제하기")
            .setMessage("확인을 누르면 삭제됩니다.")
            .setPositiveButton("확인") { _, _ ->
                mTodoViewModel.deleteTodo(todoModel)
            }
            .setNegativeButton("취소",null)
            .create()

        dialog.show()
    }
}