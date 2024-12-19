package controlador;
import javax.swing.table.DefaultTableModel;
import modelo.FuncionesConsola;
import modelo.Soldado;
import vista.gestion_config.tablaGestion;
import vista.inicio_config.tablaSoldados;
import vista.vistaGUI;
import java.util.Scanner;


public class ControladorC {
    private vistaGUI vista;
    private FuncionesConsola modelo;
    static int opcionaejecutar;

    public ControladorC() {
        this.vista = new vistaGUI(); // Instancia única de la interfaz gráfica
        this.modelo = new FuncionesConsola();
    }

    public void iniciar() {
        Scanner scanner = new Scanner(System.in);
        while (true) { // Permite que el menú sea un ciclo continuo hasta elegir "Salir"
            System.out.println("Sistema de Gestión de Rangos Militares");
            System.out.println("1. AGREGAR SOLDADO...");
            System.out.println("2. MODIFICAR SOLDADO...");
            System.out.println("3. ELIMINAR SOLDADO...");
            System.out.println("4. ASIGNAR MISIONES...");
            System.out.println("5. VER ESTADO DE SOLDADO...");
            System.out.println("10. ABRIR INTERFAZ GRÁFICA (JFrame)");
            System.out.println("Seleccione una opción: ");
            int opcion = scanner.nextInt();
            scanner.nextLine(); // Consumir la nueva línea
            seleccionarOpcion(opcion, scanner);
        }
    }

