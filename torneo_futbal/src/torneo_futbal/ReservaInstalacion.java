package torneo_futbal;

import java.time.LocalDateTime;
import java.util.UUID;

public class ReservaInstalacion {
  private String idReserva;
  private Usuario usuario;
  private LocalDateTime fechaReservaInicio;
  private LocalDateTime fechaReservaFin;
  private String estado; // "Reservada", "Cancelada", "Confirmada", "Pago_Pendiente"
  private String motivo;

  // Constructor
  public ReservaInstalacion(Usuario usuario, LocalDateTime fechaInicio, LocalDateTime fechaReservaFin) {
    this.idReserva = UUID.randomUUID().toString(); // Generar un ID aleatorio
    this.usuario = usuario;
    this.fechaReservaInicio = fechaInicio;
    this.fechaReservaFin = fechaReservaFin;
    this.estado = "Reservada";
    this.motivo = null;
  }

  // Getters y setters
  public String getIdReserva() {
    return idReserva;
  }

  public Usuario getUsuario() {
    return usuario;
  }

  public LocalDateTime getFechaReservaInicio() {
    return fechaReservaInicio;
  }

  public LocalDateTime getFechaReservaFin() {
    return fechaReservaFin;
  }

  public String getEstado() {
    return estado;
  }

  public String getMotivo() {
    return motivo;
  }

  public void setMotivo(String motivo) {
    this.motivo = motivo;
  }

  @Override
  public String toString() {
    return "ID: " + idReserva + "\n" +
        "Usuario: " + usuario.getNombre() + " " + usuario.getApellido() + "\n" +
        "Fecha de inicio: " + fechaReservaInicio + "\n" +
        "Fecha de fin: " + fechaReservaFin + "\n" +
        "Estado: " + estado + "\n" +
        "Motivo: " + motivo;
  }
}
