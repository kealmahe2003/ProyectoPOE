import controlador.ControladorM;
import controlador.ControladorC;
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
        // CONTROLADOR CONSOLA


        // CONTROLADOR GRAFICO
        ControladorM controladorVIsual = new ControladorM();
        controladorVIsual.obtenerVistaGui().setVisible(true);        
    }
    }
