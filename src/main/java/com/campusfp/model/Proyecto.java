package com.campusfp.model;

import java.math.BigDecimal;

public class Proyecto {

    private int id;
    private String nombre;
    private BigDecimal presupuesto;

    public Proyecto() {

    }

    public Proyecto(int id, String nombre, BigDecimal presupuesto) {
        this.id = id;
        this.nombre = nombre;
        this.presupuesto = presupuesto;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public BigDecimal getPresupuesto() {
        return presupuesto;
    }

    public void setPresupuesto(BigDecimal presupuesto) {
        this.presupuesto = presupuesto;
    }

    @Override
    public String toString() {
        return "Proyecto{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", salario=" + presupuesto +
                '}';
    }
}
