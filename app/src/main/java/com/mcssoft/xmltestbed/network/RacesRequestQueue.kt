package com.mcssoft.xmltestbed.network

import android.content.Context
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley

// https://developer.android.com/training/volley/requestqueue
class RacesRequestQueue constructor(context: Context) {
    companion object {
        @Volatile
        private var INSTANCE: RacesRequestQueue? = null

        fun getInstance(context: Context) =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: RacesRequestQueue(context).also {
                    INSTANCE = it
                }
            }
    }

    private val requestQueue: RequestQueue by lazy {
        // From doco:
        // applicationContext is key, it keeps you from leaking the Activity or BroadcastReceiver if one passed one in.
        Volley.newRequestQueue(context.applicationContext)
    }

    fun <T> addToRequestQueue(req: Request<T>) {
        requestQueue.add(req)
    }
}
/*
    XmlPullParser xpp = context.getResources().getXml(R.raw.zoo_table);
    XPath xpath = XPathFactory.newInstance().newXPath();
    try {
        String askFor2 = "//creature[@ID='2']";
        NodeList creaturesNodes = (NodeList) xpath.evaluate(askFor2, xpp, XPathConstants.NODESET);

 */