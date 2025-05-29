package torneo_futbal;

import java.time.LocalDateTime;

public class Compra {
   private int id;
   private Usuario usuario;
   private Entrada entrada;
   private LocalDateTime fecha; 
   
   public Compra(int id, Usuario usuario, Entrada entrada, LocalDateTime fecha) {
      this.id = id;
      this.usuario = usuario;       
      this.entrada = entrada;
      this.fecha = fecha;
   }
   // Getters y setters
   public int getId() {
      return id;
   }
   public void setId(int id) {
      this.id = id;
   }
   public Usuario getUsuario() {
      return usuario;
   }
   public void setUsuario(Usuario usuario) {
      this.usuario = usuario;
   }
   public Entrada getEntrada() {
      return entrada;
   }
   public void setEntrada(Entrada entrada) {
      this.entrada = entrada;
   }
   public LocalDateTime getFecha() {
      return fecha;
   }
   public void setFecha(LocalDateTime fecha) {
      this.fecha = fecha;
   }    
   // toString
   @Override
   public String toString() {
      return "Compra [id=" + id + ", usuario=" + usuario.getNombre() + ", entrada=" + entrada.getPartido().toString() + ", fecha=" + fecha + "]";
   }    
}

