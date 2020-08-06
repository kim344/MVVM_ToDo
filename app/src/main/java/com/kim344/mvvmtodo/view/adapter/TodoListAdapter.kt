package com.kim344.mvvmtodo.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.kim344.mvvmtodo.R
import com.kim344.mvvmtodo.model.TodoModel
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.item_todo.view.*
import java.text.SimpleDateFormat
import java.util.*

class TodoListAdapter() :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var todoItems : List<TodoModel> = listOf()
    var listener : OnTodoItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_todo,parent,false)
        return TodoViewHolder(view, listener)
    }

    override fun getItemCount(): Int {
        return todoItems.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val todoModel = todoItems[position]

        val todoViewHolder = holder as TodoViewHolder
        todoViewHolder.bind(todoModel)
    }

    fun setTodoItems(todoItems : List<TodoModel>) {
        Observable.just(todoItems)
            .subscribeOn(AndroidSchedulers.mainThread())
            .observeOn(Schedulers.io())
            .map { DiffUtil.calculateDiff(TodoListDiffCallback(this.todoItems, todoItems)) }
            .subscribe({
                this.todoItems = todoItems
                it.dispatchUpdatesTo(this)
            },{
                // Handle Error
            })
    }

    fun getItem(position: Int) : TodoModel{
        return todoItems[position]
    }

    class TodoViewHolder(view: View, listener: OnTodoItemClickListener?): RecyclerView.ViewHolder(view) {

        private val title: TextView = view.tv_todo_title
        private val description: TextView = view.tv_todo_description
        private val createdDate: TextView = view.tv_todo_created_date

        init {
            view.setOnClickListener {
                listener?.onTodoItemClick(adapterPosition)
            }

            view.setOnLongClickListener {
                listener?.onTodoItemLongClick(adapterPosition)
                return@setOnLongClickListener true
            }
        }

        fun bind(todoModel: TodoModel) {

            title.text = todoModel.title
            description.text = todoModel.description
            createdDate.text = todoModel.createdDate.toDateString("yyyy.MM.dd HH:mm")
        }

        private fun Long.toDateString(format: String): String {
            val simpleDateFormat = SimpleDateFormat(format)
            return simpleDateFormat.format((Date(this)))
        }

    }

    interface OnTodoItemClickListener {
        fun onTodoItemClick(position: Int)
        fun onTodoItemLongClick(position: Int)
    }

}