package torneo_futbal;

import java.io.File;

import javax.swing.ImageIcon;

public class Equipo {
	
	
		protected int idEquipo;
	    private String nombre;
	    private String categoria;
	    private String colores;
	    private String rutaEscudo; 
	    private Estadio estadioLocal;
	    
	    private DirectorTecnico directorTecnico;

	    public Equipo(int idEquipo, String nombre, String categoria, String colores, String rutaEscudo, Estadio estadioLocal) {
	        this.idEquipo = idEquipo;
	    	this.nombre = nombre;
	        this.categoria = categoria;
	        this.colores = colores;
	        this.rutaEscudo = rutaEscudo; // Creamos un ImageIcon con el escudo del equipo, usando la ruta del archivo de imagen seleccionada por el usuario
	        this.estadioLocal = estadioLocal;
	    }
	    
	    public int getIdEquipo() {
	        return idEquipo;
	    }

	    public String getNombre() {
	        return nombre;
	    }

	    public String getCategoria() {
	        return categoria;
	    }

	    public String getColores() {
	        return colores;
	    }

	    public ImageIcon getEscudo() {
	        if (rutaEscudo != null && !rutaEscudo.isBlank()) {
	            File archivo = new File(rutaEscudo);
	            if (archivo.exists()) {
	                return new ImageIcon(rutaEscudo);
	            }
	        }
	        return null;
	    }


	    public Estadio getEstadioLocal() {
	        return estadioLocal;
	    }
	    
	    public DirectorTecnico getDirectorTecnico() {
	        return directorTecnico;
	    }

	    public void setDirectorTecnico(DirectorTecnico directorTecnico) {
	        this.directorTecnico = directorTecnico;
	    }

	    @Override
	    public String toString() {
	        return "Equipo: " + nombre +
	               "\nCategoría: " + categoria +
	               "\nColores: " + colores +
	               "\nEscudo: " + rutaEscudo +  // Путь к эмблеме
	               "\nEstadio Local: " + (estadioLocal != null ? estadioLocal.getNombre() : "No asignado");  
	    }
}
