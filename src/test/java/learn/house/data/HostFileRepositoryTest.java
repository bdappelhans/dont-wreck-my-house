package learn.house.data;

import learn.house.models.Host;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HostFileRepositoryTest {

    static final String SEED_PATH = "./data/hosts_test/hosts-seed.csv";
    static final String TEST_PATH = "./data/hosts_test/hosts-test.csv";

    HostFileRepository repository = new HostFileRepository(TEST_PATH);

    @BeforeEach
    void setup() throws IOException {
        Path seedPath = Paths.get(SEED_PATH);
        Path testPath = Paths.get(TEST_PATH);
        Files.copy(seedPath, testPath, StandardCopyOption.REPLACE_EXISTING);
    }

    @Test
    void shouldFindAll() throws DataException {
        List<Host> all = repository.findAll();
        assertEquals(15, all.size());
    }

    @Test
    void shouldNotFindNonexistentHostId() throws DataException {
        Host result = repository.findById("Nonexistent Id");

        assertNull(result);
    }

    @Test
    void shouldFindExistingHostId() throws DataException {
        Host result = repository.findById("d63304e3-de36-4ecc-8f8f-847431ffff64");

        assertNotNull(result);
        assertEquals("ezuppa8@yale.edu", result.getEmail());
        assertEquals("(915) 4423313", result.getPhoneNumber());
    }

    @Test
    void shouldNotFindNonexistentHostEmail() throws DataException {
        Host result = repository.findByEmail("nonexistentemail@gmail.com");

        assertNull(result);
    }

    @Test
    void shouldFindExistingHostEmail() throws DataException {
        Host result = repository.findByEmail("ezuppa8@yale.edu");

        assertNotNull(result);
        assertEquals("d63304e3-de36-4ecc-8f8f-847431ffff64", result.getId());
        assertEquals("(915) 4423313", result.getPhoneNumber());
    }
}