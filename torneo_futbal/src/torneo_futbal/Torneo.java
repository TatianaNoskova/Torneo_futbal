package torneo_futbal;

import java.util.ArrayList;
import java.util.List;

public class Torneo {
	
	String nombreTorneo;
	String anoTorneo;
	private List<Equipo> equiposParticipantes = new ArrayList<>();
	
	public Torneo(String nombreTorneo, String anoTorneo) {
		
		this.nombreTorneo = nombreTorneo;
		this.anoTorneo = anoTorneo;
	}
	
	
	
	public String getNombreTorneo() {
		return nombreTorneo;
	}
	
	public String getAnoTorneo() {
		return anoTorneo;
	}
	
	public void agregarEquipoParticipante(Equipo equipo) {
	    equiposParticipantes.add(equipo);
	}

	public List<Equipo> getEquiposParticipantes() {
	    return equiposParticipantes;
	}
	
	
	

}
