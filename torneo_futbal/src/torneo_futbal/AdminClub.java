package torneo_futbal;

import java.awt.Image;
import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public class AdminClub extends Administrador {

	private Club club;

	public AdminClub(String nombre, String apellido, String email, String password) {
		super(nombre, apellido, email, password, "Administrador del Club");
	}

	public void setClub(Club club) {
		this.club = club;
	}

	@Override
	public void mostrarMenu() {
		boolean salir = false;

		while (!salir) {
			String[] opciones = {
					"Registrar club",
					"Registrar estadio",
					"Registrar equipo",
					"Registrar disciplina y instalacion deportiva",
					"Registrar director técnico",
					"Gestionar disciplinas y actividades",
					"Reservar/administrar instalaciones",
					"Agregar categorías de entradas",
					"Vender entradas",
					"Administrar beneficios y premios",
					"Salir"
			};

			String seleccion = (String) JOptionPane.showInputDialog(
					null,
					"Menú Administrador de Club",
					"Opciones",
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
			case "Registrar club" -> registrarClub();
			case "Registrar estadio" -> registrarEstadio();
			case "Registrar equipo" -> registrarEquipo();
			case "Registrar disciplina y instalacion deportiva" -> registrarDisciplinaYInstalacion();
			case "Registrar director técnico" -> registrarDirectorTecnico();
			case "Vender entradas" -> venderEntradas();

			// Otros opciones van a ser agregadas despues

			default -> JOptionPane.showMessageDialog(null,
					"Has seleccionado: " + seleccion + "\n(Función aún no implementada)");
		}
	}

	private void registrarClub() {
		if (club != null) {
			JOptionPane.showMessageDialog(null, "Ya tienes un club registrado:\n" + club);
			return;
		}

		String nombre = JOptionPane.showInputDialog("Ingrese el nombre del club:");
		String direccion = JOptionPane.showInputDialog("Ingrese la dirección del club:");

		if (nombre == null || direccion == null || nombre.isBlank() || direccion.isBlank()) {
			JOptionPane.showMessageDialog(null, "Datos inválidos. Por favor, intenta nuevamente");
			return;
		}

		club = new Club(nombre, direccion);

		// Agregamos a la lista de los clubes
		SistemaRegistro.clubesRegistrados.add(club);

		JOptionPane.showMessageDialog(null, "Club registrado exitosamente:\n" + club);
	}

	private void registrarEstadio() {
		if (club == null) {
			JOptionPane.showMessageDialog(null, "Primero debe registrar un club.");
			return;
		}

		String nombre = JOptionPane.showInputDialog("Ingrese el nombre del estadio:");
		String direccion = JOptionPane.showInputDialog("Ingrese la dirección del estadio:");
		String capacidadStr = JOptionPane.showInputDialog("Ingrese la capacidad del estadio:");

		if (nombre == null || direccion == null || capacidadStr == null ||
				nombre.isBlank() || direccion.isBlank() || capacidadStr.isBlank()) {
			JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios.");
			return;
		}

		int capacidad = 0;
		try {
			capacidad = Integer.parseInt(capacidadStr);
			if (capacidad <= 0) {
				JOptionPane.showMessageDialog(null, "La capacidad debe ser mayor que cero.");
				return;
			}
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(null, "La capacidad debe ser un número válido.");
			return;
		}

		Estadio estadio = new Estadio(nombre, direccion, capacidad);
		club.agregarEstadio(estadio);

		club.agregarEstadio(estadio); // ← сохраняется в списке самого клуба

		JOptionPane.showMessageDialog(null, "Estadio registrado exitosamente:\n" + estadio);
	}

	private void registrarEquipo() {
		if (club == null) {
			JOptionPane.showMessageDialog(null, "Primero debe registrar un club.");
			return;
		}

		String nombreEquipo = JOptionPane.showInputDialog("Ingrese el nombre del equipo:");
		String categoria = JOptionPane.showInputDialog("Ingrese la categoría del equipo (ej: Primera, Juvenil, etc):");
		String colores = JOptionPane.showInputDialog("Ingrese los colores del equipo:");

		String nombreEscudo = JOptionPane
				.showInputDialog("Ingrese el nombre del archivo del escudo del equipo (ej: escudo1.png):");

		String rutaEscudo = "./escudos/" + nombreEscudo;
		System.out.println("Ruta del escudo: " + rutaEscudo); // Выводим путь в консоль для отладки

		File archivoEscudo = new File(rutaEscudo);
		if (!archivoEscudo.exists()) {
			JOptionPane.showMessageDialog(null, "El archivo del escudo no existe en la ruta especificada.");
			return;
		}

		// Выбор стадиона
		List<Estadio> estadios = club.getEstadios();
		if (estadios.isEmpty()) {
			JOptionPane.showMessageDialog(null, "Primero debe registrar al menos un estadio.");
			return;
		}

		String[] nombresEstadios = new String[estadios.size()];

		for (int i = 0; i < estadios.size(); i++) {
			nombresEstadios[i] = estadios.get(i).getNombre();
		}

		String seleccion = (String) JOptionPane.showInputDialog(
				null,
				"Seleccione el estadio local:",
				"Estadio",
				JOptionPane.QUESTION_MESSAGE,
				null,
				nombresEstadios,
				nombresEstadios[0]);

		if (seleccion == null) {
			JOptionPane.showMessageDialog(null, "No se seleccionó ningún estadio.");
			return;
		}

		Estadio estadioSeleccionado = null;
		for (Estadio estadio : estadios) {
			if (estadio.getNombre().equals(seleccion)) {
				estadioSeleccionado = estadio;
				break;
			}
		}

		Equipo equipo = new Equipo(nombreEquipo, categoria, colores, rutaEscudo, estadioSeleccionado);
		club.agregarEquipo(equipo);

		ImageIcon escudo = equipo.getEscudo();
		if (escudo != null) {

			Image img = escudo.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
			ImageIcon escudoEscalado = new ImageIcon(img);

			JOptionPane.showMessageDialog(
					null,
					"Equipo registrado:\n" +
							"Nombre: " + equipo.getNombre() + "\n" +
							"Categoría: " + equipo.getCategoria() + "\n" +
							"Colores: " + equipo.getColores() + "\n" +
							"Estadio Local: " + equipo.getEstadioLocal().getNombre(),
					"Escudo del equipo",
					JOptionPane.INFORMATION_MESSAGE,
					escudoEscalado);
		} else {
			JOptionPane.showMessageDialog(null, "Equipo registrado, pero no se pudo cargar el escudo.");
		}
	}

	private void registrarDisciplinaYInstalacion() {
		if (club == null) {
			JOptionPane.showMessageDialog(null, "Primero debe registrar un club.");
			return;
		}

		String nombreDisciplina = JOptionPane.showInputDialog("Ingrese el nombre de la disciplina:");
		String nombreInstalacion = JOptionPane.showInputDialog("Ingrese el nombre de la instalación deportiva:");
		String direccionInstalacion = JOptionPane.showInputDialog("Ingrese la direcciòn de la instalación deportiva:");

		if (nombreDisciplina == null || nombreInstalacion == null ||
				nombreDisciplina.isBlank() || nombreInstalacion.isBlank()) {
			JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios.");
			return;
		}

		// Crear disciplina e instalación
		Disciplina disciplina = new Disciplina(nombreDisciplina);
		InstalacionDeportiva instalacion = new InstalacionDeportiva(nombreInstalacion, direccionInstalacion, disciplina,
				1000);

		// Registrar en el club
		club.agregarDisciplina(disciplina);
		club.agregarInstalacion(instalacion);

		// Mostrar confirmación
		JOptionPane.showMessageDialog(
				null,
				"Disciplina e instalación registradas correctamente:\n" +
						"Disciplina: " + disciplina.getNombreDisciplina() + "\n" +
						"Instalación: " + instalacion.getNombreInstalacion());
	}

	private void registrarDirectorTecnico() {
		if (club == null) {
			JOptionPane.showMessageDialog(null, "Primero debe registrar un club.");
			return;
		}

		String nombre = JOptionPane.showInputDialog("Ingrese el nombre del Director Técnico:");
		String apellido = JOptionPane.showInputDialog("Ingrese el apellido del Director Técnico:");
		String email = null;
		boolean emailValido = false;
		while (!emailValido) {
			email = JOptionPane.showInputDialog("Ingrese el email del Director Técnico:");

			if (email == null || email.isBlank()) {
				JOptionPane.showMessageDialog(null, "El email no puede estar vacío.");
				continue;
			}

			// Проверка на наличие символа "@"
			if (!email.contains("@")) {
				JOptionPane.showMessageDialog(null, "El email ingresado no es válido. Debe contener '@'.");
				continue;
			}

			emailValido = true; // Выход из цикла, если email корректный
		}
		String password = JOptionPane.showInputDialog("Ingrese la contraseña del Director Técnico:");
		if (password == null || password.isBlank()) {
			JOptionPane.showMessageDialog(null, "La contraseña no puede estar vacía.");
			return;
		}

		// Если имя или фамилия пустые
		if (nombre == null || apellido == null || nombre.isBlank() || apellido.isBlank()) {
			JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios.");
			return;
		}
		List<Equipo> equipos = club.getEquipos();
		if (equipos.isEmpty()) {
			JOptionPane.showMessageDialog(null, "Primero debe registrar al menos un equipo.");
			return;
		}
		String[] nombresEquipos = new String[equipos.size()];
		for (int i = 0; i < equipos.size(); i++) {
			nombresEquipos[i] = equipos.get(i).getNombre();
		}

		String seleccion = (String) JOptionPane.showInputDialog(
				null,
				"Seleccione el equipo al que desea asignar el Director Técnico:",
				"Equipo",
				JOptionPane.QUESTION_MESSAGE,
				null,
				nombresEquipos,
				nombresEquipos[0]);

		if (seleccion == null) {
			JOptionPane.showMessageDialog(null, "No se seleccionó ningún equipo.");
			return;
		}

		Equipo equipoSeleccionado = null;
		for (Equipo eq : equipos) {
			if (eq.getNombre().equals(seleccion)) {
				equipoSeleccionado = eq;
				break;
			}
		}

		DirectorTecnico dt = new DirectorTecnico(nombre, apellido, email, password);
		equipoSeleccionado.setDirectorTecnico(dt);

		JOptionPane.showMessageDialog(null,
				"Director Técnico asignado al equipo " + equipoSeleccionado.getNombre() + ":\n" +
						dt.getNombre() + " " + dt.getApellido() + "\nEmail: " + dt.getEmail());
	}

	private void venderEntradas() {
		if (club == null) {
			JOptionPane.showMessageDialog(null, "Primero debe registrar un club.");
			return;
		}

		List<Partido> partidos = obtenerPartidosDisponibles();
		if (partidos.isEmpty()) {
			JOptionPane.showMessageDialog(null, "No hay partidos disponibles.");
			return;
		}

		String[] nombresPartidos = new String[partidos.size()];
		for (int i = 0; i < partidos.size(); i++) {
			nombresPartidos[i] = partidos.get(i).toString();
		}

		String partidoSeleccionado = (String) JOptionPane.showInputDialog(
				null,
				"Seleccione el partido:",
				"Partido",
				JOptionPane.QUESTION_MESSAGE,
				null,
				nombresPartidos,
				nombresPartidos[0]);

		if (partidoSeleccionado == null)
			return;

		Partido partido = null;
		for (Partido p : partidos) {
			if (p.toString().equals(partidoSeleccionado)) {
				partido = p;
				break;
			}
		}

		if (partido == null) {
			JOptionPane.showMessageDialog(null, "Partido no válido.");
			return;
		}

		int capacidadEstadio = partido.getEstadio().getCapacidad();
		int entradasRestantes = capacidadEstadio;

		Set<String> categoriasProcesadas = new HashSet<>();
		String[] categorias = { "palco", "vip", "popular", "platea" };

		while (categoriasProcesadas.size() < categorias.length && entradasRestantes > 0) {
			List<String> restantes = new ArrayList<>();
			for (String cat : categorias) {
				if (!categoriasProcesadas.contains(cat)) {
					restantes.add(cat);
				}
			}

			String categoriaSeleccionada = (String) JOptionPane.showInputDialog(
					null,
					"Seleccione la categoría de la entrada (faltan " + restantes.size() + "):\n" +
							"Entradas restantes disponibles: " + entradasRestantes,
					"Categoría",
					JOptionPane.QUESTION_MESSAGE,
					null,
					restantes.toArray(),
					restantes.get(0));

			if (categoriaSeleccionada == null)
				return;

			String precioStr = JOptionPane.showInputDialog(
					"Ingrese el precio de la entrada para la categoría " + categoriaSeleccionada + ":");
			if (precioStr == null)
				continue;

			double precio;
			try {
				precio = Double.parseDouble(precioStr);
				if (precio <= 0)
					throw new NumberFormatException();
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(null, "Debe ingresar un precio válido y mayor que cero.");
				continue;
			}

			String cantidadStr = JOptionPane.showInputDialog(
					"Ingrese la cantidad de entradas a vender para categoría " + categoriaSeleccionada + ":\n" +
							"Máximo disponible: " + entradasRestantes);
			if (cantidadStr == null)
				continue;

			int cantidad;
			try {
				cantidad = Integer.parseInt(cantidadStr);
				if (cantidad <= 0)
					throw new NumberFormatException();
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(null, "La cantidad debe ser un número mayor que cero.");
				continue;
			}

			if (cantidad > entradasRestantes) {
				JOptionPane.showMessageDialog(null,
						"No hay suficientes entradas disponibles. Puedes vender hasta " + entradasRestantes + ".");
				continue;
			}

			for (int i = 0; i < cantidad; i++) {
				int contador = 0;
				Entrada entrada = new Entrada(partido, categoriaSeleccionada, precio, contador++, false, club);
			}

			entradasRestantes -= cantidad;
			categoriasProcesadas.add(categoriaSeleccionada);

			JOptionPane.showMessageDialog(null,
					cantidad + " entradas de categoría " + categoriaSeleccionada +
							" vendidas.\nEntradas restantes para otras categorías: " + entradasRestantes);
		}

		if (entradasRestantes == 0) {
			JOptionPane.showMessageDialog(null, "¡Se ha alcanzado la capacidad máxima del estadio!");
		} else {
			JOptionPane.showMessageDialog(null, "¡Venta completada para todas las categorías!");
		}
	}

	private List<Partido> obtenerPartidosDisponibles() {
		List<Partido> partidosDisponibles = new ArrayList<>();

		for (Torneo torneo : SistemaRegistro.torneosRegistrados) {
			for (Partido partido : torneo.getPartidosPorCategoria("Primera")) { // Или все категории?

				partidosDisponibles.add(partido);
				break;
			}
		}

		return partidosDisponibles;
	}

}
