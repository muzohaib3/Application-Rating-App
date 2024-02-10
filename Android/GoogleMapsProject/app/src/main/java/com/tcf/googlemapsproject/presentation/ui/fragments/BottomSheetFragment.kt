package com.tcf.googlemapsproject.presentation.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.tcf.googlemapsproject.R
import com.tcf.googlemapsproject.data.local.AppDB
import com.tcf.googlemapsproject.databinding.FragmentBottomSheetBinding

class BottomSheetFragment : BottomSheetDialogFragment() {
    private val db by lazy { AppDB.getFileDatabase(requireContext()) }
    private lateinit var binding: FragmentBottomSheetBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentBottomSheetBinding.inflate(inflater,container,false)
        initViews()
        return binding.root
    }

    private fun initViews(){

        val appRating = db.appDao().getAllRatingData()
        val fb = appRating.facebook_rate
        val whatsapp_rate = appRating.whatsapp_rate
        val insta_rate = appRating.insta_rate
        val youtube_rate = appRating.youtube_rate

        binding.facebookProgressBar.progress = fb.toInt()
        binding.whatsappProgressBar.progress = whatsapp_rate.toInt()
        binding.instaProgressBar.progress = insta_rate.toInt()
        binding.youtubeProgressBar.progress = youtube_rate.toInt()

    }
}