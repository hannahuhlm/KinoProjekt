import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class BeispieltabelleKino {
    @Id
    private Long id;
    private String name;
    public BeispieltabelleKino() {}

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
}
