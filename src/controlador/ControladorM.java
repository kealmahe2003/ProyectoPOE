package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import modelo.Funciones;
import modelo.Soldado;
import vista.gestion_config.tablaGestion;
import vista.inicio_config.tablaSoldados;
import vista.vistaGUI;

public class ControladorM {

    private vistaGUI vista;
    private Funciones modelo;

    public ControladorM() {
        this.vista = new vistaGUI();
        this.modelo = new Funciones();

        // Registra el ActionListener para el botón
        // Registra el ActionListener para el botón
        this.vista.btnAgregar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Mostrar un JOptionPane para llenar los datos
                Object[] message = {
                    "Nombre:", vista.txtNombre,
                    "ID:", vista.txtID
                };

                // Mostrar el JOptionPane de confirmación para los datos
                int option = JOptionPane.showConfirmDialog(
                        vista, // Ventana padre
                        message,
                        "Agregar Soldado",
                        JOptionPane.OK_CANCEL_OPTION
                );

                // Si el usuario presiona OK, procesar los datos
                if (option == JOptionPane.OK_OPTION) {
                    // Obtener los datos de los campos de texto
                    String nombre = vista.txtNombre.getText();
                    String id = vista.txtID.getText();

                    // Validar que los datos no estén vacíos
                    if (nombre.isEmpty() || id.isEmpty()) {
                        JOptionPane.showMessageDialog(
                                vista,
                                "Por favor, completa todos los campos.",
                                "Error",
                                JOptionPane.ERROR_MESSAGE
                        );
                        return; // Salir si los datos son inválidos
                    }

                    // Llamar al modelo para agregar el soldado
                    String respuesta = modelo.agregarSoldado(nombre, id);

                    // Limpiar los campos de texto
                    vista.txtNombre.setText("");
                    vista.txtID.setText("");

                    // Mostrar un mensaje de confirmación final
                    JOptionPane.showMessageDialog(vista, respuesta);
                    actualizarLista(vista.tablaSoldados);
                    actualizarListaOperaciones(vista.tablaGestion);
                }
            }
        });
        this.vista.btnModificar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Solicitar el ID del soldado a modificar
                String id = JOptionPane.showInputDialog(vista, "Ingrese el ID del soldado a modificar:");

                // Buscar el soldado por ID
                Soldado soldado = modelo.buscarID(id);
                if (soldado == null) {
                    JOptionPane.showMessageDialog(vista, "Soldado no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Crear un cuadro de diálogo para ingresar los nuevos datos
                JTextField ingresarNombre = new JTextField(soldado.getNombre()); // Se crea un JTextField para modificar el nombre del soldado
                JComboBox<String> ingresarRango = new JComboBox<>(new String[]{"Soldado Raso", "Teniente", "Capitán", "Coronel"}); // Se crea un JComboBox con los rangos
                ingresarRango.setSelectedItem(soldado.getRango()); // El ComboBox tiene por defecto el rango actual del soldado que se quiere modificar
                Object[] message = {
                    "Nombre:", ingresarNombre,
                    "Rango:", ingresarRango,}; // Se crea un objeto con los componentes a mostrar
                int option = JOptionPane.showConfirmDialog(vista, message, "Modificar Soldado", JOptionPane.OK_CANCEL_OPTION);

                if (option == JOptionPane.OK_OPTION) {
                    // Si se presiona OK, se actualizan los datos del soldado
                    String nuevoNombre = ingresarNombre.getText();
                    String nuevoRango = ingresarRango.getSelectedItem().toString();

                    // Llamar al modelo para modificar el soldado
                    String respuesta = modelo.modificarSoldado(id, nuevoNombre, nuevoRango);

                    // Mostrar un mensaje de confirmación final
                    JOptionPane.showMessageDialog(vista, respuesta);
                    actualizarLista(vista.tablaSoldados);
                    actualizarListaOperaciones(vista.tablaGestion);
                }
            }
        });

        this.vista.btnEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Solicitar el ID del soldado a eliminar
                String id = JOptionPane.showInputDialog(vista, "Ingrese el ID del soldado a eliminar:");

                // Llamar al modelo para eliminar el soldado
                String respuesta = modelo.eliminarSoldado(id);

                // Mostrar un mensaje de confirmación final
                JOptionPane.showMessageDialog(vista, respuesta);
                actualizarLista(vista.tablaSoldados);
                actualizarListaOperaciones(vista.tablaGestion);
            }
        });

        this.vista.btnDeshacerCambios.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Llamar al modelo para deshacer los cambios
                String respuesta = modelo.deshacerCambios();

                // Mostrar un mensaje de confirmación final
                JOptionPane.showMessageDialog(vista, respuesta);
                actualizarLista(vista.tablaSoldados);
                actualizarListaOperaciones(vista.tablaGestion);
            }
        });

        this.vista.btnAsignarMision.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Solicitar el ID del soldado a asignar la misión
                String id = JOptionPane.showInputDialog(vista, "Ingrese el ID del soldado a asignar la misión:");

                // Buscar el soldado por ID
                Soldado soldado = modelo.buscarID(id);
                if (soldado == null) {
                    JOptionPane.showMessageDialog(vista, "Soldado no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Verificar el rango del soldado y asignar la misión según su rango
                String rango = soldado.getRango();

                if ("Soldado Raso".equals(rango)) {
                    JOptionPane.showMessageDialog(vista, "El Soldado Raso no puede recibir misiones directamente.");
                    return;
                }

                String mision = JOptionPane.showInputDialog(vista, "Ingrese la misión:");
                String cualidad = null;

                switch (rango) {
                    case "Teniente" -> {
                        cualidad = JOptionPane.showInputDialog(vista, "Ingrese la unidad a la que pertenece (en números):");
                    }
                    case "Capitán" -> {
                        cualidad = JOptionPane.showInputDialog(vista, "Ingrese la cantidad de soldados a su cargo:");
                    }
                    case "Coronel" -> {
                        cualidad = JOptionPane.showInputDialog(vista, "Ingrese la estrategia a implementar:");
                    }
                }

                if (cualidad != null && mision != null) {
                    // Llamar al modelo para asignar la misión al soldado
                    String respuesta = modelo.asignarMision(id, mision, cualidad);

                    // Mostrar un mensaje de confirmación final
                    JOptionPane.showMessageDialog(vista, respuesta);
                    actualizarLista(vista.tablaSoldados);
                    actualizarListaOperaciones(vista.tablaGestion);
                }
            }
        });
        this.vista.btnVerEstado.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Solicitar el ID del soldado a ver el estado
                String id = JOptionPane.showInputDialog(vista, "Ingrese el ID del soldado a ver el estado:");

                // Llamar al modelo para ver el estado del soldado
                String respuesta = modelo.verEstado(id);

                // Mostrar un mensaje de confirmación final
                JOptionPane.showMessageDialog(vista, respuesta);
            }
        });

        this.vista.btnRealizarAccion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    // Solicitar el ID del soldado a realizar la acción
                    String id = JOptionPane.showInputDialog(vista, "Ingrese el ID del soldado a realizar la acción:").trim();
                    if (id != null && !id.isEmpty()) {
                        // Buscar el soldado por ID
                        Soldado soldado = modelo.buscarID(id);
                        if (soldado == null) {
                            JOptionPane.showMessageDialog(vista, "Soldado no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        // Verificar el rango del soldado y mostrar las acciones correspondientes
                        String rango = soldado.getRango();
                        JComboBox<String> ingresarAccion;
                        switch (rango) {
                            case "Soldado Raso" ->
                                ingresarAccion = new JComboBox<>(new String[]{"Patrullar", "Saludar", "Retirarse"});
                            case "Teniente" ->
                                ingresarAccion = new JComboBox<>(new String[]{"Regañar", "Supervisar", "???"});
                            case "Capitán" ->
                                ingresarAccion = new JComboBox<>(new String[]{"Regañar", "Sondear", "Misión"});
                            case "Coronel" ->
                                ingresarAccion = new JComboBox<>(new String[]{"Saludar", "Regañar", "Desfile", "Premio o Castigo"});
                            default -> {
                                JOptionPane.showMessageDialog(vista, "Rango no reconocido.", "Error", JOptionPane.ERROR_MESSAGE);
                                return;
                            }
                        }
                        boolean enviar = true;
                        Object[] message = {"Accion a realizar:", ingresarAccion};
                        int option = JOptionPane.showConfirmDialog(vista, message, "Realizar accion", JOptionPane.OK_CANCEL_OPTION);
                        if (option == JOptionPane.OK_OPTION && enviar==true) {
                            // Obtener la acción seleccionada
                            String accion = ingresarAccion.getSelectedItem().toString();
                            // Manejar la acción de "Mision" para el Capitán
                            if ("Misión".equals(accion) && "Capitán".equals(rango)) {
                                JTextField objetivoField = new JTextField();
                                JTextField estrategiaField = new JTextField();
                                Object[] misionMessage = {
                                    "Ingrese el objetivo del rescate:", objetivoField,
                                    "Ingrese la estrategia:", estrategiaField
                                };
                                int misionOption = JOptionPane.showConfirmDialog(vista, misionMessage, "Planificación de Misión", JOptionPane.OK_CANCEL_OPTION);
                                if (misionOption == JOptionPane.OK_OPTION) {
                                    String objetivo = objetivoField.getText();
                                    String estrategia = estrategiaField.getText();
                                    if (objetivo.isEmpty() || estrategia.isEmpty()) {
                                        JOptionPane.showMessageDialog(vista, "Debe ingresar todos los datos.", "Error", JOptionPane.ERROR_MESSAGE);
                                        return;
                                    }
                                    accion = "Misión:" + objetivo + ":" + estrategia;
                                } else {
                                    return;
                                }
                            } // Manejar la acción de "Sondear" para el Capitán
                            else if ("Sondear".equals(accion) && "Capitán".equals(rango)) {
                                // Aquí puedes agregar cualquier lógica adicional si es necesario
                                accion = "Sondear";
                            } // Manejar la acción de "Regañar" para el Capitán
                            else if ("Regañar".equals(accion) && "Capitán".equals(rango)) {
                                String idSoldado = JOptionPane.showInputDialog(vista, "Ingrese el ID del soldado a regañar:").trim();
                                if (idSoldado == null || idSoldado.isEmpty()) {
                                    JOptionPane.showMessageDialog(vista, "Debe ingresar el ID del soldado a regañar.", "Error", JOptionPane.ERROR_MESSAGE);
                                    return;
                                }
                                accion = "Regañar:" + idSoldado;

                            }else {
                                return;
                            }
                            // Llamar al modelo para realizar la acción del soldado
                            String respuesta = modelo.realizarAccion(id, accion);
                            enviar = false;
                            // Mostrar un mensaje de confirmación final
                            JOptionPane.showMessageDialog(vista, respuesta);
                            // Actualizar las listas
                            actualizarLista(vista.tablaSoldados);
                            actualizarListaOperaciones(vista.tablaGestion);
                        }
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(vista, "Error al realizar la acción.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    private void actualizarLista(tablaSoldados tabla) {
        DefaultTableModel auxiliar = (DefaultTableModel) tabla.getTableModelo();
        auxiliar.setRowCount(0); // Limpiar la tabla

        // Se recorren los soldados y se agregan a la tabla
        for (Soldado soldado : Funciones.getSoldados()) {
            auxiliar.addRow(new Object[]{soldado.getNombre(), soldado.getId(), soldado.getRango(), soldado.getMision(), soldado.getCualidad()});
        }
    }

    private void actualizarListaOperaciones(tablaGestion tabla) {
        DefaultTableModel auxiliar = (DefaultTableModel) tabla.getModelo();
        auxiliar.setRowCount(0); // Limpiar la tabla

        // Se recorren los soldados y se agregan a la tabla
        for (Soldado soldado : Funciones.getSoldados()) {
            auxiliar.addRow(new Object[]{soldado.getId(), soldado.getRango(), soldado.getCualidad(),});
        }
    }

    public vistaGUI obtenerVistaGui() {
        return this.vista;
    }

    public String agregarSoldado(String nombre, String id) {
        return this.modelo.agregarSoldado(nombre, id);
    }

    public String asignarMision(String id, String mision, String cualidad) {
        return this.modelo.asignarMision(id, mision, cualidad);
    }
}
