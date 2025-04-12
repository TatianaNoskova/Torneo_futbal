package torneo_futbal;

import javax.swing.JOptionPane;

public class AdminClub extends Administrador {

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
                JOptionPane.showMessageDialog(null,
                        "Has seleccionado: " + seleccion + "\n(Función aún no implementada)");
            }
        }
    }
}
