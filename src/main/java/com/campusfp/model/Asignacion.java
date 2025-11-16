package com.campusfp.model;

import java.time.LocalDate;

public class Asignacion {
    private int id;
    private int empleadoId;
    private int proyectoId;
    private LocalDate fechaAsignacion;

    public Asignacion() {
    }

    public Asignacion(int id, int empleadoId, int proyectoId, LocalDate fechaAsignacion) {
        this.id = id;
        this.empleadoId = empleadoId;
        this.proyectoId = proyectoId;
        this.fechaAsignacion = fechaAsignacion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEmpleadoId() {
        return empleadoId;
    }

    public void setEmpleadoId(int empleadoId) {
        this.empleadoId = empleadoId;
    }

    public int getProyectoId() {
        return proyectoId;
    }

    public void setProyectoId(int proyectoId) {
        this.proyectoId = proyectoId;
    }

    public LocalDate getFechaAsignacion() {
        return fechaAsignacion;
    }

    public void setFechaAsignacion(LocalDate fechaAsignacion) {
        this.fechaAsignacion = fechaAsignacion;
    }

    @Override
    public String toString() {
        return "Asignacion{" +
                "id=" + id +
                ", empleadoId=" + empleadoId +
                ", proyectoId=" + proyectoId +
                ", fechaAsignacion=" + fechaAsignacion +
                '}';
    }
}
