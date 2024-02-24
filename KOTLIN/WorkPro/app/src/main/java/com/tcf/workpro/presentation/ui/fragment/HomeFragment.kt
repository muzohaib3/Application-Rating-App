package com.tcf.workpro.presentation.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.tcf.workpro.R
import com.tcf.workpro.databinding.FragmentHomeBinding
import com.tcf.workpro.presentation.ui.adapter.AttendanceSummaryAdapter
import com.tcf.workpro.presentation.ui.adapter.HomeLeaveRequestAdapter
import com.tcf.workpro.presentation.ui.adapter.TopPerformerAdapter

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        initViews()
        return binding.root
    }

    private fun initViews(){

        binding.rvTopPerformer.apply {
            adapter = TopPerformerAdapter(this.context)
            layoutManager = LinearLayoutManager(this.context)
        }
        binding.rvLeaveRequest.apply {
            adapter = HomeLeaveRequestAdapter(this.context)
            layoutManager = GridLayoutManager(this.context,2,LinearLayoutManager.VERTICAL,false)
        }
        binding.rvAttendanceSummary.apply {
            adapter = AttendanceSummaryAdapter(this.context)
            layoutManager = LinearLayoutManager(this.context,LinearLayoutManager.HORIZONTAL,false)

        }
    }
}