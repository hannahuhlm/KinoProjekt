package kino.application.data;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservierungSitzplatzRepository extends JpaRepository<ReservierungSitzplatz, Long> {
    // Hier könnte eine Methode hinzugefügt werden, um ReservierungSitzplätze zu finden
}
