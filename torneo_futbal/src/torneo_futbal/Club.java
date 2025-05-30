package torneo_futbal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.JOptionPane;

public class Club {
	
	protected int id; 
    private String nombre;
    private String direccion;
    private List<Estadio> estadios;
    private List<Equipo> equipos;
    private List<Disciplina> disciplinas;
    private List<InstalacionDeportiva> instalaciones;
    private List<DirectorTecnico> directoresTecnicos;
    private Set<String> sociosEmailList = new HashSet<>();

    public Club(String nombre, String direccion) {
        this.nombre = nombre;
        this.direccion = direccion;

        this.estadios = new ArrayList<>();
        this.equipos = new ArrayList<>();
        this.disciplinas = new ArrayList<>();
        this.instalaciones = new ArrayList<>();
        this.directoresTecnicos = new ArrayList<>();

    }
    
    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void agregarEstadio(Estadio estadio) {
        estadios.add(estadio);
    }

    public List<Estadio> getEstadios() {
        return estadios;
    }

    public void agregarEquipo(Equipo equipo) {
        equipos.add(equipo);
    }

    public List<Equipo> getEquipos() {
        return equipos;
    }

    public void agregarDisciplina(Disciplina disciplina) {
        disciplinas.add(disciplina);
    }

    public List<Disciplina> getDisciplinas() {
        return disciplinas;
    }

    public void agregarInstalacion(InstalacionDeportiva instalacion) {
        instalaciones.add(instalacion);
    }

    public List<InstalacionDeportiva> getInstalaciones() {
        return instalaciones;
    }
    public void agregarDirectorTecnico(DirectorTecnico dt) {
        directoresTecnicos.add(dt);
    }

    public List<DirectorTecnico> getDirectoresTecnicos() {
        return directoresTecnicos;
    }
    
    public Set<String> getSociosEmailList() {
        return sociosEmailList;
    }

    @Override
    public String toString() {
        return "Nombre del club: " + nombre + "\nDirección: " + direccion;
    }
    
    // ---------- métod para mostrar acitvidades de los clubes--------------
    public void mostrarPlazasYDisciplinaConHorario(int idClub) {
        
        if (idClub == -1) {
            JOptionPane.showMessageDialog(null, "Id de club no válido.");
            return;
        }

        String sql = 
            "SELECT i.nombre AS nombre_instalacion, " +
            "d.nombre_disciplina, " +
            "h.horario_semana_apertura, h.horario_semana_cierre, " +
            "h.horario_fin_semana_apertura, h.horario_fin_semana_cierre " +
            "FROM instalacion i " +
            "JOIN disciplina d ON i.id_disciplina = d.id_disciplina " +
            "JOIN horario_instalacion h ON i.id_instalacion = h.id_instalacion " +
            "WHERE i.id_club = ?";  

        try (Connection conn = Conexion.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idClub);

            try (ResultSet rs = stmt.executeQuery()) {
                if (!rs.isBeforeFirst()) {
                    JOptionPane.showMessageDialog(null, "No hay instalaciones registradas para este club.");
                    return;
                }

                StringBuilder result = new StringBuilder("Instalaciones y horarios del club:\n\n");

                while (rs.next()) {
                    String nombreInstalacion = rs.getString("nombre_instalacion");
                    String nombreDisciplina = rs.getString("nombre_disciplina");
                    Time horarioSemanaApertura = rs.getTime("horario_semana_apertura");
                    Time horarioSemanaCierre = rs.getTime("horario_semana_cierre");
                    Time horarioFinSemanaApertura = rs.getTime("horario_fin_semana_apertura");
                    Time horarioFinSemanaCierre = rs.getTime("horario_fin_semana_cierre");

                    result.append("Instalación: ").append(nombreInstalacion).append("\n")
                          .append("Disciplina: ").append(nombreDisciplina).append("\n")
                          .append("Horarios de semana (lunes a viernes): ").append(horarioSemanaApertura).append(" - ").append(horarioSemanaCierre).append("\n")
                          .append("Horarios de fin de semana (sábado y domingo): ").append(horarioFinSemanaApertura).append(" - ").append(horarioFinSemanaCierre).append("\n\n");
                }

                JOptionPane.showMessageDialog(null, result.toString());

            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error al obtener la información de las instalaciones.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al conectar con la base de datos.");
        }
    }

    
    
    
}

