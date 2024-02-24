package com.tcf.workpro.presentation.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tcf.workpro.R
import com.tcf.workpro.data.model.EmployeeModel
import com.tcf.workpro.databinding.HomeAttendanceSummaryViewBinding
import com.tcf.workpro.databinding.HomeLeaveRequestViewBinding
import com.tcf.workpro.databinding.HomeTopPerformerViewBinding

class AttendanceSummaryAdapter (
    val context: Context
//    private val list:List<EmployeeModel>
): RecyclerView.Adapter<AttendanceSummaryAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = HomeAttendanceSummaryViewBinding.bind(view)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.home_attendance_summary_view, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        val data = list[position]
        with(holder){
            when(position){
                0->{

                }
                1->{

                }
                2->{

                }
            }
        }
    }

    override fun getItemCount(): Int = 3


}