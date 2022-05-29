package com.utn.palestrafitness.ui.main;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentContainerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.utn.palestrafitness.lib.Alumno;
import com.utn.palestrafitness.lib.Profesor;
import com.utn.palestrafitness.R;
import com.utn.palestrafitness.ui.rutina.RutinaActivity;
import com.utn.palestrafitness.ui.administration.AdministrationActivity;

public class LoginActivity extends AppCompatActivity {

    TextView textoDocumento;
    TextView textoContrasena;

    FragmentContainerView fragmentoError;

    LinearLayout layoutError;


    Boolean profesor = Boolean.FALSE;
    private FirebaseAuth mAuth;

    public void cierraMensaje(View view) {
        layoutError.setVisibility(INVISIBLE);
    }

    public void noEncontrado() {
        layoutError.setVisibility(VISIBLE);
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser != null) {
            currentUser.reload();
        }
    }

    public void aceptaIngreso(View view) {

        final LoginActivity thisActivity = this;


        String documento = textoDocumento.getText().toString();
        String contrasena = textoContrasena.getText().toString();

        FirebaseDatabase rootRef = FirebaseDatabase.getInstance();

        Query busquedaAlumno = rootRef.getReference().child("Usuario/Alumno/" + documento + "/"); //

        busquedaAlumno.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                final Alumno datosAlumno;
                if (snapshot.exists()) {
                    datosAlumno = snapshot.getValue(Alumno.class);
                    mAuth.signInWithEmailAndPassword(datosAlumno.getEmail(), contrasena)
                            .addOnCompleteListener(task -> {
                                if (task.isSuccessful()){
                                    Intent intent = new Intent(thisActivity, RutinaActivity.class);
                                    intent.putExtra("documento", datosAlumno.getDocumento());
                                    startActivity(intent);
                                }else{
                                    //Utility.showDialog(getActivity(), task);
                                    noEncontrado();
                                }

                            });


                    /*
                    if (datosAlumno.getContrasena().equals(contrasena)) {
                        layoutError.setVisibility(View.INVISIBLE);
                        Intent intent = new Intent(thisActivity, Rutina.class);
                        intent.putExtra("user", datosAlumno.getEmail());
                        intent.putExtra("pass", contrasena);
                        startActivity(intent);
                    }
                    else layoutError.setVisibility(VISIBLE);
                    */
                }
              else noEncontrado();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Query busquedaProfesor = rootRef.getReference().child("Usuario/Profesor/" + documento + "/");


        busquedaProfesor.addListenerForSingleValueEvent(new ValueEventListener() {
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
                    else noEncontrado();
                }
            else noEncontrado();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textoDocumento = findViewById(R.id.textoDocumento);
        textoContrasena = findViewById(R.id.textoContrasena);

        Button botonAceptar = findViewById(R.id.botonAceptar);

        layoutError = findViewById(R.id.layoutError);

        botonAceptar.setBackgroundColor(Color.rgb(255,165,0));

    }
}