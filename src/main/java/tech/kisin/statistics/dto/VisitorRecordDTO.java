package tech.kisin.statistics.dto;

import tech.kisin.statistics.entity.VisitorRecord;

public class VisitorRecordDTO {
    private String identifier;
    private String visitTime;
    private String remoteIp;

    public VisitorRecordDTO(String identifier, String visitTime, String remoteIp) {
        this.identifier = identifier;
        this.visitTime = visitTime;
        this.remoteIp = remoteIp;
    }

    public VisitorRecordDTO(VisitorRecord visitorRecord) {
        this.identifier = visitorRecord.getIdentifier();
        this.visitTime = visitorRecord.getVisitTime();
        this.remoteIp = visitorRecord.getRemoteIp();
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
