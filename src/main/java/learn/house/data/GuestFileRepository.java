package learn.house.data;

import learn.house.models.Guest;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GuestFileRepository implements GuestRepository {

    private final String filePath;

    public GuestFileRepository(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public List<Guest> findAll() throws DataException {
        List<Guest> all = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {

            reader.readLine(); // read header

            for (String line = reader.readLine(); line != null; line = reader.readLine()) {

                String[] fields = line.split(",", -1);
                if (fields.length == 6) {
                    all.add(deserialize(fields));
                }
            }
        } catch (IOException ex) {
            // don't throw on read
        }

        return all;
    }

    @Override
    public Guest findById(int id) throws DataException {
        List<Guest> guests = findAll().stream()
                .filter(g -> g.getId() == id)
                .toList();

        if (guests.size() == 0) {
            return null;
        } else {
            return guests.get(0);
        }
    }

    @Override
    public Guest findByEmail(String email) throws DataException {
        List<Guest> guests = findAll().stream()
                .filter(g -> g.getEmail().equals(email))
                .toList();

        if (guests.size() == 0) {
            return null;
        } else {
            return guests.get(0);
        }
    }

    private Guest deserialize(String[] fields) {
        Guest result = new Guest();
        result.setId(Integer.parseInt(fields[0]));
        result.setFirstName(fields[1]);
        result.setLastName(fields[2]);
        result.setEmail(fields[3]);
        result.setPhoneNumber(fields[4]);
        result.setState(fields[5]);

        return result;
    }
}
