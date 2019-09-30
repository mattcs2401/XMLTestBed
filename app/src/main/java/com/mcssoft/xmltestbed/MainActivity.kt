package com.mcssoft.xmltestbed

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.android.volley.Response
import com.android.volley.VolleyError
import java.io.ByteArrayInputStream
import org.w3c.dom.NodeList
import org.xml.sax.InputSource
import javax.xml.xpath.XPathConstants
import javax.xml.xpath.XPathFactory


class MainActivity : AppCompatActivity(), Response.Listener<String>, Response.ErrorListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tvResponse = findViewById(R.id.id_tv_response)
    }

    override fun onStart() {
        super.onStart()

        /**
         * Note: Specific to e.g. "https://tatts.com/pagedata/racing/2019/9/30/NR.xml"
         *
         * For detailed Runner info, need: e.g. "https://tatts.com/pagedata/racing/2019/9/30/NR1.xml"
         * Example XPath: "/RaceDay/Meeting/Race[@RaceNo=1]/Runner"
         */
        val raceDayData = parseRaceDay()
        val meetingData = parseMeeting()
        val raceData = parseRace()
        val raceDataNo = parseRaceNo()
        val bp = "bp"

//        val url = "https://tatts.com/pagedata/racing/2019/9/30/NR.raw"
//        // Request a string response from the provided URL.
//        val stringRequest = StringRequest(Request.Method.GET, url, this, this)
//        // Add the request to the RequestQueue.
//        RacesRequestQueue.getInstance(this).addToRequestQueue(stringRequest)
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

    private fun parseRaceDay(): MutableMap<String, String> {
        val inputSource = InputSource(resources.openRawResource((R.raw.raceday)))
        val xpath = XPathFactory.newInstance().newXPath()
        val expr = "/RaceDay"
        val lNodes = xpath.evaluate(expr, inputSource, XPathConstants.NODESET) as NodeList
        val map = mutableMapOf<String, String>()

        if(lNodes.length > 0) {
            val len = lNodes.length
            for(ndx in 0..len) {
                val node = lNodes.item(ndx)
                if (node != null) {
                    val lNodeAttrs = node.attributes
                    for (ndx in 0..lNodeAttrs.length - 1) {
                        val attrNode = lNodeAttrs.item(ndx)
                        map.put(attrNode.localName, attrNode.nodeValue)
                    }
                }
            }
        }
        return map
    }

    private fun parseMeeting(): MutableMap<String, String> {
        val inputSource = InputSource(resources.openRawResource((R.raw.raceday)))
        val xpath = XPathFactory.newInstance().newXPath()
        val expr = "/RaceDay/Meeting"
        val lNodes = xpath.evaluate(expr, inputSource, XPathConstants.NODESET) as NodeList
        val map = mutableMapOf<String, String>()

        if(lNodes.length > 0) {


            val len = lNodes.length
            for(ndx in 0..len) {
                val node = lNodes.item(ndx)
                if (node != null) {
                    val lNodeAttrs = node.attributes
                    for (ndx in 0..lNodeAttrs.length - 1) {
                        val attrNode = lNodeAttrs.item(ndx)
                        map.put(attrNode.localName, attrNode.nodeValue)
                    }
                }
            }
        }
        return map
    }

    private fun parseRace(): MutableMap<String, String> {
        val inputSource = InputSource(resources.openRawResource((R.raw.raceday)))
        val xpath = XPathFactory.newInstance().newXPath()
        val expr = "/RaceDay/Meeting/Race"
        val lNodes = xpath.evaluate(expr, inputSource, XPathConstants.NODESET) as NodeList
        val map = mutableMapOf<String, String>()

        if(lNodes.length > 0) {
            val len = lNodes.length
            for(ndx in 0..len) {
                val node = lNodes.item(ndx)
                if (node != null) {
                    val lNodeAttrs = node.attributes
                    for (ndx in 0..lNodeAttrs.length - 1) {
                        val attrNode = lNodeAttrs.item(ndx)
                        map.put(attrNode.localName, attrNode.nodeValue)
                    }
                }
            }
        }
        return map
    }

    private fun parseRaceNo(): MutableMap<String, String> {
        val inputSource = InputSource(resources.openRawResource((R.raw.raceday)))
        val xpath = XPathFactory.newInstance().newXPath()
        val expr = "/RaceDay/Meeting/Race[@RaceNo=1]"
        val lNodes = xpath.evaluate(expr, inputSource, XPathConstants.NODESET) as NodeList
        val map = mutableMapOf<String, String>()

        if(lNodes.length > 0) {
            val len = lNodes.length
            for(ndx in 0..len) {
                val node = lNodes.item(ndx)
                if (node != null) {
                    val lNodeAttrs = node.attributes
                    for (ndx in 0..lNodeAttrs.length - 1) {
                        val attrNode = lNodeAttrs.item(ndx)
                        map.put(attrNode.localName, attrNode.nodeValue)
                    }
                }
            }
        }
        return map
    }

    lateinit var tvResponse: TextView
    lateinit var raceMeetingParser: RaceMeetingParser

}
