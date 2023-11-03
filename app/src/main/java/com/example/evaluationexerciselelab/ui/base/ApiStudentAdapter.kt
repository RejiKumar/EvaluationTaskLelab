package com.example.evaluationexerciselelab.ui.base

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.example.evaluationexerciselelab.R
import com.example.evaluationexerciselelab.data.model.ApiStudentsItem

class ApiStudentAdapter(
    private val students: ArrayList<ApiStudentsItem>,
    val itemClickListener: (ApiStudentsItem)->Unit
) : RecyclerView.Adapter<ApiStudentAdapter.DataViewHolder>() {

    class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(student: ApiStudentsItem) {
            itemView.findViewById<AppCompatTextView>(R.id.tvStudentName).text =
                student.firstName.toString() + " " + student.lastName.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        DataViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_layout, parent,
                false
            )
        )

    override fun getItemCount(): Int = students.size

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.bind(students[position])
        holder.itemView.setOnClickListener { itemClickListener(students.get(position)) }

    }

    fun addData(list: List<ApiStudentsItem>) {
        students.addAll(list)
    }
}
