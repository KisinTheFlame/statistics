package tech.kisin.statistics.dto;

import tech.kisin.statistics.entity.VisitorCount;

public class VisitorCountDTO {
    private String identifier;
    private Integer count;

    public VisitorCountDTO(String identifier, Integer count) {
        this.identifier = identifier;
        this.count = count;
    }

    public VisitorCountDTO(VisitorCount visitorCount) {
        this.identifier = visitorCount.getIdentifier();
        this.count = visitorCount.getCount();
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
