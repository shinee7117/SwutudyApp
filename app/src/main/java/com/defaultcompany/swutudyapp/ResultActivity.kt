package com.defaultcompany.swutudyapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import java.util.*

class ResultActivity : AppCompatActivity() {

    lateinit var randomImageView: ImageView
    lateinit var doneTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        randomImageView = findViewById(R.id.randomImageView)
        doneTextView = findViewById(R.id.doneTextView)
        var subject = intent.getStringExtra("subject")
        var time = intent.getStringExtra("time")

        val random = Random()
        val num = random.nextInt(3) //0~2 무작위 Int

        when(num){
            0 -> randomImageView.setImageResource(R.drawable.good_1)
            1 -> randomImageView.setImageResource(R.drawable.good_2)
            2 -> randomImageView.setImageResource(R.drawable.good_3)
        }

        doneTextView.text = subject + "과목을 " + time + "분이나!! 참 잘했어요."
    }
}