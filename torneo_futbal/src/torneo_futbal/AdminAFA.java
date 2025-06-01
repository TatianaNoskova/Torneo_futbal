package torneo_futbal;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

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
	
	private Set<String> adminsClubEmailList = new HashSet<>();
	
	public Set<String> getAdminsClubEmailList() {
	    return adminsClubEmailList;
	}
	
	

	
	 @Override
	    public void mostrarMenu() {
	        boolean salir = false;

	        while (!salir) {
	            String[] opciones = {
	                    "Organizar torneo",
	                    "Registrar correos de administradores de los Clubes",
	                    "Asignar sedes, horarios y árbitros",
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
	            case "Registrar correos de administradores de los Clubes" -> registrarAdminClubPorEmail();
	            case "Asignar sedes, horarios y árbitros" -> mostrarSubmenuAsignarFechas();
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
	            case "Registrar árbitro" -> registrarArbitro();
	            
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

	        try (Connection conn = Conexion.getInstance().getConnection()) {

	            // SQL-запрос вставки с возвратом сгенерированного ID
	            String sql = "INSERT INTO torneo (nombre, ano) VALUES (?, ?)";
	            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

	            stmt.setString(1, nombreTorneo);
	            stmt.setInt(2, Integer.parseInt(anoTorneo));

	            int filasInsertadas = stmt.executeUpdate();

	            if (filasInsertadas > 0) {
	                ResultSet rs = stmt.getGeneratedKeys();
	                int idTorneo = -1;
	                if (rs.next()) {
	                    idTorneo = rs.getInt(1); // получаем сгенерированный ID турнира
	                }

	                JOptionPane.showMessageDialog(null, "Torneo creado exitosamente:\n" +
	                                                  "Nombre: " + nombreTorneo +
	                                                  "\nAño: " + anoTorneo +
	                                                  "\nID en base de datos: " + idTorneo);

	                // Создание и добавление в систему, если еще нужно (например, в память)
	                Torneo nuevoTorneo = new Torneo(idTorneo, nombreTorneo, anoTorneo);
	                sistemaTorneos.agregarTorneo(nuevoTorneo);
	            }

	            stmt.close();
	            conn.close();

	        } catch (SQLException e) {
	            JOptionPane.showMessageDialog(null, "Error al guardar el torneo en la base de datos:\n" + e.getMessage());
	            e.printStackTrace();
	        } catch (NumberFormatException nfe) {
	            JOptionPane.showMessageDialog(null, "El año debe ser un número entero.");
	        }

	        mostrarSubmenuOrganizarTorneo();
	    }

	    

	    // Метод для отображения всех турниров
	    public void mostrarTorneos() {
	        sistemaTorneos.mostrarTorneos();
	    }
	    
	    private void registrarEquiposEnTorneoPorCategoria() {
	    	try {
	        try (Connection conn = Conexion.getInstance().getConnection()) {

	            // 1. Obtener torneos
	            List<String> nombresTorneos = new ArrayList<>();
	            Map<String, Integer> torneoIdMap = new HashMap<>();

	            String sqlTorneos = "SELECT id_torneo, nombre FROM torneo";
	            try (PreparedStatement stmt = conn.prepareStatement(sqlTorneos);
	                 ResultSet rs = stmt.executeQuery()) {
	                while (rs.next()) {
	                    int id = rs.getInt("id_torneo");
	                    String nombre = rs.getString("nombre");
	                    nombresTorneos.add(nombre);
	                    torneoIdMap.put(nombre, id);
	                }
	            }

	            if (nombresTorneos.isEmpty()) {
	                JOptionPane.showMessageDialog(null, "No hay torneos registrados.");
	                return;
	            }

	            String seleccionTorneo = (String) JOptionPane.showInputDialog(
	                    null,
	                    "Seleccione el torneo al que desea agregar equipos:",
	                    "Torneo",
	                    JOptionPane.QUESTION_MESSAGE,
	                    null,
	                    nombresTorneos.toArray(),
	                    nombresTorneos.get(0)
	            );

	            if (seleccionTorneo == null) return;
	            int idTorneoSeleccionado = torneoIdMap.get(seleccionTorneo);

	            // 2. Seleccionar categoría
	            Set<String> categoriasDisponibles = new HashSet<>();
	            String sqlCategorias = "SELECT DISTINCT categoria FROM equipo";
	            try (PreparedStatement stmt = conn.prepareStatement(sqlCategorias);
	                 ResultSet rs = stmt.executeQuery()) {
	                while (rs.next()) {
	                    categoriasDisponibles.add(rs.getString("categoria"));
	                }
	            }

	            if (categoriasDisponibles.isEmpty()) {
	                JOptionPane.showMessageDialog(null, "No hay equipos registrados.");
	                return;
	            }

	            String categoriaSeleccionada = (String) JOptionPane.showInputDialog(
	                    null,
	                    "Seleccione la categoría:",
	                    "Categoría",
	                    JOptionPane.QUESTION_MESSAGE,
	                    null,
	                    categoriasDisponibles.toArray(),
	                    categoriasDisponibles.iterator().next()
	            );

	            if (categoriaSeleccionada == null) return;

	            // 3. Iniciar цикл для добавления нескольких команд
	            while (true) {

	                // 4. Obtener equipos de esa categoría que aún no estén registrados en ese torneo
	                Map<String, Integer> equipoIdMap = new LinkedHashMap<>();
	                String sqlEquiposDisponibles = """
	                    SELECT e.id_equipo, e.nombre 
	                    FROM equipo e
	                    WHERE e.categoria = ?
	                    AND e.id_equipo NOT IN (
	                        SELECT et.id_equipo 
	                        FROM equipo_torneo et 
	                        WHERE et.id_torneo = ?
	                    )
	                """;

	                try (PreparedStatement stmt = conn.prepareStatement(sqlEquiposDisponibles)) {
	                    stmt.setString(1, categoriaSeleccionada);
	                    stmt.setInt(2, idTorneoSeleccionado);

	                    try (ResultSet rs = stmt.executeQuery()) {
	                        while (rs.next()) {
	                            equipoIdMap.put(rs.getString("nombre"), rs.getInt("id_equipo"));
	                        }
	                    }
	                }

	                if (equipoIdMap.isEmpty()) {
	                    JOptionPane.showMessageDialog(null, "No hay más equipos disponibles en esta categoría para este torneo.");
	                    break;
	                }

	                // 5. Mostrar menú selección equipo
	                String seleccionEquipo = (String) JOptionPane.showInputDialog(
	                        null,
	                        "Seleccione el equipo para agregar al torneo:",
	                        "Equipo",
	                        JOptionPane.QUESTION_MESSAGE,
	                        null,
	                        equipoIdMap.keySet().toArray(),
	                        equipoIdMap.keySet().iterator().next()
	                );

	                if (seleccionEquipo == null) break;

	                int idEquipoSeleccionado = equipoIdMap.get(seleccionEquipo);

	                // 6. Insertar equipo en el torneo
	                String sqlInsert = "INSERT INTO equipo_torneo (id_equipo, id_torneo, categoria, fecha_registro) VALUES (?, ?, ?, ?)";
	                try (PreparedStatement stmt = conn.prepareStatement(sqlInsert)) {
	                    stmt.setInt(1, idEquipoSeleccionado);
	                    stmt.setInt(2, idTorneoSeleccionado);
	                    stmt.setString(3, categoriaSeleccionada);
	                    stmt.setDate(4, new java.sql.Date(System.currentTimeMillis()));

	                    stmt.executeUpdate();
	                }

	                JOptionPane.showMessageDialog(null, "Equipo agregado exitosamente al torneo.");
	            }

	        } catch (SQLException e) {
	            e.printStackTrace();
	            JOptionPane.showMessageDialog(null, "Error al registrar equipo en torneo.");
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	        JOptionPane.showMessageDialog(null, "Error inesperado: " + e.getMessage());
	    }
	        // 🔁 Возврат в подменю "Registrar equipo por categoría"
	        mostrarSubmenuOrganizarTorneo();
	    }



	    
	    private void registrarArbitro() {
	        String nombre = JOptionPane.showInputDialog("Ingrese el nombre del árbitro:");
	        String apellido = JOptionPane.showInputDialog("Ingrese el apellido del árbitro:");
	        String email = JOptionPane.showInputDialog("Ingrese el correo electrónico del árbitro:");
	        String password = JOptionPane.showInputDialog("Ingrese la contraseña del árbitro:");

	        if (nombre == null || apellido == null || email == null || nombre.isBlank() || apellido.isBlank() || email.isBlank() || password == null || password.isBlank()) {
	            JOptionPane.showMessageDialog(null, "Datos inválidos. Intente nuevamente.");
	            return;
	        }

	        try (Connection conn = Conexion.getInstance().getConnection()) {
	            // 1. Проверка: существует ли уже пользователь с таким email
	            String checkSql = "SELECT COUNT(*) FROM persona WHERE email = ?";
	            try (PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {
	                checkStmt.setString(1, email);
	                try (ResultSet rs = checkStmt.executeQuery()) {
	                    if (rs.next() && rs.getInt(1) > 0) {
	                        JOptionPane.showMessageDialog(null, "Este correo ya está registrado.");
	                        return;
	                    }
	                }
	            }

	            // 2. Вставка нового арбитра в таблицу persona с rol = 'Arbitro'
	            String insertSql = "INSERT INTO persona (nombre, apellido, email, password, rol) VALUES (?, ?, ?, ?, 'Arbitro')";
	            try (PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {
	                insertStmt.setString(1, nombre);
	                insertStmt.setString(2, apellido);
	                insertStmt.setString(3, email);
	                insertStmt.setString(4, password); // в идеале: хешировать!
	                insertStmt.executeUpdate();
	            }

	            JOptionPane.showMessageDialog(null, "¡Árbitro registrado exitosamente!");

	        } catch (SQLException e) {
	            e.printStackTrace();
	            JOptionPane.showMessageDialog(null, "Error al registrar el árbitro.");
	        }

	        
	        // Возврат в подменю "Registrar árbitro"
	        mostrarSubmenuOrganizarTorneo();
	    }

	    
	    private void sortearPartidosPorCategoria() {
	        try (Connection conn = Conexion.getInstance().getConnection()) {
	            // 1. Obtener los torneos registrados
	            String sqlTorneos = "SELECT id_torneo, nombre FROM torneo";
	            List<String> torneos = new ArrayList<>();
	            try (PreparedStatement stmt = conn.prepareStatement(sqlTorneos);
	                 ResultSet rs = stmt.executeQuery()) {
	                while (rs.next()) {
	                    torneos.add(rs.getString("nombre"));
	                }
	            }

	            if (torneos.isEmpty()) {
	                JOptionPane.showMessageDialog(null, "No hay torneos registrados.");
	                return;
	            }

	            // 2. Selección del torneo
	            String seleccion = (String) JOptionPane.showInputDialog(
	                null, "Seleccione un torneo:", "Torneos",
	                JOptionPane.QUESTION_MESSAGE, null, torneos.toArray(), torneos.get(0)
	            );

	            if (seleccion == null) return;

	            // 3. Obtener el ID del torneo seleccionado
	            int idTorneo = -1;
	            String sqlIdTorneo = "SELECT id_torneo FROM torneo WHERE nombre = ?";
	            try (PreparedStatement stmt = conn.prepareStatement(sqlIdTorneo)) {
	                stmt.setString(1, seleccion);
	                try (ResultSet rs = stmt.executeQuery()) {
	                    if (rs.next()) {
	                        idTorneo = rs.getInt("id_torneo");
	                    }
	                }
	            }

	            if (idTorneo == -1) {
	                JOptionPane.showMessageDialog(null, "Torneo no encontrado.");
	                return;
	            }

	            // 4. Obtener los equipos participantes por categoría
	            String sqlEquipos = "SELECT e.id_equipo, e.nombre, e.categoria " +
	                                "FROM equipo e " +
	                                "JOIN equipo_torneo te ON e.id_equipo = te.id_equipo " +
	                                "WHERE te.id_torneo = ? " +
	                                "ORDER BY e.categoria";
	            Map<String, List<Equipo>> equiposPorCategoria = new HashMap<>();
	            try (PreparedStatement stmt = conn.prepareStatement(sqlEquipos)) {
	                stmt.setInt(1, idTorneo);
	                try (ResultSet rs = stmt.executeQuery()) {
	                    while (rs.next()) {
	                        String categoria = rs.getString("categoria");
	                        Equipo equipo = new Equipo(
	                            rs.getInt("id_equipo"),
	                            rs.getString("nombre"),
	                            categoria, null, null, null
	                        );
	                        equiposPorCategoria
	                            .computeIfAbsent(categoria, k -> new ArrayList<>())
	                            .add(equipo);
	                    }
	                }
	            }

	            if (equiposPorCategoria.isEmpty()) {
	                JOptionPane.showMessageDialog(null, "No hay equipos registrados en este torneo.");
	                return;
	            }

	            // 5. Selección de la categoría
	            String[] categorias = equiposPorCategoria.keySet().toArray(new String[0]);
	            String seleccionCategoria = (String) JOptionPane.showInputDialog(
	                null, "Seleccione categoría:", "Categorías",
	                JOptionPane.QUESTION_MESSAGE, null, categorias, categorias[0]
	            );

	            if (seleccionCategoria == null) return;

	            // 6. Verificar si ya existen partidos sorteados para esta categoría
	            String sqlPartidos = "SELECT COUNT(*) FROM partido " +
	                                 "WHERE id_torneo = ? ";
	            try (PreparedStatement stmt = conn.prepareStatement(sqlPartidos)) {
	                stmt.setInt(1, idTorneo);
	                
	                try (ResultSet rs = stmt.executeQuery()) {
	                    if (rs.next() && rs.getInt(1) > 0) {
	                        JOptionPane.showMessageDialog(null, "Los partidos para esta categoría ya fueron sorteados.");
	                        return;
	                    }
	                }
	            }

	            // 7. Obtener los equipos de la categoría seleccionada
	            List<Equipo> equipos = equiposPorCategoria.get(seleccionCategoria);
	            if (equipos.size() != 8 && equipos.size() != 16) {
	                JOptionPane.showMessageDialog(null, "Debe haber 8 o 16 equipos para sortear. Actualmente hay: " + equipos.size());
	                return;
	            }

	            // 8. Sortear los equipos aleatoriamente
	            Collections.shuffle(equipos);

	            // 9. Crear y registrar los partidos sorteados
	            StringBuilder resultado = new StringBuilder("Partidos sorteados:\n");
	            for (int i = 0; i < equipos.size(); i += 2) {
	                Equipo eq1 = equipos.get(i);
	                Equipo eq2 = equipos.get(i + 1);
	                Partido partido = new Partido(eq1, eq2);
	                partido.registrar(conn, seleccionCategoria, idTorneo);
	                resultado.append(eq1.getNombre())
	                         .append(" vs ")
	                         .append(eq2.getNombre())
	                         .append("\n");
	            }

	            JOptionPane.showMessageDialog(null, resultado.toString());

	        } catch (SQLException e) {
	            e.printStackTrace();
	            JOptionPane.showMessageDialog(null, "Error al registrar los partidos.");
	        }

	        // Mostrar el submenú correspondiente
	        mostrarSubmenuAsignarFechas();
	        
	    }

	    

	    
	    private void asignarSedeAPartido() {
	        try (Connection conn = Conexion.getInstance().getConnection()) {

	            // 1. Получаем список турниров
	            List<String> nombresTorneos = new ArrayList<>();
	            try (Statement stmt = conn.createStatement();
	                 ResultSet rs = stmt.executeQuery("SELECT nombre FROM torneo")) {
	                while (rs.next()) {
	                    nombresTorneos.add(rs.getString("nombre"));
	                }
	            }

	            if (nombresTorneos.isEmpty()) {
	                JOptionPane.showMessageDialog(null, "No hay torneos disponibles.");
	                return;
	            }

	            String seleccionTorneo = (String) JOptionPane.showInputDialog(null, "Seleccione un torneo:", "Torneos", JOptionPane.QUESTION_MESSAGE, null, nombresTorneos.toArray(), nombresTorneos.get(0));
	            if (seleccionTorneo == null) return;

	            // 2. Получаем ID турнира
	            int idTorneo;
	            try (PreparedStatement ps = conn.prepareStatement("SELECT id_torneo FROM torneo WHERE nombre = ?")) {
	                ps.setString(1, seleccionTorneo);
	                ResultSet rs = ps.executeQuery();
	                if (!rs.next()) return;
	                idTorneo = rs.getInt("id_torneo");
	            }

	         // 3. Получаем список матчей без стадиона (id_estadio = 1)
	            List<Partido> partidosSinSede = new ArrayList<>();
	            String sql = """
	                SELECT p.id_partido, p.id_equipo1, p.id_equipo2,
	                       e1.nombre AS nombre_local, e2.nombre AS nombre_visitante
	                FROM partido p
	                JOIN equipo e1 ON p.id_equipo1 = e1.id_equipo
	                JOIN equipo e2 ON p.id_equipo2 = e2.id_equipo
	                WHERE p.id_torneo = ? AND p.id_estadio = 1
	            """;

	            try (PreparedStatement ps = conn.prepareStatement(sql)) {
	                ps.setInt(1, idTorneo);
	                ResultSet rs = ps.executeQuery();
	                while (rs.next()) {
	                    Equipo equipo1 = new Equipo(
	                        rs.getInt("id_equipo1"),
	                        rs.getString("nombre_local"),
	                        null, null, null, null
	                    );
	                    Equipo equipo2 = new Equipo(
	                        rs.getInt("id_equipo2"),
	                        rs.getString("nombre_visitante"),
	                        null, null, null, null
	                    );

	                 // создаём матч
	                    Partido partido = new Partido(equipo1, equipo2);

	                    // напрямую задаём ID (раз поле доступно в классе)
	                    partido.idPartido = rs.getInt("id_partido"); // если поле доступно в пакете или public

	                    partidosSinSede.add(partido);

	                }
	            }



	            if (partidosSinSede.isEmpty()) {
	                JOptionPane.showMessageDialog(null, "No hay partidos sin sede.");
	                return;
	            }

	            Partido partido = (Partido) JOptionPane.showInputDialog(
	                    null,
	                    "Seleccione un partido:",
	                    "Partidos sin sede",
	                    JOptionPane.QUESTION_MESSAGE,
	                    null,
	                    partidosSinSede.toArray(),
	                    partidosSinSede.get(0)
	            );
	            if (partido == null) return;

	            // 4. Получаем список всех стадионов
	            int idEquipo1 = partido.getEquipo1().getIdEquipo();
	            int idEquipo2 = partido.getEquipo2().getIdEquipo();

	            List<Estadio> estadios = new ArrayList<>();
	            Set<Integer> idsVistos = new HashSet<>();

	            String sqlEstadios = """
	                SELECT DISTINCT es.id_estadio, es.nombre, es.direccion, es.capacidad
	            		FROM equipo e
	            		JOIN estadio es ON e.id_club = es.id_club
	            		WHERE e.id_equipo IN (?, ?)
	            			
	            		""";
	            
	            

	            try (PreparedStatement ps = conn.prepareStatement(sqlEstadios)) {

	                ps.setInt(1, idEquipo1);
	                ps.setInt(2, idEquipo2);

	                try (ResultSet rs = ps.executeQuery()) {
	                    while (rs.next()) {
	                        int idEstadio = rs.getInt("id_estadio");
	                        if (!idsVistos.contains(idEstadio)) {
	                            Estadio estadio = new Estadio(
	                                idEstadio,
	                                rs.getString("nombre"),
	                                rs.getString("direccion"),
	                                rs.getInt("capacidad")
	                            );
	                            estadios.add(estadio);
	                            idsVistos.add(idEstadio);
	                        }
	                    }
	                }
	            }

	            if (estadios.isEmpty()) {
	                JOptionPane.showMessageDialog(null, "Ninguno de los clubes tiene estadio registrado.");
	                return;
	            }

	            Estadio estadioSeleccionado = (Estadio) JOptionPane.showInputDialog(
	                    null,
	                    "Seleccione un estadio:",
	                    "Estadios de los clubes",
	                    JOptionPane.QUESTION_MESSAGE,
	                    null,
	                    estadios.toArray(),
	                    estadios.get(0)
	            );

	            if (estadioSeleccionado == null) return;


	            // 5. Ввод даты и времени
	            String fechaStr = JOptionPane.showInputDialog("Ingrese la fecha del partido (dd/MM/yyyy):");
	            if (fechaStr == null || fechaStr.isEmpty()) return;
	            LocalDate fecha;
	            try {
	                fecha = LocalDate.parse(fechaStr, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
	            } catch (DateTimeParseException e) {
	                JOptionPane.showMessageDialog(null, "Fecha inválida.");
	                return;
	            }

	            String horaStr = JOptionPane.showInputDialog("Ingrese la hora del partido (HH:mm):");
	            if (horaStr == null || horaStr.isEmpty()) return;
	            LocalTime hora;
	            try {
	                hora = LocalTime.parse(horaStr, DateTimeFormatter.ofPattern("HH:mm"));
	            } catch (DateTimeParseException e) {
	                JOptionPane.showMessageDialog(null, "Hora inválida.");
	                return;
	            }

	            LocalDateTime fechaHora = LocalDateTime.of(fecha, hora);

	            // 6. Проверка конфликта по стадиону (3 часа до/после)
	            boolean conflicto = false;
	            try (PreparedStatement ps = conn.prepareStatement(
	                    "SELECT fecha_hora FROM partido WHERE id_estadio = ? AND fecha_hora IS NOT NULL")) {
	                ps.setInt(1, estadioSeleccionado.getIdEstadio());
	                ResultSet rs = ps.executeQuery();
	                while (rs.next()) {
	                    LocalDateTime fh = rs.getTimestamp("fecha_hora").toLocalDateTime();
	                    long diferencia = Math.abs(Duration.between(fh, fechaHora).toMinutes());
	                    if (fh.toLocalDate().equals(fecha) && diferencia < 180) {
	                        conflicto = true;
	                        break;
	                    }
	                }
	            }


	            /// 7. Обновляем матч в базе
	            try (PreparedStatement ps = conn.prepareStatement("UPDATE partido SET id_estadio = ?, fecha_hora = ? WHERE id_partido = ?")) {
	                ps.setInt(1, estadioSeleccionado.getIdEstadio());
	                ps.setTimestamp(2, Timestamp.valueOf(fechaHora));
	                ps.setInt(3, partido.getIdPartido());
	                int updated = ps.executeUpdate();
	                if (updated > 0) {
	                    JOptionPane.showMessageDialog(null, "¡Estadio y horario asignados correctamente!");
	                    
	                    mostrarSubmenuAsignarFechas();
	                    
	                } else {
	                    JOptionPane.showMessageDialog(null, "No se pudo actualizar el partido.");
	                }
	            }


	        } catch (SQLException e) {
	            e.printStackTrace();
	            JOptionPane.showMessageDialog(null, "Error al acceder a la base de datos.");
	        }
	    }

	    
	    
	    
	    private void asignarArbitro() {
	        try (Connection conn = Conexion.getInstance().getConnection()) {

	            // Paso 1: Obtener torneos
	            List<Torneo> torneos = new ArrayList<>();
	            try (PreparedStatement stmt = conn.prepareStatement("SELECT id_torneo, nombre, ano FROM torneo")) {
	                ResultSet rs = stmt.executeQuery();
	                while (rs.next()) {
	                    // Используем вспомогательный конструктор с ID
	                    torneos.add(new Torneo(rs.getInt("id_torneo"), rs.getString("nombre"), rs.getString("ano")));
	                }
	            }

	            if (torneos.isEmpty()) {
	                JOptionPane.showMessageDialog(null, "No hay torneos disponibles.");
	                return;
	            }

	            String[] nombresTorneos = torneos.stream()
	                    .map(Torneo::getNombreTorneo)
	                    .toArray(String[]::new);

	            String seleccionTorneo = (String) JOptionPane.showInputDialog(
	                    null, "Seleccione un torneo:", "Torneos",
	                    JOptionPane.QUESTION_MESSAGE, null, nombresTorneos, nombresTorneos[0]);

	            if (seleccionTorneo == null) return;

	            Torneo torneo = torneos.stream()
	                    .filter(t -> t.getNombreTorneo().equals(seleccionTorneo))
	                    .findFirst().orElse(null);
	            if (torneo == null) return;

	            // Paso 2: Obtener categorías
	            Set<String> categorias = new HashSet<>();
	            String catQuery = """
	                    SELECT DISTINCT e.categoria
	                    FROM partido p
	                    JOIN equipo e ON p.id_equipo1 = e.id_equipo OR p.id_equipo2 = e.id_equipo
	                    WHERE p.id_torneo = ?
	                    """;

	            try (PreparedStatement stmt = conn.prepareStatement(catQuery)) {
	                stmt.setInt(1, torneo.getIdTorneo());
	                ResultSet rs = stmt.executeQuery();
	                while (rs.next()) {
	                    categorias.add(rs.getString("categoria"));
	                }
	            }

	            if (categorias.isEmpty()) {
	                JOptionPane.showMessageDialog(null, "No hay partidos registrados en este torneo.");
	                return;
	            }

	            String[] categoriasArray = categorias.toArray(new String[0]);
	            String categoriaSeleccionada = (String) JOptionPane.showInputDialog(
	                    null, "Seleccione categoría:", "Categorías",
	                    JOptionPane.QUESTION_MESSAGE, null, categoriasArray, categoriasArray[0]);

	            if (categoriaSeleccionada == null) return;

	            // Paso 3: Obtener partidos elegibles
	            List<Partido> partidosElegibles = new ArrayList<>();
	            String partidoQuery = """
	                    SELECT p.*
	                    FROM partido p
	                    JOIN equipo e1 ON p.id_equipo1 = e1.id_equipo
	                    JOIN equipo e2 ON p.id_equipo2 = e2.id_equipo
	                    WHERE p.id_torneo = ? AND (e1.categoria = ? OR e2.categoria = ?)
	                      AND p.id_arbitro IS NULL AND p.id_estadio IS NOT NULL AND p.fecha_hora IS NOT NULL
	                    """;

	            try (PreparedStatement stmt = conn.prepareStatement(partidoQuery)) {
	                stmt.setInt(1, torneo.getIdTorneo());
	                stmt.setString(2, categoriaSeleccionada);
	                stmt.setString(3, categoriaSeleccionada);
	                ResultSet rs = stmt.executeQuery();
	                while (rs.next()) {
	                    Partido partido = new Partido();
	                    
	                    

	                    partido.setIdPartido(rs.getInt("id_partido"));
	                    
	                    Timestamp timestamp = rs.getTimestamp("fecha_hora");
	                    if (timestamp != null) {
	                        LocalDateTime fechaHora = timestamp.toLocalDateTime();
	                        partido.setFecha(fechaHora.toLocalDate());
	                        partido.setHora(fechaHora.toLocalTime());
	                    }

	                    
	                    int idEquipo1 = rs.getInt("id_equipo1");
	                    int idEquipo2 = rs.getInt("id_equipo2");

	                    Equipo equipo1 = Equipo.obtenerEquipoPorId(conn, idEquipo1);
	                    Equipo equipo2 = Equipo.obtenerEquipoPorId(conn, idEquipo2);

	                    if (equipo1 == null || equipo2 == null) {
	                        System.err.println("Equipo no encontrado. ID1: " + idEquipo1 + ", ID2: " + idEquipo2);
	                        continue;
	                    }
	                    partido.setEquipo1(equipo1);
	                    partido.setEquipo2(equipo2);



	                    // Проверим, вдруг id_arbitro = null
	                    int idArbitro = rs.getInt("id_arbitro");
	                    if (!rs.wasNull()) {
	                        Arbitro arbitro = Arbitro.obtenerArbitroPorId(conn, idArbitro);  // метод для получения объекта Арбитра из БД
	                        partido.setArbitro(arbitro);
	                    }


	                    partidosElegibles.add(partido);
	                }

	            }

	            if (partidosElegibles.isEmpty()) {
	                JOptionPane.showMessageDialog(null, "No hay partidos disponibles para asignar árbitro.");
	                return;
	            }

	            Partido partidoSeleccionado = (Partido) JOptionPane.showInputDialog(
	                    null, "Seleccione un partido:", "Partidos disponibles",
	                    JOptionPane.QUESTION_MESSAGE, null, partidosElegibles.toArray(), partidosElegibles.get(0));

	            if (partidoSeleccionado == null) return;

	            // Paso 4: Obtener árbitros
	            List<Arbitro> arbitros = new ArrayList<>();
	            try (PreparedStatement stmt = conn.prepareStatement(
	                    "SELECT id_persona, nombre, apellido FROM persona WHERE rol = 'Arbitro'")) {
	                ResultSet rs = stmt.executeQuery();
	                while (rs.next()) {
	                    arbitros.add(new Arbitro(rs.getInt("id_persona"), rs.getString("nombre"), rs.getString("apellido")));
	                }
	            }

	            if (arbitros.isEmpty()) {
	                JOptionPane.showMessageDialog(null, "No hay árbitros registrados.");
	                return;
	            }

	            Arbitro arbitroSeleccionado = (Arbitro) JOptionPane.showInputDialog(
	                    null, "Seleccione un árbitro:", "Árbitros disponibles",
	                    JOptionPane.QUESTION_MESSAGE, null, arbitros.toArray(), arbitros.get(0));

	            if (arbitroSeleccionado == null) return;

	            // Paso 5: Verificar disponibilidad EXACTA del árbitro
	            String disponibilidadQuery = """
	                    SELECT 1 FROM partido
	            		WHERE id_arbitro = ?
	            		AND fecha_hora IS NOT NULL
	            		AND TIMESTAMPDIFF(MINUTE, ?, fecha_hora) BETWEEN -180 AND 180

	                    """;

	            boolean ocupado = false;
	            try (PreparedStatement stmt = conn.prepareStatement(disponibilidadQuery)) {
	                stmt.setInt(1, arbitroSeleccionado.getIdArbitro());
	                stmt.setTimestamp(2, Timestamp.valueOf(partidoSeleccionado.getFechaHora()));
	                ResultSet rs = stmt.executeQuery();
	                ocupado = rs.next();
	            }

	            if (ocupado) {
	                JOptionPane.showMessageDialog(null, "El árbitro ya tiene un partido en esa fecha y hora.");
	                return;
	            }
	            
	            System.out.println("ID árbitro: " + arbitroSeleccionado.getIdArbitro());
	            System.out.println("ID partido: " + partidoSeleccionado.getIdPartido());
	            System.out.println("FechaHora partido: " + partidoSeleccionado.getFechaHora());


	            // Paso 6: Asignar árbitro
	            try (PreparedStatement stmt = conn.prepareStatement(
	                    "UPDATE partido SET id_arbitro = ? WHERE id_partido = ?")) {
	                stmt.setInt(1, arbitroSeleccionado.getIdArbitro());
	                stmt.setInt(2, partidoSeleccionado.getIdPartido());
	                
	                System.out.println("Asignando árbitro ID=" + arbitroSeleccionado.getIdArbitro() +
	                        " al partido ID=" + partidoSeleccionado.getIdPartido());
	                
	                int filasActualizadas = stmt.executeUpdate();
	                if (filasActualizadas == 0) {
	                    JOptionPane.showMessageDialog(null, "No se pudo asignar el árbitro: partido no encontrado o no actualizado.");
	                    return;
	                }

	            }

	            JOptionPane.showMessageDialog(null, "¡Árbitro asignado correctamente al partido!");
	            mostrarSubmenuAsignarFechas();

	        } catch (SQLException e) {
	            e.printStackTrace();
	            JOptionPane.showMessageDialog(null, "Error al conectar con la base de datos: " + e.getMessage());
	        }
	    }
	    
	    

	    
	    private void ingresarResultados() {
	        try (Connection conn = Conexion.getInstance().getConnection()) {

	            // Paso 1: Obtener torneos
	            List<Torneo> torneos = new ArrayList<>();
	            try (PreparedStatement stmt = conn.prepareStatement("SELECT id_torneo, nombre, ano FROM torneo")) {
	                ResultSet rs = stmt.executeQuery();
	                while (rs.next()) {
	                    torneos.add(new Torneo(rs.getInt("id_torneo"), rs.getString("nombre"), rs.getString("ano")));
	                }
	            }

	            if (torneos.isEmpty()) {
	                JOptionPane.showMessageDialog(null, "No hay torneos disponibles.");
	                return;
	            }

	            String[] nombresTorneos = torneos.stream().map(Torneo::getNombreTorneo).toArray(String[]::new);
	            String seleccionTorneo = (String) JOptionPane.showInputDialog(null, "Seleccione un torneo:", "Torneos",
	                    JOptionPane.QUESTION_MESSAGE, null, nombresTorneos, nombresTorneos[0]);
	            if (seleccionTorneo == null) return;

	            Torneo torneo = torneos.stream()
	                    .filter(t -> t.getNombreTorneo().equals(seleccionTorneo))
	                    .findFirst().orElse(null);
	            if (torneo == null) return;

	            // Paso 2: Obtener categorías
	            Set<String> categorias = new HashSet<>();
	            String catQuery = """
	                    SELECT DISTINCT e.categoria
	                    FROM partido p
	                    JOIN equipo e ON p.id_equipo1 = e.id_equipo OR p.id_equipo2 = e.id_equipo
	                    WHERE p.id_torneo = ?
	                    """;
	            try (PreparedStatement stmt = conn.prepareStatement(catQuery)) {
	                stmt.setInt(1, torneo.getIdTorneo());
	                ResultSet rs = stmt.executeQuery();
	                while (rs.next()) {
	                    categorias.add(rs.getString("categoria"));
	                }
	            }

	            if (categorias.isEmpty()) {
	                JOptionPane.showMessageDialog(null, "No hay categorías disponibles.");
	                return;
	            }

	            String[] categoriasArray = categorias.toArray(new String[0]);
	            String categoriaSeleccionada = (String) JOptionPane.showInputDialog(null, "Seleccione categoría:", "Categorías",
	                    JOptionPane.QUESTION_MESSAGE, null, categoriasArray, categoriasArray[0]);
	            if (categoriaSeleccionada == null) return;

	            // Paso 3: Obtener partidos jugados sin resultado
	            List<Partido> partidosJugados = new ArrayList<>();
	            String partidosQuery = """
	                SELECT p.*
	                FROM partido p
	                JOIN equipo e1 ON p.id_equipo1 = e1.id_equipo
	                JOIN equipo e2 ON p.id_equipo2 = e2.id_equipo
	                WHERE p.id_torneo = ? AND (e1.categoria = ? OR e2.categoria = ?)
	                  AND p.fecha_hora IS NOT NULL
	                  AND p.id_estadio IS NOT NULL
	                  AND p.id_arbitro IS NOT NULL
	                  AND p.fecha_hora < NOW()
	                  AND NOT EXISTS (
	                      SELECT 1 FROM evento_partido ep
	                      WHERE ep.id_partido = p.id_partido AND ep.tipo = 'GOL'
	                  )
	            """;
	            try (PreparedStatement stmt = conn.prepareStatement(partidosQuery)) {
	                stmt.setInt(1, torneo.getIdTorneo());
	                stmt.setString(2, categoriaSeleccionada);
	                stmt.setString(3, categoriaSeleccionada);
	                ResultSet rs = stmt.executeQuery();
	                while (rs.next()) {
	                    Partido partido = new Partido();
	                    partido.setIdPartido(rs.getInt("id_partido"));

	                    Timestamp ts = rs.getTimestamp("fecha_hora");
	                    if (ts != null) {
	                        partido.setFecha(ts.toLocalDateTime().toLocalDate());
	                        partido.setHora(ts.toLocalDateTime().toLocalTime());
	                    }

	                    int idEquipo1 = rs.getInt("id_equipo1");
	                    int idEquipo2 = rs.getInt("id_equipo2");

	                    partido.setEquipo1(Equipo.obtenerEquipoPorId(conn, idEquipo1));
	                    partido.setEquipo2(Equipo.obtenerEquipoPorId(conn, idEquipo2));

	                    partidosJugados.add(partido);
	                }
	            }

	            if (partidosJugados.isEmpty()) {
	                JOptionPane.showMessageDialog(null, "No hay partidos finalizados sin resultados.");
	                return;
	            }

	            Partido partido = (Partido) JOptionPane.showInputDialog(
	                    null, "Seleccione un partido para ingresar resultado:", "Partidos finalizados",
	                    JOptionPane.QUESTION_MESSAGE, null, partidosJugados.toArray(), partidosJugados.get(0));
	            if (partido == null) return;

	            // Paso 4: Ingresar goles y registrar eventos
	            int golesEquipo1, golesEquipo2;
	            String sqlInsertEvento = "INSERT INTO evento_partido (tipo, minuto, descripcion, id_equipo, id_partido) VALUES (?, ?, ?, ?, ?)";

	            try (PreparedStatement insertStmt = conn.prepareStatement(sqlInsertEvento)) {
	                // Equipo 1
	                String input1 = JOptionPane.showInputDialog("¿Cuántos goles anotó " + partido.getEquipo1().getNombre() + "?");
	                if (input1 == null) return;
	                golesEquipo1 = Integer.parseInt(input1);

	                for (int i = 1; i <= golesEquipo1; i++) {
	                    String minutoStr = JOptionPane.showInputDialog("¿En qué minuto fue el gol #" + i + " de " + partido.getEquipo1().getNombre() + "?");
	                    if (minutoStr == null) return;
	                    int minuto = Integer.parseInt(minutoStr);

	                    insertStmt.setString(1, "GOL");
	                    insertStmt.setInt(2, minuto);
	                    insertStmt.setString(3, "Gol del equipo " + partido.getEquipo1().getNombre());
	                    insertStmt.setInt(4, partido.getEquipo1().getIdEquipo());
	                    insertStmt.setInt(5, partido.getIdPartido());
	                    insertStmt.executeUpdate();
	                }

	                // Equipo 2
	                String input2 = JOptionPane.showInputDialog("¿Cuántos goles anotó " + partido.getEquipo2().getNombre() + "?");
	                if (input2 == null) return;
	                golesEquipo2 = Integer.parseInt(input2);

	                for (int i = 1; i <= golesEquipo2; i++) {
	                    String minutoStr = JOptionPane.showInputDialog("¿En qué minuto fue el gol #" + i + " de " + partido.getEquipo2().getNombre() + "?");
	                    if (minutoStr == null) return;
	                    int minuto = Integer.parseInt(minutoStr);

	                    insertStmt.setString(1, "GOL");
	                    insertStmt.setInt(2, minuto);
	                    insertStmt.setString(3, "Gol del equipo " + partido.getEquipo2().getNombre());
	                    insertStmt.setInt(4, partido.getEquipo2().getIdEquipo());
	                    insertStmt.setInt(5, partido.getIdPartido());
	                    insertStmt.executeUpdate();
	                }

	                JOptionPane.showMessageDialog(null,
	                        "Resultado guardado correctamente:\n" +
	                                partido.getEquipo1().getNombre() + " " + golesEquipo1 +
	                                " - " + golesEquipo2 + " " + partido.getEquipo2().getNombre());

	            } catch (NumberFormatException e) {
	                JOptionPane.showMessageDialog(null, "Número inválido. Intente de nuevo.");
	            }

	            mostrarSubmenuCapturarResultados();

	        } catch (SQLException e) {
	            e.printStackTrace();
	            JOptionPane.showMessageDialog(null, "Error al conectar con la base de datos: " + e.getMessage());
	        }
	    }

	    
	    public void registrarAdminClubPorEmail() {
	        String email;
	        while (true) {
	            email = JOptionPane.showInputDialog("Ingrese el e-mail del nuevo Admin del club:");
	            if (email == null) {
	                JOptionPane.showMessageDialog(null, "Operación cancelada.");
	                return;
	            }
	            if (email.isBlank() || !email.contains("@")) {
	                JOptionPane.showMessageDialog(null, "Correo inválido.");
	                continue;
	            }

	            if (isEmailAlreadyRegistered(email)) {
	                JOptionPane.showMessageDialog(null, "Este correo ya está registrado.");
	                continue;
	            }

	            break;
	        }

	        try (Connection conn = Conexion.getInstance().getConnection()) {
	            String sql = "INSERT INTO persona (email, rol, nombre, apellido, password) VALUES (?, 'Admin Club', '', '', '')";
	            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
	                stmt.setString(1, email.toLowerCase());

	                int rows = stmt.executeUpdate();
	                if (rows > 0) {
	                    JOptionPane.showMessageDialog(null, "Correo autorizado exitosamente para rol 'Admin Club'.");
	                } else {
	                    JOptionPane.showMessageDialog(null, "No se pudo autorizar el correo.");
	                }
	            }
	        } catch (SQLException e) {
	            JOptionPane.showMessageDialog(null, "Error al registrar e-mail en base de datos:\n" + e.getMessage());
	            e.printStackTrace();
	        }
	    }



	    // Метод для проверки, зарегистрирован ли уже e-mail в базе данных
	    private boolean isEmailAlreadyRegistered(String email) {
	        boolean exists = false;
	        try (Connection connection = Conexion.getInstance().getConnection()) {
	            String query = "SELECT COUNT(*) FROM persona WHERE email = ?";
	            try (PreparedStatement stmt = connection.prepareStatement(query)) {
	                stmt.setString(1, email.toLowerCase());
	                var resultSet = stmt.executeQuery();
	                if (resultSet.next()) {
	                    exists = resultSet.getInt(1) > 0;  // Если есть хотя бы одна строка, значит, e-mail уже зарегистрирован
	                }
	            }
	        } catch (SQLException e) {
	            JOptionPane.showMessageDialog(null, "Error al verificar el correo en la base de datos: " + e.getMessage());
	        }
	        return exists;
	    }
}


