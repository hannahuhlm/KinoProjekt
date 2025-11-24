package kino.application.data;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservierungRepository extends JpaRepository<Reservierung, Long> {
    Reservierung findByReservierungsnummer(int reservierungsnummer);
}
