package com.tcf.todo

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform