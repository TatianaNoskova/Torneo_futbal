package torneo_futbal;

public class Estadio {
    
	private String nombre;
    private String direccion;
    private int capacidad;

    public Estadio(String nombre, String direccion, int capacidad) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.capacidad = capacidad;
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
