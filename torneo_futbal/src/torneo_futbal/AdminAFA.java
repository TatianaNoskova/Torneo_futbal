package torneo_futbal;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import javax.swing.JOptionPane;

public class AdminAFA extends Administrador {

	private List<Arbitro> arbitrosRegistrados;
	
	public AdminAFA(String nombre, String apellido, String email, String password) {
		super(nombre, apellido, email, password, "Administrador AFA");
		this.arbitrosRegistrados = new ArrayList<>();
	}

	private SistemaTorneos sistemaTorneos = new SistemaTorneos();

	public void setSistemaTorneos(SistemaTorneos sistemaTorneos) {
		this.sistemaTorneos = sistemaTorneos;
	}
	
	

	
	 @Override
	    public void mostrarMenu() {
	        boolean salir = false;

	        while (!salir) {
	            String[] opciones = {
	                    "Organizar torneo",
	                    "Registrar árbitro",
	                    "Asignar fechas y horarios",
	                    "Capturar estadísticas y resultados",
	                    "Salir"
	            };

	            String seleccion = (String) JOptionPane.showInputDialog(
	                    null,
	                    "Menú Administrador AFA",
	                    "Opciones",
	                    JOptionPane.QUESTION_MESSAGE,
	                    null,
	                    opciones,
	                    opciones[0]
	            );

	            if (seleccion == null || seleccion.equals("Salir")) {
	                salir = true;
	            } else {
	                procesarOpcion(seleccion);
	            }
	        }
	    }

	    private void procesarOpcion(String seleccion) {
	        switch (seleccion) {
	            case "Organizar torneo" -> mostrarSubmenuOrganizarTorneo();
	            case "Registrar árbitro" -> registrarArbitro();
	            case "Asignar fechas y horarios" -> mostrarSubmenuAsignarFechas();
	            case "Capturar estadísticas y resultados" -> mostrarSubmenuCapturarResultados();
	            default -> JOptionPane.showMessageDialog(null, "Opción no valida.");
	        }
	    }

	    private void mostrarSubmenuOrganizarTorneo() {
	        String[] opciones = {
	                "Registrar nuevo torneo",
	                "Registrar equipo por categoria",
	                "Registrar árbitro",
	                "Volver"
	        };

	        String seleccion = (String) JOptionPane.showInputDialog(
	                null,
	                "Submenú - Organizar torneo",
	                "Opciones",
	                JOptionPane.QUESTION_MESSAGE,
	                null,
	                opciones,
	                opciones[0]
	        );

	        if (seleccion == null || seleccion.equals("Volver")) {
	            return;
	        }

	        switch (seleccion) {
	            case "Registrar nuevo torneo" -> registrarNuevoTorneo();
	            case "Registrar equipo por categoria" -> registrarEquiposEnTorneoPorCategoria();
	            
	         // Otros opciones van a ser agregadas despues
	            default -> JOptionPane.showMessageDialog(null, "Has seleccionado: " + seleccion + "\n(Función aún no implementada)");
	        }
	    }

	    private void mostrarSubmenuAsignarFechas() {
	        String[] opciones = {
	                "Sortear partido",
	                //"Generar los grupos",
	                "Asignar sedes y horarios",
	                "Asignar árbitros",
	                "Volver"
	        };

	        String seleccion = (String) JOptionPane.showInputDialog(
	                null,
	                "Submenú - Asignar fechas y horarios",
	                "Opciones",
	                JOptionPane.QUESTION_MESSAGE,
	                null,
	                opciones,
	                opciones[0]
	        );

	        if (seleccion != null && !seleccion.equals("Volver")) {
	            switch (seleccion) {
	                case "Sortear partido" -> sortearPartidosPorCategoria();
	                case "Asignar sedes y horarios" -> asignarSedeAPartido();
	                case "Asignar árbitros" -> asignarArbitro();
	      
	                // Если выбран другой пункт, выводим сообщение
	                default -> JOptionPane.showMessageDialog(null,
	                        "Has seleccionado: " + seleccion + "\n(Función aún no implementada)");
	            }
	        }
	    }

