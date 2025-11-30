package kino.application.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface ReservierungRepository extends JpaRepository<Reservierung, Long> {
    Reservierung findByReservierungsnummer(int reservierungsnummer);

    // Lädt alle Reservierungen eines Kunden inkl. zugehöriger Sitzplatz-Joins und Sitzplätze
    @Query("select distinct r from Reservierung r " +
           "left join fetch r.reservierungSitzplaetze rsp " +
           "left join fetch rsp.sitzplatz sp " +
           "where r.kunde.id = :kid")
    List<Reservierung> findWithSeatsByKundeId(@Param("kid") Long kundeId);

    // Einzel-Reservierung inkl. Sitzplätze (für Buchungsdialog)
    @Query("select r from Reservierung r " +
           "left join fetch r.reservierungSitzplaetze rsp " +
           "left join fetch rsp.sitzplatz sp " +
           "left join fetch sp.reihe sr " +
           "where r.id = :id")
    java.util.Optional<Reservierung> findWithSeatsById(@Param("id") Long id);
}
