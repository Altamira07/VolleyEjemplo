package com.example.luis.ejemplovolley;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
//"/auth_users.php"
    String url="https://storeitc-luisitoaltamira.rhcloud.com/wc-api/v3/products";
    String ck = "ck_a64ead43b826563f0e0fad044920da0f166ae7fe", cs ="cs_0b6239dded2cbf1bc56bbbae5a49e913842123b9";
    TextView txt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txt = (TextView) findViewById(R.id.tvPrueba);
        txt.setText("Holi");
        //Ejecucion de peticiones
        RequestQueue queue = Volley.newRequestQueue(this);
        //Se hace la peticion, se pide el metodo (GET,PUT,POST,DELETE), la url, el error, y la respuesta
        StringRequest stringRequest = new StringRequest(Request.Method.GET,url ,
                //Respueta de la peticion
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {

                        txt.setText(response);
                    }
                },
                //Sino se pudo hacer la respuesta manda los siguientes errores
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        String message ="";
                        if (volleyError instanceof NetworkError) {
                            message = "Cannot connect to Internet...Please check your connection!";
                        } else if (volleyError instanceof ServerError) {
                            message = "The server could not be found. Please try again after some time!!";
                        } else if (volleyError instanceof AuthFailureError) {
                            message = "Cannot connect to Internet...Please check your connection!";
                        } else if (volleyError instanceof ParseError) {
                            message = "Parsing error! Please try again after some time!!";
                        } else if (volleyError instanceof NoConnectionError) {
                            message = "Cannot connect to Internet...Please check your connection!";
                        } else if (volleyError instanceof TimeoutError) {
                            message = "Connection TimeOut! Please check your internet connection.";
                        }
                        txt.setText(message);
                    }
            }){
            //Se puden las cabeceras (autenticacion) etc
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                String credentials = ck+":"+cs;
                String auth = "Basic "+ Base64.encodeToString(credentials.getBytes(),Base64.NO_WRAP);
                headers.put("Authorization",auth);
                headers.put("Content-Type","application/json");
                headers.put("Accept","application/json");
                return headers;
            }

        };
        queue.add(stringRequest);
        queue.start();
    }
}
