package kino.application.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.Optional;

public interface BuchungRepository extends JpaRepository<Buchung, Long> {

	@Query("select b from Buchung b " +
		   "left join fetch b.buchungSitzplaetze bsp " +
		   "left join fetch bsp.sitzplatz sp " +
		   "left join fetch sp.reihe sr " +
		   "where b.id = :id")
	Optional<Buchung> findWithSeatsById(@Param("id") Long id);
}
