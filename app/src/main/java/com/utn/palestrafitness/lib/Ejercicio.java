package com.utn.palestrafitness.lib;

public class Ejercicio {
    String dia, cantSeries, cantRepeticiones, peso;

    public String getDia() {
        return dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }

    public String getCantSeries() {
        return cantSeries;
    }

    public void setCantSeries(String cantSeries) {
        this.cantSeries = cantSeries;
    }

    public String getCantRepeticiones() {
        return cantRepeticiones;
    }

    public void setCantRepeticiones(String cantRepeticiones) {
        this.cantRepeticiones = cantRepeticiones;
    }

    public String getPeso() {
        return peso;
    }

    public void setPeso(String peso) {
        this.peso = peso;
    }

    public Ejercicio(String dia, String cantSeries, String cantRepeticiones, String peso) {
        this.dia = dia;
        this.cantSeries = cantSeries;
        this.cantRepeticiones = cantRepeticiones;
        this.peso = peso;
    }

    public Ejercicio() {
        dia = "";
        cantSeries = "";
        cantRepeticiones = "";
        peso = "";
    }
}
