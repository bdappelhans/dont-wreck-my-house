package learn.house.domain;

import learn.house.data.DataException;
import learn.house.data.ReservationRepository;
import learn.house.models.Guest;
import learn.house.models.Host;
import learn.house.models.Reservation;

import java.util.ArrayList;
import java.util.List;

public class ReservationService {

    private final ReservationRepository reservationRepository;

    private final GuestService guestService;

    private final HostService hostService;

    public ReservationService(ReservationRepository reservationRepository, GuestService guestService, HostService hostService) {
        this.reservationRepository = reservationRepository;
        this.guestService = guestService;
        this.hostService = hostService;
    }

    public List<Reservation> findReservationsByHostEmail(String email) throws DataException {
        String hostId = hostService.findByEmail(email).getId();

        List<Reservation> reservations = new ArrayList<>();
        reservations = reservationRepository.findByHostId(hostId);

        if (reservations.size() == 0) { // return null if list is empty/no reservations found for host
            return null;
        }

        // loop through list and assign guest and host to each reservation
        for (Reservation r : reservations) {
            Host host = new Host();
            Guest guest = new Guest();

            host = hostService.findById(r.getHost().getId());
            guest = guestService.findById(r.getGuest().getId());

            r.setHost(host);
            r.setGuest(guest);
        }

        return reservations;
    }
}
