package com.example.todo_api

class User (
    var id: Int = -1,
    var name: String = "",
    var email: String = "",
): Any()

class Todo(
    var id: Int = -1,
    var userId: Int =-1,
    var title: String = "",
    var completed: Boolean = false,
)

class Post(
    var id: Int = -1,
    var userId: Int = -1,
    var title: String = "",
    var body: String = "",
)