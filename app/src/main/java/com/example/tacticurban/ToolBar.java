package com.example.tacticurban;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toolbar;

public class ToolBar extends AppCompatActivity {

    Toolbar toolbar;
   


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tool_bar);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        
    }

    private void setSupportActionBar(Toolbar toolbar) {
    }

}