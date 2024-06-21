package learn.house.domain;

import learn.house.data.DataException;
import learn.house.data.GuestRepository;
import learn.house.data.HostRepository;
import learn.house.data.ReservationRepository;
import learn.house.models.Guest;
import learn.house.models.Host;
import learn.house.models.Reservation;

import java.time.LocalDate;
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

    public Result<List<Reservation>> findCurrentAndFutureReservationsByHost(Host host) throws DataException {
        Result<List<Reservation>> result = findReservationsByHost(host);

        // if result is initially unsuccessful return it
        if (!result.isSuccess()) {
            return result;
        }

        List<Reservation> currentAndFutureReservations = new ArrayList<>();
        List<Reservation> reservations = result.getPayload();

        // loop through all reservations list
        for (Reservation r : reservations) {
            // if reservation's end date is after current date, add the reservation to the current and future reservation list
            if (r.getEndDate().isAfter(LocalDate.now())) {
                currentAndFutureReservations.add(r);
            }
        }

        // replace old result payload with updated filtered list and return result
        result.setPayload(currentAndFutureReservations);

        return result;
    }

    public Result<Reservation> add(Reservation reservation) throws DataException {
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

        if (!result.isSuccess()) {
            return result;
        }

        // check that start date is not in the past
        if (reservation.getStartDate().isBefore(LocalDate.now())) {
            result.addErrorMessage("Start date cannot be in the past");
        }

        // check that start date is before end date
        if (reservation.getEndDate().isBefore(reservation.getStartDate())) {
            result.addErrorMessage("Start date must be before end date");
        }

        if (!result.isSuccess()) {
            return result;
        }

        // check that reservation doesn't overlap with host's other reservations
        List<Reservation> reservations = reservationRepository.findByHostId(reservation.getHost().getId());

        if (reservations != null && reservations.size() != 0) {
            // iterate through reservations list and check them against reservation we're attempting to add
            for (Reservation r : reservations) { // attempted reservation start date is same as a current reservation start date
                // or attempted reservation end date is the same as a current res end date
                if (reservation.getStartDate().isEqual(r.getStartDate()) || reservation.getEndDate().isEqual(r.getEndDate())) {
                    result.addErrorMessage("Attempted reservation conflicts with a current reservation");

                    // attempted reservation start date falls between current reservation start and end date
                } else if (reservation.getStartDate().isAfter(r.getStartDate()) && reservation.getStartDate().isBefore(r.getEndDate())) {
                    result.addErrorMessage("Attempted reservation conflicts with a current reservation");

                    // attempted reservation end date falls between current reservation start and end date
                } else if (reservation.getEndDate().isAfter(r.getStartDate()) && reservation.getEndDate().isBefore(r.getEndDate())) {
                    result.addErrorMessage("Attempted reservation conflicts with a current reservation");
                }
            }
        }

        // if no issues, add reservation to repository and to result payload
        if (result.isSuccess()) {
            Reservation addedReservation = reservationRepository.add(reservation);
            result.setPayload(addedReservation);
        }

        return result;
    }
}
