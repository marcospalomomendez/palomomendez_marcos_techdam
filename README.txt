Pasos para ejecutar el programa

1. Configuración previa
Base de datos:

Asegúrate de tener creada la base de datos.

Debes implementar el script SQL proporcionado para crear las tablas necesarias (empleados, proyectos, asignaciones, etc.) en script/techdam_completo.sql.

Archivo de configuración (DatabaseConfig):

Revisa que en DatabaseConfig tengas correctamente configurada la conexión JDBC (URL, usuario, contraseña, driver) encontrados para modificar en src/main/resources/db.properties.

Modifica las siguientes lineas:

db.url=jdbc:mysql://localhost:3306/techdam_completo
db.username=root
db.password=tu_password
db.driver=com.mysql.cj.jdbc.Driver

2. Compilación
Desde la raíz del proyecto, compila con Maven o Gradle (si lo tienes configurado). Si es un proyecto simple con javac:

3. Ejecución
Ejecuta la clase Main

4. Flujo esperado

Cuando lo ejecutes, deberías ver en consola:

Inicio del test masivo JDBC

CRUD de empleados:

Listado inicial

Creación de un nuevo empleado

Actualización de salario

Eliminación de un empleado

Listado final

CRUD de proyectos:

Creación de un nuevo proyecto

Actualización de preusupuesto

Eliminación de un proyecto

Listado final

Transacciones:

Transferencia de presupuesto entre proyectos

Asignación de empleados con savepoints

Procedimientos:

Actualización de salarios por departamento

Conteo de asignaciones