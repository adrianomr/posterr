package br.com.adrianorodrigues.posterr.helper.context;

import java.util.Objects;

import lombok.extern.slf4j.Slf4j;

import org.testcontainers.containers.PostgreSQLContainer;

@Slf4j
public class PostgresContainer extends PostgreSQLContainer<PostgresContainer> {

    private static final String IMAGE_VERSION = "postgres:11.5";
    private static PostgresContainer instance;

    private PostgresContainer() {
        super("postgres:11.5");
    }

    public static PostgresContainer getInstance() {
        if (Objects.isNull(instance)) {
            instance = new PostgresContainer();
            instance.withReuse(true);
        }

        return instance;
    }

    public void start() {
        super.start();
        System.setProperty("DB_URL", instance.getJdbcUrl());
        System.setProperty("DB_USERNAME", instance.getUsername());
        System.setProperty("DB_PASSWORD", instance.getPassword());
    }

    public void stop() {
        log.info("JVM handles shut down");
    }
}