package kino.application.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kino.Kino;

/**
 * Repository für die Film-Entity.
 *
 * - Film ist eine @Entity-Klasse -> entspricht einer Tabelle in der Datenbank.
 * - JpaRepository<Film, Long> bedeutet:
 *      * Film  = auf welche Tabelle/Entity sich das Repository bezieht
 *      * Long  = Typ des Primärschlüssels (id vom Film)
 *
 * Über dieses Interface bekommen wir automatisch Methoden wie:
 *  - findAll()     -> SELECT * FROM film
 *  - findById(id)  -> SELECT * FROM film WHERE id = ?
 *  - save(film)    -> INSERT / UPDATE film
 *  - delete(film)  -> DELETE FROM film ...
 */
@Repository
public interface KinosaalRepository extends JpaRepository<Kinosaal, Long> {

}