	    private void mostrarSubmenuCapturarResultados() {
	        String[] opciones = {
	                "Ingresar resultados de los partidos",
	                "Tabla de goleadores",
	                "Estadísticas por equipo",
	                "Estadísticas individuales de jugadores",
	                "Volver"
	        };

	        String seleccion = (String) JOptionPane.showInputDialog(
	                null,
	                "Submenú - Capturar estadísticas y resultados",
	                "Opciones",
	                JOptionPane.QUESTION_MESSAGE,
	                null,
	                opciones,
	                opciones[0]
	        );

	        if (seleccion == null || seleccion.equals("Volver")) {
	            return;
	        }

	        switch (seleccion) {
	            case "Ingresar resultados de los partidos":
	                ingresarResultados();
	                break;

	            default:
	                JOptionPane.showMessageDialog(null,
	                        "Has seleccionado: " + seleccion + "\n(Función aún no implementada)");
	                break;
	        }
	    }
	    private void registrarNuevoTorneo() {
	        String nombreTorneo = JOptionPane.showInputDialog("Ingrese el nombre del torneo:");
	        String anoTorneo = JOptionPane.showInputDialog("Ingrese el año del torneo:");

	        if (nombreTorneo == null || anoTorneo == null || nombreTorneo.isBlank() || anoTorneo.isBlank()) {
	            JOptionPane.showMessageDialog(null, "Datos inválidos. Por favor, intenta nuevamente.");
	            return;
	        }

	        // Создание нового турнира
	        Torneo nuevoTorneo = new Torneo(nombreTorneo, anoTorneo);
	        sistemaTorneos.agregarTorneo(nuevoTorneo);

	        JOptionPane.showMessageDialog(null, "Torneo creado exitosamente:\n" + 
	                                      "Nombre: " + nuevoTorneo.getNombreTorneo() + 
	                                      "\nAño: " + nuevoTorneo.getAnoTorneo());
	        
	        mostrarSubmenuOrganizarTorneo();
	        
	    }
	    

	    // Метод для отображения всех турниров
	    public void mostrarTorneos() {
	        sistemaTorneos.mostrarTorneos();
	    }
	    
	    private void registrarEquiposEnTorneoPorCategoria() {
	        // Obtener torneos disponibles
	        List<Torneo> torneos = sistemaTorneos.obtenerTorneos();
	        if (torneos.isEmpty()) {
	            JOptionPane.showMessageDialog(null, "No hay torneos registrados.");
	            return;
	        }

	        String[] nombresTorneos = new String[torneos.size()];
	        for (int i = 0; i < torneos.size(); i++) {
	            nombresTorneos[i] = torneos.get(i).getNombreTorneo();
	        }

	        String seleccionTorneo = (String) JOptionPane.showInputDialog(
	            null,
	            "Seleccione el torneo al que desea agregar equipos:",
	            "Torneo",
	            JOptionPane.QUESTION_MESSAGE,
	            null,
	            nombresTorneos,
	            nombresTorneos[0]
	        );

	        if (seleccionTorneo == null) {
	            return;
	        }

	        Torneo torneoSeleccionado = null;
	        for (Torneo torneo : torneos) {
	            if (torneo.getNombreTorneo().equals(seleccionTorneo)) {
	                torneoSeleccionado = torneo;
	                break;
	            }
	        }

	        // Reunir todas las categorías disponibles de todos los equipos
	        Set<String> categoriasDisponibles = new HashSet<>();
	        for (Club club : SistemaRegistro.clubesRegistrados) {
	            for (Equipo equipo : club.getEquipos()) {
	                categoriasDisponibles.add(equipo.getCategoria());
	            }
	        }

	        if (categoriasDisponibles.isEmpty()) {
	            JOptionPane.showMessageDialog(null, "No hay equipos registrados en ningún club.");
	            return;
	        }

	        String[] categoriasArray = categoriasDisponibles.toArray(new String[0]);
	        String categoriaSeleccionada = (String) JOptionPane.showInputDialog(
	            null,
	            "Seleccione la categoría:",
	            "Categoría",
	            JOptionPane.QUESTION_MESSAGE,
	            null,
	            categoriasArray,
	            categoriasArray[0]
	        );

	        if (categoriaSeleccionada == null) {
	            return;
	        }

	        // Mostrar equipos disponibles de esa categoría
	        List<Equipo> equiposCategoria = new ArrayList<>();
	        for (Club club : SistemaRegistro.clubesRegistrados) {
	            for (Equipo equipo : club.getEquipos()) {
	                if (equipo.getCategoria().equalsIgnoreCase(categoriaSeleccionada)) {
	                    equiposCategoria.add(equipo);
	                }
	            }
	        }

	        if (equiposCategoria.isEmpty()) {
	            JOptionPane.showMessageDialog(null, "No hay equipos en esa categoría.");
	            return;
	        }

	        String[] nombresEquipos = new String[equiposCategoria.size()];
	        for (int i = 0; i < equiposCategoria.size(); i++) {
	            nombresEquipos[i] = equiposCategoria.get(i).getNombre();
	        }

	        String seleccionEquipo = (String) JOptionPane.showInputDialog(
	            null,
	            "Seleccione el equipo para agregar al torneo:",
	            "Equipo",
	            JOptionPane.QUESTION_MESSAGE,
	            null,
	            nombresEquipos,
	            nombresEquipos[0]
	        );

	        if (seleccionEquipo == null) {
	            return;
	        }

	        for (Equipo equipo : equiposCategoria) {
	            if (equipo.getNombre().equals(seleccionEquipo)) {
	            	// Проверка: если команда уже добавлена
	                if (torneoSeleccionado.getEquiposParticipantes().contains(equipo)) {
	                    JOptionPane.showMessageDialog(null, "Este equipo ya está registrado en el torneo.");
	                    return;
	                }

	                // Добавляем только если не добавлена раньше
	                torneoSeleccionado.agregarEquipoParticipante(equipo);
	                JOptionPane.showMessageDialog(null, "Equipo agregado exitosamente al torneo.");
	                return;
	            }
	        }
	    }
	    
