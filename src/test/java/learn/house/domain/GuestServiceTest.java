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
        Result<Guest> result = service.findById(3);
        assertNull(result.getPayload());
        assertFalse(result.isSuccess());
    }

    @Test
    void shouldFindExistingGuestId() throws DataException {
        Result<Guest> result = service.findById(2);
        assertNotNull(result.getPayload());
        assertTrue(result.isSuccess());
        assertEquals(2, result.getPayload().getId());
        assertEquals("anothertestemail@gmail.com", result.getPayload().getEmail());
    }

    @Test
    void shouldNotFindNonExistentGuestEmail() throws DataException {
        Result<Guest> result = service.findByEmail("nonexistentemail@yahoo.com");
        assertNull(result.getPayload());
        assertFalse(result.isSuccess());
    }

    @Test
    void shouldFindExistingEmail() throws DataException {
        Result<Guest> result = service.findByEmail("testemail@gmail.com");
        assertNotNull(result.getPayload());
        assertEquals(1, result.getPayload().getId());
        assertEquals("McTest", result.getPayload().getLastName());
    }

}