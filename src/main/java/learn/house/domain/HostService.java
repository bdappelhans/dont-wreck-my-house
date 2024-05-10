package learn.house.domain;

import learn.house.data.DataException;
import learn.house.data.HostRepository;
import learn.house.models.Guest;
import learn.house.models.Host;

import java.util.List;

public class HostService {

    private final HostRepository repository;

    public HostService(HostRepository repository) {
        this.repository = repository;
    }

    public Host findById(String id) throws DataException {
        return repository.findById(id);
    }

    public Host findByEmail(String email) throws DataException {
        return repository.findByEmail(email);
    }
}
