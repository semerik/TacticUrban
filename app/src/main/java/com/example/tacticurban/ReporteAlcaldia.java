package com.example.tacticurban;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.ListView;

import OpenHelper.IncidentesSQLiteOpenHelper;

public class ReporteAlcaldia extends AppCompatActivity {


    private ListView listView;
    private ReportesAdapter adapter;
    private IncidentesSQLiteOpenHelper dbHelper;
    private String username;
    private UsuarioActual usuarioActual;
    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reporte_alcaldia);


        usuarioActual = UsuarioActual.getInstance();
        username = usuarioActual.getUsername();

        listView = findViewById(R.id.listViewAlcaldia);

        dbHelper = new IncidentesSQLiteOpenHelper(this);
        cursor = dbHelper.getAllIncidentes();

        // Crea un adaptador personalizado para mostrar los datos en el ListView
        adapter = new ReportesAdapter(this, cursor);

        // Asigna el adaptador al ListView
        listView.setAdapter(adapter);

    }
}