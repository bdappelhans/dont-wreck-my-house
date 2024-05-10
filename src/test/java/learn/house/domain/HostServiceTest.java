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
        Host result = service.findById("Nonexistent Id");
        assertNull(result);
    }

    @Test
    void shouldFindExistingHostId() throws DataException {
        Host result = service.findById("host_one_id");
        assertNotNull(result);
        assertEquals("host_one_id", result.getId());
        assertEquals("Chicago", result.getCity());
    }

    @Test
    void shouldNotFindNonexistentHostEmail() throws DataException {
        Host result = service.findByEmail("nonexistentemail@gmail.com");
        assertNull(result);
    }

    @Test
    void shouldFindExistingHostEmail() throws DataException {
        Host result = service.findByEmail("testingemail@gmail.com");
        assertNotNull(result);
        assertEquals("host_two_id", result.getId());
        assertEquals("testingemail@gmail.com", result.getEmail());
    }
}