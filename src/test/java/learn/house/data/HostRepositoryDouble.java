package learn.house.data;

import learn.house.models.Host;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class HostRepositoryDouble implements HostRepository {

    private final List<Host> hosts = new ArrayList<>();

    private final Host hostOne = new Host("host_one_id", "McTest", "testemail@gmail.com", "(555)-123-4567",
            "123 Test St", "Chicago", "IL", "12345", BigDecimal.ONE, BigDecimal.TEN);

    private final Host hostTwo = new Host("host_two_id", "McTest", "testingemail@gmail.com", "(555)-456-1237",
            "123 Test Blvd", "New York", "NY", "12345", BigDecimal.ONE, BigDecimal.TEN);

    public HostRepositoryDouble() {
        hosts.add(hostOne);
        hosts.add(hostTwo);

    }

    @Override
    public List<Host> findAll() throws DataException {
        return hosts;
    }
}
