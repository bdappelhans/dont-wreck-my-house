package learn.house.data;

import learn.house.models.Guest;

import java.util.ArrayList;
import java.util.List;

public class GuestRepositoryDouble implements GuestRepository {

    private final List<Guest> guests = new ArrayList<>();

    private final Guest guestOne = new Guest(1, "Test", "McTest",
            "testemail@gmail.com", "(555)-123-4567", "CA");
    private final Guest guestTwo = new Guest(2, "Testing", "McTester",
            "anothertestemail@gmail.com", "(555)-456-1237", "NY");

    public GuestRepositoryDouble() {
        guests.add(guestOne);
        guests.add(guestTwo);
    }

    @Override
    public List<Guest> findAll() throws DataException {
        return guests;
    }

    @Override
    public Guest findById(int id) throws DataException {
        if (id == 1) {
            return guestOne;
        } else if (id == 2) {
            return guestTwo;
        } else {
            return null;
        }
    }

    @Override
    public Guest findByEmail(String email) throws DataException {
        if (email.equals(guestOne.getEmail())) {
            return guestOne;
        } else if (email.equals(guestTwo.getEmail())) {
            return guestTwo;
        } else {
            return null;
        }
    }
}
