package com.example.proyectointegracion;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

public class
ProgramacionRutas extends AppCompatActivity {

    private TextView text_origen, text_destino, text_fecha_ida;
    private TextView phora, pservicio, pprecio;
    private TextView phora2, pbus2, pprecio2;
    private TextView phora3, pbus3, pprecio3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_programacion_rutas);
        text_origen = (TextView) findViewById(R.id.text_origen);
        text_destino = (TextView) findViewById(R.id.text_destino);
        text_fecha_ida = (TextView) findViewById(R.id.text_fecha_ida);

        phora = (TextView) findViewById(R.id.phora);
        pservicio = (TextView) findViewById(R.id.pservicio);
        pprecio = (TextView) findViewById(R.id.pprecio);

        phora2 = (TextView) findViewById(R.id.phora2);
        pbus2 = (TextView) findViewById(R.id.pbus2);
        pprecio2 = (TextView) findViewById(R.id.pprecio2);

        phora3 = (TextView) findViewById(R.id.phora3);
        pbus3 = (TextView) findViewById(R.id.pbus3);
        pprecio3 = (TextView) findViewById(R.id.pprecio3);

        // Obtén los datos de la ruta principal desde el Intent
        String origen = getIntent().getStringExtra("Origen");
        String destino = getIntent().getStringExtra("Destino");
        String fechaIda = getIntent().getStringExtra("FechaIda");

        String Salida= getIntent().getStringExtra("Salida0");
        String Servicio = getIntent().getStringExtra("Servicio0");
        String precio = getIntent().getStringExtra("Precio0");

        // Asigna los valores a los TextViews
        text_origen.setText(origen);
        text_destino.setText(destino);
        text_fecha_ida.setText(fechaIda);

        phora.setText(Salida);
        pservicio.setText(Servicio);
        pprecio.setText(precio);

        // Ahora, debes manejar las rutas adicionales si las tienes.
        // Por ejemplo, para la segunda ruta:
        String horaSalida2 = getIntent().getStringExtra("Salida1");
        String tipoServicio2 = getIntent().getStringExtra("Servicio1");
        String precio2 = getIntent().getStringExtra("Precio1");

        phora2.setText(horaSalida2);
        pbus2.setText(tipoServicio2);
        pprecio2.setText(precio2);

        // Para la tercera ruta:
        String horaSalida3 = getIntent().getStringExtra("Salida2");
        String tipoServicio3 = getIntent().getStringExtra("Servicio2");
        String precio3 = getIntent().getStringExtra("Precio2");

        phora3.setText(horaSalida3);
        pbus3.setText(tipoServicio3);
        pprecio3.setText(precio3);


    }

    public void Seleccionar(View view){
        int id_Programacion = 4;
        Intent seleccionar = new Intent(this,RegistroPasajero.class);
        seleccionar.putExtra("Id_Programacion", id_Programacion);
        seleccionar.putExtra("Origen", text_origen.getText().toString());
        seleccionar.putExtra("Destino", text_destino.getText().toString());
        seleccionar.putExtra("FechaIda", text_fecha_ida.getText().toString());
        startActivity(seleccionar);
    }

    public void SeleccionarOp2(View view){
        int id_Programacion = 5;
        Intent seleccionarOp2 = new Intent(this,RegistroPasajero.class);
        seleccionarOp2.putExtra("Id_Ruta", id_Programacion);
        seleccionarOp2.putExtra("Origen", text_origen.getText().toString());
        seleccionarOp2.putExtra("Destino", text_destino.getText().toString());
        seleccionarOp2.putExtra("FechaIda", text_fecha_ida.getText().toString());
        startActivity(seleccionarOp2);
    }

    public void SeleccionarOp3(View view){
        int id_Programacion = 6;
        Intent seleccionarOp3 = new Intent(this, RegistroPasajero.class);
        seleccionarOp3.putExtra("Id_Ruta", id_Programacion);
        seleccionarOp3.putExtra("Origen", text_origen.getText().toString());
        seleccionarOp3.putExtra("Destino", text_destino.getText().toString());
        seleccionarOp3.putExtra("FechaIda", text_fecha_ida.getText().toString());
        startActivity(seleccionarOp3);

    }

    public void Anterior(View view){
        Intent anterior = new Intent(this, MainActivity.class);
        startActivity(anterior);

    }


}