package torneo_futbal;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Torneo {
	
	String nombreTorneo;
	String anoTorneo;
	private List<Equipo> equiposParticipantes = new ArrayList<>();
	private List<String> partidosSorteados; // Хранение матчей
	private List<Partido> partidos;  
	
	public Torneo(String nombreTorneo, String anoTorneo) {
		
		this.nombreTorneo = nombreTorneo;
		this.anoTorneo = anoTorneo;
		this.partidos = new ArrayList<>();
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
	
	// Метод для получения сортировки матчей
    public List<String> obtenerPartidosSorteados() {
        return partidosSorteados;
    }

    // Метод для сохранения результата розыгрыша
    public void setPartidosSorteados(List<String> partidos) {
        partidosSorteados = partidos;
    }
    
 // Метод для добавления матча в турнир
    public void agregarPartido(Partido partido) {
        partidos.add(partido);
    }

    // Метод для получения всех уникальных категорий команд, участвующих в турнире
    public Set<String> getCategoriasDePartidos() {
        Set<String> categorias = new HashSet<>();
        for (Partido partido : partidos) {
            if (partido.getEquipo1() != null) {
                categorias.add(partido.getEquipo1().getCategoria());
            }
            if (partido.getEquipo2() != null) {
                categorias.add(partido.getEquipo2().getCategoria());
            }
        }
        return categorias;
    }
    

    // Метод для получения списка матчей для выбранной категории
    public List<Partido> getPartidosPorCategoria(String categoria) {
        List<Partido> partidosPorCategoria = new ArrayList<>();
        for (Partido partido : partidos) {
            // Проверяем, соответствует ли категория хотя бы одной из команд
            if (partido.getEquipo1().getCategoria().equalsIgnoreCase(categoria) ||
                partido.getEquipo2().getCategoria().equalsIgnoreCase(categoria)) {
                partidosPorCategoria.add(partido);
            }
        }
        return partidosPorCategoria;
    }
public List<Partido> getPartidos() {
	return partidos;
}
   
}
	
	
	

