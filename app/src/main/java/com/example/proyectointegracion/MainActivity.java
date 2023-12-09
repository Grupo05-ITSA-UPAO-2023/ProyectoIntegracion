package com.example.proyectointegracion;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private static final String[] ORIGENES = new String[]{
            "Lima", "Trujillo", "Cajamarca", "Chiclayo"
    };
    private static final String[] DESTINOS = new String[]{
            "Chiclayo", "Cajamarca", "Trujillo", "Lima"
    };
    private FirebaseAuth auth;
    public EditText fechaida, fechavuelta;
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AutoCompleteTextView autoCompleteTextViewOrigen = findViewById(R.id.origen);
        ArrayAdapter<String> adapterOrigen = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, ORIGENES);
        autoCompleteTextViewOrigen.setAdapter(adapterOrigen);

        AutoCompleteTextView autoCompleteTextViewDestino = findViewById(R.id.destino);
        ArrayAdapter<String> adapterDestino = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, DESTINOS);
        autoCompleteTextViewDestino.setAdapter(adapterDestino);

        fechaida = findViewById(R.id.fechaida);
        fechavuelta = findViewById(R.id.fechavuelta);
        requestQueue = Volley.newRequestQueue(this);
        Button logOutButton = findViewById(R.id.logOutButton);
        auth = FirebaseAuth.getInstance();

        logOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth auth = FirebaseAuth.getInstance();
                FirebaseUser currentUser = auth.getCurrentUser();
                if (currentUser != null) {
                    auth.signOut();
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                    finish();
                }
            }
        });
    }

    public void siguienteYVerRutas(View view) {
        String fechaIdaText = fechaida.getText().toString().trim();
        String fechaVueltaText = fechavuelta.getText().toString().trim();

        AutoCompleteTextView autoCompleteTextViewOrigen = findViewById(R.id.origen);
        String origenText = autoCompleteTextViewOrigen.getText().toString().trim();

        AutoCompleteTextView autoCompleteTextViewDestino = findViewById(R.id.destino);
        String destinoText = autoCompleteTextViewDestino.getText().toString().trim();

        if (TextUtils.isEmpty(origenText)) {
            autoCompleteTextViewOrigen.setError("Ingrese la ruta de origen");
            return;
        }

        if (TextUtils.isEmpty(destinoText)) {
            autoCompleteTextViewDestino.setError("Ingrese la ruta de destino");
            return;
        }

        if (!isValidDate(fechaIdaText)) {
            Toast.makeText(this, "Formato de fecha de ida inválido (yyyy-MM-dd)", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!TextUtils.isEmpty(fechaVueltaText) && !isValidDate(fechaVueltaText)) {
            Toast.makeText(this, "Formato de fecha de vuelta inválido (yyyy-MM-dd)", Toast.LENGTH_SHORT).show();
            return;
        }

        String URL = "https://pruebasbetoapi.000webhostapp.com/Api-Itsa/Mostrar.php?origen=" + origenText + "&destino=" + destinoText;
        Log.d("URL_DEBUG", URL);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                URL,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray responseArray) {
                        Intent i = new Intent(MainActivity.this, ProgramacionRutas.class);
                        try {
                            for (int j = 0; j < responseArray.length(); j++) {
                                JSONObject ruta = responseArray.getJSONObject(j);
                                int idRuta = ruta.has("id_Ruta") ? ruta.getInt("Id_Ruta") : 0;
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

                        i.putExtra("Origen", origenText);
                        i.putExtra("Destino", destinoText);
                        i.putExtra("FechaIda", fechaIdaText);
                        i.putExtra("FechaRetorno", fechaVueltaText);
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

    private boolean isValidDate(String date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);

        try {
            Date parsedDate = dateFormat.parse(date);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }
}