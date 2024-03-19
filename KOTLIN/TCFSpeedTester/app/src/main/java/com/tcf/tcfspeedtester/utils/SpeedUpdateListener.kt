package com.tcf.tcfspeedtester.utils

interface SpeedUpdateListener {
    fun updateSpeeds(uploadSpeed: Double, downloadSpeed: Double, uploadProgress: Int, downloadProgress: Int)
}