package learn.house.domain;

import learn.house.data.DataException;
import learn.house.data.GuestRepository;
import learn.house.data.HostRepository;
import learn.house.data.ReservationRepository;
import learn.house.models.Guest;
import learn.house.models.Host;
import learn.house.models.Reservation;

import java.util.ArrayList;
import java.util.List;

public class ReservationService {

    private final ReservationRepository reservationRepository;

    private final GuestRepository guestRepository;

    private final HostRepository hostRepository;

    public ReservationService(ReservationRepository reservationRepository, GuestRepository guestRepository, HostRepository hostRepository) {
        this.reservationRepository = reservationRepository;
        this.guestRepository = guestRepository;
        this.hostRepository = hostRepository;
    }

    public Result<List<Reservation>> findReservationsByHost(Host host) throws DataException {
        List<Reservation> reservations = new ArrayList<>();
        Result<List<Reservation>> result = new Result<>();

        if (host == null) { // return unsuccessful result if host can't be found
            result.addErrorMessage("No host found");
            return result;
        }

        reservations = reservationRepository.findByHostId(host.getId());

        if (reservations == null) { // return unsuccessful result if list is empty/no reservations found for host
            result.addErrorMessage(String.format("There are no current reservations for host with email '%s'", host.getEmail()));
            return result;
        }

        // loop through list and assign guest and host to each reservation
        for (Reservation r : reservations) {
            Guest guest = guestRepository.findById(r.getGuest().getId());

            r.setHost(host);
            r.setGuest(guest);
        }

        result.setPayload(reservations);

        return result;
    }

    Result<Reservation> add(Reservation reservation) throws DataException {
        Result<Reservation> result = new Result<>();

        // check to make sure guest, host, and dates aren't null
        if (reservation.getGuest() == null) {
            result.addErrorMessage("Valid guest required");
        }

        if (reservation.getHost() == null) {
            result.addErrorMessage("Valid host required");
        }

        if (reservation.getStartDate() == null) {
            result.addErrorMessage("Valid start date required");
        }

        if (reservation.getEndDate() == null) {
            result.addErrorMessage("Valid end date required");
        }

        return result;
    }
}
