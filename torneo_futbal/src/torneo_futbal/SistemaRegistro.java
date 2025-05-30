package torneo_futbal;

import javax.swing.*;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SistemaRegistro {

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

				if (!rol.equals("Público general") && !emailAutorizadoParaRol(email, rol)) {
					JOptionPane.showMessageDialog(null, "Este correo no está autorizado para ese rol.");
					return;
				}

				Persona persona;
				switch (rol) {
				case "Administrador AFA" -> persona = new AdminAFA(nombre, apellido, email, password);
				case "Administrador de club" -> persona = new AdminClub(nombre, apellido, email, password);
				case "Socio del club" -> persona = new Socio(nombre, apellido, email, password);
				default -> persona = new Publico(nombre, apellido, email, password);
				}
					
				// Mapping para la BD
				String rolBD = switch (rol) {
					case "Administrador AFA" -> "Admin Afa";
					case "Administrador de club" -> "Admin Club";
					case "Socio del club" -> "Socio";
					default -> "Publico";
				};
				
				try (Connection conn = Conexion.getInstance().getConnection()) {
			        String sql = "SELECT COUNT(*) FROM persona WHERE email = ?";
			        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
			            stmt.setString(1, email);
			            ResultSet rs = stmt.executeQuery();
			            if (rs.next() && rs.getInt(1) > 0) {
			                
			                String updateSql = "UPDATE persona SET nombre = ?, apellido = ?, password = ?, rol = ? WHERE email = ?";
			                try (PreparedStatement updateStmt = conn.prepareStatement(updateSql)) {
			                    updateStmt.setString(1, nombre);
			                    updateStmt.setString(2, apellido);
			                    updateStmt.setString(3, password);
			                    updateStmt.setString(4, rolBD);
			                    updateStmt.setString(5, email);
			                    updateStmt.executeUpdate();
			                    JOptionPane.showMessageDialog(null, "¡Datos actualizados para el email: " + email + "!");
			                    persona.mostrarMenu();
			                } 

			            } else {
			                
			                String insertSql = "INSERT INTO persona (nombre, apellido, email, password, rol) VALUES (?, ?, ?, ?, ?)";
			                try (PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {
			                    insertStmt.setString(1, nombre);
			                    insertStmt.setString(2, apellido);
			                    insertStmt.setString(3, email);
			                    insertStmt.setString(4, password);
			                    insertStmt.setString(5, rolBD);
			                    insertStmt.executeUpdate();
			                    JOptionPane.showMessageDialog(null, "¡Registro exitoso como " + rol + "!");
			                    persona.mostrarMenu();
			                }
			            }
			        }
			    } catch (SQLException e) {
			        e.printStackTrace();
			        JOptionPane.showMessageDialog(null, "Error al registrar el usuario.");
			    }
			}
	
	private boolean emailAutorizadoParaRol(String email, String rolInterfaz) {
	    String rolBD = switch (rolInterfaz) {
	        case "Administrador AFA" -> "Admin Afa";
	        case "Administrador de club" -> "Admin Club";
	        case "Socio del club" -> "Socio";
	        case "Público general" -> "Publico";
	        default -> null;
	    };

	    if (rolBD == null) {
	        System.out.println("Rol desconocido: " + rolInterfaz);
	        return false;
	    }
	    
	    if (rolBD.equals("Publico")) {
	        return true;
	    }

	    String sql = "SELECT COUNT(*) FROM persona WHERE LOWER(email) = LOWER(?) AND rol = ?";
	    try (Connection conn = Conexion.getInstance().getConnection();
	         PreparedStatement stmt = conn.prepareStatement(sql)) {
	        
	        stmt.setString(1, email);
	        stmt.setString(2, rolBD);
	        
	        try (ResultSet rs = stmt.executeQuery()) {
	            if (rs.next()) {
	                int count = rs.getInt(1);
	                System.out.println("Coincidencias para " + email + " con rol " + rolBD + ": " + count);
	                return count > 0;
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return false;
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

	    try (Connection conn = Conexion.getInstance().getConnection()) {
	    	String sql = "SELECT * FROM persona WHERE email = ? AND password = ?";
	        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
	            stmt.setString(1, email);
	            stmt.setString(2, password);  // В будущем следует использовать хеширование паролей

	            try (ResultSet rs = stmt.executeQuery()) {
	                if (rs.next()) {
	                    String nombre = rs.getString("nombre");
	                    String apellido = rs.getString("apellido");
	                    String rol = rs.getString("rol");

	                    JOptionPane.showMessageDialog(null, "¡Inicio de sesión exitoso como " + rol + "!");

	                    Persona persona = null;

	                    switch (rol.toLowerCase()) {
	                        case "admin afa":  
	                            persona = new AdminAFA(nombre, apellido, email, password);  // Передаем параметры в конструктор
	                            break;
	                        case "admin club": 
	                            persona = new AdminClub(nombre, apellido, email, password);
	                            break;
	                        case "socio":  
	                            persona = new Socio(nombre, apellido, email, password);
	                            break;
	                        case "publico":  
	                            persona = new Publico(nombre, apellido, email, password);
	                            break;
	                        case "director tecnico":  
	                            persona = new DirectorTecnico(nombre, apellido, email, password);
	                            break;
	                        case "arbitro":  
	                            persona = new Arbitro(nombre, apellido, email, password);
	                            break;
	                        default:
	                            JOptionPane.showMessageDialog(null, "Rol desconocido.");
	                            return;  
	                    }

	                    if (persona != null) {
	                        persona.mostrarMenu();  
	                    }
	                } else {
	                    JOptionPane.showMessageDialog(null, "Correo o contraseña incorrectos.");
	                }
	            }
	        }
	    } catch (SQLException e) {
	        JOptionPane.showMessageDialog(null, "Error de conexión a la base de datos: " + e.getMessage());
	    }
	}





	private boolean emailExiste(String email) {
	    boolean exists = false;
	    try (Connection connection = Conexion.getInstance().getConnection()) {
	        String query = "SELECT rol FROM persona WHERE LOWER(email) = LOWER(?)";
	        try (PreparedStatement stmt = connection.prepareStatement(query)) {
	            stmt.setString(1, email.toLowerCase());
	            var resultSet = stmt.executeQuery();
	            if (resultSet.next()) {
	                String rolEnBaseDeDatos = resultSet.getString("rol");
	                
	                return !rolEnBaseDeDatos.equals("Admin Club"); 
	            }
	        }
	    } catch (SQLException e) {
	        JOptionPane.showMessageDialog(null, "Error al verificar el correo en la base de datos: " + e.getMessage());
	    }
	    return exists;
	}



}
