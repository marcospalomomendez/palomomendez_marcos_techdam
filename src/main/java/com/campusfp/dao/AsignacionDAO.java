package com.campusfp.dao;

import com.campusfp.config.DatabaseConfig;

import java.sql.*;

public class AsignacionDAO {
    public int crear(Connection conn, int empId, int proyectoId) throws SQLException{

        String sql = "INSERT INTO asignaciones (empleado_id, proyecto_id, fecha_asignacion) VALUES (?, ?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(sql,PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, empId);
            ps.setInt(2, proyectoId);
            ps.setDate(3, new java.sql.Date(System.currentTimeMillis()));
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1); // Devuelve ID generado
                }
            }
        }
        return -1;
    }

}
