package modelo;

import java.util.Random;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class Capitan extends Rango implements OperacionesMilitares, Persona {

    private int cantSoldados;
    private String id;
    private String nombre;
    private String rango;
    private String mision;
    private Random random = new Random();
    private String message;
    public Capitan() {
        super(3); // Se asigna el rango con un número para darle valor numérico superior a quienes manda
        this.id = "";
        this.nombre = "";
        this.cantSoldados = 0;
        this.rango = "Capitán";
        this.mision = "Sin asignar";
    }
    public Capitan(String id, String nombre, String cualidad) {
        super(3); // Se asigna el rango con un número para darle valor numérico superior a quienes manda
        this.id = id;
        this.nombre = nombre;
        this.cantSoldados = 0;
        this.rango = "Capitán";
        this.mision = "Sin asignar";
    }

    // Diversas tareas para que la gestión de las misiones funcione correctamente
    @Override
    public String realizarAccion() {
        return "El Capitán realiza un sondeo a sus: " + cantSoldados + " soldados";
    }

    @Override
    public String reportarEstado() {
        if (random.nextBoolean()) {
            return "El Capitán lidera la misión con sus " + cantSoldados + " soldados... ¡La misión fue exitosa!";
        } else {
            int soldadosPerdidos = random.nextInt(cantSoldados + 1);
            return "El Capitán lidera la misión con sus " + cantSoldados + " soldados... La misión fracasó. Soldados perdidos: " + soldadosPerdidos + "/" + cantSoldados;
        }
    }

    public String planificarMision(String objetivo, String estrategia) {
        // Panel para ingresar detalles de la misión
        JTextField objetivoField = new JTextField(objetivo);
        JTextField estrategiaField = new JTextField(estrategia);
        Object[] message = {
            "Ingrese el objetivo del rescate:", objetivoField,
            "Ingrese la estrategia:", estrategiaField
        };

        // Se muestra el mensaje de la misión
        int option = JOptionPane.showConfirmDialog(null, message, "Planificación de Rescate", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            // Se evalúa si se ingresaron los datos
            String objetivoTexto = objetivoField.getText();
            String estrategiaTexto = estrategiaField.getText();
            if (objetivoTexto.isEmpty() || estrategiaTexto.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Debe ingresar todos los datos.", "Error", JOptionPane.ERROR_MESSAGE);
                return "Error: Datos incompletos.";
            }

            // Resultado de la misión
            String mensaje;
            int resultado = random.nextInt(2); // 0: éxito, 1: fracaso
            if (resultado == 0) {
                mensaje = "La misión de rescate fue un éxito. El objetivo \n" + objetivoTexto + "\n ha sido asegurado!";
            } else {
                mensaje = "La misión de rescate falló. La estrategia \n" + estrategiaTexto + "\n no funcionó.";
            }

            JOptionPane.showConfirmDialog(null, mensaje, "Resultado", JOptionPane.OK_OPTION);
            return mensaje;
        }
        return "Planificación de misión cancelada.";
    }

    public int getCantSoldados() {
        return cantSoldados;
    }

    public void setCantSoldados(int cantSoldados) {
        this.cantSoldados = cantSoldados;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getId() {
        return id.trim();
    }

    @Override
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String getNombre() {
        return nombre;
    }

    @Override
    public void setRango(String rango) {
        this.rango = rango;
    }

    @Override
    public String getRango() {
        return rango;
    }

    @Override
    public void setCualidad(String cualidad) {
        this.cantSoldados = Integer.parseInt(cualidad.split(" ")[0]);
    }

    @Override
    public String getCualidad() {
        return cantSoldados + " soldados a su mando";
    }

    public String regañar(int id) {
        return "El Capitán " + nombre + " ha regañado al soldado " + id;
    }

    // Método de regaño
    @Override
    public void regañado() {
        try {
            nivel = nivel - 1;
            String message = "El Capitán fue regañado por no cumplir con su deber. Por lo tanto será degradado";
            JOptionPane.showConfirmDialog(null, message, "¡!", JOptionPane.OK_OPTION);
        } catch (Exception e) {
            String message = "Este soldado no ha podido ser regañado.";
            JOptionPane.showConfirmDialog(null, message, "¡!", JOptionPane.OK_OPTION);
        }
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
