package learn.house.ui;

import learn.house.data.DataException;
import learn.house.domain.*;
import learn.house.models.Guest;
import learn.house.models.Host;
import learn.house.models.Reservation;

import java.util.List;

public class Controller {

    private final GuestService guestService;
    private final HostService hostService;
    private final ReservationService reservationService;
    private final View view;

    public Controller(GuestService guestService, HostService hostService, ReservationService reservationService, View view) {
        this.guestService = guestService;
        this.hostService = hostService;
        this.reservationService = reservationService;
        this.view = view;
    }

    public void run() {
        view.displayHeader("Welcome to Don't Wreck My House");
        try {
            runAppLoop();
        } catch (DataException ex) {
            view.displayException(ex);
        }
        view.displayHeader("Goodbye.");
    }

    private void runAppLoop() throws DataException {
        MainMenuOption option;
        do {
            option = view.selectMainMenuOption();
            switch (option) {
                case VIEW_RESERVATIONS_BY_HOST:
                    viewReservationsByHost();
                    break;
                case MAKE_RESERVATION:
                    makeReservation();
                    break;
                case EDIT_RESERVATION:
                    editReservation();
                    break;
                case CANCEL_RESERVATION:
                    cancelReservation();
                    break;
            }
        } while (option != MainMenuOption.EXIT);
    }

    private void viewReservationsByHost() throws DataException {
        view.displayHeader("View Reservations By Host");

        String hostEmail = view.getEmail("host");
        Result<Host> hostResult = hostService.findByEmail(hostEmail);

        if (!hostResult.isSuccess()) {
            view.displayError(hostResult);
            return;
        }

        Host host = hostResult.getPayload();

        Result<List<Reservation>> reservationResult = reservationService.findReservationsByHost(host);

        view.displayHeader(String.format("All Reservations for %s / %s: %s, %s", host.getEmail(), host.getLastName(),
                host.getCity(), host.getState()));
        view.displayReservations(reservationResult);
    }

    private void makeReservation() throws DataException {
        view.displayHeader("Make a Reservation");

        String hostEmail = view.getEmail("host");
        Result<Host> hostResult = hostService.findByEmail(hostEmail);

        if (!hostResult.isSuccess()) {
            view.displayError(hostResult);
            return;
        }

        Host host = hostResult.getPayload();
        Result<List<Reservation>> currentReservationsResult = reservationService.findCurrentAndFutureReservationsByHost(host);

        view.displayHeader(String.format("Current and Future Reservations for %s / %s: %s, %s", host.getEmail(),
                host.getLastName(), host.getCity(), host.getState()));

        view.displayReservations(currentReservationsResult);
        System.out.println();

        String guestEmail = view.getEmail("guest");
        Result<Guest> guestResult = guestService.findByEmail(guestEmail);

        if (!guestResult.isSuccess()) {
            view.displayError(guestResult);
            return;
        }

        Guest guest = guestResult.getPayload();

        Reservation reservation = view.makeReservation(host, guest);
        Result<Reservation> result = reservationService.add(reservation);

        view.displayReservation(result, "added");
    }

    private void editReservation() throws DataException {
        view.displayHeader("Edit a Reservation");

        String hostEmail = view.getEmail("host");
        Result<Host> hostResult = hostService.findByEmail(hostEmail);

        if (!hostResult.isSuccess()) {
            view.displayError(hostResult);
            return;
        }

        Host host = hostResult.getPayload();
        Result<List<Reservation>> currentReservationsResult = reservationService.findCurrentAndFutureReservationsByHost(host);

        view.displayHeader(String.format("Current and Future Reservations for %s / %s: %s, %s", host.getEmail(),
                host.getLastName(), host.getCity(), host.getState()));

        view.displayReservations(currentReservationsResult);
        System.out.println();


    }

    private void cancelReservation() throws DataException {
        view.displayHeader("Cancel a Reservation");

        String hostEmail = view.getEmail("host");
        Result<Host> hostResult = hostService.findByEmail(hostEmail);

        if (!hostResult.isSuccess()) {
            view.displayError(hostResult);
            return;
        }

        Host host = hostResult.getPayload();
        Result<List<Reservation>> futureReservationsResult = reservationService.findFutureReservationsByHost(host);
        List<Reservation> reservations = futureReservationsResult.getPayload();

        view.displayHeader(String.format("Future Reservations for %s / %s: %s, %s", host.getEmail(),
                host.getLastName(), host.getCity(), host.getState()));
        view.displayReservations(futureReservationsResult);

        // if there are no reservations on file to cancel, return from method
        if (reservations.size() == 0) {
            return;
        }

        int reservationId = view.getReservationId();
        Reservation cancelReservation = null;

        // search for reservation ID input inside host's list of future reservations
        for (Reservation reservation : reservations) {
            // if reservation found, assign it to cancelReservation variable
            if (reservation.getId() == reservationId) {
                cancelReservation = reservation;
            }
        }

        // if reservation not found, print error message and return to main method
        if (cancelReservation == null) {
            view.printError("Reservation ID " + reservationId + " not found in host's future reservations list");
            return;
        }

        view.displayReservation(cancelReservation);

        // if user doesn't confirm delete, return to main method
        if (!view.confirm("delete")) {
            return;
        }

        Result<Reservation> cancelResult = reservationService.cancel(cancelReservation);
        view.displayReservation(cancelResult, "canceled");

    }
}
