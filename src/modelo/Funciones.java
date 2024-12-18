
package modelo;

import java.awt.Desktop;
import java.awt.HeadlessException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import static vista.vistaGUI.sonido;

public class Funciones {

    // Lista de soldados
    private final static List<Soldado> soldados = new ArrayList<>();

    private final static List<Soldado> soldadosBackup = new ArrayList<>();

    // Lista de operaciones
    public Funciones() {

    }

    // Método para agregar un soldado
    public String agregarSoldado(String nombre, String id) {
        // Validar que el ID solo contenga números y tenga un máximo de 6 caracteres
        if (!id.matches("\\d{1,5}")) {
            return "El ID debe contener solo números y tener un máximo de 6 caracteres.";
        }

        // Verificar si el soldado existe o no mediante el ID
        if (buscarID(id) != null) {
            return "El ID ya existe.";
        }

        // Se crea el nuevo soldado y se almacena en el arrayList soldados
        SoldadoRaso raso = new SoldadoRaso(nombre, id);
        soldados.add(raso);
        // Al final del proceso se actualiza la lista de soldados

        return "Soldado agregado correctamente.";

    }

    // Método para modificar un soldado
    public String modificarSoldado(String id, String nuevoNombre, String nuevoRango) {
        try {
            backupSoldados(); // Se realiza un backup de los soldados antes de modificar

            Soldado soldado = buscarID(id);

            if (soldado == null) { // Si el soldado no existe, se muestra un mensaje de error
                return "Soldado no encontrado.";
            }

            // Se actualizan los datos del soldado
            soldado.setNombre(nuevoNombre); // Se actualiza el nombre del soldado
            soldado.setRango(nuevoRango); // Se actualiza el rango del soldado

            return "Soldado modificado correctamente.";
        } catch (Exception e) {
            return "Error al modificar el soldado.";
        }
    }

    // Método para eliminar un soldado
    public String eliminarSoldado(String id) {
        try {
            backupSoldados(); // Se realiza un backup de los soldados antes de eliminar

            Soldado soldado = buscarID(id);

            if (soldado == null) { // Si el soldado no existe, se muestra un mensaje de error
                return "Soldado no encontrado.";
            }

            soldados.remove(soldado); // Se elimina el soldado de la lista de soldados
            return "Soldado eliminado correctamente.";
        } catch (Exception e) {
            return "Error al eliminar el soldado.";
        }
    }

    // Método para realizar un backup de los soldados (implementación de ejemplo)
    // Método para actualizar la lista de soldados en la tabla
    /*private void actualizarListaOperaciones() {
            try {
                tablaOperaciones.setRowCount(0);
                for (Persona personas : personas) {
                    tablaOperaciones.addRow(new Object[]{personas.getId(), personas.getRango(), personas.getCualidad()});
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error al actualizar la lista de operaciones.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }*/
    // Método para buscar un soldado por su ID
    public Soldado buscarID(String id) {
        for (Soldado soldado : soldados) { // Se recorren los soldados y se compara el ID
            if (soldado.getId().equals(id)) {
                return soldado;
            }
        }
        return null;
    }

    // Método para realizar un backup de los soldados
    private void backupSoldados() {
        soldadosBackup.clear(); // Se limpia el backup anterior
        for (Soldado soldado : soldados) { // Se recorren los soldados y se agregan al backup
            SoldadoRaso raso = new SoldadoRaso(soldado.getNombre(), soldado.getId());
            soldadosBackup.add(raso);
        }
    }

    public String deshacerCambios() {
        try {
            if (soldadosBackup.isEmpty()) { // Si no hay cambios para deshacer, se muestra un mensaje
                return "No hay cambios para deshacer";
            }

            soldados.clear(); // Se limpia la lista de soldados actual
            for (Soldado soldado : soldadosBackup) {
                SoldadoRaso raso = new SoldadoRaso(soldado.getNombre(), soldado.getId()); // Se recorren los soldados del backup y se agregan a la lista actual
                soldados.add(raso);
            }
            return "Cambios deshechos correctamente";
        } catch (Exception e) {
            return "Error al deshacer los cambios";
        }
    }

