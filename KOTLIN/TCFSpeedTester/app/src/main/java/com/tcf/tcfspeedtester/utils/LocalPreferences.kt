package com.tcf.tcfspeedtester.utils

import android.content.Context
import android.preference.PreferenceManager
import com.tcf.tcfspeedtester.utils.LocalPreferences.AppKeys.PREF

object LocalPreferences
{
    fun put(context: Context, key: String, value: String) {
        val pref = context.getSharedPreferences(PREF, Context.MODE_PRIVATE)
        val editor = pref.edit()
        editor.putString(key, value).apply()
    }

    fun put(context: Context, key: String, value: Int) {
        val pref = context.getSharedPreferences(PREF, Context.MODE_PRIVATE)
        val editor = pref.edit()
        editor.putInt(key, value).apply()
    }

    fun put(context: Context, key: String, value: Boolean) {
        val pref = context.getSharedPreferences(PREF, Context.MODE_PRIVATE)
        val editor = pref.edit()
        editor.putBoolean(key, value).apply()
    }

    fun getBoolean(context: Context, key: String): Boolean {
        val pref = context.getSharedPreferences(PREF, Context.MODE_PRIVATE)
        return pref.getBoolean(key, false)
    }

    fun getString(context: Context, key: String): String? {
        val pref = context.getSharedPreferences(PREF, Context.MODE_PRIVATE)
        return pref.getString(key, null)
    }

    fun getInt(context: Context, key: String): Int {
        val pref = context.getSharedPreferences(PREF, Context.MODE_PRIVATE)
        return pref.getInt(key, 0)
    }

    fun getStringPref(context: Context, key: String, defaultValue: String): String {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        return sharedPreferences.getString(key, defaultValue) ?: defaultValue
    }

    fun getIntPref(context: Context, key: String, defaultValue: Int): Int {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        return sharedPreferences.getInt(key, defaultValue)
    }

    object AppKeys {

        const val PREF = "PREF"
    }

    object KeyValue{

        const val upSpeedKey = "uploadSpeed"
        const val downSpeedKey = "downloadSpeed"
        const val isLogin = "isLogin"
        const val SpeedTest = "SpeedTest"
    }
}
