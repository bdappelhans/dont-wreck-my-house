package learn.house.data;

import learn.house.models.Guest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GuestFileRepositoryTest {

    static final String SEED_PATH = "./data/guests_test/guests-seed.csv";
    static final String TEST_PATH = "./data/guests_test/guests-test.csv";

    GuestFileRepository repository = new GuestFileRepository(TEST_PATH);

    @BeforeEach
    void setup() throws IOException {
        Path seedPath = Paths.get(SEED_PATH);
        Path testPath = Paths.get(TEST_PATH);
        Files.copy(seedPath, testPath, StandardCopyOption.REPLACE_EXISTING);
    }

    @Test
    void shouldFindAll() throws DataException {
        List<Guest> all = repository.findAll();
        assertEquals(15, all.size());
    }

    @Test
    void shouldNotFindNonexistentId() throws DataException {
        Guest result = repository.findById(100);

        assertNull(result);
    }

    @Test
    void shouldFindById() throws DataException {
        Guest result = repository.findById(13);

        assertNotNull(result);
        assertEquals(13, result.getId());
        assertEquals("amountc@ehow.com", result.getEmail());
    }

    @Test
    void shouldNotFindNonExistentGuestEmail() throws DataException {
        Guest result = repository.findByEmail("nonexistentemail@yahoo.com");
        assertNull(result);
    }

    @Test
    void shouldFindByEmail() throws DataException {
        Guest result = repository.findByEmail("amountc@ehow.com");

        assertNotNull(result);
        assertEquals(13, result.getId());
        assertEquals("Mount", result.getLastName());
    }
}