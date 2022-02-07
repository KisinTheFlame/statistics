package tech.kisin.statistics.dto;

public class VisitorCountQueryDTO {
    private String identifier;

    public VisitorCountQueryDTO() {
    }

    public VisitorCountQueryDTO(String identifier) {
        this.identifier = identifier;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }
}
