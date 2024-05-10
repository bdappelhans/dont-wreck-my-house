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

        return repository.findById(id);
    }

    public Guest findByEmail(String email) throws DataException {

        return repository.findByEmail(email);
    }
}