    private void seleccionarOpcion(int opcion, Scanner scanner) {
        switch (opcion) {
            case 1 -> {
                System.out.println("Ingrese el nombre del soldado:");
                String nombre = scanner.nextLine();
                System.out.println("Ingrese el ID del soldado:");
                String id = scanner.nextLine();
                String respuesta = modelo.agregarSoldado(nombre, id);
                System.out.println(respuesta);
                actualizarLista(vista.tablaSoldados);
            }
            case 2 -> {
                System.out.println("Ingrese el ID del soldado a modificar:");
                String idBuscar = scanner.nextLine();
                Soldado soldado = modelo.buscarID(idBuscar);
                if (soldado == null) {
                    System.out.println("Soldado no encontrado.");
                    return;
                }
                System.out.println("Ingrese el nuevo nombre del soldado:");
                String nuevoNombre = scanner.nextLine();
                System.out.println("Ingrese el nuevo rango del soldado (Teniente, Capitan, Coronel):");
                String nuevoRango = scanner.nextLine();
                String respuestaModificar = modelo.modificarSoldado(idBuscar, nuevoNombre, nuevoRango);
                System.out.println(respuestaModificar);
                actualizarLista(vista.tablaSoldados);
                actualizarListaOperaciones(vista.tablaGestion);
            }
            case 3 -> {
                System.out.println("Ingrese el ID del soldado a eliminar:");
                String idEliminar = scanner.nextLine();
                String respuestaEliminar = modelo.eliminarSoldado(idEliminar);
                System.out.println(respuestaEliminar);
                actualizarLista(vista.tablaSoldados);
                actualizarListaOperaciones(vista.tablaGestion);
            }
            case 4 -> {
                System.out.println("Ingrese el ID del soldado a asignar la misión:");
                String idMision = scanner.nextLine();
                Soldado soldado = modelo.buscarID(idMision);
                if (soldado == null) {
                    System.out.println("Soldado no encontrado.");
                    return;
                }
                System.out.println("Ingrese la misión:");
                String mision = scanner.nextLine();
                String cualidad = switch (soldado.getRango()) {
                    case "Teniente" -> {
                        System.out.println("Ingrese la unidad a la que pertenece (en números):");
                        yield scanner.nextLine();
                    }
                    case "Capitán" -> {
                        System.out.println("Ingrese la cantidad de soldados a su cargo:");
                        yield scanner.nextLine();
                    }
                    case "Coronel" -> {
                        System.out.println("Ingrese la estrategia a implementar:");
                        yield scanner.nextLine();
                    }
                    default -> {
                        System.out.println("Rango no válido para misiones.");
                        yield null;
                    }
                };
                if (cualidad != null) {
                    String respuestaMision = modelo.asignarMision(idMision, mision, cualidad);
                    System.out.println(respuestaMision);
                    actualizarLista(vista.tablaSoldados);
                    actualizarListaOperaciones(vista.tablaGestion);
                }
            }
            case 10-> {
                abrirInterfazGrafica();
                iniciar();
            }

            case 5 -> {
                System.out.println("Ingrese el ID del soldado a ver el estado:");
                String idEstado = scanner.nextLine();
                Soldado soldado = modelo.buscarID(idEstado);
                if (soldado == null) {
                    System.out.println("Soldado no encontrado.");
                    return;
                }
                String respuestaEstado = modelo.verEstado(idEstado);
                System.out.println(respuestaEstado);


            }
            case 6 -> {
                System.out.println("Ingrese el ID del soldado a realizar la acción:");
                String idAccion = scanner.nextLine();
                try {
                    if (idAccion != null && !idAccion.isEmpty()) {
                        Soldado soldado = modelo.buscarID(idAccion);
                        if (soldado == null) {
                            System.out.println("Soldado no encontrado.");
                            return;
                        }
            
                        // Verificar el rango del soldado y mostrar las acciones correspondientes
                        String rango = soldado.getRango();
                        String[] acciones;
                        switch (rango) {
                            case "Soldado Raso" -> acciones = new String[]{"Patrullar", "Saludar", "Retirarse"};
                            case "Teniente" -> acciones = new String[]{"Regañar", "Supervisar", "???"};
                            case "Capitan" -> acciones = new String[]{"Regañar", "Sondear", "Misión"};
                            case "Coronel" -> acciones = new String[]{"Saludar", "Regañar", "Desfile", "Premio o Castigo"};
                            default -> {
                                System.out.println("Rango no reconocido.");
                                return;
                            }
                        }
            
                        System.out.println("Seleccione una acción:");
                        for (int i = 0; i < acciones.length; i++) {
                            System.out.println((i + 1) + ". " + acciones[i]);
                        }
            
                        int opcionAcciones = Integer.parseInt(scanner.nextLine());
                        if (opcionAcciones < 1 || opcionAcciones > acciones.length) {
                            System.out.println("Opción no válida.");
                            return;
                        }

                        String accion = acciones[opcionAcciones - 1];

                    
                        // Manejar la acción de "Misión" para el Capitán
                        if ("Misión".equals(accion) && "Capitán".equals(rango)) {
                            System.out.println("Ingrese el objetivo del rescate:");
                            String objetivo = scanner.nextLine();
                            System.out.println("Ingrese la estrategia:");
                            String estrategia = scanner.nextLine();
            
                            if (objetivo.isEmpty() || estrategia.isEmpty()) {
                                System.out.println("Debe ingresar todos los datos.");
                                return;
                            }
            
                            accion = "Misión:" + objetivo + ":" + estrategia;
                        }
                        // Manejar la acción de "Sondear" para el Capitán
                        else if ("Sondear".equals(accion) && "Capitán".equals(rango)) {
                            accion = "Sondear";
                        }
                        // Manejar la acción de "Regañar" para el Capitán
                        else if ("Regañar".equals(accion) && "Capitán".equals(rango)) {
                            System.out.println("Ingrese el ID del soldado a regañar:");
                            String idSoldado = scanner.nextLine().trim();
            
                            if (idSoldado.isEmpty()) {
                                System.out.println("Debe ingresar el ID del soldado a regañar.");
                                return;
                            }
            
                            accion = "Regañar:" + idSoldado;
                        }
            
                        // Llamar al modelo para realizar la acción del soldado
                        String respuesta = modelo.realizarAccion(idAccion, accion);
                        System.out.println(respuesta);
            
                        // Actualizar las listas
                        System.out.println("Listas actualizadas.");
                    }
                } catch (Exception ex) {
                    System.out.println("Error al realizar la acción.");
                }
            }
            }
        }
        
        
    

    private void abrirInterfazGrafica() {
        // Hacer visible la interfaz gráfica
        if (vista != null) {
            vista.setVisible(true); // Muestra el JFrame si aún no está visible
            System.out.println("Interfaz gráfica abierta.");
        } else {
            System.out.println("Error: No se pudo abrir la interfaz gráfica.");
        }
    }

    private void actualizarLista(tablaSoldados tabla) {
        DefaultTableModel auxiliar = (DefaultTableModel) tabla.getTableModelo();
        auxiliar.setRowCount(0);
        for (Soldado soldado : FuncionesConsola.getSoldados()) {
            auxiliar.addRow(new Object[]{soldado.getNombre(), soldado.getId(), soldado.getRango(), soldado.getMision(), soldado.getCualidad()});
        }
    }

    private void actualizarListaOperaciones(tablaGestion tabla) {
        DefaultTableModel auxiliar = (DefaultTableModel) tabla.getModelo();
        auxiliar.setRowCount(0);
        for (Soldado soldado : FuncionesConsola.getSoldados()) {
            auxiliar.addRow(new Object[]{soldado.getId(), soldado.getRango(), soldado.getCualidad()});
        }
    }
}
