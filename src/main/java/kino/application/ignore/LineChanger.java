package kino.application.ignore;

import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

/*
* Klasse zum einmaligen Ändern der Länge von Beschreibung und Poster URL
* Stellt sicher, dass die Datenbank wirklich angepasst ist, nicht nur in der Entity
* Kann theoretisch nur ein Mal ausgeführt werden, ist jedoch nicht signifikant Leistungsbeanspruchend
 */

@Component
public class LineChanger implements CommandLineRunner {

    private final JdbcTemplate jdbcTemplate;

    public LineChanger(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void run(String... args) {
        try {
            // Länge der Beschreibung auf 1000 erhöhen
            jdbcTemplate.execute("""
                ALTER TABLE IF EXISTS film
                ALTER COLUMN beschreibung TYPE varchar(1000);
            """);

            // Länge der Poster-URL auf 500 erhöhen (optional)
            jdbcTemplate.execute("""
                ALTER TABLE IF EXISTS film
                ALTER COLUMN poster_url TYPE varchar(500);
            """);
        } catch (Exception e) {
            // Nur loggen, App soll trotzdem hochfahren
            System.out.println("Schema-Migration konnte nicht komplett ausgeführt werden: " + e.getMessage());
        }
    }
}
