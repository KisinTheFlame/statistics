package tech.kisin.statistics.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@Table(name = "visitor_record")
@JsonIgnoreProperties({"handler", "hibernateLazyInitializer"})
public class VisitorRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String identifier;
    private String visitTime;
    private String remoteIp;

    public VisitorRecord() {

    }

    public VisitorRecord(String identifier, String visitTime, String remoteIp) {
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
