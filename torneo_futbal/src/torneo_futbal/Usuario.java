package torneo_futbal;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

public abstract class Usuario extends Persona {

	protected String rol;
	protected List<ReservaInstalacion> misReservas;

	public Usuario(String nombre, String apellido, String email, String password, String rol) {
		super(nombre, apellido, email, password);
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
		"Búsqueda por nombre de Club",
		"Búsqueda por nombre de Disciplina",
		"Consultar fechas y horarios disponibles",
		"Reservar / modificar / cancelar reserva",
		"Volver"
		};

		boolean volver = false;

		while (!volver) {
		String seleccion = (String) JOptionPane.showInputDialog(
		null,
		"Submenú - Gestión de los Clubes y Diciplinas",
		"Opciones",
		JOptionPane.QUESTION_MESSAGE,
		null,
		opciones,
		opciones[0]
		);

		if (seleccion == null || seleccion.equals("Volver")) {
		volver = true;
		} else {
		switch (seleccion) {
		case "Búsqueda por nombre de Club" -> {realizarBusqueda("Club");}
		case "Búsqueda por nombre de Disciplina" -> {realizarBusqueda("Disciplina");}
		case "Consultar fechas y horarios disponibles", 
		"Reservar / modificar / cancelar reserva" -> 
		JOptionPane.showMessageDialog(null,
		"Has seleccionado: " + seleccion + "\n(Función aún no implementada)");
		default -> JOptionPane.showMessageDialog(null, "Opción no válida.");
		}
		}
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
	
	
	public List<Disciplina> buscarDisciplinasPorNombreClub(String nombreClub) {
		for (Club club : SistemaRegistro.clubesRegistrados) {
		if (club.getNombre().equalsIgnoreCase(nombreClub)) {
		return club.getDisciplinas();
		}
		}
		return new ArrayList<>(); // если клуб не найден
		}
		public List<Club> buscarClubesPorNombreDisciplina(String nombreDisciplina) {
		List<Club> clubesConDisciplina = new ArrayList<>();
		for (Club club : SistemaRegistro.clubesRegistrados) {
		for (Disciplina disciplina : club.getDisciplinas()) {
		if (disciplina.getNombreDisciplina().equalsIgnoreCase(nombreDisciplina)) {
		clubesConDisciplina.add(club);
		break; // Как только нашли клуб, можно остановиться
		}
		}
		}
		return clubesConDisciplina;
		}

		public void realizarBusqueda(String tipoBusqueda) {
		String input = JOptionPane.showInputDialog("Ingrese el nombre del " + (tipoBusqueda.equals("Club") ? "Club" : "Disciplina") + ":");

		if (input != null && !input.isEmpty()) {
		if (tipoBusqueda.equals("Club")) {
		// Поиск дисциплин по названию клуба
		List<Disciplina> disciplinas = buscarDisciplinasPorNombreClub(input);
		if (disciplinas.isEmpty()) {
		JOptionPane.showMessageDialog(null, "No se encontraron disciplinas para el club: " + input);
		} else {
		StringBuilder disciplinasInfo = new StringBuilder("Disciplinas del club " + input + ":\n");
		for (Disciplina disciplina : disciplinas) {
		disciplinasInfo.append(disciplina.getNombreDisciplina()).append("\n");
		}
		JOptionPane.showMessageDialog(null, disciplinasInfo.toString());
		}
		} else if (tipoBusqueda.equals("Disciplina")) {
		// Поиск клубов по названию дисциплины
		List<Club> clubes = buscarClubesPorNombreDisciplina(input);
		if (clubes.isEmpty()) {
		JOptionPane.showMessageDialog(null, "No se encontraron clubes con la disciplina: " + input);
		} else {
		StringBuilder clubesInfo = new StringBuilder("Clubes con la disciplina " + input + ":\n");
		for (Club club : clubes) {
		clubesInfo.append(club.getNombre()).append("\n");
		}
		JOptionPane.showMessageDialog(null, clubesInfo.toString());
		}
		}
		} else {
		JOptionPane.showMessageDialog(null, "Por favor, ingrese un nombre válido.");
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