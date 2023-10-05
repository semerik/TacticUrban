package com.example.tacticurban;

import androidx.appcompat.app.AppCompatActivity;

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
                    Cursor cursor= bdHelper.selectUser(user.getText().toString(),password.getText().toString());
                    if(cursor.getCount() == 1){
                        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                        startActivity(intent);
                    }else{
                        Toast.makeText(getApplicationContext(),"usuario y/o password no validos",Toast.LENGTH_LONG).show();
                    }
                    user.setText("");
                    password.setText("");
                }catch (SQLException e){
                    e.printStackTrace();
                }

            }
        });

    }
}