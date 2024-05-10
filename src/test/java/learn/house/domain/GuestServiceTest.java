package learn.house.domain;

import learn.house.data.DataException;
import learn.house.data.GuestRepositoryDouble;
import learn.house.models.Guest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GuestServiceTest {

    GuestService service = new GuestService(new GuestRepositoryDouble());

    @Test
    void shouldNotFindNonexistentGuestId() throws DataException {
        Guest result = service.findById(3);
        assertNull(result);
    }

    @Test
    void shouldFindExistingGuestId() throws DataException {
        Guest result = service.findById(2);
        assertNotNull(result);
        assertEquals(2, result.getId());
        assertEquals("anothertestemail@gmail.com", result.getEmail());
    }

    @Test
    void shouldNotFindNonExistentGuestEmail() throws DataException {
        Guest result = service.findByEmail("nonexistentemail@yahoo.com");
        assertNull(result);
    }

    @Test
    void shouldFindExistingEmail() throws DataException {
        Guest result = service.findByEmail("testemail@gmail.com");
        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals("McTest", result.getLastName());
    }

}