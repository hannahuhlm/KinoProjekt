package kino.application.aggregation;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Document(collection = "daily_revenue")
public class RevenueAggregate {
    @Id
    private String id;
    private LocalDate day;
    private LocalDateTime aggregatedAt;
    private Long filmId;
    private Long auffuehrungId;
    private double revenue;
    private int bookingsCount;
    private int reservationsExpiredCount;
    private int occupiedSeatsCount; // belegte Sitzplätze zum Aggregationszeitpunkt
    private int totalSeatsCount;    // gesamte mögliche Sitzplätze im Saal
    private double occupancyPercent; // (occupied / total) * 100

    public RevenueAggregate() {}

    public RevenueAggregate(LocalDate day, LocalDateTime aggregatedAt, Long filmId, Long auffuehrungId,
                            double revenue, int bookingsCount, int reservationsExpiredCount) {
        this(day, aggregatedAt, filmId, auffuehrungId, revenue, bookingsCount, reservationsExpiredCount, 0, 0, 0.0);
    }

    public RevenueAggregate(LocalDate day, LocalDateTime aggregatedAt, Long filmId, Long auffuehrungId,
                            double revenue, int bookingsCount, int reservationsExpiredCount,
                            int occupiedSeatsCount, int totalSeatsCount, double occupancyPercent) {
        this.day = day;
        this.aggregatedAt = aggregatedAt;
        this.filmId = filmId;
        this.auffuehrungId = auffuehrungId;
        this.revenue = revenue;
        this.bookingsCount = bookingsCount;
        this.reservationsExpiredCount = reservationsExpiredCount;
        this.occupiedSeatsCount = occupiedSeatsCount;
        this.totalSeatsCount = totalSeatsCount;
        this.occupancyPercent = occupancyPercent;
    }

    public String getId() { return id; }
    public LocalDate getDay() { return day; }
    public LocalDateTime getAggregatedAt() { return aggregatedAt; }
    public Long getFilmId() { return filmId; }
    public Long getAuffuehrungId() { return auffuehrungId; }
    public double getRevenue() { return revenue; }
    public int getBookingsCount() { return bookingsCount; }
    public int getReservationsExpiredCount() { return reservationsExpiredCount; }
    public int getOccupiedSeatsCount() { return occupiedSeatsCount; }
    public int getTotalSeatsCount() { return totalSeatsCount; }
    public double getOccupancyPercent() { return occupancyPercent; }

    public void setId(String id) { this.id = id; }
    public void setDay(LocalDate day) { this.day = day; }
    public void setAggregatedAt(LocalDateTime aggregatedAt) { this.aggregatedAt = aggregatedAt; }
    public void setFilmId(Long filmId) { this.filmId = filmId; }
    public void setAuffuehrungId(Long auffuehrungId) { this.auffuehrungId = auffuehrungId; }
    public void setRevenue(double revenue) { this.revenue = revenue; }
    public void setBookingsCount(int bookingsCount) { this.bookingsCount = bookingsCount; }
    public void setReservationsExpiredCount(int reservationsExpiredCount) { this.reservationsExpiredCount = reservationsExpiredCount; }
    public void setOccupiedSeatsCount(int occupiedSeatsCount) { this.occupiedSeatsCount = occupiedSeatsCount; }
    public void setTotalSeatsCount(int totalSeatsCount) { this.totalSeatsCount = totalSeatsCount; }
    public void setOccupancyPercent(double occupancyPercent) { this.occupancyPercent = occupancyPercent; }
}
