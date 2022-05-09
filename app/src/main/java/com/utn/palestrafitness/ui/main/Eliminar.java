package com.utn.palestrafitness.ui.main;

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
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.utn.palestrafitness.Alumno;
import com.utn.palestrafitness.R;

import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Eliminar#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Eliminar extends Fragment {

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

    private Button button;
    private int defaultColor;
    private TableLayout tablaClientes;

    public Eliminar() {
        // Required empty public constructor
    }

    public void buscarAlumno() {
        System.out.println("me llamaron");
        FirebaseDatabase rootRef = FirebaseDatabase.getInstance();
        dni = textoDni.getText().toString();
        this.usuario = textoUsuario.getText().toString();
        this.apellido = textoApellido.getText().toString();
        Query consulta = rootRef.getReference().child("Usuario/Alumno/" + dni + "/");

        tablaEliminar.removeViews(1, tablaEliminar.getChildCount() - 1);


        if (!dni.isEmpty()) {
            consulta.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {


                        System.out.println("Entrando1");

                        HashMap esteAlumno = ((HashMap) snapshot.getValue());

                        TableRow fila = new TableRow(thisView.getContext());
                        TextView nombre = new TextView(thisView.getContext());
                        nombre.setTextSize(21);
                        TextView apellido = new TextView(thisView.getContext());
                        apellido.setTextSize(21);
                        TextView documento = new TextView(thisView.getContext());
                        documento.setTextSize(21);
                        TextView estado = new TextView(thisView.getContext());

                        fila.setClickable(true);
                        for (int i = 0; i < tablaEliminar.getChildCount(); i++) {
                            tablaEliminar.getChildAt(i).setBackgroundColor(defaultColor);
                        }

                        fila.setOnClickListener(v -> {
                            fila.setBackgroundColor(Color.GRAY);
                            seleccionado = fila;
                        });
                        fila.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

                        nombre.setText((String) esteAlumno.get("usuario"));
                        apellido.setText((String) esteAlumno.get("apellido"));
                        documento.setText((String) esteAlumno.get("documento"));
                        estado.setBackgroundColor((Boolean) (esteAlumno.get("esAlumnoActivo")) ? Color.GREEN : Color.RED);

                        fila.addView(nombre);
                        fila.addView(apellido);
                        fila.addView(documento);
                        fila.addView(estado);
                        fila.setGravity(Gravity.CENTER);
                        if (documento.getText().toString() != "") tablaEliminar.addView(fila);
                    } else System.out.println("no"); //todo: implementar
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }

        Query consultaPorNombre = rootRef.getReference().child("Usuario/Alumno/");

        if (dni.isEmpty()) {
            consultaPorNombre.addValueEventListener(new ValueEventListener() {
                String textoApellido = apellido;
                String textoNombre = usuario;

                @RequiresApi(api = Build.VERSION_CODES.N)
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        HashMap encontrados = ((HashMap) snapshot.getValue());
                        encontrados.forEach((documentoClave, esteAlumno) -> {
                            System.out.println(documentoClave);

                            TableRow fila = new TableRow(thisView.getContext());
                            TextView nombre = new TextView(thisView.getContext());
                            nombre.setTextSize(21);
                            TextView apellido = new TextView(thisView.getContext());
                            apellido.setTextSize(21);
                            TextView documento = new TextView(thisView.getContext());
                            documento.setTextSize(21);
                            TextView estado = new TextView(thisView.getContext());

                            fila.setClickable(true);
                            fila.setOnClickListener(v -> {
                                for (int i = 0; i < tablaEliminar.getChildCount(); i++) {
                                    tablaEliminar.getChildAt(i).setBackgroundColor(defaultColor);
                                }
                                fila.setBackgroundColor(Color.GRAY);
                                seleccionado = fila;
                            });

                            HashMap alumno = (HashMap) esteAlumno;

                            //System.out.println(alumno);
                            //System.out.println(((String) alumno.get(apellido)));");

                            if ((textoApellido.equals("") && textoNombre.equals(alumno.get("usuario"))) || (textoNombre.equals("") && alumno.get("apellido").equals(this.textoApellido)) || (textoNombre.equals(alumno.get("usuario")) && textoApellido.equals(alumno.get("apellido")))) {
                                nombre.setText((String) alumno.get("usuario"));
                                apellido.setText((String) alumno.get("apellido"));
                                documento.setText((String) alumno.get("documento"));
                                estado.setBackgroundColor((Boolean) (alumno.get("esAlumnoActivo")) ? Color.GREEN : Color.RED);

                                fila.addView(nombre);
                                fila.addView(apellido);
                                fila.addView(documento);
                                fila.addView(estado);
                                fila.setGravity(Gravity.CENTER );
                                System.out.println("Agrego " + nombre.getText().toString());
                                tablaEliminar.addView(fila);
                            }
                        });
                    } else System.out.println("no"); //todo: implementar
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

                Alumno datosAlumno = snapshot.getValue(Alumno.class);


                datosAlumno.setEsAlumnoActivo(!datosAlumno.getEsAlumnoActivo());

                snapshot.getRef().setValue(datosAlumno);

                tablaEliminar.removeViews(1, tablaEliminar.getChildCount() - 1);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }





    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Eliminar.
     */
    // TODO: Rename and change types and number of parameters
    public static Eliminar newInstance(String param1, String param2) {
        Eliminar fragment = new Eliminar();
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


        View view = inflater.inflate(R.layout.fragment_eliminar, container, false);

        suspenderBoton = view.findViewById(R.id.suspenderBoton);

        tablaClientes = view.findViewById(R.id.tablaEliminar);

        button = view.findViewById(R.id.botonBuscar);

        button.setOnClickListener(view1 -> buscarAlumno());

        textoDni = view.findViewById(R.id.textoDNI);

        tablaEliminar = view.findViewById(R.id.tablaEliminar);

        textoUsuario = view.findViewById(R.id.nombreBaja);

        textoApellido = view.findViewById(R.id.apellidoBaja);

        thisView = view;

        seleccionado = null;

        suspenderBoton.setOnClickListener(i -> suspenderAlumno());

        defaultColor = tablaEliminar.getSolidColor();

        return view;
    }
}