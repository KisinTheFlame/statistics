package tech.kisin.statistics.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@Table(name = "visitor_count")
@JsonIgnoreProperties({"handler", "hibernateLazyInitializer"})
public class VisitorCount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String identifier;
    private Integer count;

    public VisitorCount() {
    }

    public VisitorCount(String identifier, Integer count) {
        this.identifier = identifier;
        this.count = count;
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
