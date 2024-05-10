package learn.house.data;

import learn.house.models.Host;

import java.util.List;

public interface HostRepository {

    List<Host> findAll() throws DataException;

    Host findById(String id) throws DataException;

    Host findByEmail(String email) throws DataException;
}
