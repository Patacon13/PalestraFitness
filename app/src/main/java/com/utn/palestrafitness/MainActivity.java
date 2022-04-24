package com.utn.palestrafitness;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    TextView textoDocumento;
    TextView textoContrasena;


    public void aceptarIngreso(View view) {

        final MainActivity thisActivity = this;

        String documento = textoDocumento.getText().toString();
        String contrasena = textoContrasena.getText().toString();

        FirebaseDatabase rootRef = FirebaseDatabase.getInstance();

        Query busquedaAlumno = rootRef.getReference().child("Usuario/Alumno/" + documento + "/"); //

        busquedaAlumno.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                final Alumno datosAlumno;
                if (snapshot.exists()) {
                    datosAlumno = snapshot.getValue(Alumno.class);
                    if (datosAlumno.getContrasena().equals(contrasena)) {
                        //todo implementar
                    }

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Query busquedaProfesor = rootRef.getReference().child("Usuario/Profesor/" + documento + "/");

        busquedaProfesor.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                Profesor datosProfesor;

                if (snapshot.exists()) {
                    System.out.println(snapshot.getValue(Profesor.class));
                    datosProfesor = snapshot.getValue(Profesor.class);

                    if (datosProfesor.getContrasena().equals(contrasena)) {
                        Intent intent = new Intent(thisActivity, AdministrationActivity.class);
                        startActivity(intent);
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textoDocumento = findViewById(R.id.textoDocumento);
        textoContrasena = findViewById(R.id.textoContrasena);

        Button botonAceptar = findViewById(R.id.botonAceptar);

        botonAceptar.setBackgroundColor(Color.rgb(255,165,0));

    }
}