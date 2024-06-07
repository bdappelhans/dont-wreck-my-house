package learn.house.data;

import learn.house.models.Guest;
import learn.house.models.Host;
import learn.house.models.Reservation;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ReservationRepositoryDouble implements ReservationRepository{

    private final Reservation reservation;

    public ReservationRepositoryDouble() {
        Host host = new Host();
        host.setId("host_one_id");

        Guest guest = new Guest();
        guest.setId(1);

        reservation = new Reservation(host, guest, 1, LocalDate.now().plusDays(14), LocalDate.now().plusDays(28), BigDecimal.TEN);
    }

    @Override
    public List<Reservation> findByHostId(String hostId) throws DataException {
        List<Reservation> reservations = new ArrayList<>();

        if (hostId.equals("host_one_id")) {
            reservations.add(reservation);
        }

        return reservations;
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
