package kino.application.aggregation;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document(collection = "daily_revenue")
public class RevenueAggregate {
    @Id
    private String id;
    private LocalDate day;
    private Long filmId;
    private Long auffuehrungId;
    private double revenue;
    private int bookingsCount;
    private int reservationsExpiredCount;

    public RevenueAggregate() {}

    public RevenueAggregate(LocalDate day, Long filmId, Long auffuehrungId,
                            double revenue, int bookingsCount, int reservationsExpiredCount) {
        this.day = day;
        this.filmId = filmId;
        this.auffuehrungId = auffuehrungId;
        this.revenue = revenue;
        this.bookingsCount = bookingsCount;
        this.reservationsExpiredCount = reservationsExpiredCount;
    }

    public String getId() { return id; }
    public LocalDate getDay() { return day; }
    public Long getFilmId() { return filmId; }
    public Long getAuffuehrungId() { return auffuehrungId; }
    public double getRevenue() { return revenue; }
    public int getBookingsCount() { return bookingsCount; }
    public int getReservationsExpiredCount() { return reservationsExpiredCount; }

    public void setId(String id) { this.id = id; }
    public void setDay(LocalDate day) { this.day = day; }
    public void setFilmId(Long filmId) { this.filmId = filmId; }
    public void setAuffuehrungId(Long auffuehrungId) { this.auffuehrungId = auffuehrungId; }
    public void setRevenue(double revenue) { this.revenue = revenue; }
    public void setBookingsCount(int bookingsCount) { this.bookingsCount = bookingsCount; }
    public void setReservationsExpiredCount(int reservationsExpiredCount) { this.reservationsExpiredCount = reservationsExpiredCount; }
}
