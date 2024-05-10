package learn.house.models;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Reservation {
    Host host;
    Guest guest;
    int id;
    LocalDate startDate;
    LocalDate endDate;
    BigDecimal total;

    // default constructor
    public Reservation() {
    }

    // concrete constructor
    public Reservation(Host host, Guest guest, int id, LocalDate startDate, LocalDate endDate, BigDecimal total) {
        this.host = host;
        this.guest = guest;
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.total = total;
    }

    // getters
    public Host getHost() {
        return host;
    }

    public Guest getGuest() {
        return guest;
    }

    public int getId() {
        return id;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public BigDecimal getTotal() {
        return total;
    }

    // setters
    public void setHost(Host host) {
        this.host = host;
    }

    public void setGuest(Guest guest) {
        this.guest = guest;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }
}
