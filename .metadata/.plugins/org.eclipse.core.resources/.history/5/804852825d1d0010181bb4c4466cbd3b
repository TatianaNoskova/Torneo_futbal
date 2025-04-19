package torneo_futbal;

import javax.swing.JOptionPane;

public class Usuario extends Persona {
	
	protected String email;
    protected String rol;

    public Usuario(String nombre, String apellido, String email, String rol) {
        super(nombre, apellido);
        this.email = email;
        this.rol = rol;
    }

	public String getEmail() {
		return email;
	}

	public String getRol() {
		return rol;
	}
    
    
	@Override
	public void mostrarMenu() {
	    
	    String menu = "Menú Usuario:\n" +
	            "1. Ver actividades de todos los clubes\n" +
	            "2. Consultar fechas y horarios disponibles\n" +
	            "3. Reservar / modificar / cancelar reserva\n" +
	            "4. Ver próximos partidos\n" +
	            "5. Elegir categoría de entrada\n" +
	            "6. Comprar entrada\n" +
	            "7. Salir";

	    JOptionPane.showMessageDialog(null, menu);
	}

}
