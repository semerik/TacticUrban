package com.example.tacticurban;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;

public class Menu extends AppCompatActivity {
private TextView userName;
private TextView eMail;
private UsuarioActual usuarioActual;
private TextView textRegistroAdmin;

private ImageView ToolAction,ToolBack,imgRegistroAdmin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        userName = findViewById(R.id.textName);
        eMail = findViewById(R.id.textEmail);

        usuarioActual = UsuarioActual.getInstance();

        textRegistroAdmin = findViewById(R.id.TextRegistrarAdmin);
        imgRegistroAdmin = findViewById(R.id.ImgRegistrarAdmin);

        if ("admin".equals(usuarioActual.getRol())) {
            textRegistroAdmin.setVisibility(View.VISIBLE);
            imgRegistroAdmin.setVisibility(View.VISIBLE);
        } else {
            textRegistroAdmin.setVisibility(View.GONE);
            imgRegistroAdmin.setVisibility(View.GONE);
        }



        userName.setText(usuarioActual.getFull_name());
        eMail.setText(usuarioActual.getEmail());

        ToolAction = findViewById(R.id.ToolAction);
        ToolBack = findViewById(R.id.ToolBack);
        ToolBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed(); // Simula el comportamiento del botón de retroceso del sistema
            }
        });

        ToolAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Funciona :D", Toast.LENGTH_LONG).show();
            }
        });

        

    }

    public void goResgistroAdmin(View view){
        Intent intent = new Intent(this, SingupAdmin.class);
        startActivity(intent);
    }

    public void goReporteAlcaldia(View view){
        Intent intent = new Intent(Menu.this, ReporteAlcaldia.class);
        startActivity(intent);
    }
    public void goReportarIncidente (View view){
        Intent intent = new Intent(Menu.this, ReportarIncidente.class);
        startActivity(intent);
    }

    public void cerrarSesion(View view){
        usuarioActual.cerrarSesion();
        Toast.makeText(getApplicationContext(), "Se ah cerrado sesion correctamente", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(Menu.this, LoginForm.class);
        startActivity(intent);
    }

    public void goReportesUsuario (View view){
        Intent intent = new Intent(Menu.this, ReportesUsuario.class);
        startActivity(intent);
    }

}