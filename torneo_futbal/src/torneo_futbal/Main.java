package torneo_futbal;

public class Main {

	public static void main(String[] args) {

		DatosSimulados.inicializar(); // ← теперь здесь

		for (Club club : SistemaRegistro.clubesRegistrados) {
			System.out.println("Club: " + club.getNombre());
			for (Equipo equipo : club.getEquipos()) {
				System.out.println("  Equipo: " + equipo.getNombre());
			}
		}
		SistemaRegistro sistema = new SistemaRegistro(); 
		sistema.iniciarRegistro();
	}
}
