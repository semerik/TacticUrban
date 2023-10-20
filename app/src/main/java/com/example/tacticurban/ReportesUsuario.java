package com.example.tacticurban;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.ListView;

import OpenHelper.IncidentesSQLiteOpenHelper;

public class ReportesUsuario extends AppCompatActivity {

    private ListView listView;
    private ReportesAdapter adapter;
    private IncidentesSQLiteOpenHelper dbHelper;
    private String username;
    private UsuarioActual usuarioActual;
    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reportes_usuario);

        usuarioActual = UsuarioActual.getInstance();
        username = usuarioActual.getUsername();

        listView = findViewById(R.id.listView);
        dbHelper = new IncidentesSQLiteOpenHelper(this);

        cursor = dbHelper.getIncidentesByUser(username);

        // Crea un adaptador personalizado para mostrar los datos en el ListView
        adapter = new ReportesAdapter(this, cursor);

        // Asigna el adaptador al ListView
        listView.setAdapter(adapter);

    }
}