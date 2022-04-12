package repository;

import org.junit.Test;
import ru.nsu.zebra.client.api.impl.RepositoryZebraApiClientImpl;
import ru.nsu.zebra.client.dto.repository.RepositoryCreateDTO;

import static org.junit.Assert.*;

public class RepositoryCreationTest {
    RepositoryZebraApiClientImpl client = new RepositoryZebraApiClientImpl();

    @Test
    public void createRepositoryTest() {
        var resp = client.create(new RepositoryCreateDTO("some_name", "fit.nsu.ru"));
        assertTrue(resp.success());

        assertNotNull(resp.response().id());
        assertEquals("some_name", resp.response().name());
        assertEquals("fit.nsu.ru", resp.response().type());
    }
}