    // Método para mostrar la información de un soldado
    public String asignarMision(String id, String mision, String cualidad) {
        try {
            for (Soldado soldado : soldados) {
                if (soldado.getId().equals(id)) {
                    String rango = soldado.getRango();

                    if ("Soldado Raso".equals(rango)) {
                        return "El Soldado Raso no puede recibir misiones directamente.";
                    }

                    switch (rango) {
                        case "Teniente" -> {
                            if (cualidad != null) {
                                soldado.setCualidad("Unidad #" + cualidad);
                                soldado.asignarMision(mision);
                                return "Misión: " + mision + " asignada al Teniente.";
                            }
                        }
                        case "Capitán" -> {
                            if (cualidad != null) {
                                soldado.setCualidad(cualidad + " soldados a su mando" + " para la misión " + mision);
                                soldado.asignarMision(mision);
                                return "Misión: " + mision + " asignada al Capitán.";
                            }
                        }
                        case "Coronel" -> {
                            if (cualidad != null) {
                                soldado.setCualidad(cualidad);
                                soldado.asignarMision(mision);
                                return "El coronel está implementando la estrategia " + cualidad + " para la misión " + mision + " asignada al Coronel.";
                            }
                        }
                        default -> {
                            return "Rango no reconocido.";
                        }
                    }
                }
            }
            return "Soldado no encontrado";
        } catch (NumberFormatException e) {
            return "Error al asignar la misión";
        }
    }

    public String verEstado(String id) {
        try {
            Persona soldado = buscarID(id);
            if (soldado == null) {
                return "Soldado no encontrado.";
            }
            String rango = soldado.getRango();
            String message;

            switch (rango) {
                case "Soldado Raso" -> {
                    message = "El Soldado Raso " + soldado.getNombre() + " " + id + " se encuentra activo.";
                }
                case "Teniente" -> {
                    if (soldado.getCualidad().equals("No tiene") && soldado.getMision().equals("Sin asignar")) {
                        message = "El Teniente no tiene mision asignada.";
                    } else {
                        message = "El Teniente " + soldado.getNombre() + " " + id + " pertenece a " + soldado.getCualidad() + " y tiene la misión de " + soldado.getMision() + ".";
                    }
                }
                case "Capitán" -> {
                    if (soldado.getCualidad().equals("No tiene") && soldado.getMision().equals("Sin asignar")) {
                        message = "El Capitán no tiene mision asignada.";
                    } else {
                        message = "El Capitán " + soldado.getNombre() + " " + id + " tiene a " + soldado.getCualidad() + " y tiene la misión de " + soldado.getMision() + ".";
                    }
                }
                case "Coronel" -> {
                    if (soldado.getCualidad().equals("No tiene") && soldado.getMision().equals("Sin asignar")) {
                        message = "El Coronel no tiene mision asignada.";
                    } else {
                        message = "El Coronel " + soldado.getNombre() + " " + id + " esta implementando la estrategia " + soldado.getCualidad() + " para la misión " + soldado.getMision() + ".";
                    }
                }
                default -> {
                    return "Rango no reconocido.";
                }
            }
            return message;
        } catch (HeadlessException e) {
            return "Error al ver el estado del soldado.";
        }
    }
    