	    private void registrarArbitro() {
	    	// Логика регистрации арбитра
	    	String nombre = JOptionPane.showInputDialog("Ingrese el nombre del árbitro:");
	    	String apellido = JOptionPane.showInputDialog("Ingrese el apellido del árbitro:");
	    	String email = JOptionPane.showInputDialog("Ingrese el correo electrónico del árbitro:");
	    	String password = JOptionPane.showInputDialog("Ingrese la contraseña del árbitro:");

	    	if (nombre == null || apellido == null || email == null || nombre.isBlank() || apellido.isBlank() || email.isBlank()) {
	    	JOptionPane.showMessageDialog(null, "Datos inválidos. Intenta nuevamente.");
	    	return;
	    	}

	    	// Проверка, существует ли уже арбитр с таким email
	    	for (Arbitro arbitro : arbitrosRegistrados) {
	    	if (arbitro.getEmail().equals(email)) {
	    	JOptionPane.showMessageDialog(null, "Este correo ya está registrado como árbitro.");
	    	return;
	    	}
	    	}

	    	// Создание нового арбитра и добавление его в систему
	    	Arbitro nuevoArbitro = new Arbitro(nombre, apellido, email, password);
	    	arbitrosRegistrados.add(nuevoArbitro);
	    	JOptionPane.showMessageDialog(null, "¡Arbitro registrado exitosamente!");
	    	}
	    
