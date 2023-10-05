package com.example.tacticurban;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;


public class MainActivity extends AppCompatActivity {

    private ProgressBar cargando;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cargando = findViewById(R.id.loadingProgressBar);


    }

    public void goSignIn(View view){
        cargando.setVisibility(View.VISIBLE);
        Intent intent = new Intent(MainActivity.this, SignupForm.class);
        startActivity(intent);
    }

    public void goLogin (View view){
        cargando.setVisibility(View.VISIBLE);
        Intent intent = new Intent(MainActivity.this, LoginForm.class);
        startActivity(intent);
    }

    public void goReportarIncidente (View view){
        cargando.setVisibility(View.VISIBLE);
        Intent intent = new Intent(MainActivity.this, ReportarIncidenteMaps.class);
        startActivity(intent);
    }

}
