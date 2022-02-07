package com.defaultcompany.swutudyapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.*
import kotlin.concurrent.timer

class MainActivity : AppCompatActivity() {
    lateinit var subjectEditText: EditText
    lateinit var progressImageView: ImageView
    lateinit var faceImageView: ImageView
    lateinit var timerEditText: EditText
    lateinit var timerSetFabBtn: FloatingActionButton
    lateinit var timerTextView: TextView
    lateinit var resultButton: Button

    private var time = 0
    private var timerTask: Timer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        subjectEditText = findViewById(R.id.subjectEditText)
        progressImageView = findViewById(R.id.progressImageView)
        faceImageView = findViewById(R.id.faceImageView)
        timerEditText = findViewById(R.id.timerEditText)
        timerSetFabBtn = findViewById(R.id.timerSetFabBtn)
        timerTextView = findViewById(R.id.timerTextView)
        resultButton = findViewById(R.id.resultButton)

        //loadData()

        timerSetFabBtn.setOnClickListener(){
            time = timerEditText.text.toString().toInt()*100*60
            start()
        }

        resultButton.setOnClickListener{
            saveData(subjectEditText.text.toString(), timerEditText.text.toString().toInt())
            var intent = Intent(this, ResultActivity::class.java)
            intent.putExtra("subject", subjectEditText.text.toString())
            intent.putExtra("time", timerEditText.text.toString())
            startActivity(intent)
        }
    }

    private fun pause(){
        timerSetFabBtn.setImageResource(R.drawable.ic_baseline_play_arrow_24)
        timerTask?.cancel()
    }

    private  fun start() {
        //timerSetFabBtn.setImageResource(R.drawable.ic_baseline_pause_24)

        timerTask = timer(period=10){
            time--
            val minute = time / 6000
            val sec = (time % 6000) / 100
            val milli = time % 6000
            val progress1 = (timerEditText.text.toString().toInt()*100*60 - time).toFloat()
            val progress2 = (timerEditText.text.toString().toInt()*100*60).toFloat()
            val progress = progress1/progress2

            if(minute==0 && sec == 0 && milli == 0) timerTask?.cancel()
            runOnUiThread {
                if (progress < 0.33f){
                    progressImageView.setImageResource(R.drawable.zero)
                    faceImageView.setImageResource(R.drawable.ic_baseline_sentiment_satisfied_24)
                } else if (progress < 0.66f){
                    progressImageView.setImageResource(R.drawable.one)
                } else if (progress < 1f){
                    progressImageView.setImageResource(R.drawable.two)
                    faceImageView.setImageResource(R.drawable.ic_baseline_sentiment_satisfied_alt_24)
                } else {
                    progressImageView.setImageResource(R.drawable.three)
                    faceImageView.setImageResource(R.drawable.ic_baseline_sentiment_very_satisfied_24)
                    resultButton.visibility = android.view.View.VISIBLE
                }
                timerTextView.text = "$minute" + ":" + "$sec"
            }
        }
    }

    private fun saveData(subjectName: String, time: Int){
        var pref = this.getPreferences(0)
        var editor = pref.edit()

        editor.putString("KEY_SUBJECT", subjectEditText.text.toString()).apply()
        editor.putInt("KEY_TIME", timerEditText.text.toString().toInt()).apply()
    }

    //여기부터 웹뷰
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item?.itemId){
            R.id.action_linkareer -> {
                var intent = Intent(this, WebViewActivity::class.java)
                intent.putExtra("urlLink", "https://linkareer.com/")
                startActivity(intent)
                return true
            }
            R.id.action_onoffmix -> {
                var intent = Intent(this, WebViewActivity::class.java)
                intent.putExtra("urlLink", "https://onoffmix.com")
                startActivity(intent)
                return true
            }
            R.id.action_swu -> {
                var intent = Intent(this, WebViewActivity::class.java)
                intent.putExtra("urlLink", "https://www.swu.ac.kr/index.do")
                startActivity(intent)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}