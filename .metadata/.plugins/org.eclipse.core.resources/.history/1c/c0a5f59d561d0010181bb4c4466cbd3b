package torneo_futbal;

import javax.swing.JOptionPane;

public class Arbitro extends Persona {
	
	protected String email;
    protected String rol;

    public Arbitro (String nombre, String apellido, String email) {
        super(nombre, apellido);
        this.email = email;
        this.rol = "Árbitro";
    }

	public String getEmail() {
		return email;
	}

		
	@Override
    public void mostrarMenu() {
        String[] opciones = {
                "Ver partidos asignados (demostración)",
                "Ver horario y lugar de los partidos",
                "Salir"
        };
        
        boolean continuar = true; 

        while (continuar) {
            String seleccion = (String) JOptionPane.showInputDialog(
                    null,
                    "Bienvenido " + nombre + " " + apellido + "\n\nSelecciona una opción:",
                    "Menú Árbitro",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    opciones,
                    opciones[0]
            );

            if (seleccion == null || seleccion.equals("Salir")) {
                JOptionPane.showMessageDialog(null, "Saliendo del menú.");
                continuar=false;
            	}else {

            switch (seleccion) {
                case "Ver partidos asignados (demostración)":
                    JOptionPane.showMessageDialog(null, "Mostrando partidos asignados (demostración).");
                    break;
                case "Ver horario y lugar de los partidos":
                    JOptionPane.showMessageDialog(null, "Mostrando horario y lugar de los partidos (demostración).");
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Opción no válida.");
            	}
            }
        }
    }
}
