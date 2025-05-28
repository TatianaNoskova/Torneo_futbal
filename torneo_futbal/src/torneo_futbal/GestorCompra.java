package torneo_futbal;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.JOptionPane;

public class GestorCompra {
    private List<Compra> compras;
    private static int contadorIdCompra = 1;

    public GestorCompra() {
        compras = new ArrayList<>();
    }

    public List<Entrada> obtenerEntradasDisponibles(Partido partido) {
        // verificar datos de entradas disponibles

        if (partido == null) {
            return new ArrayList<>();
        }
        return GestorEntradas.getEntradasDisponibles().stream().filter(entrada -> entrada.getPartido().equals(partido))
                .collect(Collectors.toList());
    }

    public Entrada comprarEntrada(Usuario usuario) {
        // seleccionar torneo
        String[] nombreTorneos = SistemaRegistro.torneosRegistrados.stream().map(torneo -> torneo.getNombreTorneo())
                .toArray(String[]::new);
        String torneoSeleccionado = (String) JOptionPane.showInputDialog(null, "Seleccione un torneo",
                "Gestionar compra", JOptionPane.QUESTION_MESSAGE, null, nombreTorneos, nombreTorneos[0]);
        if (torneoSeleccionado == null) {
            return null;
        }
        // buscar torneo
        Torneo torneo = SistemaRegistro.torneosRegistrados.stream()
                .filter(torneo1 -> torneo1.getNombreTorneo().equals(torneoSeleccionado)).findFirst().get();
        if (torneo == null) {
            JOptionPane.showMessageDialog(null, "No existe torneo con ese nombre");
            return null;
        }
        // seleccionar partido
        List<Partido> partidos = torneo.getPartidos();
        if (partidos.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay partidos para ese torneo");
            return null;
        }
        String[] nombrePartidos = partidos.stream().map(partido -> partido.toString()).toArray(String[]::new);
        String partidoSeleccionado = (String) JOptionPane.showInputDialog(null, "Seleccione un partido",
                "Gestionar compra", JOptionPane.QUESTION_MESSAGE, null, nombrePartidos, nombrePartidos[0]);
        if (partidoSeleccionado == null) {
            return null;
        }
        // buscar partido
        Partido partido = partidos.stream().filter(partido1 -> partido1.toString().equals(partidoSeleccionado))
                .findFirst().get();
        if (partido == null) {
            JOptionPane.showMessageDialog(null, "No existe partido con ese nombre");
            return null;
        }
        // seleccionar categoria
        String[] categorias = new String[] { "palco", "vip", "popular", "platea" };
        String categoriaSeleccionado = (String) JOptionPane.showInputDialog(null, "Seleccione una categoria",
                "Gestionar compra", JOptionPane.QUESTION_MESSAGE, null, categorias, categorias[0]);
        if (categoriaSeleccionado == null) {
            return null;
        }
        // mostrar entrada disponible
        List<Entrada> entradasDisponibles = GestorEntradas.getEntradasDisponibles();
        if (entradasDisponibles.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay entradas disponibles");
            return null;
        }
        String[] nombreEntradas = entradasDisponibles.stream().map(entrada -> "Id= " + entrada.getId() + " - precio: $"
                + entrada.getPrecio() + " - categoria: " + entrada.getCategoria()).toArray(String[]::new);
        String entradaSeleccionada = (String) JOptionPane.showInputDialog(null, "Seleccione una entrada",
                "Gestionar compra", JOptionPane.QUESTION_MESSAGE, null, nombreEntradas, nombreEntradas[0]);
        if (entradaSeleccionada == null) {
            return null;
        }
        int idEntrada = Integer.parseInt(entradaSeleccionada.split(" ")[1]);
        Entrada entrada = entradasDisponibles.stream().filter(entrada1 -> entrada1.getId() == idEntrada).findFirst()
                .get();
        if (entrada == null) {
            JOptionPane.showMessageDialog(null, "No existe entrada con ese id");
            return null;
        }
        return entrada;

    }

    public boolean procesarPago(Entrada entrada, Usuario usuario) {
        boolean esSocio = usuario.getRol().equals("Socio del club"); 
        double precioEntrada = !esSocio ? entrada.getPrecio() : entrada.getPrecio() * 0.5; // si es socio, se paga la mitad
        
    

        String mensaje = "Detalle de la entrada:\n" +
                "id: " + entrada.getId() + "\n" +
                "Categoria: " + entrada.getCategoria() + "\n" +
                "Precio: $" + precioEntrada + "\n" +
                "Partido: " + entrada.getPartido().toString() + "\n" +
                "Club: " + entrada.getClubVendedor().getNombre() + "\n" +
                "Usuario: " + usuario.getNombre() + "\n" +
                "confirmar pago??";
                int confirmacion = (int) JOptionPane.showConfirmDialog(null, mensaje, "Pago",
                        JOptionPane.YES_NO_OPTION);
        if (confirmacion == JOptionPane.YES_OPTION) {
            entrada.setVendida(true);

            JOptionPane.showMessageDialog(null, "Entrada pagada exitosamente, \n" +
                    "id: " + entrada.getId() + "\n" +
                    "Categoria: " + entrada.getCategoria() + "\n" +
                    "Precio: $" + entrada.getPrecio() + "\n" +
                    "Partido: " + entrada.getPartido().toString() + "\n" +
                    "Club: " + entrada.getClubVendedor().getNombre() + "\n" +
                    "Usuario: " + usuario.getNombre() + "\n" +
                    "imprima su ticket");   
            return true;
        } else {
            JOptionPane.showMessageDialog(null, "Pago cancelado");   
            return false;
        }

    }
    public Compra confirmarCompra(Usuario usuario, Entrada entrada) {
        Compra compra = new Compra(contadorIdCompra++, usuario, entrada, LocalDateTime.now());
        JOptionPane.showMessageDialog(null, "Compra exitosa, \n" +
                "id: " + compra.getId() + "\n" +
                "Usuario: " + usuario.getNombre() + "\n" +
                "Entrada: " + entrada.getPartido().toString() + "\n" +
                "Precio: $" + entrada.getPrecio() + "\n" +
                "Club: " + entrada.getClubVendedor().getNombre() + "\n" +
                "imprima su ticket");
                this.compras.add(compra);   
                usuario.agregarCompra(compra);  
                return compra;
    }   
}
