package torneo_futbal;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class InstalacionDeportiva {

    private String nombreInstalacion; // Name of the installation
    private String direccion; // Direcciòn
    private Disciplina disciplina; // Associated disciplines
    private int capacidad;
    private List<ReservaInstalacion> reservas; // Reservations

    // Constructor
    public InstalacionDeportiva(String nombreInstalacion, String direccion, Disciplina disciplina, int capacidad) {
        this.nombreInstalacion = nombreInstalacion;
        this.direccion = direccion;
        this.capacidad = capacidad;
        this.disciplina = disciplina;
        this.reservas = new ArrayList<>();
    }

    // Getters y setters
    public String getNombreInstalacion() {
        return nombreInstalacion;
    }

    public void setNombreInstalacion(String nombreInstalacion) {
        this.nombreInstalacion = nombreInstalacion;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Disciplina getDisciplina() {
        return disciplina;
    }

    public List<ReservaInstalacion> getReservas() {
        return new ArrayList<>(reservas);
    }

    // setters disciplinas and reservas

    public void setDisciplina(Disciplina disciplina) {
        this.disciplina = disciplina;
    }

    public void setReservas(ReservaInstalacion reserva) {
        this.reservas.add(reserva);
    }

    // Verificar disponibilidad de instalación
    public boolean estaDisponible(LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        for (ReservaInstalacion reserva : reservas) {
            if (reserva.getEstado().equals("Reservada")) {
                // Comprobar si la instalación está solapada en tiempos
                if (reserva.getFechaReservaInicio().isBefore(fechaFin)
                        && reserva.getFechaReservaFin().isAfter(fechaInicio)) {
                    return false; // La instalación está solapada en tiempos
                }
            }
        }
        return true; // La instalación está disponible
    }

    // toString
    @Override
    public String toString() {
        return "Nombre: " + nombreInstalacion + "\n" +
                "Disciplinas: " + disciplina + "\n" +
                "Reservas: " + reservas;
    }

}
