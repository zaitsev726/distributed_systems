import org.junit.Before;
import org.junit.Test;
import ru.nsu.zebra.client.api.impl.DatabaseZebraApiClientImpl;
import ru.nsu.zebra.client.api.impl.RepositoryZebraApiClientImpl;
import ru.nsu.zebra.client.api.impl.StorageZebraApiClientImpl;
import ru.nsu.zebra.client.dto.QueryType;
import ru.nsu.zebra.client.dto.database.DatabaseCreateDTO;
import ru.nsu.zebra.client.dto.database.DatabaseResponseDTO;
import ru.nsu.zebra.client.dto.database.DatabaseStorageCreateDTO;
import ru.nsu.zebra.client.dto.repository.RepositoryCreateDTO;
import ru.nsu.zebra.client.dto.search.SearchRequestDTO;
import ru.nsu.zebra.client.migration.TimestampStorage;
import ru.nsu.zebra.client.migration.impl.MigrationExecutorDefaultImpl;
import ru.nsu.zebra.client.migration.impl.TimestampStorageJsonImpl;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class IntegrationMigrationTest {
    private final DatabaseZebraApiClientImpl databaseClient = new DatabaseZebraApiClientImpl();
    private final RepositoryZebraApiClientImpl repositoryClient = new RepositoryZebraApiClientImpl();
    private final StorageZebraApiClientImpl storageClient = new StorageZebraApiClientImpl();
    private TimestampStorage storage;

    private DatabaseResponseDTO prepareDatabase(DatabaseCreateDTO createDTO) {
        var databaseResponse = databaseClient.create(createDTO);
        assertTrue(databaseResponse.success());
        assertTrue(repositoryClient.commit(createDTO.repositoryId()).success());
        var database = databaseResponse.response();
        System.out.println(database);
        return database;
    }

    private void addData(DatabaseResponseDTO database, String fileName) {
        var storageResponse = storageClient.create(database.id(), new File(fileName));
        assertTrue(storageResponse.success());
        var storage = storageResponse.response();
        System.out.println(storageResponse);

        assertTrue(databaseClient.insertStorage(
                database.id(),
                new DatabaseStorageCreateDTO(
                        storage.id())
        ));
    }

    @Before
    public void before() {
        try {
            storage = new TimestampStorageJsonImpl();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void migrateDataFromExistedDatabases() {
        var repositoryResponse = repositoryClient.create(new RepositoryCreateDTO(
                "repositoryName",
                "fit.nsu.ru"
        ));

        assertTrue(repositoryResponse.success());
        var repository = repositoryResponse.response();
        assertTrue(repositoryClient.init(repository.id()).success());
        System.out.println(repositoryResponse);


        var db1 = prepareDatabase(new DatabaseCreateDTO(repository.id(), "db1"));
        addData(db1, "src/main/resources/data.xml");

        var db2 = prepareDatabase(new DatabaseCreateDTO(repository.id(), "db2"));
        addData(db2, "src/main/resources/data2.xml");

        var db3 = prepareDatabase(new DatabaseCreateDTO(repository.id(), "db3"));

        var executor = new MigrationExecutorDefaultImpl(List.of(db1, db2), db3, storage, databaseClient);
        executor.migrate();

        var searchResult = databaseClient.search(db3.id(), new SearchRequestDTO(
                QueryType.PQF,
                "@1=4 НГУ",
                null,
                null,
                "dc",
                null
        ));
        assertTrue(searchResult.success());
        System.out.println(searchResult.response());
    }
}
