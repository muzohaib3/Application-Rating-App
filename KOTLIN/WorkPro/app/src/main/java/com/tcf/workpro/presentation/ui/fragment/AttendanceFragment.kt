package com.tcf.workpro.presentation.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tcf.workpro.R
import com.tcf.workpro.databinding.FragmentHomeBinding
import com.tcf.workpro.databinding.FragmentNotificationBinding

class AttendanceFragment : Fragment() {
    private lateinit var binding: FragmentNotificationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNotificationBinding.inflate(inflater, container, false)
        initViews()
        return binding.root
    }

    private fun initViews(){

    }

}