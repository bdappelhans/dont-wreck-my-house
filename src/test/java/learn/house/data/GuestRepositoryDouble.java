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
}
