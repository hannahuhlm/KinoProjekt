package kino.application.kafka.dto;

import kino.application.data.Kinosaal;

public class SaalDTO {
    private Long id;
    private String name;
    private boolean freigegeben;
    private int reihenCount;

    public SaalDTO() {}

    public SaalDTO(Kinosaal saal) {
        this.id = saal.getId();
        this.name = saal.getName();
        this.freigegeben = saal.isFreigegeben();
        this.reihenCount = saal.getReihen() != null ? saal.getReihen().size() : 0;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public boolean isFreigegeben() { return freigegeben; }
    public void setFreigegeben(boolean freigegeben) { this.freigegeben = freigegeben; }

    public int getReihenCount() { return reihenCount; }
    public void setReihenCount(int reihenCount) { this.reihenCount = reihenCount; }
}
