package torneo_futbal;

public abstract class Administrador extends Persona {
	
	protected String rol;

	public Administrador(String nombre, String apellido, String email, String password, String rol) {
		super(nombre, apellido, email, password);
		this.rol = rol;
	}

	public String getRol() {
		return rol;
	}
    
    

}
