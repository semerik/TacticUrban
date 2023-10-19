package com.example.tacticurban;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Menu extends AppCompatActivity {
private TextView userName;
private TextView eMail;
private UsuarioActual usuarioActual;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        userName = findViewById(R.id.textName);
        eMail = findViewById(R.id.textEmail);

        usuarioActual = UsuarioActual.getInstance();

        userName.setText(usuarioActual.getFull_name());
        eMail.setText(usuarioActual.getEmail());
    }

    public void goReportarIncidente (View view){
        Intent intent = new Intent(Menu.this, ReportarIncidente.class);
        startActivity(intent);
    }

}