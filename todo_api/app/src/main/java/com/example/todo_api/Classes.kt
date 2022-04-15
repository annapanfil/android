package com.example.todo_api

class User (
    var id: Int = -2,
    var name: String = "",
    var email: String = "",
    var taskCtr: Int = 0,
    var postCtr: Int = 0,
)

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