package com.utn.palestrafitness;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.util.Base64;
import android.view.Gravity;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.common.collect.Table;

public class Rutina extends AppCompatActivity {

    TableLayout tabla;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rutina);

        tabla = findViewById(R.id.tablaEjercicios);
        for (int i = 0; i < 4; i++) {
            //Agrego la semana
            TextView semana = new TextView(this);
            semana.setTextSize(21);
            semana.setText("Semana " + (i + 1) + " Serie/Rep:");
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

                dia.setTextSize(21);
                peso.setTextSize(21);
                dia.setWidth(500);
                peso.setWidth(150);

                filaEncabezado.addView(dia);
                filaEncabezado.addView(peso);
            }
            System.out.println(filaEncabezado.getChildCount());
            tabla.addView(filaEncabezado);
            //Agrego los EditText para los ejercicios

            for (int j = 0; j < 5; j++) {
                TableRow filaEjercicio = new TableRow(this);
                for (int k = 0; k < 6; k++) {
                    EditText textoEditable = new EditText(this);
                    textoEditable.setTextColor(Color.BLACK);
                    if (k % 2 != 0) textoEditable.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_SIGNED);
                    textoEditable.setBackgroundResource(R.drawable.border);
                    filaEjercicio.addView(textoEditable);
                }
                tabla.addView(filaEjercicio);
            }

        }
    }
}