package torneo_futbal;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.JOptionPane;

public class GestorEntradas {

    private static List<Entrada> entradasDisponibles = new ArrayList<>();   
    private static int contadorIdEntradas = 1;

    public static void ponerEntradasEnVenta(Club club, Partido partido, Scanner entradas) {

        JOptionPane.showMessageDialog(null,
                "Club: " + club.getNombre() + "\nPartido: " + partido.toString() + "\n estadio: "
                        + partido.getEstadio().getNombre() + "estadio : " + partido.getEstadio().getCapacidad());

        // verificar si hay entradas disponibles

        long entradasExistentes = entradasDisponibles.stream().filter(entrada -> entrada.getPartido().equals(partido))
                .count();
        int capacidadRestante = partido.getEstadio().getCapacidad() - (int) entradasExistentes;

        if (capacidadRestante <= 0) {
            JOptionPane.showMessageDialog(null, "No hay entradas disponibles");
            return;
        }

        JOptionPane.showMessageDialog(null,
                "Entradas disponibles: " + capacidadRestante + "\n entradas existentes: " + entradasExistentes);

        // llamar a metodo para gestionar categorias

        gestionarCategorias(club, partido, capacidadRestante);

    }

    public static void gestionarCategorias(Club club, Partido partido, int capacidadRestante) {
        int entradasCreadas = 0;
        String[] categorias = new String[] { "palco", "vip", "popular", "platea" };
        while (entradasCreadas < capacidadRestante) {

            String categoriaSeleccionada = (String) JOptionPane.showInputDialog(null, "Seleccione una categoria",
                    "Gestionar entradas", JOptionPane.QUESTION_MESSAGE, null, categorias, categorias[0]);
            // dar a elegir entre palco, vip, popular, platea.
            if (categoriaSeleccionada == null) {

                return;
            }

            // solicitar precio
            double precioSeleccionado = (double) JOptionPane.showInputDialog(null, "Seleccione un precio",
                    "Gestionar entradas", JOptionPane.QUESTION_MESSAGE, null, null, null);
            if (precioSeleccionado <= 0) {
                return;
            }

            // solicitar cantidad
            int cantidadSeleccionada = (int) JOptionPane.showInputDialog(null, "Seleccione cantidad",
                    "Gestionar entradas", JOptionPane.QUESTION_MESSAGE, null, null, null);
            if (cantidadSeleccionada <= 0) {
                return;
            }
            // crear entradas
            for (int i = 0; i < cantidadSeleccionada; i++) {
                Entrada entrada = new Entrada(partido, categoriaSeleccionada, precioSeleccionado, contadorIdEntradas++,
                        false, club);
                entradasDisponibles.add(entrada);
                entradasCreadas++;
            }
            int respuesta = (int) JOptionPane.showConfirmDialog(null, "¿Desea crear mas entradas?",
                    "Gestionar entradas",
                    JOptionPane.YES_NO_OPTION);
            if (respuesta == JOptionPane.YES_OPTION) {

                continue;
            }
            break;
        }

    }

    // compra de entradas
    public static void comprarEntradas(Partido partido, String categoria) {
        List<Entrada> entradasDisponiblesCategoria = entradasDisponibles.stream()
                .filter(entrada -> entrada.getPartido().equals(partido) && entrada.getCategoria().equals(categoria)
                        && entrada.isVendida() == false)
                .toList();
        // verificar disponibilidad
        if (entradasDisponiblesCategoria.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay entradas disponibles");
            return;
        }

        // mostrar detalle de la entrada disponible.
        Entrada entrada = entradasDisponiblesCategoria.get(0);
        String detalles = "Entrada disponible:\n" +
                "id: " + entrada.getId() + "\n" +
                "Categoria: " + entrada.getCategoria() + "\n" +
                "Precio: " + entrada.getPrecio() + "\n" +
                "Partido: " + entrada.getPartido().toString() + "\n" +
                "Club: " + entrada.getClubVendedor().getNombre() + "\n" + "confirmar compra??";

        int respuesta = (int) JOptionPane.showConfirmDialog(null, detalles, "Comprar entradas",
                JOptionPane.YES_NO_OPTION);
        if (respuesta == JOptionPane.YES_OPTION) {
            entrada.setVendida(true);
            JOptionPane.showMessageDialog(null, "Entrada comprada exitosamente, \n" +
                    "id: " + entrada.getId() + "\n" +
                    "Categoria: " + entrada.getCategoria() + "\n" +
                    "Precio: " + entrada.getPrecio() + "\n" +
                    "Partido: " + entrada.getPartido().toString() + "\n" +
                    "Club: " + entrada.getClubVendedor().getNombre() + "\n" +
                    "imprima su ticket");
            return;
        } else{ 
            JOptionPane.showMessageDialog(null, "Entrada no comprada");
        }

    }
    public static void agregarEntrada(Entrada entrada) {
        entradasDisponibles.add(entrada);
    }
    public static List<Entrada> getEntradasDisponibles() {
        return entradasDisponibles;
    }
}