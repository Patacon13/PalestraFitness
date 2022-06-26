package com.utn.palestrafitness.ui.rutina;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.preference.EditTextPreference;
import androidx.preference.PreferenceManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.utn.palestrafitness.ConfiguracionFragment;
import com.utn.palestrafitness.R;
import com.utn.palestrafitness.SettingsActivity;
import com.utn.palestrafitness.lib.Alumno;
import com.utn.palestrafitness.lib.Ejercicio;
import com.utn.palestrafitness.lib.Rutina;
import com.utn.palestrafitness.lib.Usuario;
import com.utn.palestrafitness.ui.administration.AdministrationActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RutinaActivity extends AppCompatActivity {

    Query busquedaRutina;
    Query busquedaUsuario;
    FirebaseDatabase rootRef;

    Menu menuDeEstaRutina;

    Usuario usuario;
    Rutina rutinaDelUsuario;

    DatabaseReference referenciaRutina;
    DatabaseReference referenciaUsuario;

    public void abreConfiguracion() {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    private void llenarTabla (Rutina rutinaDelUsuario) {
        tabla.removeAllViews();
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
            for (int j = 0; j < rutinaDelUsuario.getCantidadDias(); j++) {
                TextView peso = new TextView(this);
                peso.setText("Peso");
                TextView dia = new TextView(this);
                dia.setText("Dia " + (j + 1));
                TextView series = new TextView(this);
                series.setText("Series");
                TextView repeticiones = new TextView(this);
                repeticiones.setText("Repeticiones");

                dia.setTextSize(21);
                dia.setTextColor(Color.CYAN);
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


                for (int k = 0; k < rutinaDelUsuario.getCantidadDias()*4; k++) {

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
                                referenciaRutina.setValue(rutinaDelUsuario);
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
                                referenciaRutina.setValue(rutinaDelUsuario);
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
                                referenciaRutina.setValue(rutinaDelUsuario);
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
                                referenciaRutina.setValue(rutinaDelUsuario);
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
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        String documento = extras.getString("documento");

        rootRef = FirebaseDatabase.getInstance();

        busquedaRutina = rootRef.getReference().child("Usuario/Rutina/" + documento + "/");

        busquedaUsuario = rootRef.getReference().child("Usuario/Alumno/" + documento + "/");

        ValueEventListener listenerBusqueda = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists()) {

                    rutinaDelUsuario = snapshot.getValue(Rutina.class);


                    referenciaRutina = rootRef.getReference("Usuario/Rutina/" + documento + "/");

                    llenarTabla(rutinaDelUsuario);
                    if (usuario != null) editMenuOptions();
                    System.out.println("LA ENCONTREEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE");
                }
                else {
                    Rutina rutina = new Rutina(3);;
                    System.out.println("gierogjeroigjer");
                    referenciaRutina.child(documento).setValue(rutina);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };

        busquedaRutina.addListenerForSingleValueEvent(listenerBusqueda);

        busquedaUsuario.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    usuario = snapshot.getValue(Usuario.class);
                    referenciaUsuario = rootRef.getReference("Usuario/Alumno/" + usuario.getDocumento() + "/");
                    if (rutinaDelUsuario != null) editMenuOptions();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        tabla = findViewById(R.id.tablaEjercicios);

    }

    public boolean editMenuOptions () {

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

        SharedPreferences.Editor editor = prefs.edit();

        editor.putString("dias", rutinaDelUsuario.getCantidadDias().toString());
        editor.putString("documento", usuario.getDocumento());
        editor.putString("nombre", usuario.getUsuario());
        editor.putString("apellido", usuario.getApellido());
        editor.putString("sexo", usuario.getSexo());
        editor.commit();

        return super.onPrepareOptionsMenu(menuDeEstaRutina);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menuDeEstaRutina = menu;
        SharedPreferences.OnSharedPreferenceChangeListener listener;


        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

        listener = ((prefs1, key) -> {
            if (key.equals("dias")) {
                System.out.println("Encontrado " + prefs1.getString("dias", "3"));
                rutinaDelUsuario.setCantidadDias(Integer.valueOf(prefs1.getString("dias", "3") ));
                referenciaRutina.setValue(rutinaDelUsuario);
                llenarTabla(rutinaDelUsuario);
            } else if (key.equals("documento")) {
                System.out.println("EncontradoD " + prefs1.getString("documento", ""));
                String nuevoDocumento = prefs1.getString("documento", "");
                usuario.setDocumento(nuevoDocumento);
                referenciaUsuario.removeValue();
                referenciaRutina.removeValue();


                DatabaseReference referenciaCrearAlumno = rootRef.getReference("Usuario/Alumno");
                DatabaseReference referenciaCrearRutina = rootRef.getReference("Usuario/Rutina");

                referenciaCrearAlumno.child(nuevoDocumento).setValue(usuario);
                referenciaCrearRutina.child(nuevoDocumento).setValue(rutinaDelUsuario);

                referenciaRutina = rootRef.getReference("Usuario/Rutina/" + nuevoDocumento + "/");
                referenciaUsuario = rootRef.getReference("Usuario/Alumno/" + nuevoDocumento + "/");

            } else if (key.equals("nombre")) {
                usuario.setUsuario(prefs1.getString("nombre", ""));
                referenciaUsuario.setValue(usuario);
            } else if (key.equals("apellido")) {
                usuario.setUsuario(prefs1.getString("apellido", ""));
                referenciaUsuario.setValue(usuario);

            } else if (key.equals("sexo")) {
                usuario.setApellido(prefs1.getString("sexo", ""));
                referenciaUsuario.setValue(usuario);

            } else if (key.equals("contrasena")) {
                Bundle extras = getIntent().getExtras();
                FirebaseUser user = (FirebaseUser) extras.get("currentUser");
                user.updatePassword(prefs1.getString("contrasena", "")).addOnCompleteListener(completed ->
                        Toast.makeText(this,"Contraseña modificada exitosamente",Toast.LENGTH_LONG).show());
            }
        });

        prefs.registerOnSharedPreferenceChangeListener(listener);

        getMenuInflater().inflate(R.menu.menu_rutina, menu);


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        abreConfiguracion();
        return super.onOptionsItemSelected(item);
    }
}