package torneo_futbal;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class SistemaRegistro {

    private List<Persona> personasRegistradas;
    private List<String> adminAFAEmails;
    private List<String> adminClubEmails;
    private List<String> socioEmails;
    private List<String> arbitroEmails;
    private List<String> dtEmails;
    private List<String> clubes;

    public SistemaRegistro() {
        personasRegistradas = new ArrayList<>();

        // "BASE DE DATOS"
        adminAFAEmails = new ArrayList<>();
        adminAFAEmails.add("admin@afa.com");
        
        adminClubEmails = new ArrayList<>();
        adminClubEmails.add("clubadmin1@club.com");

        socioEmails = new ArrayList<>();
        socioEmails.add("juan.socio@gmail.com");
        socioEmails.add("ana.socia@yahoo.com");
        
        arbitroEmails = new ArrayList<>();
        arbitroEmails.add("arbitro1@futbol.com");
        
        dtEmails = new ArrayList<>();
        dtEmails.add("director.tecnico1@futbol.com");
        
        
        
        
        
    }

    public void iniciarRegistro() {
        String email = JOptionPane.showInputDialog("Ingrese su orreo electrónico:");

        if (emailExiste(email)) {
            JOptionPane.showMessageDialog(null, "Este correo electrónico ya está registrado.");
            return;
        }
        
     
        if (arbitroEmails.contains(email)) {
            
            String nombre = "<Nombre del Árbitro>";  
            String apellido = "<Apellido del Árbitro>";  
            Persona nuevaPersona = new Arbitro(nombre, apellido, email);
            personasRegistradas.add(nuevaPersona);
            
            JOptionPane.showMessageDialog(null, "Has iniciado sesión como Árbitro.");
            nuevaPersona.mostrarMenu();
            return;
        };  
        
        if (dtEmails.contains(email)) {

            
            String nombre = "<Nombre del DT>";  
            String apellido = "<Apellido del DT>";  
            Persona nuevaPersona = new DirectorTecnico(nombre, apellido, email);
            personasRegistradas.add(nuevaPersona);
            
            JOptionPane.showMessageDialog(null, "Has iniciado sesión como Director Técnico del club (funcionalidad en desarrollo)");
            nuevaPersona.mostrarMenu();
            return;
        }; 
        	

        String nombre = JOptionPane.showInputDialog("Ingrese su nombre:");
        String apellido = JOptionPane.showInputDialog("Ingrese su apellido:");

        
        String[] opcionesRol = {
                "Administrador AFA",
                "Administrador de club",
                "Socio del club",
                "Público general"
        };

        String rolSeleccionado = (String) JOptionPane.showInputDialog(
                null,
                "Seleccione su rol:",
                "Selección de rol",
                JOptionPane.QUESTION_MESSAGE,
                null,
                opcionesRol,
                opcionesRol[0]
        );

        if (rolSeleccionado == null) {
            JOptionPane.showMessageDialog(null, "No se seleccionó ninguna opción.");
            return;
        }

        // verificación del rol
        boolean verificado = switch (rolSeleccionado) {
            case "Administrador AFA" -> adminAFAEmails.contains(email);
            case "Administrador de club" -> adminClubEmails.contains(email);
            case "Socio del club" -> socioEmails.contains(email);
            case "Árbitro" -> arbitroEmails.contains(email);
            default -> true; // Público general - para todos
        };

        if (!verificado) {
            JOptionPane.showMessageDialog(null, "Error de verificación: el correo no está autorizado para el rol seleccionado.");
            return;
        }

        
        
        Persona nuevaPersona;

        switch (rolSeleccionado) {
            case "Administrador AFA":
                nuevaPersona = new AdminAFA(nombre, apellido, email);
                break;
            case "Administrador de club":
                nuevaPersona = new AdminClub(nombre, apellido, email);
                break;
            case "Socio del club":
                nuevaPersona = new Socio(nombre, apellido, email);
                break;
            case "Árbitro":
                nuevaPersona = new Arbitro(nombre, apellido, email);
                JOptionPane.showMessageDialog(null, "Has iniciado sesión como árbitro.");
                break;
                
            default: // Público general
                nuevaPersona = new Publico(nombre, apellido, email);
        }
        
        personasRegistradas.add(nuevaPersona);
        
     
        JOptionPane.showMessageDialog(null, "¡Te has registrado con éxito como: " + rolSeleccionado + "!");
        nuevaPersona.mostrarMenu();

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
