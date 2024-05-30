package learn.house.domain;

import learn.house.data.DataException;
import learn.house.data.HostRepositoryDouble;
import learn.house.models.Host;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HostServiceTest {

    HostService service = new HostService(new HostRepositoryDouble());

    @Test
    void shouldNotFindNonexistentHostId() throws DataException {
        Result<Host> result = service.findById("Nonexistent Id");
        assertNull(result.getPayload());
        assertTrue(result.getErrorMessages().get(0).contains("No host with id"));
    }

    @Test
    void shouldFindExistingHostId() throws DataException {
        Result<Host> result = service.findById("host_one_id");
        assertNotNull(result.getPayload());
        assertEquals(0, result.getErrorMessages().size());
        assertEquals("host_one_id", result.getPayload().getId());
        assertEquals("Chicago", result.getPayload().getCity());
    }

    @Test
    void shouldNotFindNonexistentHostEmail() throws DataException {
        Result<Host> result = service.findByEmail("nonexistentemail@gmail.com");
        assertNull(result.getPayload());
        assertTrue(result.getErrorMessages().get(0).contains("No host with email"));
    }

    @Test
    void shouldFindExistingHostEmail() throws DataException {
        Result<Host> result = service.findByEmail("testingemail@gmail.com");
        assertNotNull(result.getPayload());
        assertEquals(0, result.getErrorMessages().size());
        assertEquals("host_two_id", result.getPayload().getId());
        assertEquals("testingemail@gmail.com", result.getPayload().getEmail());
    }
}