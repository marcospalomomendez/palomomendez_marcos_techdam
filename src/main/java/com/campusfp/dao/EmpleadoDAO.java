package com.campusfp.dao;

import com.campusfp.config.DatabaseConfig;
import com.campusfp.model.Empleado;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EmpleadoDAO {

    // Crear un nuevo empleado en la base de datos
    public int crear(Empleado empleado) {
        String sql = "INSERT INTO empleados (nombre, departamento, salario, activo) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, empleado.getNombre());
            ps.setString(2, empleado.getDepartamento());
            ps.setBigDecimal(3, empleado.getSalario());
            ps.setBoolean(4, empleado.isActivo());

            int filas = ps.executeUpdate();

            if (filas > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        return rs.getInt(1); // ID generado
                    }
                }
            }

            return -1;

        } catch (SQLException e) {
            throw new RuntimeException("Error al crear empleado", e);
        }
    }

    // Obtener todos los empleados
    public List<Empleado> obtenerTodos() {
        String sql = "SELECT * FROM empleados";
        List<Empleado> empleados = new ArrayList<>();

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Empleado empleado = new Empleado();
                empleado.setId(rs.getInt("id"));
                empleado.setNombre(rs.getString("nombre"));
                empleado.setDepartamento(rs.getString("departamento"));
                empleado.setSalario(rs.getBigDecimal("salario"));
                empleado.setActivo(rs.getBoolean("activo"));
                empleados.add(empleado);
            }

            return empleados;

        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener todos los empleados", e);
        }
    }

    // Obtener un empleado por su ID
    public Optional<Empleado> obtenerPorId(int id) {
        String sql = "SELECT * FROM empleados WHERE id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    Empleado empleado = new Empleado();
                    empleado.setId(rs.getInt("id"));
                    empleado.setNombre(rs.getString("nombre"));
                    empleado.setDepartamento(rs.getString("departamento"));
                    empleado.setSalario(rs.getBigDecimal("salario"));
                    empleado.setActivo(rs.getBoolean("activo"));
                    return Optional.of(empleado);
                }

                return Optional.empty();
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener empleado con ID: " + id, e);
        }
    }

    // Actualizar un empleado existente
    public boolean actualizar(Empleado empleado) {
        String sql = "UPDATE empleados SET nombre = ?, departamento = ?, salario = ?, activo = ? WHERE id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, empleado.getNombre());
            ps.setString(2, empleado.getDepartamento());
            ps.setBigDecimal(3, empleado.getSalario());
            ps.setBoolean(4, empleado.isActivo());
            ps.setInt(5, empleado.getId());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar empleado con ID: " + empleado.getId(), e);
        }
    }

    // Eliminar un empleado por su ID
    public boolean eliminar(int id) {
        String sql = "DELETE FROM empleados WHERE id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            if (ps.executeUpdate() > 0){
                return true;
            }else{
                return false;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar empleado con ID: " + id, e);
        }
    }
}
