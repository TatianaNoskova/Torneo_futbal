package torneo_futbal;

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
    
    
    
}
