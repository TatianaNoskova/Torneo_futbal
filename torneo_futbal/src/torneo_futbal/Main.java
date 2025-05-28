package torneo_futbal;

public class Main {
	private static GestorCompra gestorCompra;
	private static GestorEntradas gestorEntradas;

	public static GestorCompra GestorCompra() {
		if (gestorCompra == null) {
			gestorCompra = new GestorCompra();
		}
		return gestorCompra;
	}

	public static GestorEntradas GestorEntradas() {
		if (gestorEntradas == null) {
			gestorEntradas = new GestorEntradas();
		}
		return gestorEntradas;
	}

	public static void main(String[] args) {
		gestorCompra = new GestorCompra();
		gestorEntradas = new GestorEntradas();

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
