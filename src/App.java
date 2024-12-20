import controlador.ControladorM;
import controlador.ControladorC;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;





public class App {
    
    ControladorM controlador = new ControladorM();
    public static void main(String[] args) {
        try {
            // Look and feel
            // Se coloca el look and feel del sistema operativo en el que se esta ejecutando el programa
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }


        // Elegir entre consola o interfaz grafica
        int opcion = JOptionPane.showOptionDialog(null, "¿Desea ejecutar el programa en consola o en interfaz grafica?", "Elegir modo de ejecucion", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, new Object[]{"Consola", "Interfaz Grafica"}, "Interfaz Grafica");
        if (opcion == JOptionPane.YES_OPTION) {
            // CONTROLADOR CONSOLA
            ControladorC controlador = new ControladorC();
            controlador.iniciar();
        } else {
            // CONTROLADOR INTERFAZ GRAFICA
            ControladorM controladorVIsual = new ControladorM();
            controladorVIsual.obtenerVistaGui().setVisible(true);
        }
    }   
       
}
