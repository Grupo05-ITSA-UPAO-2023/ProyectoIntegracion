package com.example.proyectointegracion;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class RegistroPasajero extends AppCompatActivity {

    public EditText hint_dni, hint_nombres, hint_apellidoMaterno, hint_apellidoPaterno;

    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_pasajero);

        hint_dni = (EditText) findViewById(R.id.hint_dni);
        hint_nombres = (EditText) findViewById(R.id.nombres);
        hint_apellidoPaterno = (EditText) findViewById(R.id.apellidoPaterno);
        hint_apellidoMaterno = (EditText) findViewById(R.id.apellidoMaterno);

        requestQueue = Volley.newRequestQueue(this);
    }
    public void Consultar(View view){

        String dni = hint_dni.getText().toString().trim();
        String URL = "http://192.168.0.8/PruebasApi/ConsultarPasajero.php?dni=" + dni;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                URL,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        String txt_nombre, txt_apellidoPaterno, txt_apellidoMaterno;

                        try {
                            txt_nombre = response.getString("nombre");
                            txt_apellidoPaterno = response.getString("apellidoPaterno");
                            txt_apellidoMaterno = response.getString("apellidoMaterno");

                            hint_nombres.setText(txt_nombre);
                            hint_apellidoPaterno.setText(txt_apellidoPaterno);
                            hint_apellidoMaterno.setText(txt_apellidoMaterno);
                        } catch (JSONException e) {
                            e.printStackTrace();

                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                hint_nombres.setText("");
                hint_apellidoPaterno.setText("");
                hint_apellidoMaterno.setText("");
                Toast.makeText(RegistroPasajero.this, "El DNI no existe", Toast.LENGTH_SHORT).show();
            }
        }
        );
        requestQueue.add(jsonObjectRequest);

    }
}

