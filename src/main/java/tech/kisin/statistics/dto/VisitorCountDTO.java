package tech.kisin.statistics.dto;

import tech.kisin.statistics.po.VisitorCountPO;

public class VisitorCountDTO {
    private String identifier;
    private Integer count;

    public VisitorCountDTO(String identifier, Integer count) {
        this.identifier = identifier;
        this.count = count;
    }

    public VisitorCountDTO(VisitorCountPO visitorCountPO) {
        this.identifier = visitorCountPO.getIdentifier();
        this.count = visitorCountPO.getCount();
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
