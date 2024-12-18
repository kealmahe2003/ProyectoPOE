package modelo;

public interface OperacionesMilitares {
    public void asignarMision(String mision);
    String reportarEstado(); //Reporta el estado actual del soldado en relación a la misión. 

    String getMision(); //Devuelve la misión asignada al soldado.

}
