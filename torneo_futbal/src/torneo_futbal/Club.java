package torneo_futbal;

import java.util.ArrayList;
import java.util.List;

public class Club {
    private String nombre;
    private String direccion;
    
    private List<Estadio> estadios;
    private List<Equipo> equipos;
    private List<Disciplina> disciplinas;
    private List<InstalacionDeportiva> instalaciones;
    private List<DirectorTecnico> directoresTecnicos;



    public Club(String nombre, String direccion) {
        this.nombre = nombre;
        this.direccion = direccion;
       
        this.estadios = new ArrayList<>();
        this.equipos = new ArrayList<>();
        this.disciplinas = new ArrayList<>();   
        this.instalaciones = new ArrayList<>();
        this.directoresTecnicos = new ArrayList<>();


        
        

    }

    public String getNombre() {
        return nombre;
    }

    public String getDireccion() {
        return direccion;
    }
    
    public void agregarEstadio(Estadio estadio) {
		estadios.add(estadio);
  }
    
    public List<Estadio> getEstadios() {
    	return estadios;
    }

    
    public void agregarEquipo(Equipo equipo) {
    	equipos.add(equipo);
	}
    
    public List<Equipo> getEquipos() {
    	return equipos;
    }
    
    public void agregarDisciplina(Disciplina disciplina) {
    	disciplinas.add(disciplina);
    }
    
    public List<Disciplina> getDisciplinas() {
        return disciplinas;
    }
    
    public void agregarInstalacion(InstalacionDeportiva instalacion) {
    	instalaciones.add(instalacion);
    }

    public List<InstalacionDeportiva> getInstalaciones() {
        return instalaciones;
    }
    
    public void agregarDirectorTecnico(DirectorTecnico dt) {
        directoresTecnicos.add(dt);
    }

    public List<DirectorTecnico> getDirectoresTecnicos() {
        return directoresTecnicos;
    }


    

    @Override
    public String toString() {
        return "Nombre del club: " + nombre + "\nDirección: " + direccion;
    }
}

