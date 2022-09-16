package com.panbadian.opiekun_lekow

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

private lateinit var doseAdapter: DoseAdapter
private lateinit var medAdapter: MedAdapter

class PatientCard : AppCompatActivity() {

//    private fun onPostClick(post: Post) {
//        Toast.makeText(this, "click", Toast.LENGTH_SHORT).show()
//        val intent = Intent(this, CommentListActivity::class.java)
//        intent.putExtra("postId", post.id)
//        intent.putExtra("name", post.title)
//        startActivityForResult(intent, 0)
//    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_patient_card)
        Log.d("Hello", "It is created")

//        val userId = intent.getIntExtra("id", -3)
//        val userName = intent.getStringExtra("name")

        //show
//        val dbHelper = DBHelper(this)
//        val todoArray = dbHelper.getTodos(userId)
//        val postArray = dbHelper.getPosts(userId)

        val tvName = findViewById<TextView>(R.id.tv_name)
        tvName.text = "Leonardo Da Vinci"

        medAdapter = MedAdapter(mutableListOf())

        val medList = findViewById<RecyclerView>(R.id.rv_meds)
        medList.adapter = medAdapter
        medList.layoutManager = LinearLayoutManager(this)

        for(med in meds) {
            medAdapter.addMed(med)
        }



//        postAdapter.onItemClick = {post -> onPostClick(post)}

//        val bPrevious = findViewById<ImageButton>(R.id.b_previous)

//        bPrevious.setOnClickListener(){
//            finish()
//        }
    }

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if (resultCode == RESULT_OK) {
//            finish()
//        }
//    }
}
