package com.campusfp.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConfig {
    // Variable dataSource definida correctamente
    private static HikariDataSource dataSource;

    // Bloque estático para inicialización
    static {
        try {
            // Cargar configuración desde db.properties
            Properties props = new Properties();
            InputStream input = DatabaseConfig.class.getClassLoader()
                    .getResourceAsStream("db.properties");

            if (input == null) {
                throw new RuntimeException("No se pudo encontrar db.properties");
            }

            props.load(input);
            input.close();

            //Inicializar HikariDataSource con las propiedades
            HikariConfig config = new HikariConfig();
            config.setJdbcUrl(props.getProperty("db.url"));
            config.setUsername(props.getProperty("db.username"));
            config.setPassword(props.getProperty("db.password"));
            config.setDriverClassName(props.getProperty("db.driver"));

            // Configuraciones del pool
            config.setMaximumPoolSize(Integer.parseInt(props.getProperty("hikari.maximumPoolSize", "10")));
            config.setMinimumIdle(Integer.parseInt(props.getProperty("hikari.minimumIdle", "5")));
            config.setIdleTimeout(Long.parseLong(props.getProperty("hikari.idleTimeout", "300000")));
            config.setConnectionTimeout(Long.parseLong(props.getProperty("hikari.connectionTimeout", "20000")));
            config.setMaxLifetime(Long.parseLong(props.getProperty("hikari.maxLifetime", "1200000")));

            // INICIALIZAR dataSource
            dataSource = new HikariDataSource(config);

            System.out.println("Pool de conexiones HikariCP configurado correctamente");

        } catch (IOException e) {
            throw new RuntimeException("Error cargando db.properties", e);
        } catch (NumberFormatException e) {
            throw new RuntimeException("Error en formato numérico en db.properties", e);
        }
    }

    // Método para obtener conexión
    public static Connection getConnection() throws SQLException {
        Connection conn = dataSource.getConnection();
        // Verificar que la conexión es válida
        if (conn.isValid(2)) {
            return conn;
        } else {
            throw new SQLException("Conexión a BD no válida");
        }
    }

    // Método para cerrar el pool
    public static void close() {
        if (dataSource != null && !dataSource.isClosed()) {
            dataSource.close();
            System.out.println("Pool de conexiones HikariCP cerrado correctamente");
        }
    }
}