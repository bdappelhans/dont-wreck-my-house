package learn.house.data;

import learn.house.models.Reservation;

import java.util.List;

public class ReservationRepositoryDouble implements ReservationRepository{

    private final Reservation reservation = new Reservation();

    @Override
    public List<Reservation> findByHostId(String hostId) throws DataException {
        return List.of();
    }

    @Override
    public Reservation add(Reservation reservation) throws DataException {
        return null;
    }

    @Override
    public boolean update(Reservation reservation) throws DataException {
        return false;
    }

    @Override
    public boolean remove(Reservation reservation) throws DataException {
        return false;
    }
}
