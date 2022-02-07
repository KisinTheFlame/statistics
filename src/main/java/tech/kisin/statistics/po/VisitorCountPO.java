package tech.kisin.statistics.po;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@Table(name = "visitor_count")
@JsonIgnoreProperties({"handler", "hibernateLazyInitializer"})
public class VisitorCountPO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String identifier;
    private Integer count;

    public VisitorCountPO() {
    }

    public VisitorCountPO(String identifier, Integer count) {
        this.identifier = identifier;
        this.count = count;
    }

    public Integer getId() {
        return id;
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
