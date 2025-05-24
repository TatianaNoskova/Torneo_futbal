package torneo_futbal;

import java.time.LocalDate;
import java.time.LocalTime;

public class Partido {
	
	private Estadio estadio;
	
	private Equipo equipo1;  
    private Equipo equipo2; 
    
    private LocalDate fecha;
    private LocalTime hora; 
    
    private Arbitro arbitro;
    private int golesEquipo1;
    private int golesEquipo2;
    private boolean resultadoCapturado = false;

    
    public Partido(Equipo equipo1, Equipo equipo2) {
        this.equipo1 = equipo1;
        this.equipo2 = equipo2;
    }

    public Estadio getEstadio() {
        return estadio;
    }

    public void setEstadio(Estadio estadio) {
        this.estadio = estadio;
    }
    
 // Геттер для первой команды
    public Equipo getEquipo1() {
        return equipo1;
    }

    // Геттер для второй команды
    public Equipo getEquipo2() {
        return equipo2;
    }
    
    
   
    
    public LocalDate getFecha() {
		return fecha;
	}

	public LocalTime getHora() {
		return hora;
	}

	public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public void setHora(LocalTime hora) {
        this.hora = hora;
    }
    
    public void setArbitro(Arbitro arbitro) {
    	this.arbitro = arbitro;
    	}

    public Arbitro getArbitro() {
    	return this.arbitro;
    	}
    public int getGolesEquipo1() { return golesEquipo1; }
    public int getGolesEquipo2() { return golesEquipo2; }

    public void setGolesEquipo1(int goles) { this.golesEquipo1 = goles; }
    public void setGolesEquipo2(int goles) { this.golesEquipo2 = goles; }

    public boolean resultadoCapturado() { return resultadoCapturado; }
    public void setResultadoCapturado(boolean capturado) { this.resultadoCapturado = capturado; }
    
    @Override
    public String toString() {
        return equipo1.getNombre() + " vs " + equipo2.getNombre();
    }


    

}
