package com.example.regisapp;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.regisapp.VIEJO.BateryService;
import com.example.regisapp.VIEJO.DB_STATUS;
import com.example.regisapp.VIEJO.Datos;

public class MainActivity extends AppCompatActivity {
    DB_STATUS status;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        status = new DB_STATUS(this);
        startService(new Intent(this, BateryService.class));
    }


}
