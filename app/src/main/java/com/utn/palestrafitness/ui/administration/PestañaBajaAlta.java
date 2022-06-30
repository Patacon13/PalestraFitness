package com.utn.palestrafitness.ui.administration;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.utn.palestrafitness.lib.Alumno;
import com.utn.palestrafitness.R;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;


public class PestañaBajaAlta extends Fragment {

    TableLayout tablaEliminar;

    View thisView;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private Button suspenderBoton;
    private String mParam1;
    private String mParam2;
    private String dni;
    private String usuario;
    private String apellido;

    private TableRow seleccionado;


    private TextView textoDni;
    private TextView textoUsuario;
    private TextView textoApellido;
    private TextView textoBaja;

    private Button button;
    private int defaultColor;

    public PestañaBajaAlta() {
        // Required empty public constructor
    }

    public void buscarAlumno() {
        textoBaja.setVisibility(View.INVISIBLE);
        FirebaseDatabase rootRef = FirebaseDatabase.getInstance();
        dni = textoDni.getText().toString();
        this.usuario = textoUsuario.getText().toString();
        this.apellido = textoApellido.getText().toString();
        Query consulta = rootRef.getReference().child("Usuario/Alumno/" + dni + "/");

        tablaEliminar.removeViews(1, tablaEliminar.getChildCount() - 1);

        if (!dni.isEmpty()) {
            consulta.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {

                        Alumno esteAlumno = snapshot.getValue(Alumno.class);

                        TableRow.LayoutParams param = new TableRow.LayoutParams(
                                TableRow.LayoutParams.MATCH_PARENT,
                                TableRow.LayoutParams.MATCH_PARENT,
                                1
                        );

                        TableRow fila = new TableRow(thisView.getContext());
                        TextView nombre = new TextView(thisView.getContext());
                        nombre.setTextSize(16);
                        nombre.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                                TableRow.LayoutParams.MATCH_PARENT));
                        nombre.setGravity(Gravity.CENTER);
                        TextView apellido = new TextView(thisView.getContext());
                        apellido.setTextSize(16);
                        apellido.setLayoutParams(param);
                        apellido.setGravity(Gravity.CENTER);
                        TextView documento = new TextView(thisView.getContext());
                        documento.setTextSize(16);
                        documento.setLayoutParams(param);
                        documento.setGravity(Gravity.CENTER);
                        TextView estado = new TextView(thisView.getContext());
                        estado.setLayoutParams(param);
                        estado.setGravity(Gravity.CENTER);

                        fila.setClickable(true);
                        for (int i = 0; i < tablaEliminar.getChildCount(); i++) {
                            tablaEliminar.getChildAt(i).setBackgroundColor(defaultColor);
                        }

                        fila.setOnClickListener(v -> {
                            fila.setBackgroundColor(Color.GRAY);
                            seleccionado = fila;
                        });
                        fila.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

                        nombre.setText(esteAlumno.getUsuario());
                        apellido.setText(esteAlumno.getApellido());
                        documento.setText(esteAlumno.getDocumento());
                        estado.setBackgroundColor((esteAlumno.getEsAlumnoActivo()) ? Color.GREEN : Color.RED);

                        fila.addView(nombre);
                        fila.addView(apellido);
                        fila.addView(documento);
                        fila.addView(estado);
                        fila.setGravity(Gravity.CENTER);
                        if (documento.getText().toString() != "") tablaEliminar.addView(fila);
                    } else textoBaja.setVisibility(View.VISIBLE); //todo: implementar
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }


            });
        }

        Query consultaPorNombre = rootRef.getReference().child("Usuario/Alumno/");

        if (dni.isEmpty()) {
            consultaPorNombre.addListenerForSingleValueEvent(new ValueEventListener() {
                String textoApellido = apellido;
                String textoNombre = usuario;

                @RequiresApi(api = Build.VERSION_CODES.N)
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    AtomicReference<Boolean> encontreAlguno = new AtomicReference<>(Boolean.FALSE);
                    if (snapshot.exists()) {
                        HashMap encontrados = ((HashMap) snapshot.getValue());
                        for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                            Alumno esteAlumno = postSnapshot.getValue(Alumno.class);
                            TableRow.LayoutParams param = new TableRow.LayoutParams(
                                    TableRow.LayoutParams.MATCH_PARENT,
                                    TableRow.LayoutParams.MATCH_PARENT,
                                    1
                            );
                            TableRow fila = new TableRow(thisView.getContext());
                            TextView nombre = new TextView(thisView.getContext());
                            nombre.setTextSize(16);
                            nombre.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                                    TableRow.LayoutParams.MATCH_PARENT));
                            nombre.setGravity(Gravity.CENTER);
                            TextView apellido = new TextView(thisView.getContext());
                            apellido.setTextSize(16);
                            apellido.setLayoutParams(param);
                            apellido.setGravity(Gravity.CENTER);
                            TextView documento = new TextView(thisView.getContext());
                            documento.setTextSize(16);
                            documento.setLayoutParams(param);
                            documento.setGravity(Gravity.CENTER);
                            TextView estado = new TextView(thisView.getContext());
                            estado.setLayoutParams(param);
                            estado.setGravity(Gravity.CENTER);


                            fila.setClickable(true);
                            fila.setOnClickListener(v -> {
                                for (int i = 0; i < tablaEliminar.getChildCount(); i++) {
                                    tablaEliminar.getChildAt(i).setBackgroundColor(defaultColor);
                                }
                                fila.setBackgroundColor(Color.GRAY);
                                seleccionado = fila;
                            });

                            //System.out.println(alumno);
                            //System.out.println(((String) alumno.get(apellido)));");

                            if ((textoApellido.equals("") && textoNombre.equals(esteAlumno.getUsuario())) || (textoNombre.equals("") && esteAlumno.getApellido().equals(this.textoApellido)) || (textoNombre.equals(esteAlumno.getUsuario())) && textoApellido.equals(esteAlumno.getApellido())) {
                                nombre.setText((String) esteAlumno.getUsuario());
                                apellido.setText((String) esteAlumno.getApellido());
                                documento.setText((String) esteAlumno.getDocumento());
                                estado.setBackgroundColor((Boolean) (esteAlumno.getEsAlumnoActivo()) ? Color.GREEN : Color.RED);

                                fila.addView(nombre);
                                fila.addView(apellido);
                                fila.addView(documento);
                                fila.addView(estado);
                                System.out.println("Agrego " + nombre.getText().toString());
                                tablaEliminar.addView(fila);
                                encontreAlguno.set(Boolean.TRUE);
                            }
                        }
                        if (!encontreAlguno.get()) {
                            textoBaja.setVisibility(View.VISIBLE);
                        }
                    } else textoBaja.setVisibility(View.VISIBLE); //todo: implementar
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }

    public void suspenderAlumno() {


        FirebaseDatabase rootRef = FirebaseDatabase.getInstance();

        Query consultaPorAlumno = rootRef.getReference().child("Usuario/Alumno/" + ((TextView) seleccionado.getChildAt(2)).getText().toString() + "/");

        consultaPorAlumno.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                if (snapshot.exists()) {
                    Alumno datosAlumno = snapshot.getValue(Alumno.class);


                    datosAlumno.setEsAlumnoActivo(!datosAlumno.getEsAlumnoActivo());

                    snapshot.getRef().setValue(datosAlumno);
                    seleccionado.getChildAt(3).setBackgroundColor(datosAlumno.getEsAlumnoActivo() ? Color.GREEN : Color.RED);
                }
                else {

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

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


        View view = inflater.inflate(R.layout.fragment_eliminar, container, false);

        suspenderBoton = view.findViewById(R.id.suspenderBoton);

        button = view.findViewById(R.id.botonBuscar);

        button.setOnClickListener(view1 -> buscarAlumno());

        textoDni = view.findViewById(R.id.textoDNI);

        tablaEliminar = view.findViewById(R.id.LinearEliminar);

        textoUsuario = view.findViewById(R.id.nombreBaja);

        textoApellido = view.findViewById(R.id.apellidoBaja);

        textoBaja = view.findViewById(R.id.textoErrorBaja);

        thisView = view;

        seleccionado = null;

        suspenderBoton.setOnClickListener(i -> suspenderAlumno());

        defaultColor = tablaEliminar.getSolidColor();

        return view;
    }
}