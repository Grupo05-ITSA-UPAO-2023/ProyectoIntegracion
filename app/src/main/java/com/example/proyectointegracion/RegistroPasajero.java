package com.example.proyectointegracion;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegistroPasajero extends AppCompatActivity {

    public EditText hint_dni, hint_nombres, hint_apellidoMaterno, hint_apellidoPaterno, hint_fecha_nacimiento,
            hint_email, hint_telefono;
    private String consultaNombre, consultaApellidoPaterno, consultaApellidoMaterno;
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_pasajero);

        // Recupera los datos de origen, destino y fechaIda
        String origen = getIntent().getStringExtra("Origen");
        String destino = getIntent().getStringExtra("Destino");
        String fechaIda = getIntent().getStringExtra("FechaIda");
        int id_Programacion = getIntent().getIntExtra("Id_Programacion", 0);


        hint_dni = (EditText) findViewById(R.id.hint_dni);
        hint_nombres = (EditText) findViewById(R.id.nombres);
        hint_apellidoPaterno = (EditText) findViewById(R.id.apellidoPaterno);
        hint_apellidoMaterno = (EditText) findViewById(R.id.apellidoMaterno);

        //Para el correo, fecha de nacimiento del pasajero y telefono
        hint_email = findViewById(R.id.hint_correo);
        hint_telefono = findViewById(R.id.hint_telefono);

        requestQueue = Volley.newRequestQueue(this);
    }

    public void Consultar(View view) {
        String dni = hint_dni.getText().toString().trim();
        String URL = "https://pruebasbetoapi.000webhostapp.com/Api-Itsa/ConsultarPasajero.php?dni=" + dni;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                URL,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            // Almacena los datos consultados en variables de instancia
                            consultaNombre = response.getString("nombre");
                            consultaApellidoPaterno = response.getString("apellidoPaterno");
                            consultaApellidoMaterno = response.getString("apellidoMaterno");

                            // Muestra los datos en los campos correspondientes
                            hint_nombres.setText(consultaNombre);
                            hint_apellidoPaterno.setText(consultaApellidoPaterno);
                            hint_apellidoMaterno.setText(consultaApellidoMaterno);
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
        });

        requestQueue.add(jsonObjectRequest);
    }

    // Método para iniciar AsientosActivity
    public void MostrarAsientos(View view) {
        // Crear un Intent para abrir AsientosActivity
        registrarPasajero();
        Intent intent = new Intent(this, AsientosActivity.class);

        // Puedes pasar datos adicionales a AsientosActivity usando putExtra
        intent.putExtra("Nombre", hint_nombres.getText().toString());
        intent.putExtra("ApellidoPaterno", hint_apellidoPaterno.getText().toString());
        intent.putExtra("ApellidoMaterno", hint_apellidoMaterno.getText().toString());
        intent.putExtra("DNI", hint_dni.getText().toString());
        intent.putExtra("Email", hint_email.getText().toString());
        intent.putExtra("Telefono", hint_telefono.getText().toString());

        // Recupera los datos de origen, destino y fechaIda
        String origen = getIntent().getStringExtra("Origen");
        String destino = getIntent().getStringExtra("Destino");
        String fechaIda = getIntent().getStringExtra("FechaIda");

        intent.putExtra("Origen", origen);
        intent.putExtra("Destino", destino);
        intent.putExtra("FechaIda", fechaIda);

        //Id_Ruta al Intent
        int id_Programacion = getIntent().getIntExtra("Id_Programacion", 0);
        intent.putExtra("Id_Programacion", id_Programacion);
        startActivity(intent);

    }

    private void registrarPasajero() {
        String dni = hint_dni.getText().toString().trim();
        String nombre = consultaNombre;
        String apellidoPaterno = consultaApellidoPaterno;
        String apellidoMaterno = consultaApellidoMaterno;

        String URL = "https://pruebasbetoapi.000webhostapp.com/Api-Itsa/RegistrarPasajero.php";

        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("RESPONSE", "Respuesta del servidor: " + response);  // Agrega este Log

                        // Verifica si la respuesta comienza con "<br"
                        if (response.startsWith("<br")) {
                            // La respuesta no es un JSON válido, probablemente un mensaje de error
                            Toast.makeText(RegistroPasajero.this, "Error en la respuesta del servidor", Toast.LENGTH_SHORT).show();
                            Log.e("SERVER_ERROR", "Error en la respuesta del servidor: " + response);
                            return;
                        }

                        try {
                            JSONObject jsonResponse = new JSONObject(response);

                            boolean success = jsonResponse.getBoolean("success");
                            String message = jsonResponse.getString("message");

                            if (success) {
                                int idPasajero = jsonResponse.getInt("id");
                                Log.d("ID_PASAJERO", "ID del Pasajero: " + idPasajero);
                            } else {
                                Toast.makeText(RegistroPasajero.this, "Error al registrar pasajero: " + message, Toast.LENGTH_SHORT).show();
                                Log.e("REGISTRO_ERROR", "Error al registrar pasajero: " + message);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e("JSON_ERROR", "Error al procesar la respuesta JSON", e);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(RegistroPasajero.this, "Error en la solicitud Volley", Toast.LENGTH_SHORT).show();
                        Log.e("VOLLEY_ERROR", "Error en la solicitud Volley", error);
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("dni", dni);
                params.put("nombre", nombre);
                params.put("apellidoPaterno", apellidoPaterno);
                params.put("apellidoMaterno", apellidoMaterno);
                return params;
            }
        };

        // Agrega la solicitud a la cola de Volley
        requestQueue.add(stringRequest);
    }

}