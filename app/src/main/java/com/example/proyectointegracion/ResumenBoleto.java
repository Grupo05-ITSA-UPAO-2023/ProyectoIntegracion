package com.example.proyectointegracion;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class ResumenBoleto extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pago);
        // Aquí recuperas los datos que fueron pasados desde AsientosActivity
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
        String numeroAsiento = intent.getStringExtra("numero_asiento");

        // Ahora obtén las referencias de los elementos de la interfaz de usuario y establece el texto
        TextView nombreTextView = findViewById(R.id.text_nombre);
        TextView apellidoPaternoTextView = findViewById(R.id.text_apellidoP);
        TextView apellidoMaternoTextView = findViewById(R.id.text_apellidoM);
        TextView dniTextView = findViewById(R.id.text_dni);
        //TextView emailTextView = findViewById(R.id.text_email);
        //TextView telefonoTextView = findViewById(R.id.text_telefono);
        TextView origenTextView = findViewById(R.id.text_origen);
        TextView destinoTextView = findViewById(R.id.text_destino);
        //TextView fechaIdaTextView = findViewById(R.id.text_fechaIda);
        //TextView idProgramacionTextView = findViewById(R.id.text_idProgramacion);
        //TextView numeroAsientoTextView = findViewById(R.id.text_numeroAsiento);

        // Establece el texto en los TextView correspondientes
        nombreTextView.setText(nombre);
        apellidoPaternoTextView.setText(apellidoPaterno);
        apellidoMaternoTextView.setText(apellidoMaterno);
        dniTextView.setText(dni);
        //emailTextView.setText(email);
        //telefonoTextView.setText(telefono);
        origenTextView.setText(origen);
        destinoTextView.setText(destino);
        //fechaIdaTextView.setText(fechaIda);
        //idProgramacionTextView.setText(String.valueOf(id_Programacion));
        //numeroAsientoTextView.setText(numeroAsiento);
    }
}