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

    public Result<Guest> findById(int id) throws DataException {
        Result<Guest> result = new Result<>();

        Guest guest = repository.findById(id);

        if (guest == null) {
            result.addErrorMessage(String.format("No guest with id '%s' found", id));
        } else {
            result.setPayload(guest);
        }

        return result;
    }

    public Result<Guest> findByEmail(String email) throws DataException {
        Result<Guest> result = new Result<>();

        Guest guest = repository.findByEmail(email);

        if (guest == null) {
            result.addErrorMessage(String.format("No guest with email '%s' found", email));
        } else {
            result.setPayload(guest);
        }

        return result;
    }
}
