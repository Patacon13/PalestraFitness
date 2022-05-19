package com.utn.palestrafitness.ui.rutina;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.Gravity;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.utn.palestrafitness.R;
import com.utn.palestrafitness.lib.Alumno;
import com.utn.palestrafitness.lib.Ejercicio;
import com.utn.palestrafitness.lib.Rutina;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RutinaActivity extends AppCompatActivity {

    Query busquedaRutina;

    private void llenarTabla (Rutina rutinaDelUsuario, ValueEventListener esteListener, DatabaseReference reference) {
        busquedaRutina.removeEventListener(esteListener);

        for (int i = 0; i < 4; i++) {

            ArrayList<Ejercicio> ejerciciosDeLaSemana = rutinaDelUsuario.getEjercicios().get(Integer.valueOf(i));


            //Agrego la semana
            TextView semana = new TextView(this);
            semana.setTextSize(21);
            semana.setText("Semana " + (i + 1));
            semana.setGravity(Gravity.CENTER);
            semana.setTextColor(Color.BLUE);
            semana.setBackgroundResource(R.drawable.border);
            tabla.addView(semana);

            //Agrego el encabezado
            TableRow filaEncabezado = new TableRow(this);
            filaEncabezado.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
            filaEncabezado.setBackgroundResource(R.drawable.border);
            for (int j = 0; j < 3; j++) {
                TextView peso = new TextView(this);
                peso.setText("Peso");
                TextView dia = new TextView(this);
                dia.setText("Dia " + (j + 1));
                TextView series = new TextView(this);
                series.setText("Series");
                TextView repeticiones = new TextView(this);
                repeticiones.setText("Repeticiones");

                dia.setTextSize(21);
                dia.setTextColor(Color.BLACK);
                peso.setTextSize(21);
                peso.setTextColor(Color.BLACK);
                series.setTextSize(21);
                series.setTextColor(Color.BLACK);
                repeticiones.setTextSize(21);
                repeticiones.setTextColor(Color.BLACK);
                dia.setWidth(500);
                peso.setWidth(200);
                series.setWidth(250);
                repeticiones.setWidth(380);

                filaEncabezado.addView(dia);
                filaEncabezado.addView(series);
                filaEncabezado.addView(repeticiones);
                filaEncabezado.addView(peso);
            }
            System.out.println(filaEncabezado.getChildCount());
            tabla.addView(filaEncabezado);
            //Agrego los EditText para los ejercicios

            for (int j = 0; j < 5; j++) {
                TableRow filaEjercicio = new TableRow(this);

                System.out.println(ejerciciosDeLaSemana.size());


                for (int k = 0; k < 12; k++) {

                    if (k % 4 == 0) {
                        if (ejerciciosDeLaSemana.size() <= (3*j) + (k / 4)) {
                            Ejercicio esteEjercicio = new Ejercicio();
                            ejerciciosDeLaSemana.add(esteEjercicio);
                        }
                    }
                    Ejercicio ejercicioDeEsteDia = ejerciciosDeLaSemana.get(Integer.valueOf((3*j) + (k / 4))); //harcodeada cantidad de dias


                    if (k % 4 == 0) {

                        EditText ejercicio = new EditText(this);
                        ejercicio.setText(ejercicioDeEsteDia.getDia());
                        ejercicio.setTextColor(Color.BLACK);
                        ejercicio.setBackgroundResource(R.drawable.border);
                        filaEjercicio.addView(ejercicio);

                        ejercicio.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                            }

                            @Override
                            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                            }

                            @Override
                            public void afterTextChanged(Editable editable) {
                                ejercicioDeEsteDia.setDia(editable.toString());
                                reference.setValue(rutinaDelUsuario);
                            }
                        });
                    }
                    else if (k % 4 == 1) { //serie

                        EditText series = new EditText(this);
                        series.setText(ejercicioDeEsteDia.getCantSeries());
                        series.setTextColor(Color.BLACK);
                        series.setBackgroundResource(R.drawable.border);
                        filaEjercicio.addView(series);

                        series.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                            }

                            @Override
                            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                            }

                            @Override
                            public void afterTextChanged(Editable editable) {
                                ejercicioDeEsteDia.setCantSeries(editable.toString());
                                reference.setValue(rutinaDelUsuario);
                            }
                        });

                    }
                    else if (k % 4 == 2) { //repeticion

                        EditText repeticiones = new EditText(this);
                        repeticiones.setText(ejercicioDeEsteDia.getCantRepeticiones());
                        repeticiones.setTextColor(Color.BLACK);
                        repeticiones.setBackgroundResource(R.drawable.border);
                        filaEjercicio.addView(repeticiones);

                        repeticiones.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                            }

                            @Override
                            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                            }

                            @Override
                            public void afterTextChanged(Editable editable) {
                                ejercicioDeEsteDia.setCantRepeticiones(editable.toString());
                                reference.setValue(rutinaDelUsuario);
                            }
                        });

                    }
                    else if (k % 4 == 3) { //peso
                        EditText peso = new EditText(this);
                        peso.setText(ejercicioDeEsteDia.getPeso());
                        peso.setTextColor(Color.BLACK);
                        peso.setBackgroundResource(R.drawable.border);
                        filaEjercicio.addView(peso);

                        peso.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                            }

                            @Override
                            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                            }

                            @Override
                            public void afterTextChanged(Editable editable) {
                                ejercicioDeEsteDia.setPeso(editable.toString());
                                reference.setValue(rutinaDelUsuario);
                            }
                        });
                    }
                }
                tabla.addView(filaEjercicio);
            }

        }
    }

    TableLayout tabla;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rutina);
        Bundle extras = getIntent().getExtras();

        String documento = extras.getString("documento");

        FirebaseDatabase rootRef = FirebaseDatabase.getInstance();

        busquedaRutina = rootRef.getReference().child("Usuario/Rutina/" + documento + "/");

        ValueEventListener listenerBusqueda = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists()) {

                    Rutina rutinaDelUsuario = snapshot.getValue(Rutina.class);


                    DatabaseReference reference = rootRef.getReference("Usuario/Rutina/" + documento + "/");

                    llenarTabla(rutinaDelUsuario, this, reference);
                    System.out.println("LA ENCONTREEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE");
                }
                else {
                    Rutina rutina = new Rutina(documento);
                    DatabaseReference reference = rootRef.getReference("Usuario/Rutina/");
                    System.out.println("gierogjeroigjer");
                    reference.child(documento).setValue(rutina);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };

        busquedaRutina.addValueEventListener(listenerBusqueda);

        tabla = findViewById(R.id.tablaEjercicios);

    }
}