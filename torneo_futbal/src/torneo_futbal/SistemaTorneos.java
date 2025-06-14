package torneo_futbal;

import java.util.ArrayList;
import java.util.List;

public class SistemaTorneos {

    private List<Torneo> torneos;

    public SistemaTorneos() {
        torneos = new ArrayList<>();
    }

    // Метод для добавления нового турнира
    public void agregarTorneo(Torneo torneo) {
        torneos.add(torneo);
    }

    // Метод для получения всех турниров
    public List<Torneo> obtenerTorneos() {
    	return torneos; 
    	
    }

    // Метод для отображения информации о турнире
    public void mostrarTorneos() {
        if (torneos.isEmpty()) {
            System.out.println("No hay torneos registrados.");
        } else {
            for (Torneo torneo : torneos) {
                System.out.println("Torneo: " + torneo.getNombreTorneo() + " (" + torneo.getAnoTorneo() + ")");
                System.out.println("Equipos participantes:");
                for (Equipo equipo : torneo.getEquiposParticipantes()) {
                    System.out.println(" - " + equipo.getNombre());
                }
            }
        }
    }
}


