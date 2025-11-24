package kino.application.buchung;

import java.io.Serializable;
import java.util.List;

public class BuchungContext implements Serializable {

    private Long auffuehrungId;
    private Long kundeId;
    private List<Long> sitzplatzIds;

    public Long getAuffuehrungId() {
        return auffuehrungId;
    }

    public void setAuffuehrungId(Long auffuehrungId) {
        this.auffuehrungId = auffuehrungId;
    }

    public Long getKundeId() {
        return kundeId;
    }

    public void setKundeId(Long kundeId) {
        this.kundeId = kundeId;
    }

    public List<Long> getSitzplatzIds() {
        return sitzplatzIds;
    }

    public void setSitzplatzIds(List<Long> sitzplatzIds) {
        this.sitzplatzIds = sitzplatzIds;
    }
}
