package com.example.demo.models

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "comments")
data class Comment(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int,
    val iduser: Int,
    val posted: LocalDateTime,
    val content: String, 
    val moderated: Boolean, 
    val identry: Int,
    val idanscomment: Int
)
