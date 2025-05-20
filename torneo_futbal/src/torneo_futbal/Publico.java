package torneo_futbal;

public class Publico extends Usuario {
	public Publico(String nombre, String apellido, String email, String password) {
		super(nombre, apellido, email, "Público general", password);
	}
}
