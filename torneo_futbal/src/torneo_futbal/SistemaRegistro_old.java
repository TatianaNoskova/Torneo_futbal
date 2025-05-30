package torneo_futbal;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;




public class SistemaRegistro_old {

	private List<Persona> personasRegistradas;
	private List<String> adminAFAEmails;
	private List<String> adminClubEmails;
	private List<String> socioEmails;
	private List<String> arbitroEmails;
	private List<String> dtEmails;
	private List<String> clubes;
	private SistemaTorneos sistemaTorneos;
	
	public SistemaRegistro_old() {
	
	personasRegistradas = new ArrayList<>();
	sistemaTorneos = new SistemaTorneos();

	// "BASE DE DATOS"
	adminAFAEmails = new ArrayList<>();
	adminAFAEmails.add("admin@afa.com");
	
	// Agregamos el usuario para simular BD:
	AdminAFA admin = new AdminAFA("Afa", "Admin", "admin@afa.com", "12");
	personasRegistradas.add(admin);
	adminClubEmails = new ArrayList<>();
	adminClubEmails.add("clubadmin1@club.com");

	socioEmails = new ArrayList<>();
	socioEmails.add("juan.socio@gmail.com");
	Socio socio = new Socio ("Juan", "Socio", "juan.socio@gmail.com", "12");
	
	personasRegistradas.add(socio);
	socioEmails.add("ana.socia@yahoo.com");
	
	arbitroEmails = new ArrayList<>();
	arbitroEmails.add("arbitro1@futbol.com");
	
	dtEmails = new ArrayList<>();
	dtEmails.add("director.tecnico1@futbol.com");
	}
	
	public static List<Club> clubesRegistrados = new ArrayList<>();
	public static List<Torneo> torneosRegistrados = new ArrayList<>();
	
	public void iniciarRegistro() {
		String[] opciones = {"Registrarse", "Iniciar sesión"};
		String seleccion = (String) JOptionPane.showInputDialog(null,
				"¿Qué deseas hacer?",
				"Bienvenido",
				JOptionPane.QUESTION_MESSAGE,
				null,
				opciones,
				opciones[0]
				);

				if (seleccion == null) {
					JOptionPane.showMessageDialog(null, "Operación cancelada.");
					return;
				}

				if (seleccion.equals("Registrarse")) {
					registrarNuevoUsuario();
					} else {
						iniciarSesion();
					}
	}

	private void registrarNuevoUsuario() {
		String email = JOptionPane.showInputDialog("Ingrese su correo electrónico:");
			if (email == null || email.isBlank() || !email.contains("@")) {
			JOptionPane.showMessageDialog(null, "Correo inválido.");
				return;
			}

			if (emailExiste(email)) {
				JOptionPane.showMessageDialog(null, "Este correo ya está registrado.");
				return;
			}

			String password = JOptionPane.showInputDialog("Cree una contraseña:");
			if (password == null || password.isBlank()) {
				JOptionPane.showMessageDialog(null, "Debe ingresar una contraseña.");
				return;
			}

			String nombre = JOptionPane.showInputDialog("Ingrese su nombre:");
			String apellido = JOptionPane.showInputDialog("Ingrese su apellido:");

			String[] roles = {"Administrador AFA", "Administrador de club", "Socio del club", "Público general"};
			String rol = (String) JOptionPane.showInputDialog(
					null,
					"Seleccione su rol:",
					"Registro",
					JOptionPane.QUESTION_MESSAGE,
					null,
					roles,
					roles[0]
					);

					if (rol == null) {
						JOptionPane.showMessageDialog(null, "No se seleccionó ningún rol.");
						return;
					}

	// Verificación de los roles
					if ((rol.equals("Administrador AFA") && !adminAFAEmails.contains(email)) ||
							(rol.equals("Administrador de club") && !adminClubEmails.contains(email)) ||
							(rol.equals("Socio del club") && !socioEmails.contains(email))) {
						JOptionPane.showMessageDialog(null, "Este correo no está autorizado para ese rol.");
						return;
					}

					Persona persona;
					switch (rol) {
					case "Administrador AFA" -> persona = new AdminAFA(nombre, apellido, email, password);
					case "Administrador de club" -> {
						// Eliminar después de la connexión a la BD
						AdminClub adminClub = new AdminClub(nombre, apellido, email, password);
						for (Club c : clubesRegistrados) {
							if (c.getNombre().equals("Club A")) {
							adminClub.setClub(c);
							break;
							}
						}
						persona = adminClub;
					}
					case "Socio del club" -> persona = new Socio(nombre, apellido, email, password);
					default -> persona = new Publico(nombre, apellido, email, password);
						}

						// Guardamos la contraseña
					if (persona instanceof Usuario usuario) {
						usuario.setPassword(password);
					}

					personasRegistradas.add(persona);
					JOptionPane.showMessageDialog(null, "¡Registro exitoso como " + rol + "!");
					persona.mostrarMenu();
	}

	private void iniciarSesion() {
		
		System.out.println("Intentando iniciar sesión...");
		
		String email = JOptionPane.showInputDialog("Ingrese su correo electrónico:");
			if (email == null || email.isBlank()) {
				JOptionPane.showMessageDialog(null, "Debe ingresar un correo.");
				return;
			}

			String password = JOptionPane.showInputDialog("Ingrese su contraseña:");
			if (password == null || password.isBlank()) {
				JOptionPane.showMessageDialog(null, "Debe ingresar una contraseña.");
				return;
			}

			// Busqueda por todos los tipos de usuarios
			for (Persona persona : personasRegistradas) {
			// Verificamos email и password
			if (persona.getEmail().equalsIgnoreCase(email) && persona.getPassword().equals(password)) {
				JOptionPane.showMessageDialog(null, "¡Inicio de sesión exitoso como " + obtenerRol(persona) + "!");
					persona.mostrarMenu(); 
					return;
				}
			}

			// si no se encuentra el usuario o el email o contraseña no coinciden
				JOptionPane.showMessageDialog(null, "Correo o contraseña incorrectos.");
		}
	
		private String obtenerRol(Persona persona) {
			if (persona instanceof Administrador admin) {
				return admin.getRol();
			} else if (persona instanceof Usuario usuario) {
				return usuario.getRol();
			} else {
				return "Usuario";
			}
	}





	private boolean emailExiste(String email) {
		for (Persona persona : personasRegistradas) {
			if (persona instanceof Usuario usuario) {
				if (usuario.getEmail().equalsIgnoreCase(email)) {
					return true;
				}
			} else if (persona instanceof Administrador admin) {
				if (admin.getEmail().equalsIgnoreCase(email)) {
						return true;
				}
			}
		}
		return false;
	}

}
