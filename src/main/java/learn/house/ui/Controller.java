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

        view.displayHeader(String.format("Reservations for %s", host.getEmail()));
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

        String guestEmail = view.getEmail("guest");
        //Result<Guest>
    }

    private void editReservation() {

    }

    private void cancelReservation() {

    }
}
