package torneo_futbal;

import javax.swing.JOptionPane;

public class DirectorTecnico extends Usuario {
	
	protected String email;
    protected String rol;

	public DirectorTecnico(String nombre, String apellido, String email) {
		super(nombre, apellido, email, "Director Técnico");
		
		this.email = email;
	
	}
	
	public String getEmail() {
		return email;
	}

		
	@Override
    public void mostrarMenu() {
		boolean salir = false;
		
		while (!salir) {
			String[] opciones = {
                "Registrar jugadores",
                "Hacer alineación",
                "Consultar estadísticas",
                "Salir"
			};
        
        
         String seleccion = (String) JOptionPane.showInputDialog(
                    null,
                    "Bienvenido " + nombre + " " + apellido + "\n\nSelecciona una opción:",
                    "Menú Director Técnico",
                    JOptionPane.PLAIN_MESSAGE,
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
	        case "Registrar jugadores" -> mostrarSubmenuRegistrarJugadores();
	        case "Hacer alineación"-> mostrarSubmenuHacerAlineacion();
	        case "Consultar estadísticas" -> mostrarSubmenuConsultarEstadisticas();
	        default -> JOptionPane.showMessageDialog(null, "Opción no válida.");
	    }
	}
	    
	private void mostrarSubmenuRegistrarJugadores() {
		String seleccion = "Registrar Jugadores";
			JOptionPane.showMessageDialog(
			null,
			"Has seleccionado: " + seleccion + "\n(Función aún no implementada)"
			 );
	}
	
	private void mostrarSubmenuHacerAlineacion() {
		String seleccion = "Hacer alineacion";
			JOptionPane.showMessageDialog(
			null,
			"Has seleccionado: " + seleccion + "\n(Función aún no implementada)"
			 );
	}
	
	private void mostrarSubmenuConsultarEstadisticas() {
		String seleccion = "Consultar estadisticas";
			JOptionPane.showMessageDialog(
			null,
			"Has seleccionado: " + seleccion + "\n(Función aún no implementada)"
			);
	}
	
}
