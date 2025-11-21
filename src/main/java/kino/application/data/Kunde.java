package kino.application.data;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.util.List;

/**
 * Entity-Klasse für einen Kunden des Kinos.
 *
 * Abgeleitet aus dem EMF-Modell:
 * - Attribute: name, email
 * - Referenzen: Listen von Reservierungen und Buchungen.
 *
 * Persistenzsicht:
 * - Entspricht einer Tabelle KUNDE in der Datenbank.
 * - Jede Instanz dieser Klasse ist ein realer Kunde, der Tickets
 *   reservieren und buchen kann.
 */
@Entity
public class Kunde {

    /**
     * Primärschlüssel des Kunden.
     *
     * Wird von der Datenbank automatisch generiert
     * (IDENTITY-Strategie = z.B. AUTO_INCREMENT).
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Name des Kunden.
     * Im EMF-Modell gibt es nur ein Feld "name" (kein Vor- / Nachname).
     * Du kannst hier z.B. "Max Mustermann" speichern.
     */
    private String name;

    /**
     * E-Mail-Adresse des Kunden.
     * Dient typischerweise zur Kontaktaufnahme und zum Versand
     * von Buchungsbestätigungen.
     */
    private String email;

    /**
     * Alle Reservierungen dieses Kunden.
     *
     * Beziehung:
     * - Ein Kunde kann viele Reservierungen besitzen (1:n).
     * - In der Entity Reservierung gibt es ein Feld "kunde"
     *   mit @ManyToOne.
     *
     * mappedBy = "kunde" bedeutet:
     * - Das Feld "kunde" in Reservierung ist die besitzende Seite.
     * - Die Fremdschlüssel-Spalte (KUNDE_ID) liegt in der Tabelle
     *   RESERVIERUNG.
     */
    @OneToMany(mappedBy = "kunde")
    private List<Reservierung> reservierungen;

    /**
     * Alle Buchungen dieses Kunden.
     *
     * Beziehung:
     * - Ein Kunde kann viele Buchungen besitzen (1:n).
     * - In der Entity Buchung gibt es ein Feld "kunde"
     *   mit @ManyToOne und @JoinColumn(name = "kunde_id").
     *
     * mappedBy = "kunde" muss genau dem Feldnamen in der Klasse
     * Buchung entsprechen.
     */
    @OneToMany(mappedBy = "kunde")
    private List<Buchung> buchungen;

    /**
     * Leerer Konstruktor, den JPA/Hibernate benötigt, um Instanzen
     * aus der Datenbank zu laden.
     */
    public Kunde() {
    }

    // === Getter und Setter ===

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Reservierung> getReservierungen() {
        return reservierungen;
    }

    public void setReservierungen(List<Reservierung> reservierungen) {
        this.reservierungen = reservierungen;
    }

    public List<Buchung> getBuchungen() {
        return buchungen;
    }

    public void setBuchungen(List<Buchung> buchungen) {
        this.buchungen = buchungen;
    }

    /*
     * Hinweis zu den EMF-Operationen (reservieren, direktBuchung, etc.):
     * -----------------------------------------------------------------
     * Diese Methoden beschreiben Geschäftslogik wie:
     * - Kunde reserviert Plätze für eine Aufführung.
     * - Kunde storniert eine Reservierung.
     * - Reservierung wird in eine Buchung umgewandelt.
     *
     * Du kannst diese Logik später entweder:
     * - in Service-Klassen (z.B. KundeService, BuchungsService) abbilden,
     *   oder
     * - als normale Methoden in dieser Entity implementieren.
     *
     * Persistiert werden nur die Felder (id, name, email, …),
     * nicht die Methoden selbst.
     */
}