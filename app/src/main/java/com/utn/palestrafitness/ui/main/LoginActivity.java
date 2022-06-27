package com.utn.palestrafitness.ui.main;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
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

    TextView textoError;

    Boolean alumno = Boolean.FALSE;
    Boolean profesor = Boolean.FALSE;
    private FirebaseAuth mAuth;

    public void cierraMensaje(View view) {
        textoError.setVisibility(INVISIBLE);
    }

    public void noEncontrado() {
        textoError.setVisibility(VISIBLE);
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser != null) {
            currentUser.reload();
        }
    }

    public void buscarProfesor(LoginActivity thisActivity, FirebaseDatabase rootRef, String documento, String contrasena) {

        Query busquedaProfesor = rootRef.getReference().child("Usuario/Profesor/" + documento + "/");


        busquedaProfesor.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                Profesor datosProfesor;

                if (snapshot.exists()) {
                    System.out.println(snapshot.getValue(Profesor.class));
                    datosProfesor = snapshot.getValue(Profesor.class);
                    mAuth.signInWithEmailAndPassword(datosProfesor.getEmail(), contrasena)
                            .addOnCompleteListener(task -> {
                                if (task.isSuccessful()){
                                    profesor = Boolean.TRUE ;
                                    textoError.setVisibility(View.INVISIBLE);
                                    Intent intent = new Intent(thisActivity, AdministrationActivity.class);
                                    startActivity(intent);
                                }else{
                                    //Utility.showDialog(getActivity(), task);
                                    noEncontrado();
                                }

                            });
                }
                else {
                    noEncontrado();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void aceptaIngreso(View view) {

        final LoginActivity thisActivity = this;
        alumno = Boolean.FALSE;
        profesor = Boolean.FALSE;
        cierraMensaje(view);

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
                                    alumno = Boolean.TRUE;
                                    Intent intent = new Intent(thisActivity, RutinaActivity.class);
                                    intent.putExtra("documento", datosAlumno.getDocumento());
                                    intent.putExtra("currentUser", mAuth.getCurrentUser());
                                    startActivity(intent);
                                }else{
                                    //Utility.showDialog(getActivity(), task);
                                    buscarProfesor(thisActivity, rootRef, documento, contrasena);
                                }

                            });
                }
                else
                    buscarProfesor(thisActivity, rootRef, documento, contrasena);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void recuperaContrasena(View view) {
        Fragment fragment = new RecuperarContrasena();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.addToBackStack("tag").replace(R.id.container_main, fragment).commit();
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

        textoError = findViewById(R.id.textoErrorLogin);

        botonAceptar.setBackgroundColor(Color.rgb(255,165,0));

    }
}