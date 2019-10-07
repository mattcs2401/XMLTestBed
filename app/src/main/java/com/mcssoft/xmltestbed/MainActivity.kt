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


class MainActivity : AppCompatActivity() {

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
        val raceDayData = parse("/RaceDay")
        val meetingData = parse("/RaceDay/Meeting")
        val raceData = parse("/RaceDay/Meeting/Race")
        val raceDataNo = parse("/RaceDay/Meeting/Race[@RaceNo=1]")
        val raceRunner = parse("/RaceDay/Meeting/Race[@RaceNo=1]/Runner")
        val bp = "bp"
    }

    private fun parse(expr: String): MutableMap<String,String> {
        val inputSource = InputSource(resources.openRawResource((R.raw.raceday1)))
        val xpath = XPathFactory.newInstance().newXPath()
        val lNodes = xpath.evaluate(expr, inputSource, XPathConstants.NODESET) as NodeList
        val map = mutableMapOf<String, String>()

        if(lNodes.length > 0) {
            val len = lNodes.length
            for(ndx in 0..len) {
                val node = lNodes.item(ndx)
                if (node != null) {
                    val lNodeAttrs = node.attributes
                    for (ndx2 in 0..lNodeAttrs.length - 1) {
                        val attrNode = lNodeAttrs.item(ndx2)
                        map.put(attrNode.localName, attrNode.nodeValue)
                    }
                }
            }
        }
        return map
    }

    lateinit var tvResponse: TextView
}
