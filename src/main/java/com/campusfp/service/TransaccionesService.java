package com.campusfp.service;

import com.campusfp.config.DatabaseConfig;
import com.campusfp.dao.AsignacionDAO;
import com.campusfp.dao.ProyectoDAO;
import com.campusfp.model.Asignacion;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.util.List;

public class TransaccionesService {

    private static ProyectoDAO proyectoDAO = new ProyectoDAO();
    private static AsignacionDAO asignacionDAO = new AsignacionDAO();

    //Transferencia de presupuesto entre proyectos
    public boolean transferirPresupuesto(int proyectoOrigenId,
                                         int proyectoDestinoId,
                                         BigDecimal monto) {

        Connection conn = null;

        try {
            conn = DatabaseConfig.getConnection();
            conn.setAutoCommit(false);

            proyectoDAO.restarPresupuesto(conn, proyectoOrigenId, monto);
            proyectoDAO.sumarPresupuesto(conn, proyectoDestinoId, monto);

            conn.commit();
            return true;

        } catch (SQLException e) {
            if (conn != null) try { conn.rollback(); } catch (SQLException ex) {}
            e.printStackTrace();
            return false;

        } finally {
            try {
                if (conn != null) conn.close();   // ✔ Solo se cierra la conexión
            } catch (SQLException ignored) {}
        }
    }

    // Asignar múltiples empleados a un proyecto usando SAVEPOINTS
    public void asignarEmpleadosConSavepoint(int proyectoId, List<Integer> empleadoIds) {

        Connection conn = null;

        try {
            conn = DatabaseConfig.getConnection();
            conn.setAutoCommit(false);

            for (int empId : empleadoIds) {

                Savepoint sp = conn.setSavepoint("SP_" + empId);

                try {
                    asignacionDAO.crear(conn, proyectoId, empId);
                } catch (SQLException e) {
                    conn.rollback(sp);
                    System.out.println("Error asignando empleado " + empId + ", rollback parcial.");
                }
            }

            conn.commit();

        } catch (SQLException e) {
            if (conn != null) try { conn.rollback(); } catch (SQLException ignored) {}
            e.printStackTrace();

        } finally {
            try {
                if (conn != null) conn.close();    // ✔ Se cierra la conexión, NO EL POOL
            } catch (SQLException ignored) {}
        }
    }
}

