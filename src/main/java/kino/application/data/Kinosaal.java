package kino.application.data;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.FetchType;

import java.util.List;

@Entity
public class Kinosaal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private boolean freigegeben = false;

    @OneToMany(mappedBy = "saal", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Sitzreihe> reihen;


    // Alle Aufführungen sofort mitladen
    @OneToMany(mappedBy = "saal", fetch = FetchType.EAGER)
    private List<Auffuehrung> auffuehrungen;

    public Kinosaal() {
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

    public boolean isFreigegeben() {
        return freigegeben;
    }

    public void setFreigegeben(boolean freigegeben) {
        this.freigegeben = freigegeben;
    }

    public List<Sitzreihe> getReihen() {
        return reihen;
    }

    public void setReihen(List<Sitzreihe> reihen) {
        this.reihen = reihen;
    }

    public List<Auffuehrung> getAuffuehrungen() {
        return auffuehrungen;
    }

    public void setAuffuehrungen(List<Auffuehrung> auffuehrungen) {
        this.auffuehrungen = auffuehrungen;
    }

    // Optional: EMF-Logik nachbilden
    public void freigeben() {
        this.freigegeben = true;
    }

    public boolean istFreigegeben() {
        return this.freigegeben;
    }
}
