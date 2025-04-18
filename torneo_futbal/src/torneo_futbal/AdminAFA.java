package torneo_futbal;

import javax.swing.JOptionPane;

public class AdminAFA extends Administrador {
	
	public AdminAFA(String nombre, String apellido, String email) {
        super(nombre, apellido, email, "Administrador AFA");
    }

	
	 @Override
	    public void mostrarMenu() {
	        boolean salir = false;

	        while (!salir) {
	            String[] opciones = {
	                    "Crear torneo",
	                    "Asignar fechas y horarios",
	                    "Capturar estadísticas y resultados",
	                    "Salir"
	            };

	            String seleccion = (String) JOptionPane.showInputDialog(
	                    null,
	                    "Menú Administrador AFA",
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
	            case "Crear torneo" -> mostrarSubmenuCrearTorneo();
	            case "Asignar fechas y horarios" -> mostrarSubmenuAsignarFechas();
	            case "Capturar estadísticas y resultados" -> mostrarSubmenuCapturarResultados();
	            default -> JOptionPane.showMessageDialog(null, "Opción no reconocida.");
	        }
	    }

	    private void mostrarSubmenuCrearTorneo() {
	        String[] opciones = {
	                "Registrar equipo",
	                "Registrar árbitro",
	                "Volver"
	        };

	        String seleccion = (String) JOptionPane.showInputDialog(
	                null,
	                "Submenú - Crear torneo",
	                "Opciones",
	                JOptionPane.QUESTION_MESSAGE,
	                null,
	                opciones,
	                opciones[0]
	        );

	        if (seleccion != null && !seleccion.equals("Volver")) {
	            JOptionPane.showMessageDialog(null,
	                    "Has seleccionado: " + seleccion + "\n(Función aún no implementada)");
	        }
	    }

	    private void mostrarSubmenuAsignarFechas() {
	        String[] opciones = {
	                "Sortear partido",
	                "Generar los grupos",
	                "Asignar sedes",
	                "Asignar árbitros",
	                "Volver"
	        };

	        String seleccion = (String) JOptionPane.showInputDialog(
	                null,
	                "Submenú - Asignar fechas y horarios",
	                "Opciones",
	                JOptionPane.QUESTION_MESSAGE,
	                null,
	                opciones,
	                opciones[0]
	        );

	        if (seleccion != null && !seleccion.equals("Volver")) {
	            JOptionPane.showMessageDialog(null,
	                    "Has seleccionado: " + seleccion + "\n(Función aún no implementada)");
	        }
	    }

	    private void mostrarSubmenuCapturarResultados() {
	        String[] opciones = {
	                "Generar fixture",
	                "Tabla de goleadores",
	                "Estadísticas por equipo",
	                "Estadísticas individuales de jugadores",
	                "Volver"
	        };

	        String seleccion = (String) JOptionPane.showInputDialog(
	                null,
	                "Submenú - Capturar estadísticas y resultados",
	                "Opciones",
	                JOptionPane.QUESTION_MESSAGE,
	                null,
	                opciones,
	                opciones[0]
	        );

	        if (seleccion != null && !seleccion.equals("Volver")) {
	            JOptionPane.showMessageDialog(null,
	                    "Has seleccionado: " + seleccion + "\n(Función aún no implementada)");
	        }
	    }
	

}
