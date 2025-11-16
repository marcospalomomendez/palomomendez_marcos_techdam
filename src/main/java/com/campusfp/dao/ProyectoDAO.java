package com.campusfp.dao;

import com.campusfp.config.DatabaseConfig;
import com.campusfp.model.Proyecto;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProyectoDAO {

    public void restarPresupuesto(Connection conn, int proyectoOrigenId, BigDecimal monto) {

        String sql = "UPDATE proyectos SET presupuesto = presupuesto - ? WHERE id = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setBigDecimal(1, monto);
            ps.setInt(2, proyectoOrigenId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al restar presupuesto del proyecto con ID: " + proyectoOrigenId, e);
        }
    }

    public void sumarPresupuesto(Connection conn, int proyectoDestinoId, BigDecimal monto) {
        String sql = "UPDATE proyectos SET presupuesto = presupuesto + ? WHERE id = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setBigDecimal(1, monto);
            ps.setInt(2, proyectoDestinoId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al restar presupuesto del proyecto con ID: " + proyectoDestinoId, e);
        }

    }

    // Crear un nuevo proyecto en la base de datos
    public int crear(Proyecto proyecto) {
        String sql = "INSERT INTO proyectos (nombre, presupuesto) VALUES (?, ?)";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, proyecto.getNombre());
            ps.setBigDecimal(2, proyecto.getPresupuesto());

            int filas = ps.executeUpdate();

            if (filas > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        return rs.getInt(1); // ID generado
                    }
                }
            }

            return -1; // No se generó ID
        } catch (SQLException e) {
            throw new RuntimeException("Error al crear el proyecto", e);
        }
    }

    // Obtener todos los proyectos
    public List<Proyecto> obtenerTodos() {
        String sql = "SELECT * FROM proyectos";
        List<Proyecto> proyectos = new ArrayList<>();

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Proyecto proyecto = new Proyecto();
                proyecto.setId(rs.getInt("id"));
                proyecto.setNombre(rs.getString("nombre"));
                proyecto.setPresupuesto(rs.getBigDecimal("presupuesto"));
                proyectos.add(proyecto);
            }

            return proyectos;

        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener los proyectos", e);
        }
    }

    // Obtener un proyecto por su ID
    public Optional<Proyecto> obtenerPorId(int id) {
        String sql = "SELECT * FROM proyectos WHERE id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Proyecto proyecto = new Proyecto();
                    proyecto.setId(rs.getInt("id"));
                    proyecto.setNombre(rs.getString("nombre"));
                    proyecto.setPresupuesto(rs.getBigDecimal("presupuesto"));
                    return Optional.of(proyecto);
                } else {
                    return Optional.empty();
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener el proyecto con ID: " + id, e);
        }
    }

    // Actualizar un proyecto existente
    public boolean actualizar(Proyecto proyecto) {
        String sql = "UPDATE proyectos SET nombre = ?, presupuesto = ? WHERE id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, proyecto.getNombre());
            ps.setBigDecimal(2, proyecto.getPresupuesto());
            ps.setInt(3, proyecto.getId());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar el proyecto con ID: " + proyecto.getId(), e);
        }
    }

    // Eliminar un proyecto por su ID
    public boolean eliminar(int id) {
        String sql = "DELETE FROM proyectos WHERE id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar el proyecto con ID: " + id, e);
        }
    }
}
