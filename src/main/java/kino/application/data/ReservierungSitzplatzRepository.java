package kino.application.data;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservierungSitzplatzRepository extends JpaRepository<ReservierungSitzplatz, Long> {

    // alle Sitzplätze zu einer bestimmten Reservierung holen
    List<ReservierungSitzplatz> findByReservierung(Reservierung reservierung);
}
