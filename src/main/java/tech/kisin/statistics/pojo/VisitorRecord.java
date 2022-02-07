package tech.kisin.statistics.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@Table(name = "visitor_record")
@JsonIgnoreProperties({"handler", "hibernateLazyInitializer"})
public class VisitorRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    String identifier;

}
