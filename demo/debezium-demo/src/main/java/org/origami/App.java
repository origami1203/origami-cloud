package org.origami;

import io.debezium.config.Configuration;
import io.debezium.embedded.EmbeddedEngine;
import io.debezium.engine.ChangeEvent;
import io.debezium.engine.DebeziumEngine;
import io.debezium.engine.format.Json;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Hello world!
 *
 */
public class App {

    private static DebeziumEngine<ChangeEvent<String, String>> engine;

    public static void main(String[] args) {
        // Define the configuration for the embedded and MySQL connector ...
        Configuration config = Configuration.create()
            /* begin engine properties */
            .with("connector.class", "io.debezium.connector.mysql.MySqlConnector")
            .with("offset.storage", "org.apache.kafka.connect.storage.FileOffsetBackingStore")
            .with("offset.storage.file.filename", "/path/to/storage/offset.dat").with("offset.flush.interval.ms", 60000)
            /* begin connector properties */
            .with("name", "my-sql-connector").with("database.hostname", "localhost").with("database.port", 3306)
            .with("database.user", "root").with("database.password", "root").with("database.server.id", 85744)
            .with("topic.prefix", "my-app-connector")
            .with("schema.history.internal", "io.debezium.storage.file.history.FileSchemaHistory")
            .with("schema.history.internal.file.filename", "/path/to/storage/schemahistory.dat").build();

        // Create the engine with this configuration ...
        EmbeddedEngine engine = EmbeddedEngine.create().using(config).notifying(System.out::println).build();

        // Run the engine asynchronously ...
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(engine);

        // At some later time ...
        engine.stop();
    }

}
