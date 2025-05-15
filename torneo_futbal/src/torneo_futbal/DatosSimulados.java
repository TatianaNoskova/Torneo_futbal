package torneo_futbal;

public class DatosSimulados {

    public static void inicializar() {
        Club club1 = new Club("Club A", "Santa Fe");
        Club club2 = new Club ("Club B", "Rio Negro");
        
        Estadio estadio1 = new Estadio("Estadio Central", "Calle Fútbol 123", 8000);
        Estadio estadio2 = new Estadio ("Gran Arena", "Calle Campiones 456", 10000);

        club1.agregarEstadio(estadio1);
        club2.agregarEstadio(estadio2);
        Equipo equipo1 = new Equipo("Tigres Rojos", "Primera", "Rojo y blanco", "./escudos/escudo1.png", estadio1);
        Equipo equipo2 = new Equipo("Lobos Azules", "Primera", "Azul y blanco", "./escudos/escudo2.png", estadio1);
        Equipo equipo3 = new Equipo("Pecez de arco iris", "Primera", "Rojo y amarillo", "./escudos/escudo3.png", estadio1);
        Equipo equipo4 = new Equipo("Gatos blancos", "Primera", "Celeste y blanco", "./escudos/escudo4.png", estadio1);
        Equipo equipo5 = new Equipo("Perros verdes", "Primera", "Verde y blanco", "./escudos/escudo5.png", estadio1);
        Equipo equipo6 = new Equipo("Monos negros", "Primera", "Negro y blanco", "./escudos/escud61.png", estadio1);
        Equipo equipo7 = new Equipo("Lorros violetos", "Primera", "Violeto y blanco", "./escudos/escudo7.png", estadio1);
        Equipo equipo8 = new Equipo("Ranas Rosadas", "Primera", "Rojo y rosado", "./escudos/escudo8.png", estadio1);

        club1.agregarEquipo(equipo1);
        club1.agregarEquipo(equipo2);
        club1.agregarEquipo(equipo3);
        club1.agregarEquipo(equipo4);
        club1.agregarEquipo(equipo5);
        club1.agregarEquipo(equipo6);
        club1.agregarEquipo(equipo7);
        club1.agregarEquipo(equipo8);

        // Добавим в статический список
        SistemaRegistro.clubesRegistrados.add(club1);
        SistemaRegistro.clubesRegistrados.add(club2);

        //Турнир
       Torneo torneo1 = new Torneo("Torneo Nacional 2025", "2025");
       SistemaRegistro.torneosRegistrados.add(torneo1);
       
    // Добавляем команду в турнир
       torneo1.agregarEquipoParticipante(equipo1);
       torneo1.agregarEquipoParticipante(equipo2);
       torneo1.agregarEquipoParticipante(equipo3);
       torneo1.agregarEquipoParticipante(equipo4);
       torneo1.agregarEquipoParticipante(equipo5);
       torneo1.agregarEquipoParticipante(equipo6);
       torneo1.agregarEquipoParticipante(equipo7);
       torneo1.agregarEquipoParticipante(equipo8);
       
        
     
    }
}





