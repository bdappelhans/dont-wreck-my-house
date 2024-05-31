package learn.house.ui;

import learn.house.domain.Result;
import learn.house.models.Guest;
import learn.house.models.Host;
import learn.house.models.Reservation;

import java.util.List;

public class View {

    private final ConsoleIO io;

    // constructor
    public View(ConsoleIO io) {
        this.io = io;
    }

    // displays main menu, returns MainMenuOption selection
    public MainMenuOption selectMainMenuOption() {
        displayHeader("Main Menu");
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        for (MainMenuOption option : MainMenuOption.values()) {
            io.printf("%s. %s%n", option.getValue(), option.getMessage());
            min = Math.min(min, option.getValue());
            max = Math.max(max, option.getValue() + 1);
        }

        String message = String.format("Select [%s-%s]: ", min, max - 1);
        return MainMenuOption.fromValue(io.readInt(message, min, max));
    }

    // display only
    public void displayHeader(String message) {
        io.println("");
        io.println(message);
        io.println("=".repeat(message.length()));
    }

    public void displayException(Exception ex) {
        displayHeader("A critical error occurred:");
        io.println(ex.getMessage());
    }

    public void displayError(Result result) {
        for (String message : result.getErrorMessages()) {
            io.println("[ERROR]");
            io.println(message);
        }
    }

    public void displayReservations(Result<List<Reservation>> result) {
        if (!result.isSuccess()) { // print error messages if unsuccessful
            for (String message : result.getErrorMessages()) {
                io.println("[ERROR]");
                io.println(message);
            }
        } else { // print reservations if successful
            for (Reservation reservation : result.getPayload()) {
                io.println(String.format("ID: %s, Dates: %s - %s, Guest: %s %s (%s)",
                        reservation.getId(),
                        reservation.getStartDate().toString(),
                        reservation.getEndDate().toString(),
                        reservation.getGuest().getFirstName(),
                        reservation.getGuest().getLastName(),
                        reservation.getGuest().getEmail()));
            }
        }
    }

    public String getEmail(String emailType) {
        String email = io.readRequiredString(String.format("Enter %s email: ", emailType));

        // keep prompting for email if input doesn't contain '@'
        while (!email.contains("@")) {
            io.println("Please enter a valid email address.\n");

           email = io.readRequiredString(String.format("Enter %s email: ", emailType));
        }

        return email;
    }

    public Reservation makeReservation(Host host, Guest guest) {
        return null;
    }
}
