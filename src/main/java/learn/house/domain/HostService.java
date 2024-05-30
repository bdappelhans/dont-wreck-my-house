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

    public Result<Host> findById(String id) throws DataException {
        Result<Host> result = new Result<>();

        Host host = repository.findById(id);

        if (host == null) {
            result.addErrorMessage(String.format("No host with id %s was found", id));
        } else {
            result.setPayload(host);
        }

        return result;
    }

    public Host findByEmail(String email) throws DataException {
        return repository.findByEmail(email);
    }
}
