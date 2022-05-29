package com.utn.palestrafitness.ui.administration;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.utn.palestrafitness.R;
import com.utn.palestrafitness.lib.Alumno;
import com.utn.palestrafitness.ui.rutina.RutinaActivity;

public class PestañaEditarRutina extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    private FirebaseAuth mAuth;
    private TextView documentoAEditar;
    private TextView alumnoNoEncontrado;


    public PestañaEditarRutina() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void escondeError() {
        alumnoNoEncontrado.setVisibility(View.INVISIBLE);
    }

    private void muestraErorr() {
        alumnoNoEncontrado.setVisibility(View.VISIBLE);
    }

    private void accederARutina() {
        escondeError();
        FirebaseDatabase rootRef = FirebaseDatabase.getInstance();
        Query busquedaAlumno = rootRef.getReference().child("Usuario/Alumno/" + documentoAEditar.getText().toString() + "/");

        busquedaAlumno.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    Alumno datosAlumno = snapshot.getValue(Alumno.class);

                    Intent intent = new Intent(getActivity(), RutinaActivity.class);
                    intent.putExtra("documento", datosAlumno.getDocumento());
                    startActivity(intent);
                }
                else muestraErorr();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        mAuth = FirebaseAuth.getInstance();

        View view = inflater.inflate(R.layout.fragment_editar, container, false);

        documentoAEditar = view.findViewById(R.id.documentoEditar);

        Button botonEditar = view.findViewById(R.id.botonEditarRutina);

        alumnoNoEncontrado = view.findViewById(R.id.alumnoNoEncontrado);

        botonEditar.setOnClickListener(l -> accederARutina());

        return view;
    }
}