package learn.house.data;

import learn.house.models.Guest;
import learn.house.models.Host;
import learn.house.models.Reservation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ReservationFileRepositoryTest {

    static final String TEST_DIRECTORY = "./data/reservations_test";
    static final String SEED_PATH = "./data/reservations_test/reservations-seed.csv";
    static final String TEST_PATH = "./data/reservations_test/9d469342-ad0b-4f5a-8d28-e81e690ba29a.csv";

    ReservationFileRepository repository = new ReservationFileRepository(TEST_DIRECTORY);

    @BeforeEach
    void setup() throws IOException {
        Path seedPath = Paths.get(SEED_PATH);
        Path testPath = Paths.get(TEST_PATH);
        Files.copy(seedPath, testPath, StandardCopyOption.REPLACE_EXISTING);
    }

    @Test
    void shouldNotFindNonexistentHostFile() throws DataException {
        List<Reservation> reservations = repository.findByHostId("Nonexistent Host Id");
        assertNull(reservations);
    }

    @Test
    void shouldNotFindByHostId() throws DataException {
        List<Reservation> reservations = repository.findByHostId("9d469342-ad0b-4f5a-8d28-e81e690ba29a");

        assertNotNull(reservations);
        assertEquals(1, reservations.size());
        assertEquals(1, reservations.get(0).getId());
    }

    @Test
    void shouldAdd() throws DataException {
        Reservation reservation = new Reservation();

        Host host = new Host();
        host.setId("9d469342-ad0b-4f5a-8d28-e81e690ba29a");
        host.setStandardRate(BigDecimal.ONE);
        host.setWeekendRate(BigDecimal.TEN);

        Guest guest = new Guest();
        guest.setId(30);

        reservation.setId(0);
        reservation.setHost(host);
        reservation.setGuest(guest);
        reservation.setStartDate(LocalDate.of(2024, 5, 31));
        reservation.setEndDate(LocalDate.of(2024, 6, 4));

        Reservation actual = repository.add(reservation);
        assertEquals(2, actual.getId());
        assertEquals(BigDecimal.valueOf(22), actual.getTotal());

        List<Reservation> reservations = repository.findByHostId("9d469342-ad0b-4f5a-8d28-e81e690ba29a");
        assertEquals(2, reservations.size());
    }

    @Test
    void shouldNotUpdate() throws DataException {
        Reservation reservation = new Reservation();

        Host host = new Host();
        host.setId("9d469342-ad0b-4f5a-8d28-e81e690ba29a");

        Guest guest = new Guest();
        guest.setId(30);

        reservation.setId(25);
        reservation.setHost(host);
        reservation.setGuest(guest);
        reservation.setStartDate(LocalDate.now());
        reservation.setEndDate(LocalDate.now().plusDays(2));
        reservation.setTotal(BigDecimal.TEN);

        boolean successfullyUpdated = repository.update(reservation);
        assertFalse(successfullyUpdated);
    }

    @Test
    void shouldUpdate() throws DataException {
        Reservation reservation = new Reservation();

        Host host = new Host();
        host.setId("9d469342-ad0b-4f5a-8d28-e81e690ba29a");

        Guest guest = new Guest();
        guest.setId(30);

        reservation.setId(1);
        reservation.setHost(host);
        reservation.setGuest(guest);
        reservation.setStartDate(LocalDate.now());
        reservation.setEndDate(LocalDate.now().plusDays(2));
        reservation.setTotal(BigDecimal.TEN);

        boolean successfullyUpdated = repository.update(reservation);
        assertTrue(successfullyUpdated);

        List<Reservation> reservations = repository.findByHostId("9d469342-ad0b-4f5a-8d28-e81e690ba29a");
        assertEquals(30, reservations.get(0).getGuest().getId());
    }

    @Test
    void shouldNotRemove() throws DataException {
        Reservation reservation = new Reservation();

        Host host = new Host();
        host.setId("9d469342-ad0b-4f5a-8d28-e81e690ba29a");

        reservation.setId(25);
        reservation.setHost(host);

        boolean successfullyRemoved = repository.remove(reservation);
        assertFalse(successfullyRemoved);

        List<Reservation> reservations = repository.findByHostId("9d469342-ad0b-4f5a-8d28-e81e690ba29a");
        assertEquals(1, reservations.size());
    }

    @Test
    void shouldRemove() throws DataException {
        Reservation reservation = new Reservation();

        Host host = new Host();
        host.setId("9d469342-ad0b-4f5a-8d28-e81e690ba29a");

        reservation.setId(1);
        reservation.setHost(host);

        boolean successfullyRemoved = repository.remove(reservation);
        assertTrue(successfullyRemoved);

        List<Reservation> reservations = repository.findByHostId("9d469342-ad0b-4f5a-8d28-e81e690ba29a");
        assertEquals(0, reservations.size());
    }
}