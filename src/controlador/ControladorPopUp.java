package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import modelo.modeloPopUp;
import vista.vistaGUI;

public class ControladorPopUp implements ActionListener {

    private modeloPopUp modeloPopUp;
    private vistaGUI vista;

    public ControladorPopUp(vistaGUI vista) {
        this.vista = vista;
        this.modeloPopUp = new modeloPopUp();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String comando = e.getActionCommand();

        switch (comando) {
            case "Cambiar Fondo" -> modeloPopUp.cambiarFondo(vista);
            case "Cambiar Color Botones" -> modeloPopUp.cambiarColorBotones(vista.botones);
            case "Cambiar Color Texto" -> modelo.modeloPopUp.cambiarColorTexto(vista.botones);
            default -> {
            }
        }
    }
}
