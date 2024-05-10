package learn.house.data;

import learn.house.models.Guest;
import learn.house.models.Host;
import learn.house.models.Reservation;

import java.io.*;
import java.math.BigDecimal;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ReservationFileRepository implements ReservationRepository {

    private final String directory;

    private final String HEADER = "id,start_date,end_date,guest_id,total";

    public ReservationFileRepository(String directory) {
        this.directory = directory;
    }

    @Override
    public List<Reservation> findByHostId(String hostId) throws DataException {
        List<Reservation> result = new ArrayList<>();
        String filePath = getFilePath(hostId);
        File f = new File(filePath);
        // if file doesn't exist in directory, return null
        if (!f.exists()) {
            return null;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            reader.readLine(); // read header

            for (String line = reader.readLine(); line != null; line = reader.readLine()) {
                String[] fields = line.split(",", -1);
                if (fields.length == 5) {
                    result.add(deserialize(fields, hostId));
                }
            }
        } catch (IOException ex) {
            // don't throw on read
        }

        return result;
    }

    @Override
    public Reservation add(Reservation reservation) throws DataException {
        List<Reservation> reservations = findByHostId(reservation.getHost().getId());
        // find next reservation ID to be created
        int nextId = reservations.stream()
                .mapToInt(Reservation::getId)
                .max()
                .orElse(0) + 1;

        reservation.setId(nextId);

        reservations.add(reservation);
        writeAll(reservations, reservation.getHost().getId());

        return reservation;
    }

    @Override
    public boolean update(Reservation reservation) throws DataException {
        List<Reservation> reservations = findByHostId(reservation.getHost().getId());

        for (int i = 0; i < reservations.size(); i++) {
            if (reservation.getId() == reservations.get(i).getId()) {
                reservations.set(i, reservation);
                writeAll(reservations, reservation.getHost().getId());
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean remove(Reservation reservation) throws DataException {
        List<Reservation> reservations = findByHostId(reservation.getHost().getId());

        for (int i = 0; i < reservations.size(); i++) {
            if (reservation.getId() == reservations.get(i).getId()) {
                reservations.remove(i);
                writeAll(reservations, reservation.getHost().getId());
                return true;
            }
        }

        return false;
    }

    private String serialize(Reservation reservation) {
        return String.format("%s,%s,%s,%s,%s",
                reservation.getId(),
                reservation.getStartDate(),
                reservation.getEndDate(),
                reservation.getGuest().getId(),
                reservation.getTotal().toString());
    }

    private Reservation deserialize(String[] fields, String hostId) {
        Reservation result = new Reservation();
        result.setId(Integer.parseInt(fields[0]));
        result.setStartDate(LocalDate.parse(fields[1]));
        result.setEndDate(LocalDate.parse(fields[2]));

        Host host = new Host();
        host.setId(hostId);
        result.setHost(host);

        Guest guest = new Guest();
        guest.setId(Integer.parseInt(fields[3]));
        result.setGuest(guest);

        result.setTotal(new BigDecimal(fields[4]));

        return result;
    }

    private void writeAll(List<Reservation> reservations, String hostId) throws DataException {
        try (PrintWriter writer = new PrintWriter(getFilePath(hostId))) {

            writer.println(HEADER);

            if (reservations == null) {
                return;
            }

            for (Reservation reservation : reservations) {
                writer.println(serialize(reservation));
            }

        } catch (FileNotFoundException ex) {
            throw new DataException(ex);
        }
    }

    private String getFilePath(String hostId) {
        return Paths.get(directory, hostId +".csv").toString();
    }

}
