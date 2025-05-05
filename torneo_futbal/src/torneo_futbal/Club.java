package torneo_futbal;

import java.util.ArrayList;
import java.util.List;

public class Club {
    private String nombre;
    private String direccion;
   // private List<Equipo> equipos;
   // private List<Estadio> estadios;

    public Club(String nombre, String direccion) {
        this.nombre = nombre;
        this.direccion = direccion;
   //     this.equipos = new ArrayList<>();
   //     this.estadios = new ArrayList<>();
    }

    public String getNombre() {
        return nombre;
    }

    public String getDireccion() {
        return direccion;
    }

   // public List<Equipo> getEquipos() {
   //     return equipos;
    //}

   // public List<Estadio> getEstadios() {
   //     return estadios;
    //}

    //public void agregarEquipo(Equipo equipo) {
  //      equipos.add(equipo);
   // }

  //  public void agregarEstadio(Estadio estadio) {
  //      estadios.add(estadio);
  //  }

    @Override
    public String toString() {
        return "Nombre del club: " + nombre + "\nDirección: " + direccion;
    }
}

