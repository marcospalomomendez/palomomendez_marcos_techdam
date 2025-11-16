package com.campusfp;

import com.campusfp.config.DatabaseConfig;
import com.campusfp.dao.EmpleadoDAO;
import com.campusfp.dao.ProyectoDAO;
import com.campusfp.model.Empleado;
import com.campusfp.model.Proyecto;
import com.campusfp.service.ProcedimientosService;
import com.campusfp.service.TransaccionesService;

import java.math.BigDecimal;
import java.util.List;


public class Main {
    public static void main(String[] args) {

        try{
            System.out.println("========================== INICIO DEL TEST MASIVO DE JDBC ==========================");
            testEmpleados();
            testProyectos();
            testTransacciones();
            testProcedimientos();
            System.out.println("========================== FIN DEL TEST MASIVO DE JDBC ==========================");
        } catch (Exception e){
            e.printStackTrace();
        }finally {
            DatabaseConfig.close();
        }
    }
      // --------------------------------------------------//
     //      1. CRUD EMPLEADOS                            //
    // __________________________________________________//
    public static void testEmpleados(){
        System.out.println("\n--- CRUD EMPLEADOS ---");

        EmpleadoDAO dao = new EmpleadoDAO();
        System.out.println("LISTA INICIAL DE EMPLEADOS:");
        // Listar
        dao.obtenerTodos().forEach(System.out::println);
        // Crear
        Empleado emp = new Empleado();
        emp.setNombre("Ana Torres");
        emp.setDepartamento("IT");
        emp.setSalario(new BigDecimal("2200"));
        emp.setActivo(true);
        int id = dao.crear(emp);
        System.out.println("Empleado creado con ID: " + id);

        // Actualizar
        emp.setId(id);
        emp.setSalario(new BigDecimal("2600"));
        System.out.println("Actualizado: " + dao.actualizar(emp));

        // Mostrar por ID
        System.out.println(dao.obtenerPorId(id));
        dao.obtenerTodos().forEach(System.out::println);

        // Eliminar un empleado
        int id_eliminar = 5;
        System.out.println("Eliminando empleado con ID: " + id_eliminar);
        boolean eliminado = dao.eliminar(id_eliminar);

        System.out.println("LISTA FINAL DE EMPLEADOS:");
        // Listar
        dao.obtenerTodos().forEach(System.out::println);


    }
    // ---------------------------------------------------//
    //      2. CRUD PROYECTOS                            //
    // _________________________________________________//

    public static void testProyectos(){
        System.out.println("\n--- CRUD PROYECTOS ---");

        ProyectoDAO dao = new ProyectoDAO();
        System.out.println("LISTA INICIAL DE PROYECTO:");
        // Listar
        dao.obtenerTodos().forEach(System.out::println);
        // Crear
        Proyecto proy = new Proyecto();
        proy.setNombre("Autoamatizacion de Procesos");
        proy.setPresupuesto(new BigDecimal("20200"));
        int id = dao.crear(proy);
        System.out.println("Proyecto creado con ID: " + id);

        // Actualizar
        proy.setId(id);
        proy.setPresupuesto(new BigDecimal("250000"));
        System.out.println("Actualizado: " + dao.actualizar(proy));

        // Mostrar por ID
        System.out.println(dao.obtenerPorId(id));
        dao.obtenerTodos().forEach(System.out::println);

        // Eliminar un proyecto
        int id_eliminar = 5;
        System.out.println("Eliminando proyecto con ID: " + id_eliminar);
        boolean eliminado = dao.eliminar(id_eliminar);

        System.out.println("LISTA FINAL DE PROYECTOS:");
        // Listar
        dao.obtenerTodos().forEach(System.out::println);

    }
    // ----------------------------------------------------//
    //      3. CRUD TRANSACCIONES                         //
    // __________________________________________________//

    public static void testTransacciones(){
        System.out.println("\n--- CRUD TRANSACCIONES ---");
        TransaccionesService ts = new TransaccionesService();
        ProyectoDAO dao = new ProyectoDAO();
        ts.transferirPresupuesto(1, 2, new BigDecimal("5000"));
        System.out.println("LISTA DE PROYECTOS DESPUES DE LA TRANSACCIÓN:");
        dao.obtenerTodos().forEach(System.out::println);
        List <Integer> empleadoIds = List.of(3,4,5,999,6);
        ts.asignarEmpleadosConSavepoint(1,empleadoIds);

    }
    // ----------------------------------------------------//
    //      4. CRUD PROCEDIMIENTOS                        //
    // __________________________________________________//

    public static void testProcedimientos(){
        System.out.println("\n--- CRUD PROCEDIMIENTOS ---");
        ProcedimientosService ps = new ProcedimientosService();
        EmpleadoDAO dao = new EmpleadoDAO();
        ps.actualizarSalariosDepartamento("IT", 25.5 );
        System.out.println("LISTA DE EMPLEADOS DESPUES DE AUMENTO DE SALARIO:");
        dao.obtenerTodos().forEach(System.out::println);
        ps.contar_asignaciones(3);

    }
}
