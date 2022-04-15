package com.example.todo_api

import android.util.JsonReader
import com.example.intelligent_guess.DBHelper
import java.io.StringReader

fun readUser(reader: JsonReader): User{
    var name: String
    val user = User()

    reader.beginObject()
    while (reader.hasNext()) {
        name = reader.nextName();
        when (name) {
            "id" -> user.id = reader.nextInt()
            "name" -> user.name = reader.nextString()
            "email" -> user.email = reader.nextString()
            else -> reader.skipValue();
        }
    }
    reader.endObject();
    return user
}


fun readTodo(reader: JsonReader): Todo{
    var name: String
    val todo = Todo()

    reader.beginObject()
    while (reader.hasNext()) {
        name = reader.nextName();
        when (name) {
            "userId" -> todo.userId = reader.nextInt()
            "id" -> todo.id = reader.nextInt()
            "title" -> todo.title = reader.nextString()
            "completed" -> todo.completed = reader.nextBoolean()
            else -> reader.skipValue()
        }
    }
    reader.endObject();
    return todo
}


fun readPost(reader: JsonReader): Post{
    var name: String
    val post = Post()

    reader.beginObject()
    while (reader.hasNext()) {
        name = reader.nextName();
        when (name) {
            "userId" -> post.userId = reader.nextInt()
            "id" -> post.id = reader.nextInt()
            "title" -> post.title = reader.nextString()
            "body" -> post.body = reader.nextString()
            else -> reader.skipValue()
        }
    }
    reader.endObject();
    return post
}


fun readComment(reader: JsonReader): Comment{
    var name: String
    val comment = Comment()

    reader.beginObject()
    while (reader.hasNext()) {
        name = reader.nextName();
        when (name) {
            "id" -> comment.id = reader.nextInt()
            "postId" -> comment.postId = reader.nextInt()
            "email" -> comment.mail = reader.nextString()
            "name" -> comment.title = reader.nextString()
            "body" -> comment.body = reader.nextString()
            else -> reader.skipValue()
        }
    }
    reader.endObject();
    return comment
}


fun readArray(reader: JsonReader, objectType: String): ArrayList<Any>{
    val objList = ArrayList<Any>()
    reader.beginArray();
    while (reader.hasNext()) {
        when (objectType) {
            "user" -> objList.add(readUser(reader))
            "todo" -> objList.add(readTodo(reader))
            "post" -> objList.add(readPost(reader))
            "comment"->objList.add(readComment(reader))
        }
    }
    reader.endArray();
    return objList
}


fun readJsonStream(json: String, objectType: String): ArrayList<Any>{
    val reader = JsonReader(StringReader(json))
    try {
        return readArray(reader, objectType)
    } finally {
        reader.close()
    }
}