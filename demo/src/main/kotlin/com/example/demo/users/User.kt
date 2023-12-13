package com.example.demo.users

import jakarta.persistence.*

@Entity
@Table(name = "users")
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int,
    val username: String,
    val email: String,
    val phone: String,
    val pfp: Int
)