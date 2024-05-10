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
        List<Host> hosts = repository.findAll().stream()
                .filter(h -> h.getId().equals(id))
                .toList();

        if (hosts.size() == 0) {
            return null;
        } else {
            return hosts.get(0);
        }
    }

    public Host findByEmail(String email) throws DataException {
        List<Host> hosts = repository.findAll().stream()
                .filter(h -> h.getEmail().equals(email))
                .toList();

        if (hosts.size() == 0) {
            return null;
        } else {
            return hosts.get(0);
        }
    }
}
