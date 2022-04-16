package com.utn.palestrafitness.ui.main;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.utn.palestrafitness.Alumno;
import com.utn.palestrafitness.Profesor;
import com.utn.palestrafitness.R;
import com.utn.palestrafitness.Usuario;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Agregar#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Agregar extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private TextView textoUsuario;
    private TextView textoApellido;
    private TextView textoDocumento;
    private TextView textoTelefono;
    private TextView textoEmail;
    private Spinner sexoSpinner;
    private CheckBox profesor;
    private TextView textoPass;

    public Agregar() {
        // Required empty public constructor
    }
    FirebaseDatabase rootNode;
    DatabaseReference reference;
    private void agregaUsuario(View view) {

        rootNode = FirebaseDatabase.getInstance();

        String user = textoUsuario.getText().toString();
        String apellido = textoApellido.getText().toString();
        String documento = textoDocumento.getText().toString();
        String telefono = textoTelefono.getText().toString();
        String email = textoEmail.getText().toString();
        String sexo = (String) sexoSpinner.getSelectedItem();
        String password = textoPass.getText().toString();
        Boolean esProfesor = profesor.isChecked();

        if (esProfesor) {
            Profesor profesor = new Profesor(user, apellido, documento, telefono, email, sexo, password);
            reference = rootNode.getReference("Usuario/Profesor");
            reference.child(documento).setValue(profesor);
        }
        else {
            Alumno alumno = new Alumno(user, apellido, documento, telefono, email, sexo, password);
            reference = rootNode.getReference("Usuario/Alumno");
            reference.child(documento).setValue(alumno);
        }



    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Agregar.
     */
    // TODO: Rename and change types and number of parameters
    public static Agregar newInstance(String param1, String param2) {
        Agregar fragment = new Agregar();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_agregar, container, false);

        textoUsuario = view.findViewById(R.id.nombre);
        textoApellido = view.findViewById(R.id.apellido);
        textoDocumento = view.findViewById(R.id.documento);
        textoTelefono = view.findViewById(R.id.telefono);
        textoEmail = view.findViewById(R.id.email);
        sexoSpinner = view.findViewById(R.id.genero);
        profesor = view.findViewById(R.id.profesor);
        textoPass = view.findViewById(R.id.contrasena);


        Button button = view.findViewById(R.id.botonAgregar);

        button.setOnClickListener(view1 -> agregaUsuario(view1));



        return view;
    }
}