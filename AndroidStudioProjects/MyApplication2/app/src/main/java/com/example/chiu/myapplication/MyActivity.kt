package com.example.chiu.myapplication

import android.content.Intent
import android.content.Intent.CATEGORY_BROWSABLE
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast


class MyActivity : AppCompatActivity() {

    private val tag = "TAG"

    private val hostPattern = "^(moptt\\.tw)$".toRegex()
    private val pathPattern = "/p/([^.]+)\\.(.+)".toRegex()
    private val boardPattern = "^/p/([^.]+)".toRegex()
    private val articlePattern = "^/p/[^.]+\\.(.+)".toRegex()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val builder = Uri.Builder()
        val uri = intent.data
        try {
            Toast.makeText(applicationContext, "Taking PTT back from " + uri.toString(), Toast.LENGTH_SHORT).show()
            builder.scheme(uri.scheme)
                    .authority("www.ptt.cc")
                    .appendPath("bbs")
                    .appendPath(requireNotNull(boardPattern.find(uri.path)?.groups?.get(1)?.value))
                    .appendPath(requireNotNull(articlePattern.find(uri.path)?.groups?.get(1)?.value) + ".html")

            val newIntent = Intent(Intent.ACTION_VIEW, builder.build())
            Log.d(tag, "uri is: " + builder.build().toString())
            startActivity(newIntent)
        } catch (ex: IllegalArgumentException) {
            Toast.makeText(applicationContext, "Taking PTT back failed with " + uri.toString(), Toast.LENGTH_SHORT).show()
        }
        finish()
    }

}
