package learn.house.ui;

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

    public String getEmail() {
        String email = io.readRequiredString("Enter host email: ");

        // keep prompting for email if input doesn't contain '@'
        while (!email.contains("@")) {
            io.println("Please enter a valid email address.\n");

           email = io.readRequiredString("Enter host email: ");
        }

        return email;
    }
}
