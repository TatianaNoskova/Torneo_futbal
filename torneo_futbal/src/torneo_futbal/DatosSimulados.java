package torneo_futbal;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class DatosSimulados {

	public static void inicializar() {
		// Creamos Varios Usuarios simulados
		Publico publico1 = new Publico("Juan", "Perez", "juan.perez@gmail.com", "12");
		Publico publico2 = new Publico("Ana", "Perez", "ana.perez@gmail.com", "12");
		Publico publico3 = new Publico("Carlos", "Perez", "carlos.perez@gmail.com", "12");
		Socio socio1 = new Socio("Luis", "Perez", "luis.perez@gmail.com", "12");
		Socio socio2 = new Socio("Maria", "Perez", "maria.perez@gmail.com", "12");
		Socio socio3 = new Socio("Pedro", "Perez", "pedro.perez@gmail.com", "12");
		Arbitro arbitro1 = new Arbitro("Juan", "Perez", "juan.perez@gmail.com", "12");
		Arbitro arbitro2 = new Arbitro("Ana", "Perez", "ana.perez@gmail.com", "12");

		// Creamos varios clubes simulados de argentina
		Club club1 = new Club("Club A", "Santa Fe");
		Club club2 = new Club("Club B", "Buenos Aires");
		Club club3 = new Club("Club C", "San Miguel");
		Club club4 = new Club("Club D", "Rosario");
		Club club5 = new Club("Club E", "Mendoza");
		Club club6 = new Club("Club F", "La Plata");
		Club club7 = new Club("Club G", "Santiago del Estero");
		Club club8 = new Club("Club H", "San Juan");

		// Creamos varias disciplinas simuladas
		Disciplina disciplina1 = new Disciplina("Tenis");
		Disciplina disciplina2 = new Disciplina("Baloncesto");
		Disciplina disciplina3 = new Disciplina("Fútbol");
		Disciplina disciplina4 = new Disciplina("Rugby");
		Disciplina disciplina5 = new Disciplina("Voleibol");
		Disciplina disciplina6 = new Disciplina("Atletismo");
		Disciplina disciplina7 = new Disciplina("Tenis");
		Disciplina disciplina8 = new Disciplina("Baloncesto");

		// Creamos varias instalaciones deportivas simuladas
		InstalacionDeportiva instalacion1 = new InstalacionDeportiva("InstalacionDeportiva Central",
				"Calle Fútbol 123", disciplina1, 8000);
		InstalacionDeportiva instalacion2 = new InstalacionDeportiva("InstalacionDeportiva en la Unidad",
				"Calle Fútbol 126", disciplina2, 9000);
		InstalacionDeportiva instalacion3 = new InstalacionDeportiva("InstalacionDeportiva en la Rivadavia",
				"Calle Fútbol 127", disciplina3, 8000);
		InstalacionDeportiva instalacion4 = new InstalacionDeportiva("InstalacionDeportiva en la Eco",
				"Calle Fútbol 128", disciplina4, 11000);
		InstalacionDeportiva instalacion5 = new InstalacionDeportiva("InstalacionDeportiva en la Jose Teran",
				"Calle Fútbol 129", disciplina5, 12000);
		InstalacionDeportiva instalacion6 = new InstalacionDeportiva("InstalacionDeportiva en la Unidad",
				"Calle Fútbol 130", disciplina6, 13000);
		InstalacionDeportiva instalacion7 = new InstalacionDeportiva("InstalacionDeportiva en la Unidad",
				"Calle Fútbol 131", disciplina7, 13000);
		InstalacionDeportiva instalacion8 = new InstalacionDeportiva("InstalacionDeportiva en la Unidad",
				"Calle Fútbol 132", disciplina8, 13000);

		// Creamos Reservas Simuladas
		// Fecha dentro de 5 horas con LocalDateTime
		// LocalDateTime fechaen5hs = LocalDateTime.now().plusHours(5);
		// Fecha de reserva para dentro de 5 dias
		// LocalDateTime fechaen5dias = LocalDateTime.now().plusDays(5);
		// Reserva de instalaciónes
		ReservaInstalacion reserva1 = new ReservaInstalacion(publico1, LocalDateTime.now().plusDays(5),
				LocalDateTime.now().plusDays(5).plusHours(5));
		ReservaInstalacion reserva2 = new ReservaInstalacion(publico2, LocalDateTime.now().plusDays(5),
				LocalDateTime.now().plusDays(5).plusHours(5));
		ReservaInstalacion reserva3 = new ReservaInstalacion(publico3, LocalDateTime.now().plusDays(5),
				LocalDateTime.now().plusDays(5).plusHours(5));
		ReservaInstalacion reserva4 = new ReservaInstalacion(socio1, LocalDateTime.now().plusDays(5),
				LocalDateTime.now().plusDays(5).plusHours(5));
		ReservaInstalacion reserva5 = new ReservaInstalacion(socio2, LocalDateTime.now().plusDays(5),
				LocalDateTime.now().plusDays(5).plusHours(5));
		ReservaInstalacion reserva6 = new ReservaInstalacion(socio3, LocalDateTime.now().plusDays(5),
				LocalDateTime.now().plusDays(5).plusHours(5));
		ReservaInstalacion reserva7 = new ReservaInstalacion(arbitro1, LocalDateTime.now().plusDays(5),
				LocalDateTime.now().plusDays(5).plusHours(5));
		ReservaInstalacion reserva8 = new ReservaInstalacion(arbitro2, LocalDateTime.now().plusDays(5),
				LocalDateTime.now().plusDays(5).plusHours(5));

		// Agregamos reservas a las instalaciones
		instalacion1.setReservas(reserva1);
		instalacion2.setReservas(reserva2);
		instalacion3.setReservas(reserva3);
		instalacion4.setReservas(reserva4);
		instalacion5.setReservas(reserva5);
		instalacion6.setReservas(reserva6);
		instalacion7.setReservas(reserva7);
		instalacion8.setReservas(reserva8);

		// Agregamos estadios simulados
		Estadio estadio1 = new Estadio("Estadio Central", "Calle Fútbol 123", 8000);
		Estadio estadio2 = new Estadio("Estadio San Isidro", "Calle Fútbol 123", 8000);
		Estadio estadio3 = new Estadio("Estadio Unidad", "Calle Fútbol 123", 10000);
		Estadio estadio4 = new Estadio("Estadio Rivadavia", "Calle Fútbol 123", 8000);
		Estadio estadio5 = new Estadio("Estadio Eco", "Calle Fútbol 123", 8000);
		Estadio estadio6 = new Estadio("Estadio Jose Teran", "Calle Fútbol 123", 8000);
		Estadio estadio7 = new Estadio("Estadio Jose Teran", "Calle Fútbol 123", 8000);
		Estadio estadio8 = new Estadio("Estadio Jose Teran", "Calle Fútbol 123", 8000);

		club1.agregarEstadio(estadio1);
		club2.agregarEstadio(estadio2);
		club3.agregarEstadio(estadio3);
		club4.agregarEstadio(estadio4);
		club5.agregarEstadio(estadio5);
		club6.agregarEstadio(estadio6);
		club7.agregarEstadio(estadio7);
		club8.agregarEstadio(estadio8);

		club1.agregarInstalacion(instalacion1);
		club2.agregarInstalacion(instalacion2);
		club3.agregarInstalacion(instalacion3);
		club4.agregarInstalacion(instalacion4);
		club5.agregarInstalacion(instalacion5);
		club6.agregarInstalacion(instalacion6);
		club7.agregarInstalacion(instalacion7);
		club8.agregarInstalacion(instalacion8);

		Equipo equipo1 = new Equipo("Tigres Rojos", "Primera", "Rojo y blanco", "./escudos/escudo1.png", estadio1);
		Equipo equipo2 = new Equipo("Lobos Azules", "Primera", "Azul y blanco", "./escudos/escudo2.png", estadio1);
		Equipo equipo3 = new Equipo("Pecez de arco iris", "Primera", "Rojo y amarillo", "./escudos/escudo3.png",
				estadio1);
		Equipo equipo4 = new Equipo("Gatos blancos", "Primera", "Celeste y blanco", "./escudos/escudo4.png", estadio1);
		Equipo equipo5 = new Equipo("Perros verdes", "Primera", "Verde y blanco", "./escudos/escudo5.png", estadio1);
		Equipo equipo6 = new Equipo("Monos negros", "Primera", "Negro y blanco", "./escudos/escud61.png", estadio1);
		Equipo equipo7 = new Equipo("Lorros violetos", "Primera", "Violeto y blanco", "./escudos/escudo7.png",
				estadio1);
		Equipo equipo8 = new Equipo("Ranas Rosadas", "Primera", "Rojo y rosado", "./escudos/escudo8.png", estadio1);

		club1.agregarEquipo(equipo1);
		club1.agregarEquipo(equipo2);
		club3.agregarEquipo(equipo3);
		club4.agregarEquipo(equipo4);
		club5.agregarEquipo(equipo5);
		club6.agregarEquipo(equipo6);
		club7.agregarEquipo(equipo7);
		club8.agregarEquipo(equipo8);

		// Agregamos los clubes en la lista statica
		SistemaRegistro.clubesRegistrados.add(club1);
		SistemaRegistro.clubesRegistrados.add(club2);
		SistemaRegistro.clubesRegistrados.add(club3);
		SistemaRegistro.clubesRegistrados.add(club4);
		SistemaRegistro.clubesRegistrados.add(club5);
		SistemaRegistro.clubesRegistrados.add(club6);
		SistemaRegistro.clubesRegistrados.add(club7);
		SistemaRegistro.clubesRegistrados.add(club8);

		// Torneo
		Torneo torneo1 = new Torneo("Torneo Nacional 2025", "2025");
		SistemaRegistro.torneosRegistrados.add(torneo1);

		// Agregamos el equipo en la lista statica
		torneo1.agregarEquipoParticipante(equipo1);
		torneo1.agregarEquipoParticipante(equipo2);
		torneo1.agregarEquipoParticipante(equipo3);
		torneo1.agregarEquipoParticipante(equipo4);
		torneo1.agregarEquipoParticipante(equipo5);
		torneo1.agregarEquipoParticipante(equipo6);
		torneo1.agregarEquipoParticipante(equipo7);
		torneo1.agregarEquipoParticipante(equipo8);

		// ====== Partido de prueba ======
crearPartido(equipo1, equipo2, torneo1, arbitro2, LocalDate.of(2025, 06, 01), LocalTime.of(17,30), club1);
crearPartido(equipo3, equipo4, torneo1, arbitro1, LocalDate.of(2025, 06, 10), LocalTime.of(18,30), club3);
crearPartido(equipo5, equipo6, torneo1, arbitro1, LocalDate.of(2025, 06, 15), LocalTime.of(19,30), club5);
crearPartido(equipo7, equipo8, torneo1, arbitro2, LocalDate.of(2025, 06, 20), LocalTime.of(20,30), club7);
	}

	private static void crearPartido(Equipo equipo1, Equipo equipo2, Torneo torneo, Arbitro arbitro, LocalDate fecha,
			LocalTime hora, Club club) {
		Partido partido = new Partido(equipo1, equipo2);
		partido.setFecha(fecha);
		partido.setHora(hora);
		partido.setArbitro(arbitro);
		partido.setEstadio(club.getEstadios().get(0));
		torneo.agregarPartido(partido);

	}
}
