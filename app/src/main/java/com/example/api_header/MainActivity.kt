package com.example.api_header

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun click_boton(view: View) {
        val queue = Volley.newRequestQueue(this)
        val url = "https://api-uat.kushkipagos.com/transfer/v1/bankList"
        val textView = findViewById<TextView>(R.id.txt_respuesta);

        var jsonRequest = object : JsonArrayRequest(
            Method.GET, url,null,
            { response ->
                var lista_bancos = ""
                for (i in 0 until response.length()) {
                    val banco: JSONObject = response.getJSONObject(i)
                    lista_bancos = lista_bancos +
                            "\n"+"code: "+banco.getString("code")+
                            "; name: "+banco.getString("name")+"\n"
                }
                textView.setText(lista_bancos)
            },
            { error ->
                textView.text = error.toString()
            }
        ) {
            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String>? {
                val headers = HashMap<String, String>()
                headers.put("Content-Type", "application/json; utf-8")
                headers.put("Public-Merchant-Id", "84e1d0de1fbf437e9779fd6a52a9ca18")
                headers.put("Accept", "*/*")
                return headers
            }
        }
        queue.add(jsonRequest);


    }
}