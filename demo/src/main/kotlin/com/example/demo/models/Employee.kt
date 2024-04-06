package com.example.demo.models

import jakarta.persistence.*
import java.time.LocalDate

enum class Position {psychologist, moderator}

@Entity
@Table(name = "employee")
data class Employee(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int,
    val username: String,
    val name: String,
    val email: String,
    val phone: String,
    val password: String,
    val role: Position
)
