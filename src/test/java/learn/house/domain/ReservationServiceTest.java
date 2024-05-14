package learn.house.domain;

import learn.house.data.GuestRepositoryDouble;
import learn.house.data.HostRepositoryDouble;
import learn.house.data.ReservationRepositoryDouble;

import static org.junit.jupiter.api.Assertions.*;

class ReservationServiceTest {

    ReservationService service = new ReservationService(new ReservationRepositoryDouble(),
            new GuestRepositoryDouble(), new HostRepositoryDouble());


}