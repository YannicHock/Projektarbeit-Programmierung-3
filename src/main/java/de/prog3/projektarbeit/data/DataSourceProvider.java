package de.prog3.projektarbeit.data;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import javax.sql.DataSource;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class DataSourceProvider {
    private static HikariDataSource dataSource;
    private static final String SOURCE_PATH = "database.sqlite";
    private static final String DESTINATION_PATH = "data/db.sqlite";

    public static void init(){
        File file = new File(DESTINATION_PATH);
        if(!file.exists()){
            file.getParentFile().mkdirs();
            copyDatabaseFile();
        }
        HikariConfig config = new HikariConfig();

        config.setJdbcUrl("jdbc:sqlite:data/db.sqlite");

        config.setDriverClassName("org.sqlite.JDBC");

        config.setMaximumPoolSize(10);

        dataSource = new HikariDataSource(config);
    }

    public static DataSource getDataSource() {
        if (dataSource == null) {
            init();
        }
        return dataSource;
    }

    private static void copyDatabaseFile() {
        ClassLoader classLoader = DataSourceProvider.class.getClassLoader();
        try (InputStream inputStream = classLoader.getResourceAsStream(SOURCE_PATH)) {
            if (inputStream == null) {
                throw new IllegalArgumentException("File not found!");
            }
            Files.copy(inputStream, Paths.get(DESTINATION_PATH));
        } catch (Exception e) {
            System.err.println(e);
        }
    }

}
