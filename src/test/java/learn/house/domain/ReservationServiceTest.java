package learn.house.domain;

import learn.house.data.DataException;
import learn.house.data.GuestRepositoryDouble;
import learn.house.data.HostRepositoryDouble;
import learn.house.data.ReservationRepositoryDouble;
import learn.house.models.Guest;
import learn.house.models.Host;
import learn.house.models.Reservation;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ReservationServiceTest {

    ReservationService service = new ReservationService(new ReservationRepositoryDouble(),
            new GuestRepositoryDouble(), new HostRepositoryDouble());

    private final Host hostOne = new Host("host_one_id", "McTest", "testemail@gmail.com", "(555)-123-4567",
            "123 Test St", "Chicago", "IL", "12345", BigDecimal.ONE, BigDecimal.TEN);

    private final Guest guestOne = new Guest(1, "Test", "McTest",
            "testemail@gmail.com", "(555)-123-4567", "CA");

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

    @Test
    void shouldNotAddPastStartDate() throws DataException {
        Reservation reservation = new Reservation();
        reservation.setHost(new Host());
        reservation.setGuest(new Guest());
        reservation.setStartDate(LocalDate.of(2020, 1, 1));
        reservation.setEndDate(LocalDate.of(2020, 1, 4));

        Result<Reservation> result = service.add(reservation);
        assertFalse(result.isSuccess());
        assertEquals(result.getErrorMessages().get(0), "Start date cannot be in the past");
    }

    @Test
    void shouldNotAddStartDateAfterEndDate() throws DataException {
        Reservation reservation = new Reservation();
        reservation.setHost(new Host());
        reservation.setGuest(new Guest());
        reservation.setStartDate(LocalDate.now().plusDays(4));
        reservation.setEndDate(LocalDate.now());

        Result<Reservation> result = service.add(reservation);
        assertFalse(result.isSuccess());
        assertEquals(result.getErrorMessages().get(0), "Start date must be before end date");
    }

    @Test
    void shouldNotAddEndDateCoincidingWithCurrentRes() throws DataException {
        Reservation reservation = new Reservation();
        reservation.setHost(new Host());
        reservation.getHost().setId("host_one_id");
        reservation.setGuest(new Guest());
        reservation.setStartDate(LocalDate.now().plusDays(5));
        reservation.setEndDate(LocalDate.now().plusDays(20));

        Result<Reservation> result = service.add(reservation);
        assertFalse(result.isSuccess());
        assertEquals(result.getErrorMessages().get(0), "Attempted reservation conflicts with a current reservation");
    }

    @Test
    void shouldAdd() throws DataException {
        Reservation reservation = new Reservation();
        reservation.setHost(hostOne);
        reservation.setGuest(guestOne);
        reservation.setStartDate(LocalDate.now().plusDays(60));
        reservation.setEndDate(LocalDate.now().plusDays(65));

        Result<Reservation> result = service.add(reservation);
        assertTrue(result.isSuccess());
        assertNotNull(result.getPayload());
    }
}