package torneo_futbal;

import javax.swing.JOptionPane;

public class Arbitro extends Usuario {
	
	protected String email;
    protected String rol;

    public Arbitro (String nombre, String apellido, String email) {
        super(nombre, apellido, email, "Árbitro" );
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
                "Ver partidos asignados",
                "Ver horario y lugar de los partidos",
                "Salir"
			};
			
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
                salir = true;
            } else {
                procesarOpcion(seleccion);
            }
        }
    }
	
	 private void procesarOpcion(String seleccion) {
	        switch (seleccion) {
	            case "Ver partidos asignados" -> mostrarSubmenuPartidosAsignados();
	            case "Ver horario y lugar de los partidos" -> mostrarSubmenuHorarioLugar();
	            
	            default -> JOptionPane.showMessageDialog(null, "Opción no valida.");
	        }
	    }


		
	 private void mostrarSubmenuPartidosAsignados() {
		 String seleccion = "Ver partidos asignados";
		    JOptionPane.showMessageDialog(
		        null,
		        "Has seleccionado: " + seleccion + "\n(Función aún no implementada)"
		    );
		}
	 
	 private void mostrarSubmenuHorarioLugar() {
		 String seleccion = "Ver horario y lugar de los partidos";
		    JOptionPane.showMessageDialog(
		        null,
		        "Has seleccionado: " + seleccion + "\n(Función aún no implementada)"
		    );
		}

	        
            
        
    
}

