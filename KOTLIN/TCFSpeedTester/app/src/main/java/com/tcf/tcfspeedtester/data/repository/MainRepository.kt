package com.tcf.tcfspeedtester.data.repository

import com.tcf.tcfspeedtester.data.database.dao.AppDao
import com.tcf.tcfspeedtester.data.database.model.NetworkModel

class MainRepository(private val appDao: AppDao) {

//    suspend fun insertNetworkData(downloadSpeed: Double, uploadSpeed: Double){
//        val speedData = NetworkModel(0.0,0.0,downloadSpeed,uploadSpeed)
//        appDao.insertData(speedData)
//    }

}