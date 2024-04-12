package com.example.psysupapplication

data class User (
    val id: Int,
    val username: String,
    val email: String,
    val phone: String,
    val password: String,
    val pfp: Int,
    val gender: Boolean,
    val birthday: String,
    val topics: List<Int>,
    val role: String
)

data class Entry(
    var id: Int,
    var iduser: Int,
    var posted: String,
    var content: String,
    var moderated: Boolean,
    var public: Boolean,
    var topics: List<Int>
)

data class Comment(
    val id: Int,
    val iduser: Int,
    val posted: String,
    val content: String,
    val moderated: Boolean,
    val identry: Int,
    val idanscomment: Int
)