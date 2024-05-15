package learn.house.domain;

import learn.house.data.DataException;
import learn.house.data.GuestRepositoryDouble;
import learn.house.data.HostRepositoryDouble;
import learn.house.data.ReservationRepositoryDouble;
import learn.house.models.Reservation;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ReservationServiceTest {

    ReservationService service = new ReservationService(new ReservationRepositoryDouble(),
            new GuestRepositoryDouble(), new HostRepositoryDouble());


    @Test
    void shouldNotFindNonexistentHostEmail() throws DataException {
        Result<List<Reservation>> result = new Result<>();

        result = service.findReservationsByHostEmail("nonexistentemail@gmail.com");
        assertFalse(result.isSuccess());
        assertEquals(1, result.getErrorMessages().size());
        assertTrue(result.getErrorMessages().get(0).contains("No host with email"));
    }

    @Test
    void shouldReturnNoReservationsByHostEmail() throws DataException {
        Result<List<Reservation>> result = new Result<>();

        result = service.findReservationsByHostEmail("testingemail@gmail.com");
        assertFalse(result.isSuccess());
        assertEquals(1, result.getErrorMessages().size());
        assertTrue(result.getErrorMessages().get(0).contains("There are no current reservations for host with email"));
    }

    @Test
    void shouldReturnReservationsByHostEmail() throws DataException {
        Result<List<Reservation>> result = new Result<>();

        result = service.findReservationsByHostEmail("testemail@gmail.com");
        assertTrue(result.isSuccess());
        assertEquals(1, result.getPayload().size());
        assertEquals("IL", result.getPayload().get(0).getHost().getState());
        assertEquals("CA", result.getPayload().get(0).getGuest().getState());
    }

}