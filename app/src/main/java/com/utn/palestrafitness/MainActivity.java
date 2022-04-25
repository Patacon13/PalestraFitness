package com.utn.palestrafitness;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentContainerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    TextView textoDocumento;
    TextView textoContrasena;

    FragmentContainerView fragmentoError;

    LinearLayout layoutError;

    Boolean alumno = Boolean.FALSE;

    Boolean profesor = Boolean.FALSE;

    public void cierraMensaje(View view) {
        layoutError.setVisibility(INVISIBLE);
    }

    public void noEncontradoAlumno() {
        alumno = Boolean.TRUE;
        if (profesor) layoutError.setVisibility(VISIBLE);
    }

    public void noEncontradoProfesor() {
        profesor = Boolean.TRUE;
        if (alumno) layoutError.setVisibility(VISIBLE);
    }

    public void aceptaIngreso(View view) {

        final MainActivity thisActivity = this;

        profesor = Boolean.FALSE;
        alumno = Boolean.FALSE;

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
                        layoutError.setVisibility(View.INVISIBLE);
                        Intent intent = new Intent(thisActivity, Rutina.class);
                        startActivity(intent);
                    }
                    else layoutError.setVisibility(VISIBLE);

                }
              else noEncontradoAlumno();
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
                        layoutError.setVisibility(View.INVISIBLE);
                        Intent intent = new Intent(thisActivity, AdministrationActivity.class);
                        startActivity(intent);
                    }
                    else layoutError.setVisibility(VISIBLE);
                }
            else noEncontradoProfesor();

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

        layoutError = findViewById(R.id.layoutError);

        botonAceptar.setBackgroundColor(Color.rgb(255,165,0));

    }
}