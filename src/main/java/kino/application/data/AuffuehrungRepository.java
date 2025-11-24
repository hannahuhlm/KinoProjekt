package kino.application.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuffuehrungRepository extends JpaRepository<Auffuehrung, Long> {
    List<Auffuehrung> findByFilmOrderByStartzeitpunktAsc(Film film);
}
