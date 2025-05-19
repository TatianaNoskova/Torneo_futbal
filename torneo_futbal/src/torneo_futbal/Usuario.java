package torneo_futbal;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

public abstract class Usuario extends Persona {

	protected String rol;
	protected List<ReservaInstalacion> misReservas;

	public Usuario(String nombre, String apellido, String email, String rol) {
		super(nombre, apellido, email);
		this.rol = rol;
		this.misReservas = new ArrayList<>();
		ReservaInstalacion reserva1 = new ReservaInstalacion(this, LocalDateTime.now().plusDays(5),
				LocalDateTime.now().plusDays(5).plusHours(5));
		this.agregarReserva(reserva1);
	}

	// getters y setters
	public String getRol() {
		return rol;
	}

	public List<ReservaInstalacion> getMisReservas() {

		return new ArrayList<>(misReservas);
	}

	public void agregarReserva(ReservaInstalacion reserva) {
		misReservas.add(reserva);
	}

	@Override
	public void mostrarMenu() {
		boolean salir = false;

		while (!salir) {
			String[] opciones = {
					"Seleccionar disciplina deportiva y gestionar el club",
					"Comprar entradas para partidos",
					"Reservar instalación",
					"Salir"
			};

			String seleccion = (String) JOptionPane.showInputDialog(
					null,
					"Menú Usuario:\n" +
							"Seleccione una opción:",
					"Menú Usuario",
					JOptionPane.QUESTION_MESSAGE,
					null,
					opciones,
					opciones[0]);

			if (seleccion == null || seleccion.equals("Salir")) {
				salir = true;
			} else {
				procesarOpcion(seleccion);
			}
		}
	}

	private void procesarOpcion(String seleccion) {
		switch (seleccion) {
			case "Seleccionar disciplina deportiva y gestionar el club" -> mostrarSubmenuClub();
			case "Comprar entradas para partidos" -> mostrarSubmenuCompraEntradas();
			case "Reservar instalación" -> mostrarSubmenuReservarInstalacion(SistemaRegistro.clubesRegistrados);
			default -> JOptionPane.showMessageDialog(null, "Opción no válida.");
		}
	}

	private void mostrarSubmenuClub() {
		String[] opciones = {
				"Ver actividades de todos los clubes",
				"Consultar fechas y horarios disponibles",
				"Reservar / modificar / cancelar reserva",
				"Volver"
		};

		String seleccion = (String) JOptionPane.showInputDialog(
				null,
				"Submenú - Gestión del Club",
				"Opciones",
				JOptionPane.QUESTION_MESSAGE,
				null,
				opciones,
				opciones[0]);

		if (seleccion != null && !seleccion.equals("Volver")) {
			JOptionPane.showMessageDialog(null,
					"Has seleccionado: " + seleccion + "\n(Función aún no implementada)");
		}
	}

	private void mostrarSubmenuCompraEntradas() {
		String[] opciones = {
				"Ver próximos partidos",
				"Elegir categoría de entrada",
				"Comprar entrada",
				"Solicitar reembolso",
				"Volver"
		};

		String seleccion = (String) JOptionPane.showInputDialog(
				null,
				"Submenú - Compra de Entradas",
				"Opciones",
				JOptionPane.QUESTION_MESSAGE,
				null,
				opciones,
				opciones[0]);

		if (seleccion != null && !seleccion.equals("Volver")) {
			JOptionPane.showMessageDialog(null,
					"Has seleccionado: " + seleccion + "\n(Función aún no implementada)");
		}
	}

	private void mostrarSubmenuReservarInstalacion(List<Club> clubesRegistrados) {
		String[] opciones = {
				"Ver disponibilidad",
				"Ver reservas",
				"Reservar instalación",
				"Cancelar reserva",
				"Volver"
		};

		String seleccion = (String) JOptionPane.showInputDialog(
				null,
				"Submenú - Reservar instalación",
				"Opciones",
				JOptionPane.QUESTION_MESSAGE,
				null,
				opciones,
				opciones[0]);

		if (seleccion == null || seleccion.equals("Volver")) {
			return;
		}

		switch (seleccion) {
			case "Ver disponibilidad" -> verInstalacionesDisponibles(clubesRegistrados);
			case "Ver reservas" -> mostrarMisReservas();
			case "Reservar instalación" -> JOptionPane.showMessageDialog(null,
					"Has seleccionado: " + seleccion + "\n(Función aún no implementada)"); // realizarNuevaReserva();
			case "Cancelar reserva" -> JOptionPane.showMessageDialog(null,
					"Has seleccionado: " + seleccion + "\n(Función aún no implementada)"); // cancelarMiReserva();
			default -> JOptionPane.showMessageDialog(null, "Opción no válida.");
		}
	}

	// Metodo para ver las instalaciones disponibles
	private void verInstalacionesDisponibles(List<Club> clubes) {
		if (clubes.isEmpty()) {
			JOptionPane.showMessageDialog(null, "No hay clubes registrados.");
			return;
		}

		String[] nombres = new String[clubes.size()];
		for (int i = 0; i < clubes.size(); i++) {
			nombres[i] = clubes.get(i).getNombre();
		}

		String seleccion = (String) JOptionPane.showInputDialog(
				null,
				"Seleccione el club:",
				"Club",
				JOptionPane.QUESTION_MESSAGE,
				null,
				nombres,
				nombres[0]);

		if (seleccion == null) {
			return;
		}

		Club clubSeleccionado = null;
		for (Club club : clubes) {
			if (club.getNombre().equals(seleccion)) {
				clubSeleccionado = club;
				break;
			}
		}
		if (clubSeleccionado == null) {
			return;
		}

		// Listamos las instalaciones deportivas del club
		List<InstalacionDeportiva> instalacionesDeportivas = clubSeleccionado.getInstalaciones();
		if (instalacionesDeportivas.isEmpty()) {
			JOptionPane.showMessageDialog(null, "No hay instalaciones deportivas disponibles.");
			return;
		}

		// Mostrar instalaciones disponibles del club
		StringBuilder resultado = new StringBuilder("Instalaciones disponibles:\n");
		for (InstalacionDeportiva instalacionDeportiva : instalacionesDeportivas) {
			resultado.append(instalacionDeportiva.getNombreInstalacion())
					.append(" (Capacidad: " + instalacionDeportiva.getCapacidad() + ")\n");
		}
		JOptionPane.showMessageDialog(null, resultado.toString());
	}

	private void mostrarMisReservas() {
		// Verificar si hay reservas
		List<ReservaInstalacion> misReservas = getMisReservas();
		if (misReservas.isEmpty()) {
			JOptionPane.showMessageDialog(null, "No tienes reservas.");
			return;
		}

		StringBuilder resultado = new StringBuilder("Mis reservas:\n");
		for (ReservaInstalacion reserva : misReservas) {
			resultado.append(reserva.getUsuario().getNombre())
					.append(" (Fecha de inicio: " + reserva.getFechaReservaInicio() + ")\n")
					.append("Fecha de fin: " + reserva.getFechaReservaFin() + "\n");
		}
		JOptionPane.showMessageDialog(null, resultado.toString());
	}
}