package learn.house.domain;

import learn.house.data.DataException;
import learn.house.data.GuestRepository;
import learn.house.models.Guest;

import java.util.List;
import java.util.stream.Collectors;

public class GuestService {

    private final GuestRepository repository;

    public GuestService(GuestRepository repository) {
        this.repository = repository;
    }

    public Guest findById(int id) throws DataException {
        List<Guest> guests = repository.findAll().stream()
                .filter(g -> g.getId() == id)
                .toList();

        if (guests.size() == 0) {
            return null;
        } else {
            return guests.get(0);
        }
    }

    public Guest findByEmail(String email) throws DataException {
        List<Guest> guests = repository.findAll().stream()
                .filter(g -> g.getEmail().equals(email))
                .toList();

        if (guests.size() == 0) {
            return null;
        } else {
            return guests.get(0);
        }
    }
}
