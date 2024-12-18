package modelo;

import java.util.Random;

public abstract class Rango {

    protected int nivel;
    private String mision;
    public Random random = new Random();

    // Constructor que inicializa el nivel y la misión
    public Rango(int nivel) {
        this.nivel = nivel;
        this.mision = "Sin misión asignada";
    }

    // Método para asignar una misión
    public void asignarMision(String mision) {
        this.mision = mision;
    }

    // Método para obtener la misión actual
    public String getMision() {
        return mision;
    }

    // Método para reportar el estado actual
    public String reportarEstado() {
        return "Misión actual: " + mision;
    }

    // Método para obtener el nivel del rango
    public int getNivel() {
        return nivel;
    }

    // Método para establecer el nivel del rango
    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    // Método para establecer la misión
    public void setMision(String mision) {
        this.mision = mision;
    }

    // Método para obtener el objeto Random
    public Random getRandom() {
        return random;
    }

    // Método para establecer el objeto Random
    public void setRandom(Random random) {
        this.random = random;
    }

    // Método abstracto que debe ser implementado por las clases que extienden Rango
    public abstract String realizarAccion();
}