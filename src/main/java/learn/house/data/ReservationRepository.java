package learn.house.data;

import learn.house.models.Reservation;
import java.util.List;

public interface ReservationRepository {

    List<Reservation> findByHostId(String hostId) throws DataException;

    Reservation add(Reservation reservation) throws DataException;

    boolean update(Reservation reservation) throws DataException;

    boolean remove(Reservation reservation) throws DataException;
}
