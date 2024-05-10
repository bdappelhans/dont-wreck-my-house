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

    @Override
    public Host findById(String id) throws DataException {
        if (id.equals(hostOne.getId())) {
            return hostOne;
        } else if (id.equals(hostTwo.getId())) {
            return hostTwo;
        } else {
            return null;
        }
    }

    @Override
    public Host findByEmail(String email) throws DataException {
        if (email.equals(hostOne.getEmail())) {
            return hostOne;
        } else if (email.equals(hostTwo.getEmail())) {
            return hostTwo;
        } else {
            return null;
        }
    }
}
