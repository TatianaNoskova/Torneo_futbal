package torneo_futbal;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class SistemaRegistro {

	/*private List<Persona> personasRegistradas;
	private List<String> adminAFAEmails;
	private List<String> adminClubEmails;
	private List<String> socioEmails;
	private List<String> arbitroEmails;
	private List<String> dtEmails; */
	private List<String> clubes;
	private SistemaTorneos sistemaTorneos;
	
	public SistemaRegistro() {
	
		/* personasRegistradas = new ArrayList<>();
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
		Socio socio = new Socio("Juan", "Socio", "juan.socio@gmail.com", "12");

		personasRegistradas.add(socio);
		socioEmails.add("ana.socia@yahoo.com");

		arbitroEmails = new ArrayList<>();
		arbitroEmails.add("arbitro1@futbol.com");

		dtEmails = new ArrayList<>();
		dtEmails.add("director.tecnico1@futbol.com"); */
	} 
	
	public static List<Club> clubesRegistrados = new ArrayList<>();
	public static List<Torneo> torneosRegistrados = new ArrayList<>();
	
	public void iniciarRegistro() {
		String[] opciones = { "Registrarse", "Iniciar sesión" };
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
	    String email;
	    String password;

	    while (true) {
	        email = JOptionPane.showInputDialog("Ingrese su correo electrónico:");

	        if (email == null || email.isBlank() || !email.contains("@")) {
	            JOptionPane.showMessageDialog(null, "Correo inválido.");
	            continue;
	        }

	        if (emailExiste(email)) {
	            JOptionPane.showMessageDialog(null, "Este correo ya está registrado.");
	            continue;
	        }

	        break;
	    }

	    while (true) {
	        password = JOptionPane.showInputDialog("Cree una contraseña:");

	        if (password == null || password.isBlank()) {
	            JOptionPane.showMessageDialog(null, "Debe ingresar una contraseña.");
	            continue;
	        }
	        break;
	    }

	    String nombre;
	    String apellido;

	    while (true) {
	        nombre = JOptionPane.showInputDialog("Ingrese su nombre:");
	        apellido = JOptionPane.showInputDialog("Ingrese su apellido:");

	        if (nombre == null || nombre.isBlank() || apellido == null || apellido.isBlank()) {
	            JOptionPane.showMessageDialog(null, "Debe ingresar un nombre y un apellido.");
	            continue;
	        }
	        break;
	    }

	    String[] roles = { "Administrador AFA", "Administrador de club", "Socio del club", "Público general" };
	    String rolSeleccionado = (String) JOptionPane.showInputDialog(
	            null,
	            "Seleccione su rol:",
	            "Registro",
	            JOptionPane.QUESTION_MESSAGE,
	            null,
	            roles,
	            roles[0]);

	    if (rolSeleccionado == null) {
	        JOptionPane.showMessageDialog(null, "No se seleccionó ningún rol.");
	        return;
	    }

	    // Mapeo del rol GUI → BD (ENUM)
	    String rolBD = switch (rolSeleccionado) {
	        case "Administrador AFA" -> "Admin Afa";
	        case "Administrador de club" -> "Admin Club";
	        case "Socio del club" -> "Socio";
	        case "Público general" -> "Publico";
	        default -> "Publico";
	    };

	 // Verificación de autorización, excepto para "Publico"
	    if (!rolBD.equals("Publico") && !emailAutorizadoParaRol(email, rolBD)) {
	        JOptionPane.showMessageDialog(null, "Este correo no está autorizado para ese rol.");
	        return;
	    }


	    // Crear objeto Persona
	    Persona persona;
	    switch (rolBD) {
	        case "Admin Afa" -> persona = new AdminAFA(nombre, apellido, email, password);
	        case "Admin Club" -> {
	            AdminClub adminClub = new AdminClub(nombre, apellido, email, password);
	            for (Club c : clubesRegistrados) {
	                if (c.getNombre().equals("Club A")) {
	                    adminClub.setClub(c);
	                    break;
	                }
	            }
	            persona = adminClub;
	        }
	        case "Socio" -> persona = new Socio(nombre, apellido, email, password);
	        default -> persona = new Publico(nombre, apellido, email, password);
	    }

	    // Сохраняем пользователя в БД
	    try {
	        Connection conn = Conexion.getInstance().getConnection();
	        String sql = "INSERT INTO persona (nombre, apellido, email, password, rol) VALUES (?, ?, ?, ?, ?)";
	        PreparedStatement stmt = conn.prepareStatement(sql);
	        stmt.setString(1, nombre);
	        stmt.setString(2, apellido);
	        stmt.setString(3, email);
	        stmt.setString(4, password);
	        stmt.setString(5, rolBD);
	        stmt.executeUpdate();
	        stmt.close();
	        System.out.println("Usuario registrado en la base de datos.");
	    } catch (SQLException e) {
	        e.printStackTrace();
	        JOptionPane.showMessageDialog(null, "Error al registrar el usuario en la base de datos.");
	        return;
	    }

	    JOptionPane.showMessageDialog(null, "¡Registro exitoso como " + rolSeleccionado + "!");
	    persona.mostrarMenu();
	}

	private void iniciarSesion() {
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

	    try {
	        Connection conn = Conexion.getInstance().getConnection();
	        String sql = "SELECT nombre, apellido, rol, password FROM persona WHERE email = ?";
	        PreparedStatement stmt = conn.prepareStatement(sql);
	        stmt.setString(1, email);
	        var rs = stmt.executeQuery();

	        if (rs.next()) {
	            String storedPassword = rs.getString("password");
	            if (!storedPassword.equals(password)) {
	                JOptionPane.showMessageDialog(null, "Contraseña incorrecta.");
	                return;
	            }

	            String nombre = rs.getString("nombre");
	            String apellido = rs.getString("apellido");
	            String rol = rs.getString("rol");

	            Persona persona;
	            switch (rol) {
	            case "Admin Afa" -> persona = new AdminAFA(nombre, apellido, email, password);
	            case "Admin Club" -> persona = new AdminClub(nombre, apellido, email, password);
	            case "Socio" -> persona = new Socio(nombre, apellido, email, password);
	            default -> persona = new Publico(nombre, apellido, email, password);
	        }


	            JOptionPane.showMessageDialog(null, "¡Inicio de sesión exitoso como " + rol + "!");
	            persona.mostrarMenu();

	        } else {
	            JOptionPane.showMessageDialog(null, "No se encontró el usuario.");
	        }

	        rs.close();
	        stmt.close();

	    } catch (SQLException e) {
	        e.printStackTrace();
	        JOptionPane.showMessageDialog(null, "Error al acceder a la base de datos.");
	    }
	}


	private boolean emailExiste(String email) {
	    String sql = "SELECT COUNT(*) FROM persona WHERE email = ?";
	    System.out.println("SQL запрос: " + sql);
	    try (Connection conn = Conexion.getInstance().getConnection()) {
	        if (conn == null) {
	            System.err.println("Ошибка: Соединение с базой данных равно null");
	            return false;
	        }
	        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
	            stmt.setString(1, email);
	            try (ResultSet rs = stmt.executeQuery()) {
	                if (rs.next()) {
	                    int count = rs.getInt(1);
	                    System.out.println("Найдено записей с email = " + email + ": " + count);
	                    return count > 0;
	                }
	            }
	        }
	    } catch (SQLException e) {
	        System.err.println("Ошибка SQL: " + e.getMessage());
	        e.printStackTrace();
	        JOptionPane.showMessageDialog(null, "Ошибка при проверке email в базе данных.");
	    }
	    return false;
	}



			
	private boolean emailAutorizadoParaRol(String email, String rolUI) {
	    // Сначала маппинг ролей из UI в роли в базе
	    String rolBD = mapearRol(rolUI);

	    String sql = "SELECT COUNT(*) FROM persona WHERE email = ? AND rol = ?";
	    try {
	        Connection conn = Conexion.getInstance().getConnection();
	        PreparedStatement stmt = conn.prepareStatement(sql);
	        stmt.setString(1, email);
	        stmt.setString(2, rolBD);
	        ResultSet rs = stmt.executeQuery();

	        if (rs.next()) {
	            return rs.getInt(1) > 0;
	        }

	        rs.close();
	        stmt.close();
	    } catch (SQLException e) {
	        e.printStackTrace();
	        JOptionPane.showMessageDialog(null, "Error al verificar autorización de rol.");
	    }
	    return false;
	}

	// Метод для преобразования ролей с UI к ролям из БД:
	private String mapearRol(String rolUI) {
	    return switch (rolUI) {
	        case "Administrador AFA" -> "Admin Afa";
	        case "Administrador de club" -> "Admin Club";
	        case "Socio del club" -> "Socio";
	        case "Público general" -> "Publico";
	        default -> "Publico";
	    };
	}




}