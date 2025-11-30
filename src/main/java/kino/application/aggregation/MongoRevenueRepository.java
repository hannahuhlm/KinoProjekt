package kino.application.aggregation;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface MongoRevenueRepository extends MongoRepository<RevenueAggregate, String> {
    List<RevenueAggregate> findByDay(LocalDate day);
    List<RevenueAggregate> findByFilmId(Long filmId);
}
