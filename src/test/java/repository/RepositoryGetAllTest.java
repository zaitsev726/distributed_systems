package repository;

import org.junit.Test;
import ru.nsu.zebra.client.api.impl.RepositoryZebraApiClientImpl;
import ru.nsu.zebra.client.dto.ResponseDTO;
import ru.nsu.zebra.client.dto.repository.RepositoryCreateDTO;
import ru.nsu.zebra.client.dto.repository.RepositoryResponseDTO;

import java.util.ArrayList;
import java.util.stream.Collectors;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public class RepositoryGetAllTest {
//    RepositoryZebraApiClientImpl client = new RepositoryZebraApiClientImpl();
//
//    @Test
//    public void createRepositoryTest() {
//        var expected = new ArrayList<ResponseDTO<RepositoryResponseDTO>>();
//
//        expected.add(client.create(new RepositoryCreateDTO("some_name_1", "fit.nsu.ru")));
//        expected.add(client.create(new RepositoryCreateDTO("some_name_2", "fit.nsu.ru")));
//        expected.add(client.create(new RepositoryCreateDTO("some_name_3", "fit.nsu.ru")));
//
//
//        var resp = client.getAll();
//        assertTrue(resp.success());
//        assertEquals(3, resp.response().size());
//
//        var casted = new ArrayList<>(resp.response());
//        for (var i = 0; i < 3; i++) {
//            var rep = casted.get(i);
//            var exp = expected.get(i);
//            assertTrue(exp.success());
//            assertNotNull(rep.id());
//            assertEquals(exp.response().name(), rep.name());
//            assertEquals(exp.response().type(), rep.type());
//        }
//    }
}
