package modelo;

import java.awt.Color;
import java.awt.Component;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import vista.vistaGUI;

public class modeloPopUp {

    private Color colorBotones;
    private static Color colorTexto;
    
        public modeloPopUp() {
            this.colorBotones = Color.DARK_GRAY;
            this.colorTexto = Color.WHITE;
        }
    
        public void cambiarFondo(vistaGUI vista) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Seleccionar una imagen de fondo");

            FileNameExtensionFilter filtroImagenes = new FileNameExtensionFilter("Imágenes", "jpg", "png");
            fileChooser.setFileFilter(filtroImagenes);

            int resultado = fileChooser.showOpenDialog(null);

            if (resultado == JFileChooser.APPROVE_OPTION) {
                File archivo = fileChooser.getSelectedFile();
                String rutaDestino = "src/vista/images/Camuflaje.jpg";
                File destino = new File(rutaDestino);

                try {

                    if (destino.exists()) {
                        Files.delete(Paths.get(rutaDestino));
                    }

                    Files.copy(archivo.toPath(), destino.toPath());

                    ImageIcon nuevaImagen = new ImageIcon(destino.getAbsolutePath());
                    JLabel imagenFondo = new JLabel();
                    imagenFondo.setIcon(nuevaImagen);
                    imagenFondo.setBounds(0, 0, vista.getWidth(), vista.getHeight());

    
                    for (Component comp : vista.panelFondo.getComponents()) {
                        if (comp instanceof JLabel) {
                            vista.panelFondo.remove(comp);
                        }
                    }

                    vista.panelFondo.add(imagenFondo);

                    vista.panelFondo.revalidate();
                    vista.panelFondo.repaint(); 

                } catch (IOException e) {
                    JOptionPane.showMessageDialog(vista, "Error al guardar la imagen: " + e.getMessage(),
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }

        public void cambiarColorBotones(JButton[] botones) {
            Color color = JColorChooser.showDialog(null, "Seleccionar Color para los Botones", colorBotones);
            if (color != null) {
                this.colorBotones = color;
                for (JButton boton : botones) {
                    boton.setBackground(color);
                }
            }
        }
    
        public static void cambiarColorTexto(JButton[] botones) {
            Color color = JColorChooser.showDialog(null, "Seleccionar Color para el Texto", colorTexto);
            if (color != null) {
                modeloPopUp.colorTexto = color;
                for (JButton boton : botones) {
                    boton.setForeground(color);
                }
            }
        }

    public Color getColorBotones() {
        return colorBotones;
    }

    public Color getColorTexto() {
        return colorTexto;
    }
}