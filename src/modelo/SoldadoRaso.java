package modelo;

import java.awt.HeadlessException;
import javax.swing.JOptionPane;


// La clase SoldadoRaso con las implementaciones de OperacionesMilitares
public class SoldadoRaso extends Soldado implements OperacionesMilitares {

    private String mision;
    private String message;

    public SoldadoRaso(String nombre, String id) {
        super(nombre, id, "Soldado Raso", "Activo");
        this.mision = "Sin asignar";
    }

    // Aquí implementamos las cosas del Soldado Raso para que pueda almacenar, imprimir y modificar su información
    @Override
    public void mostrarInfo() {
        super.mostrarInfo();
    }

    public void realizarAccion() {
        try {
            // Genera un número random del 1 al 3
            int randomNum = new java.util.Random().nextInt(3) + 1;
            // Si el número es 1, se va a entrenar
            if (randomNum == 1) {
                message = "El Soldado Raso se encuentra entrenando.";
            }
            // Si el número es 2, se va a descansar
            if (randomNum == 2) {
                message = "El Soldado Raso se encuentra descansando.";
            }
            // Si el número es 3, se va a patrullar
            if (randomNum == 3) {
                message = "El Soldado Raso se encuentra patrullando.";
            }
            JOptionPane.showMessageDialog(null, message, "Soldado", JOptionPane.OK_CANCEL_OPTION);
        } catch (HeadlessException e) {
            JOptionPane.showMessageDialog(null, "El Soldado Raso no ha podido realizar ninguna acción.");
            System.out.println(e);
        }
    }

    @Override
    public String reportarEstado() {
        return "El soldado " + getId() + " se encuentra activo";
    }

    // Método para asignar misión
    @Override
    public void asignarMision(String mision) {
        this.mision = mision;
    }

    @Override
    public String getMision() {
        return mision;
    }
}