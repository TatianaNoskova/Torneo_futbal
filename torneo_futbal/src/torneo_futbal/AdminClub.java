package torneo_futbal;

import javax.swing.JOptionPane;

public class AdminClub extends Administrador {
	
	private Club club;

    public AdminClub(String nombre, String apellido, String email) {
        super(nombre, apellido, email, "Administrador del Club");
    }

    @Override
    public void mostrarMenu() {
        boolean salir = false;

        while (!salir) {
            String[] opciones = {
                    "Registrar club",
                    "Registrar estadio",
                    "Registrar equipo",
                    "Registrar director técnico",
                    "Gestionar disciplinas y actividades",
                    "Reservar/administrar instalaciones",
                    "Agregar categorías de entradas",
                    "Vender entradas",
                    "Administrar beneficios y premios",
                    "Salir"
            };

            String seleccion = (String) JOptionPane.showInputDialog(
                    null,
                    "Menú Administrador de Club",
                    "Opciones",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    opciones,
                    opciones[0]
            );

            if (seleccion == null || seleccion.equals("Salir")) {
                salir = true;
            } else {
            	procesarOpcion(seleccion);
            }
        }
    }
    
    private void procesarOpcion(String seleccion) {
        switch (seleccion) {
            case "Registrar club" -> registrarClub();
            // Otros opciones van a ser agregadas despues
            
            default -> JOptionPane.showMessageDialog(null,
                    "Has seleccionado: " + seleccion + "\n(Función aún no implementada)");
        }
    }
    
    private void registrarClub() {
        if (club != null) {
            JOptionPane.showMessageDialog(null, "Ya tienes un club registrado:\n" + club);
            return;
        }

        String nombre = JOptionPane.showInputDialog("Ingrese el nombre del club:");
        String direccion = JOptionPane.showInputDialog("Ingrese la dirección del club:");

        if (nombre == null || direccion == null || nombre.isBlank() || direccion.isBlank()) {
            JOptionPane.showMessageDialog(null, "Datos inválidos. Por favor, intenta nuevamente");
            return;
        }

        club = new Club(nombre, direccion);
        JOptionPane.showMessageDialog(null, "Club registrado exitosamente:\n" + club);
    }
}
