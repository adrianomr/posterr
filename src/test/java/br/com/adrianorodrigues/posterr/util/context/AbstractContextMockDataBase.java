package br.com.adrianorodrigues.posterr.util.context;

public class AbstractContextMockDataBase {
    static final PostgresContainer POSTGRESQL_CONTAINER = PostgresContainer.getInstance();

    protected AbstractContextMockDataBase() {
    }

    static {
        POSTGRESQL_CONTAINER.start();
    }
}