package com.utn.palestrafitness.lib;

import java.sql.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Rutina {

    private ArrayList<ArrayList<Ejercicio>> ejercicios;
    private Integer cantidadDias;

    public void pushEjercicio(Ejercicio ejercicio, Integer semana, Integer dia) {
        if (ejercicios.get(semana) == null) ejercicios.set(semana, new ArrayList<>());
        ejercicios.get(semana).set(dia, ejercicio);
    }


    public ArrayList<ArrayList<Ejercicio>> getEjercicios() {
        return ejercicios;
    }

    public void setEjercicios(ArrayList<ArrayList<Ejercicio>> ejercicios) {
        this.ejercicios = ejercicios;
    }

    public Integer getCantidadDias() {
        return cantidadDias;
    }

    public void setCantidadDias(Integer cantidadDias) {
        this.cantidadDias = cantidadDias;
    }

    public Rutina () {
        ejercicios = new ArrayList<>();
    }

    public Rutina (int cantidadDias) {
        this.cantidadDias = cantidadDias;
        ejercicios = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            ejercicios.add(new ArrayList<>());
            ejercicios.get(i).add(new Ejercicio());
        }
    }

}
