package com.tcf.googlemapsproject

import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.Toast

fun toast(context: Context,message:String){
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}

fun gotoActivity(context: Context, activity:Class<*>){
    val intent = Intent(context,activity)
    context.startActivity(intent)
}

fun View.click(it: (View) -> Unit) {
    this.setOnClickListener(it)
}

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.invisible() {
    this.visibility = View.INVISIBLE
}

fun View.gone() {
    this.visibility = View.GONE
}