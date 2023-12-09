package com.example.proyectointegracion;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AsientosActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asientos);

        // Aquí recuperas los datos que fueron pasados desde RegistroPasajero
        Intent intent = getIntent();
        String nombre = intent.getStringExtra("Nombre");
        String apellidoPaterno = intent.getStringExtra("ApellidoPaterno");
        String apellidoMaterno = intent.getStringExtra("ApellidoMaterno");
        String dni = intent.getStringExtra("DNI");
        String email = intent.getStringExtra("Email");
        String telefono = intent.getStringExtra("Telefono");

        String origen = intent.getStringExtra("Origen");
        String destino = intent.getStringExtra("Destino");
        String fechaIda = intent.getStringExtra("FechaIda");
        int id_Programacion = intent.getIntExtra("Id_Programacion", 0);

        // Crear un objeto OnClickListener común para todos los botones
        View.OnClickListener commonOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtener el texto del botón que se hizo clic
                String numeroAsiento = ((Button) v).getText().toString();

                // Crea un Intent para abrir la actividad ResumenBoleto
                Intent intent = new Intent(AsientosActivity.this, PagoActivity.class);

                // Pasando atributos para imprimir boleta
                intent.putExtra("Nombre", nombre);
                intent.putExtra("ApellidoPaterno", apellidoPaterno);
                intent.putExtra("ApellidoMaterno", apellidoMaterno);
                intent.putExtra("DNI", dni);
                intent.putExtra("Email", email);
                intent.putExtra("Telefono", telefono);
                intent.putExtra("Origen", origen);
                intent.putExtra("Destino", destino);
                intent.putExtra("FechaIda", fechaIda);
                intent.putExtra("Id_Programacion", id_Programacion);

                // Puedes pasar información adicional a través de Intent, por ejemplo, el número de asiento
                intent.putExtra("numero_asiento", numeroAsiento);

                // Inicia la nueva actividad
                startActivity(intent);
            }
        };

        // Asigna el OnClickListener común a todos los botones de asiento
        for (int i = 1; i <= 20; i++) {
            int buttonId = getResources().getIdentifier("Asiento" + i, "id", getPackageName());
            Button asientoButton = findViewById(buttonId);
            asientoButton.setOnClickListener(commonOnClickListener);
        }
    }
}
