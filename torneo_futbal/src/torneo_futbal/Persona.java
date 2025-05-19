package torneo_futbal;

public abstract class Persona {
    protected String nombre;
    protected String apellido;
    protected String email;

    public Persona(String nombre, String apellido, String email) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
    }

	public String getNombre() {
		return nombre;
	}

	public String getApellido() {
		return apellido;
	}
	
	public String getEmail() {
	    return email;
	}

	public abstract void mostrarMenu(); 
}
