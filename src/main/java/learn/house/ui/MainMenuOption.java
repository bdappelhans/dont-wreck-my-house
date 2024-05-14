package learn.house.ui;

public enum MainMenuOption {
    EXIT(0, "Exit"),
    VIEW_RESERVATIONS_BY_HOST(1, "View Reservations By Host"),
    MAKE_RESERVATION(2, "Make Reservation"),
    EDIT_RESERVATION(3, "Edit Reservation"),
    CANCEL_RESERVATION(4, "Cancel Reservation");

    private int value;
    private String message;

    MainMenuOption(int value, String message) {
        this.value = value;
        this.message = message;
    }

    public static MainMenuOption fromValue(int value) {
        for (MainMenuOption option : MainMenuOption.values()) {
            if (option.getValue() == value) {
                return option;
            }
        }
        return EXIT;
    }

    public int getValue() {
        return value;
    }

    public String getMessage() {
        return message;
    }
}
