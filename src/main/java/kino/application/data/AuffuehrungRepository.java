package kino.application.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuffuehrungRepository extends JpaRepository<Auffuehrung, Long> {
    List<Auffuehrung> findByFilmOrderByStartzeitpunktAsc(Film film);
    
    @Modifying
    @Query("DELETE FROM Auffuehrung a WHERE a.id = :id")
    int deleteAuffuehrungById(@Param("id") Long id);
}
