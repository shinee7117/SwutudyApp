package com.defaultcompany.swutudyapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient

class WebViewActivity : AppCompatActivity() {
    lateinit var webView : WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)

        webView = findViewById(R.id.webView)

        webView.apply {
            settings.javaScriptEnabled = true
            webViewClient = WebViewClient()
        }

        var url = intent.getStringExtra("urlLink")

        webView.loadUrl(url)

        registerForContextMenu(webView)
    }

    override fun onBackPressed() {
        if(webView.canGoBack()) {
            webView.goBack()
        }
        else {
            super.onBackPressed()
        }
    }

}