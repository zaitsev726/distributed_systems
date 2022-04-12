import org.junit.Test;
import ru.nsu.zebra.client.api.impl.DatabaseZebraApiClientImpl;
import ru.nsu.zebra.client.api.impl.RepositoryZebraApiClientImpl;
import ru.nsu.zebra.client.api.impl.StorageZebraApiClientImpl;
import ru.nsu.zebra.client.dto.QueryType;
import ru.nsu.zebra.client.dto.database.DatabaseCreateDTO;
import ru.nsu.zebra.client.dto.database.DatabaseStorageCreateDTO;
import ru.nsu.zebra.client.dto.repository.RepositoryCreateDTO;
import ru.nsu.zebra.client.dto.scan.ScanRequestDTO;
import ru.nsu.zebra.client.dto.search.SearchRequestDTO;

import java.io.File;

import static org.junit.Assert.assertTrue;

public class IntegrationTest {

    private final DatabaseZebraApiClientImpl databaseClient = new DatabaseZebraApiClientImpl();
    private final RepositoryZebraApiClientImpl repositoryClient = new RepositoryZebraApiClientImpl();
    private final StorageZebraApiClientImpl storageClient = new StorageZebraApiClientImpl();

    @Test
    public void createRepositoryAndFillIt() {
        var repositoryResponse = repositoryClient.create(new RepositoryCreateDTO(
                "nameForTest",
                "fit.nsu.ru"
        ));
        assertTrue(repositoryResponse.success());
        var repository = repositoryResponse.response();
        assertTrue(repositoryClient.init(repository.id()).success());
        System.out.println(repositoryResponse);

        var databaseResponse = databaseClient.create(new DatabaseCreateDTO(
                repository.id(),
                "nameForDatabase"
        ));
        assertTrue(databaseResponse.success());
        assertTrue(repositoryClient.commit(repository.id()).success());
        var database = databaseResponse.response();
        System.out.println(databaseResponse);

        var storageResponse = storageClient.create(
                database.id(),
                new File("src/main/resources/data.xml")
        );
        assertTrue(storageResponse.success());
        var storage = storageResponse.response();
        System.out.println(storageResponse);

        assertTrue(databaseClient.insertStorage(
                database.id(),
                new DatabaseStorageCreateDTO(
                        storage.id())
        ));


        var searchResult = databaseClient.search(database.id(), new SearchRequestDTO(
                QueryType.PQF,
                "@1=4 НГУ",
                null,
                null,
                "dc",
                null
        ));
        assertTrue(searchResult.success());
        System.out.println(searchResult.response());

        var scanResult = databaseClient.scan(database.id(), new ScanRequestDTO(
                QueryType.PQF,
                "dc.creator=Ольга",
                null,
                null
        ));

        assertTrue(scanResult.success());
        System.out.println(scanResult.response());
    }
}
