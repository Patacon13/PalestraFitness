package com.utn.palestrafitness.ui.administration;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.utn.palestrafitness.lib.Alumno;
import com.utn.palestrafitness.lib.Profesor;
import com.utn.palestrafitness.R;

import java.util.regex.Pattern;

public class PestañaAgregar extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private FirebaseAuth mAuth;

    private TextView textoUsuario;
    private TextView textoApellido;
    private TextView textoDocumento;
    private TextView textoTelefono;
    private TextView textoEmail;
    private Spinner sexoSpinner;
    private CheckBox profesor;
    private TextView textoPass;
    private TextView errorAgregar;

    private int defaultTextColor;

    public PestañaAgregar() {
        // Required empty public constructor
    }
    FirebaseDatabase rootNode;
    DatabaseReference reference;

    private void pintaDefault() {
        textoUsuario.setBackgroundColor(defaultTextColor);
        textoApellido.setBackgroundColor(defaultTextColor);
        textoDocumento.setBackgroundColor(defaultTextColor);
        textoTelefono.setBackgroundColor(defaultTextColor);
        textoEmail.setBackgroundColor(defaultTextColor);
        textoPass.setBackgroundColor(defaultTextColor);
    }

    private boolean pintaErrores(String user, String apellido, String documento, String telefono, String email, String sexo, String password) {
        Pattern pattern=Patterns.EMAIL_ADDRESS;

        Boolean hayErrores = false;
        if (user.isEmpty() || user.matches(".*[0-9].*") || user.length()>50) {
            textoUsuario.setBackgroundColor(Color.RED);
            hayErrores = true;
        }
        if (apellido.isEmpty() || apellido.matches(".*[0-9].*") || apellido.length()>50) {
            textoApellido.setBackgroundColor(Color.RED);
            hayErrores = true;
        }
        if (documento.isEmpty() || documento.length()==8) {
            textoDocumento.setBackgroundColor(Color.RED);
            hayErrores = true;
        }
        if (telefono.isEmpty() || telefono.length()>15) {
            textoTelefono.setBackgroundColor(Color.RED);
            hayErrores = true;
        }
        if (email.isEmpty() || !pattern.matcher(email).matches()) {
            textoEmail.setBackgroundColor(Color.RED);
            hayErrores = true;
        }
        if (password.isEmpty()) {
            textoPass.setBackgroundColor(Color.RED);
            hayErrores = true;
        }
        return hayErrores;
    }


    private void agregaUsuario(View view) {


        rootNode = FirebaseDatabase.getInstance();

        pintaDefault();
        errorAgregar.setVisibility(View.INVISIBLE);

        String user = textoUsuario.getText().toString();
        String apellido = textoApellido.getText().toString();
        String documento = textoDocumento.getText().toString();
        String telefono = textoTelefono.getText().toString();
        String email = textoEmail.getText().toString();
        String sexo = (String) sexoSpinner.getSelectedItem();
        String password = textoPass.getText().toString();
        Boolean esProfesor = profesor.isChecked();



        if (pintaErrores(user, apellido, documento, telefono, email, sexo, password)) {
            errorAgregar.setVisibility(View.VISIBLE);
            return;
        }
        else{
            Toast.makeText(getContext(),"El usuario ha sido agregado con éxito",Toast.LENGTH_LONG).show();
        }

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

        mAuth.createUserWithEmailAndPassword(email, password)
        .addOnCompleteListener(task -> {
            if (task.isSuccessful()){
                //mListener.onSignUpDone();
                System.out.println("anduvo");
            }else{
                //Utility.showDialog(getActivity(), task);
                System.out.println("no");
            }

        });
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
        // ...
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if(currentUser != null){
            currentUser.reload();
        }


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

        errorAgregar = view.findViewById(R.id.errorAgregar);

        button.setOnClickListener(view1 -> agregaUsuario(view1));

        defaultTextColor = textoApellido.getSolidColor();


        return view;
    }
}