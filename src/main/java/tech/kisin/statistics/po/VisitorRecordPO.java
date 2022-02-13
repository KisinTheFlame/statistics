package tech.kisin.statistics.po;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@Table(name = "visitor_record")
public class VisitorRecordPO {
    @Id
    private int id;

    private String identifier;
    private String visitTime;
    private String remoteIp;

    public VisitorRecordPO() {

    }
    public VisitorRecordPO(String identifier, String visitTime, String remoteIp) {
        this.identifier = identifier;
        this.visitTime = visitTime;
        this.remoteIp = remoteIp;
    }

    public int getId() {
        return id;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getVisitTime() {
        return visitTime;
    }

    public void setVisitTime(String visitTime) {
        this.visitTime = visitTime;
    }

    public String getRemoteIp() {
        return remoteIp;
    }

    public void setRemoteIp(String remoteIp) {
        this.remoteIp = remoteIp;
    }
}
