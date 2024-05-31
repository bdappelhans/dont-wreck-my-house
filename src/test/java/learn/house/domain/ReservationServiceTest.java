package learn.house.domain;

import learn.house.data.DataException;
import learn.house.data.GuestRepositoryDouble;
import learn.house.data.HostRepositoryDouble;
import learn.house.data.ReservationRepositoryDouble;
import learn.house.models.Guest;
import learn.house.models.Host;
import learn.house.models.Reservation;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ReservationServiceTest {

    ReservationService service = new ReservationService(new ReservationRepositoryDouble(),
            new GuestRepositoryDouble(), new HostRepositoryDouble());


    @Test
    void shouldReturnNoReservationsByHostEmail() throws DataException {
        Result<List<Reservation>> result = new Result<>();
        Host host = new Host();
        host.setEmail("testingemail@gmail.com");
        host.setId("test_id");

        result = service.findReservationsByHost(host);
        assertFalse(result.isSuccess());
        assertEquals(1, result.getErrorMessages().size());
        assertTrue(result.getErrorMessages().get(0).contains("There are no current reservations for host with email"));
    }

    @Test
    void shouldReturnReservationsByHostEmail() throws DataException {
        Result<List<Reservation>> result = new Result<>();
        Host host = new Host();
        host.setEmail("testemail@gmail.com");
        host.setId("host_one_id");

        result = service.findReservationsByHost(host);
        assertTrue(result.isSuccess());
        assertEquals(1, result.getPayload().size());
        assertEquals("CA", result.getPayload().get(0).getGuest().getState());
    }

    @Test
    void shouldNotAddNullGuest() throws DataException {
        Reservation reservation = new Reservation();
        reservation.setHost(new Host());
        reservation.setStartDate(LocalDate.now().plusDays(1));
        reservation.setEndDate(LocalDate.now().plusDays(4));

        Result<Reservation> result = service.add(reservation);
        assertFalse(result.isSuccess());
        assertEquals(result.getErrorMessages().get(0), "Valid guest required");
    }

    @Test
    void shouldNotAddNullHost() throws DataException {
        Reservation reservation = new Reservation();
        reservation.setGuest(new Guest());
        reservation.setStartDate(LocalDate.now().plusDays(1));
        reservation.setEndDate(LocalDate.now().plusDays(4));

        Result<Reservation> result = service.add(reservation);
        assertFalse(result.isSuccess());
        assertEquals(result.getErrorMessages().get(0), "Valid host required");
    }

    @Test
    void shouldNotAddNullStartDate() throws DataException {
        Reservation reservation = new Reservation();
        reservation.setHost(new Host());
        reservation.setGuest(new Guest());
        reservation.setEndDate(LocalDate.now().plusDays(4));

        Result<Reservation> result = service.add(reservation);
        assertFalse(result.isSuccess());
        assertEquals(result.getErrorMessages().get(0), "Valid start date required");
    }

    @Test
    void shouldNotAddNullEndDate() throws DataException {
        Reservation reservation = new Reservation();
        reservation.setHost(new Host());
        reservation.setGuest(new Guest());
        reservation.setStartDate(LocalDate.now().plusDays(1));

        Result<Reservation> result = service.add(reservation);
        assertFalse(result.isSuccess());
        assertEquals(result.getErrorMessages().get(0), "Valid end date required");
    }
}