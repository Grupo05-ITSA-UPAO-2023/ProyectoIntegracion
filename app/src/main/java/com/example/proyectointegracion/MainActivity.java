package com.example.proyectointegracion;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity {

    public EditText origen,destino, fechaida, fechavuelta;
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        origen =(EditText) findViewById(R.id.origen);
        destino =(EditText) findViewById(R.id.destino);
        fechaida = (EditText) findViewById(R.id.fechaida);
        fechavuelta = (EditText) findViewById(R.id.fechavuelta);
        requestQueue = Volley.newRequestQueue(this);

    }
    public void siguienteYVerRutas(View view) {

        String origenbus = origen.getText().toString().trim();
        String destinobus = destino.getText().toString().trim();
        String URL = "http://192.168.0.8/PruebasApi/Mostrar.php?origen=" + origenbus + "&destino=" + destinobus;
        Log.d("URL_DEBUG", URL);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                URL,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray responseArray) {
                        Intent i = new Intent(MainActivity.this,ProgramacionRutas.class);
                        try {
                            for (int j = 0; j < responseArray.length(); j++) {
                                JSONObject ruta = responseArray.getJSONObject(j);
                                String salida = ruta.has("Salida") ? ruta.getString("Salida") : "";
                                String origen = ruta.has("Origen") ? ruta.getString("Origen") : "";
                                String destino = ruta.has("Destino") ? ruta.getString("Destino") : "";
                                String servicio = ruta.has("Servicio") ? ruta.getString("Servicio") : "";
                                String precio = ruta.has("Precio") ? ruta.getString("Precio") : "";

                                Log.d("VALORES_JSON", "Salida" + j + ": " + salida);
                                Log.d("VALORES_JSON", "Servicio" + j + ": " + servicio);
                                Log.d("VALORES_JSON", "Precio" + j + ": " + precio);


                                i.putExtra("Salida" + j, salida);
                                i.putExtra("Servicio" + j, servicio);
                                i.putExtra("Precio" + j, precio);



                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        i.putExtra("Origen", origen.getText().toString());
                        i.putExtra("Destino", destino.getText().toString());
                        i.putExtra("FechaIda", fechaida.getText().toString());
                        i.putExtra("FechaRetorno", fechavuelta.getText().toString());
                        startActivity(i);
                        Toast.makeText(MainActivity.this, "Rutas encontradas", Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("VOLLEY_ERROR", error.toString());
                Toast.makeText(MainActivity.this, "Rutas no encontradas", Toast.LENGTH_SHORT).show();
            }
        }
        );

        requestQueue.add(jsonArrayRequest);
    }

    /*
    public void siguiente(View view){
        Intent i = new Intent(this, ProgramacionRutas.class);
        i.putExtra("Origen", origen.getText().toString());
        i.putExtra("Destino",destino.getText().toString());
        i.putExtra("FechaIda",fechaida.getText().toString());
        i.putExtra("FechaRetorno",fechavuelta.getText().toString());
        startActivity(i);
    }

    public void verRutas(View view){
        String origenbus = origen.getText().toString().trim();
        String destinobus = destino.getText().toString().trim();
        String URL = "http://192.168.0.8/PruebasApi/Mostrar.php?origen=" + origenbus + "&destino=" + destinobus;
        Log.d("URL_DEBUG", URL);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                URL,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray responseArray) {
                        try {
                            for (int i = 0; i < responseArray.length(); i++) {
                                JSONObject ruta = responseArray.getJSONObject(i);
                                String horaSalida = ruta.getString("Hora_Salida");
                                String origen = ruta.getString("Origen");
                                String destino = ruta.getString("Destino");
                                String tipoServicio = ruta.getString("Tipo_Servicio");
                                String terminal = ruta.getString("Terminal");
                                String precio = ruta.getString("Precio");
                                String opcion = ruta.getString("Opcion");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Toast.makeText(MainActivity.this, "Rutas encontradas", Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("VOLLEY_ERROR", error.toString());
                Toast.makeText(MainActivity.this, "Rutas no encontradas", Toast.LENGTH_SHORT).show();
            }
        }
        );

        requestQueue.add(jsonArrayRequest);
    }

     */

}