    public String realizarAccion(String id, String accion) {
        try {
            System.out.println("entro" + accion);
            Persona soldado = buscarID(id);
            if (soldado == null) {
                return "Soldado no encontrado.";
            }
            String rango = soldado.getRango();
            String resultado = "";
            System.out.println(rango + "Rango");
            switch (rango) {
                case "Soldado Raso" -> {
                    SoldadoRaso raso = new SoldadoRaso(soldado.getId(), soldado.getNombre());
                    switch (accion) {
                        case "Patrullar" ->
                            resultado = raso.patrullar();
                        case "Saludar" -> {
                            raso.saludar();
                            resultado = "El Soldado Raso ha saludado.";
                        }
                        case "Retirarse" -> {
                            soldados.remove(buscarID(id));
                            resultado = raso.getNombre() + " se retiró.";
                        }
                    }
                }
                case "Teniente" -> {
                    System.out.println(accion + "accion elegida");
                    Teniente teniente = new Teniente(soldado.getCualidad());
                    switch (accion) {
                        case "Regañar" -> {
                            String idSoldado = JOptionPane.showInputDialog(null, "Ingrese el ID del soldado:").trim();
                            Persona soldadito = buscarID(String.valueOf(idSoldado));
                            if (soldadito.getNivel() > 2) {
                                resultado = teniente.regañar(Integer.parseInt(idSoldado));
                                soldadito.regañado();
                                soldados.remove(buscarID(idSoldado));
                            } else {
                                resultado = "El soldado no es un Soldado Raso.";
                            }
                        }
                        case "Supervisar" -> {
                            String p = teniente.realizarAccion();
                            switch (p) {
                                case "1" ->
                                    resultado = "Los soldados se revelaron contra el teniente.";
                                case "2" -> {
                                    Persona ganador = soldados.get(new java.util.Random().nextInt(soldados.size()));
                                    resultado = "¡Competencia terminada! El ganador es: " + ganador.getNombre();
                                }
                                case "3" -> {
                                    StringBuilder resultadoMochilas = new StringBuilder("El Teniente está inspeccionando las mochilas de los soldados!\n\n");
                                    int contador = 0;
                                    String[] objetos = {"una cantimplora", "un mapa viejo", "un queso mordido", "un libro de estrategia", "un muñeco"};
                                    for (Persona soldadito : soldados) {
                                        if (contador == 10) {
                                            break;
                                        }
                                        if ("Soldado Raso".equals(soldadito.getRango())) {
                                            int indiceObjeto = new java.util.Random().nextInt(objetos.length);
                                            resultadoMochilas.append(soldadito.getNombre())
                                                    .append(" lleva en su mochila: ")
                                                    .append(objetos[indiceObjeto])
                                                    .append(".\n");
                                            contador += 1;
                                        }
                                    }
                                    resultado = resultadoMochilas.toString();
                                }
                            }
                        }
                        case "???" -> {
                            try {
                                String url = "https://www.youtube.com/watch?v=dQw4w9WgXcQ&ab_channel=RickAstley";
                                Desktop desktop = Desktop.getDesktop();
                                desktop.browse(new URI(url));
                                resultado = "Redirigiendo a un enlace...";
                            } catch (IOException | URISyntaxException e) {
                                resultado = "Error al abrir el enlace.";
                            }
                        }
                    }
                }
                
                case "Capitán" -> {
                    System.out.println("Mision" + accion);
                    Capitan capitan = new Capitan();
                    switch (accion.split(":")[0]) {
                        case "Misión" -> {
                            String[] partes = accion.split(":");
                            
                            if (partes.length < 3) {
                                resultado = "Formato incorrecto. Use: Mision:objetivo:estrategia";
                            } else {
                                String objetivo = partes[1].trim();
                                String estrategia = partes[2].trim();
                                resultado = capitan.planificarMision(objetivo, estrategia);
                            }
                            return resultado;
                        }
                        case "Sondear" -> resultado = capitan.realizarAccion();
                        case "Regañar" -> {
                            String idSoldado = accion.split(":")[1].trim();
                            Soldado soldadito = buscarID(idSoldado);
                            if (soldadito != null && soldadito.getNivel() > 3) {
                                resultado = capitan.regañar(Integer.parseInt(idSoldado));
                                soldadito.regañado();
                                if (soldadito.getNivel() == 1 && soldadito instanceof Soldado) {
                                    soldados.remove((Soldado) soldadito);
                                }
                            } else {
                                resultado = "El Capitán no puede regañar a alguien del mismo rango o mayor.";
                            }
                        }
                        default -> resultado = "Acción no reconocida para el Capitán.";
                    }
                }

                    
                    
                case "Coronel" -> {
                    Coronel coronel = new Coronel(soldado.getCualidad());
                    switch (accion) {
                        case "Saludar" -> {
                            sonido(1);
                            resultado = coronel.saludar();
                        }
                        case "Regañar" -> {
                            String idSoldado = JOptionPane.showInputDialog(null, "Ingrese el ID del soldado:").trim();
                            Persona soldadito = buscarID(idSoldado);
                            if (soldadito.getNivel() > 4) {
                                resultado = coronel.regañar(Integer.parseInt(idSoldado));
                                soldadito.regañado();
                                if (soldadito.getNivel() == 1) {
                                    soldados.remove(buscarID(idSoldado));
                                }
                            } else {
                                resultado = "El Coronel no puede regañar a alguien del mismo rango.";
                            }
                        }
                        case "Desfile" -> {
                            coronel.organizarDesfile();
                            resultado = "El Coronel ha organizado un desfile.";
                        }
                        case "Premio o Castigo" -> {
                            coronel.premioOcastigo(soldados);
                            resultado = "El Coronel ha decidido un premio o castigo.";
                        }
                    }
                }
            }
            return resultado;
        } catch (HeadlessException e) {
            return "Error al realizar la acción.";
        }
    }

    public static List<Soldado> getSoldados() {
        return soldados;
    }

}
