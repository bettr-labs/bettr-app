package org.example.bettr

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform