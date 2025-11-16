CREATE DATABASE IF NOT EXISTS techdam_completo;
USE techdam_completo;
CREATE TABLE empleados(
                          id INT AUTO_INCREMENT PRIMARY KEY,
                          nombre VARCHAR(100) NOT NULL,
                          departamento VARCHAR(100) NOT NULL,
                          salario DECIMAL (10,2) NOT NULL,
                          activo BOOLEAN DEFAULT TRUE
);
CREATE TABLE proyectos(
                          id INT AUTO_INCREMENT PRIMARY KEY,
                          nombre VARCHAR(100) NOT NULL,
                          presupuesto DECIMAL (10,2) NOT NULL
);

CREATE TABLE asignaciones (
                              id INT AUTO_INCREMENT PRIMARY KEY,
                              empleado_id INT NOT NULL,
                              proyecto_id INT NOT NULL,
                              fecha_asignacion DATE NOT NULL,
                              FOREIGN KEY (empleado_id) REFERENCES empleados(id),
                              FOREIGN KEY (proyecto_id) REFERENCES proyectos(id)
);

DELIMITER $$
CREATE PROCEDURE actualizar_salario_departamento(
    IN p_departamento VARCHAR(50),
    IN p_porcentaje DECIMAL(5,2),
    OUT p_empleados_actualizados INT

)

CREATE INDEX idx_emp_depto ON empleados(departamento);
CREATE INDEX idx_asig_emp ON asignaciones(empleado_id);
CREATE INDEX idx_asig_proy ON asignaciones(proyecto_id);

DELIMITER $$
CREATE PROCEDURE actualizar_salario_departamento(
    IN p_departamento VARCHAR(50),
    IN p_porcentaje DECIMAL(5,2),
    OUT p_empleados_actualizados INT
)
BEGIN
UPDATE empleados
SET salario = salario * (1 + p_porcentaje / 100)
WHERE departamento = p_departamento AND activo = TRUE;


SET p_empleados_actualizados = ROW_COUNT();
END$$
DELIMITER ;


DELIMITER $$
CREATE PROCEDURE contar_asignaciones(
    IN p_empleado_id INT,
    OUT p_total INT
)
BEGIN
SELECT COUNT(*) INTO p_total FROM asignaciones WHERE empleado_id = p_empleado_id;
END$$
DELIMITER ;

DELIMITER $$
CREATE FUNCTION salario_anual(p_empleado_id INT)
    RETURNS DECIMAL(10,2)
    DETERMINISTIC
BEGIN
    DECLARE v_salario DECIMAL(10,2);

SELECT salario INTO v_salario
FROM empleados
WHERE id = p_empleado_id;

RETURN v_salario * 12;
END$$
DELIMITER ;

INSERT INTO empleados(nombre, departamento, salario) VALUES
                                                         ('Ana Torres', 'IT', 2200),
                                                         ('Luis Pérez', 'Marketing', 1800),
                                                         ('Marta Gómez', 'IT', 2400),
                                                         ('Carlos Ruiz', 'Ventas', 1600),
                                                         ('Laura Díaz', 'IT', 2500);


INSERT INTO proyectos(nombre, presupuesto) VALUES
                                               ('Migración Servidores', 20000),
                                               ('Campaña Q1', 10000),
                                               ('App Móvil', 50000),
                                               ('Web Corporativa', 15000),
                                               ('Auditoría Interna', 8000);


INSERT INTO asignaciones(empleado_id, proyecto_id, fecha_asignacion) VALUES
                                                                         (1, 1, CURDATE()),
                                                                         (2, 2, CURDATE()),
                                                                         (3, 3, CURDATE()),
                                                                         (4, 4, CURDATE()),
                                                                         (3, 3, CURDATE());