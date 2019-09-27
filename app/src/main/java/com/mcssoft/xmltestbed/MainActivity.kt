package com.mcssoft.xmltestbed

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.mcssoft.xmltestbed.network.RacesRequestQueue
import java.io.ByteArrayInputStream
import java.io.InputStream
import java.io.InputStreamReader
import org.xmlpull.v1.XmlPullParser
import android.util.Xml.newPullParser
import com.mcssoft.xmltestbed.xmlPojo.Meeting
import com.mcssoft.xmltestbed.xmlPojo.Race
import com.mcssoft.xmltestbed.xmlPojo.RaceDay
import com.mcssoft.xmltestbed.xmlPojo.Runner
import org.xmlpull.v1.XmlPullParserFactory
import java.io.StringReader


class MainActivity : AppCompatActivity(), Response.Listener<String>, Response.ErrorListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tvResponse = findViewById(R.id.id_tv_response)
    }

    override fun onStart() {
        super.onStart()
        val url = "https://tatts.com/pagedata/racing/2019/8/16/NR1.xml"
        // Request a string response from the provided URL.
        val stringRequest = StringRequest(Request.Method.GET, url, this, this)
        // Add the request to the RequestQueue.
        RacesRequestQueue.getInstance(this).addToRequestQueue(stringRequest)
    }

    override fun onResponse(response: String?) {
        val response = response?.substring(3)  //?.substring(0,3)

        val inputStream = ByteArrayInputStream(response?.toByteArray())
        raceMeetingParser = RaceMeetingParser()
        raceMeetingParser.parse(inputStream)

        val sb = StringBuilder()
        sb.appendln(raceMeetingParser.getRace().toString())

        for(runner in raceMeetingParser.getRunners()!!) {
            sb.appendln(runner.toString())
        }

        tvResponse.text = sb.toString()
    }

    override fun onErrorResponse(error: VolleyError?) {
        //
        tvResponse.text = error?.message
    }

    lateinit var tvResponse: TextView
    lateinit var raceMeetingParser: RaceMeetingParser

}
