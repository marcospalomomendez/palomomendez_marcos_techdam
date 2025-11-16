package com.campusfp.service;

import com.campusfp.config.DatabaseConfig;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;

public class ProcedimientosService {
    public int actualizarSalariosDepartamento(String departamento, double
            porcentaje) {
        try (Connection conn = DatabaseConfig.getConnection();
             CallableStatement cstmt = conn.prepareCall(
                     "{call actualizar_salario_departamento(?, ?, ?)}")) {
            cstmt.setString(1, departamento);
            cstmt.setBigDecimal(2, BigDecimal.valueOf(porcentaje));
            cstmt.registerOutParameter(3, Types.INTEGER);
            cstmt.execute();
            System.out.println("Procedimiento ejecutado: " + cstmt.getInt(3) + " empleados actualizados");

            return cstmt.getInt(3);
        } catch (SQLException e) {
            System.err.println("Error ejecutando procedimiento actualizar_salario_departamento: " + e.getMessage());
            return -1;
        }
    }
    public int contar_asignaciones(int empleado_id) {
        try (Connection conn = DatabaseConfig.getConnection();
             CallableStatement cstmt = conn.prepareCall(
                     "{call contar_asignaciones(?, ?)}")) {
            cstmt.setInt(1, empleado_id);
            cstmt.registerOutParameter(2, Types.INTEGER);
            cstmt.execute();

            System.out.println("Procedimiento ejecutado: Empleado " + empleado_id + " tiene " + cstmt.getInt(2) + " asignaciones");

            return cstmt.getInt(2);
        } catch (SQLException e) {
            System.err.println("Error ejecutando procedimiento contar_asignaciones: " + e.getMessage());
            return -1;
        }
    }
}