	 // Метод для сортировки матчей по категориям
	    private void sortearPartidosPorCategoria() {
	        // Получаем список турниров
	        List<Torneo> torneos = sistemaTorneos.obtenerTorneos();

	        if (torneos.isEmpty()) {
	            JOptionPane.showMessageDialog(null, "No hay torneos registrados.");
	            return;
	        }

	        // Выбор турнира
	        String[] nombres = new String[torneos.size()];
	        for (int i = 0; i < torneos.size(); i++) {
	            nombres[i] = torneos.get(i).getNombreTorneo();
	        }

	        String seleccion = (String) JOptionPane.showInputDialog(
	            null, "Seleccione un torneo:", "Torneos",
	            JOptionPane.QUESTION_MESSAGE, null, nombres, nombres[0]
	        );

	        if (seleccion == null) return;

	        // Находим выбранный турнир
	        Torneo torneoActual = null;
	        for (Torneo t : torneos) {
	            if (t.getNombreTorneo().equals(seleccion)) {
	                torneoActual = t;
	                break;
	            }
	        }

	        if (torneoActual == null) {
	            JOptionPane.showMessageDialog(null, "Torneo no encontrado.");
	            return;
	        }

	        // Получаем список команд для выбранного турнира
	        List<Equipo> participantes = torneoActual.getEquiposParticipantes();

	        // Группируем команды по категориям
	        Map<String, List<Equipo>> equiposPorCategoria = new HashMap<>();
	        for (Equipo equipo : participantes) {
	            String categoria = equipo.getCategoria();
	            if (!equiposPorCategoria.containsKey(categoria)) {
	                equiposPorCategoria.put(categoria, new ArrayList<>());
	            }
	            equiposPorCategoria.get(categoria).add(equipo);
	        }

	        if (equiposPorCategoria.isEmpty()) {
	            JOptionPane.showMessageDialog(null, "No hay categorías con equipos.");
	            return;
	        }

	        // Выбор категории
	        String[] categorias = new String[equiposPorCategoria.size()];
	        int i = 0;
	        for (String categoria : equiposPorCategoria.keySet()) {
	            categorias[i++] = categoria;
	        }

	        String seleccionCategoria = (String) JOptionPane.showInputDialog(
	            null, "Seleccione categoría:", "Categorías",
	            JOptionPane.QUESTION_MESSAGE, null, categorias, categorias[0]
	        );
	        
	     // Проверка: уже есть матчи по этой категории?
	        List<Partido> partidosExistentes = torneoActual.getPartidosPorCategoria(seleccionCategoria);
	        if (!partidosExistentes.isEmpty()) {
	            JOptionPane.showMessageDialog(null, "Los partidos para esta categoría ya fueron sorteados.");
	            return;
	        }

	        if (seleccionCategoria == null) return;

	        // Obtenemos la lista de los equipos para la categoria elegida
	        List<Equipo> equipos = equiposPorCategoria.get(seleccionCategoria);
	        if (equipos.size() != 8 && equipos.size() != 16) {
	            JOptionPane.showMessageDialog(null, "Debe haber 8 o 16 equipos para sortear. Actualmente hay: " + equipos.size());
	            return;
	        }

	        // Сортируем команды случайным образом
	        Collections.shuffle(equipos);

	        // Формируем и выводим результат (пары команд)
	        StringBuilder resultado = new StringBuilder("Partidos sorteados:\n");
	        for (int j = 0; j < equipos.size(); j += 2) {
	            Equipo eq1 = equipos.get(j);
	            Equipo eq2 = equipos.get(j + 1);

	            Partido partido = new Partido(eq1, eq2); // создаём матч
	            torneoActual.agregarPartido(partido);    // добавляем матч в турнир

	            resultado.append(eq1.getNombre())
	                     .append(" vs ")
	                     .append(eq2.getNombre())
	                     .append("\n");
	        }

	        JOptionPane.showMessageDialog(null, resultado.toString());
	        
	        mostrarSubmenuAsignarFechas();
	       
	    }
	    private void asignarSedeAPartido() {
	        // Получаем список турниров
	        List<Torneo> torneos = sistemaTorneos.obtenerTorneos();
	        if (torneos.isEmpty()) {
	            JOptionPane.showMessageDialog(null, "No hay torneos disponibles.");
	            return;
	        }

	        // 1. Выбор турнира
	        String[] nombres = torneos.stream().map(Torneo::getNombreTorneo).toArray(String[]::new);
	        String seleccion = (String) JOptionPane.showInputDialog(null, "Seleccione un torneo:", "Torneos", JOptionPane.QUESTION_MESSAGE, null, nombres, nombres[0]);
	        if (seleccion == null) return;

	        Torneo torneo = torneos.stream().filter(t -> t.getNombreTorneo().equals(seleccion)).findFirst().orElse(null);
	        if (torneo == null) return;

	        // 2. Выбор категории
	        Set<String> categorias = torneo.getCategoriasDePartidos();
	        if (categorias.isEmpty()) {
	            JOptionPane.showMessageDialog(null, "No hay partidos registrados en este torneo.");
	            return;
	        }

	        String[] categoriasArray = categorias.toArray(new String[0]);
	        String categoria = (String) JOptionPane.showInputDialog(null, "Seleccione categoría:", "Categorías", JOptionPane.QUESTION_MESSAGE, null, categoriasArray, categoriasArray[0]);
	        if (categoria == null) return;

	        // 3. Список матчей без стадиона
	        List<Partido> partidos = torneo.getPartidosPorCategoria(categoria).stream()
	                .filter(p -> p.getEstadio() == null)
	                .toList();

	        if (partidos.isEmpty()) {
	            JOptionPane.showMessageDialog(null, "Todos los partidos ya tienen sede asignada.");
	            return;
	        }

	        Partido partido = (Partido) JOptionPane.showInputDialog(
	                null,
	                "Seleccione un partido:",
	                "Partidos sin sede",
	                JOptionPane.QUESTION_MESSAGE,
	                null,
	                partidos.toArray(),
	                partidos.get(0)
	        );
	        if (partido == null) return;

	        // 4. Получаем все стадионы из зарегистрированных клубов
	        List<Estadio> estadios = new ArrayList<>();
	        for (Club club : SistemaRegistro.clubesRegistrados) {
	            estadios.addAll(club.getEstadios());
	        }

	        if (estadios.isEmpty()) {
	            JOptionPane.showMessageDialog(null, "No hay estadios registrados.");
	            return;
	        }

	        Estadio estadio = (Estadio) JOptionPane.showInputDialog(
	                null,
	                "Seleccione un estadio:",
	                "Estadios disponibles",
	                JOptionPane.QUESTION_MESSAGE,
	                null,
	                estadios.toArray(),
	                estadios.get(0)
	        );
	        if (estadio == null) return;

	        // 5. Выбор даты и времени для матча
	        String fechaStr = JOptionPane.showInputDialog("Ingrese la fecha del partido (dd/MM/yyyy):");
	        if (fechaStr == null || fechaStr.isEmpty()) {
	            JOptionPane.showMessageDialog(null, "Fecha inválida.");
	            return;
	        }

	        LocalDate fecha;
	        try {
	            // Преобразуем строку в LocalDate с нужным форматом
	            fecha = LocalDate.parse(fechaStr, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
	        } catch (DateTimeParseException e) {
	            JOptionPane.showMessageDialog(null, "Fecha inválida.");
	            return;
	        }

	        String horaStr = JOptionPane.showInputDialog("Ingrese la hora del partido (HH:mm):");
	        if (horaStr == null || horaStr.isEmpty()) {
	            JOptionPane.showMessageDialog(null, "Hora inválida.");
	            return;
	        }

	        LocalTime hora;
	        try {
	            // Преобразуем строку в LocalTime с нужным форматом
	            hora = LocalTime.parse(horaStr, DateTimeFormatter.ofPattern("HH:mm"));
	        } catch (DateTimeParseException e) {
	            JOptionPane.showMessageDialog(null, "Hora inválida.");
	            return;
	        }

	        // Устанавливаем дату и время на матч
	        partido.setFecha(fecha);   // Передаем LocalDate
	        partido.setHora(hora);     // Передаем LocalTime

	        // 6. Проверка на конфликты времени на этом стадионе
	        boolean conflicto = false;
	        for (Partido otro : torneo.getPartidosPorCategoria(categoria)) {
	            if (otro == partido) continue; // Не сравниваем сам с собой
	            if (estadio.equals(otro.getEstadio()) && otro.getFecha() != null && otro.getHora() != null) {
	                // Проверка на ту же дату
	                if (otro.getFecha().equals(partido.getFecha())) {
	                    // Преобразуем часы в LocalTime, если они не LocalTime
	                    LocalTime horaOtro = otro.getHora();
	                    LocalTime horaPartido = partido.getHora();

	                    long diferenciaMinutos = Math.abs(Duration.between(horaOtro, horaPartido).toMinutes());
	                    if (diferenciaMinutos < 180) { // Меньше 3 часов
	                        conflicto = true;
	                        break;
	                    }
	                }
	            }
	        }

	        if (conflicto) {
	            JOptionPane.showMessageDialog(null, "Ya hay un partido programado en ese estadio en un horario muy cercano.");
	            return;
	        }

	        // 7. Назначаем стадион для матча
	        partido.setEstadio(estadio);
	        JOptionPane.showMessageDialog(null, "¡Estadio y horario asignados correctamente!");

	        mostrarSubmenuAsignarFechas();
	    }
	    
	    private void asignarArbitro() {
	    	List<Torneo> torneos = sistemaTorneos.obtenerTorneos();
	    	if (torneos.isEmpty()) {
	    	JOptionPane.showMessageDialog(null, "No hay torneos disponibles.");
	    	return;
	    	}

	    	// Paso 1: Seleccionar torneo
	    	String[] nombresTorneos = torneos.stream()
	    	.map(Torneo::getNombreTorneo)
	    	.toArray(String[]::new);

	    	String seleccionTorneo = (String) JOptionPane.showInputDialog(
	    	null,
	    	"Seleccione un torneo:",
	    	"Torneos",
	    	JOptionPane.QUESTION_MESSAGE,
	    	null,
	    	nombresTorneos,
	    	nombresTorneos[0]
	    	);
	    	if (seleccionTorneo == null) return;

	    	Torneo torneo = torneos.stream()
	    	.filter(t -> t.getNombreTorneo().equals(seleccionTorneo))
	    	.findFirst()
	    	.orElse(null);
	    	if (torneo == null) return;

	    	// Paso 2: Seleccionar categoría con partidos
	    	Set<String> categorias = torneo.getCategoriasDePartidos();
	    	if (categorias.isEmpty()) {
	    	JOptionPane.showMessageDialog(null, "No hay partidos registrados en este torneo.");
	    	return;
	    	}

	    	String[] categoriasArray = categorias.toArray(new String[0]);
	    	String categoriaSeleccionada = (String) JOptionPane.showInputDialog(
	    	null,
	    	"Seleccione categoría:",
	    	"Categorías",
	    	JOptionPane.QUESTION_MESSAGE,
	    	null,
	    	categoriasArray,
	    	categoriasArray[0]
	    	);
	    	if (categoriaSeleccionada == null) return;

	    	// Paso 3: Filtrar partidos que ya tienen sede, fecha, hora, pero aún no tienen árbitro
	    	List<Partido> partidosElegibles = torneo.getPartidosPorCategoria(categoriaSeleccionada).stream()
	    	.filter(p -> p.getArbitro() == null &&
	    	p.getEstadio() != null &&
	    	p.getFecha() != null &&
	    	p.getHora() != null)
	    	.toList();

	    	if (partidosElegibles.isEmpty()) {
	    	JOptionPane.showMessageDialog(null, "No hay partidos disponibles para asignar árbitro. Asegúrese de que el partido tenga sede, fecha y hora asignadas.");
	    	return;
	    	}

	    	Partido partidoSeleccionado = (Partido) JOptionPane.showInputDialog(
	    	null,
	    	"Seleccione un partido:",
	    	"Partidos disponibles",
	    	JOptionPane.QUESTION_MESSAGE,
	    	null,
	    	partidosElegibles.toArray(),
	    	partidosElegibles.get(0)
	    	);
	    	if (partidoSeleccionado == null) return;

	    	// Paso 4: Seleccionar árbitro registrado
	    	if (arbitrosRegistrados.isEmpty()) {
	    	JOptionPane.showMessageDialog(null, "No hay árbitros registrados.");
	    	return;
	    	}

	    	Arbitro arbitroSeleccionado = (Arbitro) JOptionPane.showInputDialog(
	    	null,
	    	"Seleccione un árbitro:",
	    	"Árbitros disponibles",
	    	JOptionPane.QUESTION_MESSAGE,
	    	null,
	    	arbitrosRegistrados.toArray(),
	    	arbitrosRegistrados.get(0)
	    	);
	    	if (arbitroSeleccionado == null) return;

	    	// Paso 5: Verificar si el árbitro ya tiene un partido asignado en un horario cercano (menos de 3 horas), en la misma fecha
	    	boolean ocupado = false;
	    	LocalDate fechaPartido = partidoSeleccionado.getFecha();
	    	LocalTime horaPartido = partidoSeleccionado.getHora();

	    	for (Torneo t : sistemaTorneos.obtenerTorneos()) {
	    	for (String cat : t.getCategoriasDePartidos()) {
	    	for (Partido p : t.getPartidosPorCategoria(cat)) {
	    	if (arbitroSeleccionado.equals(p.getArbitro()) &&
	    	p.getFecha() != null &&
	    	p.getHora() != null &&
	    	p.getFecha().equals(fechaPartido)) {

	    	long diferencia = Math.abs(Duration.between(p.getHora(), horaPartido).toMinutes());
	    	if (diferencia < 180) {
	    	ocupado = true;
	    	break;
	    	}
	    	}
	    	}
	    	if (ocupado) break;
	    	}
	    	if (ocupado) break;
	    	}

	    	if (ocupado) {
	    	JOptionPane.showMessageDialog(null, "El árbitro ya tiene un partido asignado en un horario cercano ese día.");
	    	return;
	    	}

	    	// Paso 6: Asignar árbitro al partido
	    	partidoSeleccionado.setArbitro(arbitroSeleccionado);
	    	JOptionPane.showMessageDialog(null, "¡Árbitro asignado correctamente al partido!");

	    	mostrarSubmenuAsignarFechas();
	    	}
	    
	    private void ingresarResultados() {
	    	List<Torneo> torneos = sistemaTorneos.obtenerTorneos();
	    	if (torneos.isEmpty()) {
	    	JOptionPane.showMessageDialog(null, "No hay torneos disponibles.");
	    	return;
	    	}

	    	// Paso 1: Seleccionar torneo
	    	String[] nombresTorneos = torneos.stream()
	    	.map(Torneo::getNombreTorneo)
	    	.toArray(String[]::new);

	    	String seleccionTorneo = (String) JOptionPane.showInputDialog(
	    	null, "Seleccione un torneo:", "Torneos",
	    	JOptionPane.QUESTION_MESSAGE, null,
	    	nombresTorneos, nombresTorneos[0]
	    	);
	    	if (seleccionTorneo == null) return;

	    	Torneo torneo = torneos.stream()
	    	.filter(t -> t.getNombreTorneo().equals(seleccionTorneo))
	    	.findFirst().orElse(null);
	    	if (torneo == null) return;

	    	// Paso 2: Seleccionar categoría
	    	Set<String> categorias = torneo.getCategoriasDePartidos();
	    	if (categorias.isEmpty()) {
	    	JOptionPane.showMessageDialog(null, "No hay partidos registrados para este torneo.");
	    	return;
	    	}

	    	String[] categoriasArray = categorias.toArray(new String[0]);
	    	String categoriaSeleccionada = (String) JOptionPane.showInputDialog(
	    	null, "Seleccione una categoría:", "Categorías",
	    	JOptionPane.QUESTION_MESSAGE, null,
	    	categoriasArray, categoriasArray[0]
	    	);
	    	if (categoriaSeleccionada == null) return;

	    	// Paso 3: Filtrar partidos jugados (fecha y hora ya pasaron)
	    	List<Partido> partidosJugados = torneo.getPartidosPorCategoria(categoriaSeleccionada).stream()
	    	.filter(p -> p.getFecha() != null && p.getHora() != null &&
	    	p.getEstadio() != null && p.getArbitro() != null &&
	    	LocalDateTime.of(p.getFecha(), p.getHora()).isBefore(LocalDateTime.now()) &&
	    	!p.resultadoCapturado()) // el resultado ya no había ingresado
	    	.toList();

	    	if (partidosJugados.isEmpty()) {
	    	JOptionPane.showMessageDialog(null, "No hay partidos finalizados sin resultados.");
	    	return;
	    	}

	    	// Paso 4: Seleccionar partido
	    	Partido partido = (Partido) JOptionPane.showInputDialog(
	    	null,
	    	"Seleccione un partido para ingresar el resultado:",
	    	"Partidos finalizados",
	    	JOptionPane.QUESTION_MESSAGE,
	    	null,
	    	partidosJugados.toArray(),
	    	partidosJugados.get(0)
	    	);
	    	if (partido == null) return;

	    	// Paso 5: Ingresar goles
	    	int golesEquipo1, golesEquipo2;

	    	try {
	    	String input1 = JOptionPane.showInputDialog("Ingrese los goles para " + partido.getEquipo1().getNombre() + ":");
	    	if (input1 == null) return;
	    	golesEquipo1 = Integer.parseInt(input1);

	    	String input2 = JOptionPane.showInputDialog("Ingrese los goles para " + partido.getEquipo2().getNombre() + ":");
	    	if (input2 == null) return;
	    	golesEquipo2 = Integer.parseInt(input2);
	    	} catch (NumberFormatException e) {
	    	JOptionPane.showMessageDialog(null, "Formato de número inválido.");
	    	return;
	    	}

	    	// Paso 6: Registrar resultado
	    	partido.setGolesEquipo1(golesEquipo1);
	    	partido.setGolesEquipo2(golesEquipo2);
	    	partido.setResultadoCapturado(true); // control para no ingresar el resultado dos veces

	    	String mensajeResultado = "¡Resultado registrado correctamente!\n" +
	    	partido.getEquipo1().getNombre() + " " + partido.getGolesEquipo1() +
	    	" - " + partido.getGolesEquipo2() + " " + partido.getEquipo2().getNombre();

	    	JOptionPane.showMessageDialog(null, mensajeResultado);


	    	mostrarSubmenuCapturarResultados();
	    	}

}


