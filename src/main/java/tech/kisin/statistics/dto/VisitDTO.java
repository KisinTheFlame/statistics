package tech.kisin.statistics.dto;

public class VisitDTO {
    private String identifier;

    public VisitDTO() {
    }

    public VisitDTO(String identifier) {
        this.identifier = identifier;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }
}
