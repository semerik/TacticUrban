package com.example.tacticurban;

import static OpenHelper.SQLite_OpenHelper.COLUMN_FULL_NAME;
import static OpenHelper.SQLite_OpenHelper.COLUMN_USERNAME;
import static OpenHelper.SQLite_OpenHelper.COLUMN_EMAIL;
import static OpenHelper.SQLite_OpenHelper.COLUMN_GENDER;
import static OpenHelper.SQLite_OpenHelper.COLUMN_BIRTHDATE;
import static OpenHelper.SQLite_OpenHelper.COLUMN_UBICATION;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import OpenHelper.SQLite_OpenHelper;

public class LoginForm extends AppCompatActivity {

    private EditText user,password;
    private Button login;
    private UsuarioActual usuarioActual;
    SQLite_OpenHelper bdHelper = new SQLite_OpenHelper(this,null,1);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_form);

        user = findViewById(R.id.loginForm_userName);
        password=findViewById(R.id.loginForm_password);
        login=findViewById(R.id.loginForm_singIn);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Cursor cursor = bdHelper.selectUser(user.getText().toString(), password.getText().toString());
                    if (cursor.moveToFirst()) {


                        // Obtener los datos del cursor
                        String fullName = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_FULL_NAME));
                        String userName = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USERNAME));
                        String email = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EMAIL));
                        String gender = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_GENDER));
                        String birthdate = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_BIRTHDATE));
                        String ubication = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_UBICATION));

                        // Llamar al método iniciarSesion de UsuarioActual
                        usuarioActual.iniciarSesion(fullName, userName, email, password.getText().toString(), gender, birthdate, ubication);

                        // Continuar a la actividad principal (MainActivity)
                        Intent intent = new Intent(getApplicationContext(), Menu.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(getApplicationContext(), "Usuario y/o contraseña no válidos", Toast.LENGTH_LONG).show();
                    }
                    user.setText("");
                    password.setText("");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });



    }
}