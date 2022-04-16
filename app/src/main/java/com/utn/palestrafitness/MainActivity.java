package com.utn.palestrafitness;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    public void aceptarIngreso(View view) {
        System.out.println("presionado!");
    }

    public void siAprieta(View view) {
        return;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button botonAceptar = findViewById(R.id.botonAceptar);

        botonAceptar.setBackgroundColor(Color.rgb(255,165,0));

        Intent intent = new Intent(this, AdministrationActivity.class);
        startActivity(intent);
    }
}