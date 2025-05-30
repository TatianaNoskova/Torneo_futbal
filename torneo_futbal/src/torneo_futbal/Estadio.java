package torneo_futbal;

public class Estadio {
    
	protected int idEstadio;
	private String nombre;
    private String direccion;
    private int capacidad;

    public Estadio(int idEstadio, String nombre, String direccion, int capacidad) {
    	this.idEstadio = idEstadio;
        this.nombre = nombre;
        this.direccion = direccion;
        this.capacidad = capacidad;
    }
    
    public int getIdEstadio() {
    	return idEstadio;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public int getCapacidad() {
        return capacidad;
    }

    @Override
    public String toString() {
        return nombre + " (Dirección: " + direccion + ", Capacidad: " + capacidad + ")";
    }
}
