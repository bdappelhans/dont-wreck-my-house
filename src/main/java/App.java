import learn.house.data.*;
import learn.house.domain.GuestService;
import learn.house.domain.HostService;
import learn.house.domain.ReservationService;
import learn.house.ui.ConsoleIO;
import learn.house.ui.Controller;
import learn.house.ui.View;

public class App {
    public static void main(String[] args) {

        // repositories
        GuestRepository guestRepository = new GuestFileRepository("./data/guests.csv");
        HostRepository hostRepository = new HostFileRepository("./data/hosts.csv");
        ReservationRepository reservationRepository = new ReservationFileRepository("./data/reservations");

        // services
        GuestService guestService = new GuestService(guestRepository);
        HostService hostService = new HostService(hostRepository);
        ReservationService reservationService = new ReservationService(reservationRepository, guestRepository, hostRepository);

        // view
        View view = new View(new ConsoleIO());

        // controller
        Controller controller = new Controller(guestService, hostService, reservationService, view);

        controller.run();
    }
}
