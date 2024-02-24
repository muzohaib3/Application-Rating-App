package com.tcf.workpro.presentation.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tcf.workpro.R
import com.tcf.workpro.data.model.EmployeeModel
import com.tcf.workpro.databinding.HomeLeaveRequestViewBinding
import com.tcf.workpro.databinding.HomeTopPerformerViewBinding

class HomeLeaveRequestAdapter (
    val context: Context
//    private val list:List<EmployeeModel>
): RecyclerView.Adapter<HomeLeaveRequestAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = HomeLeaveRequestViewBinding.bind(view)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.home_leave_request_view, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        val data = list[position]
        with(holder){

        }
    }

    override fun getItemCount(): Int = 3


}