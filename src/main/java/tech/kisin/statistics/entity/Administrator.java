package tech.kisin.statistics.entity;

import javax.persistence.*;

@Entity
@Table(name = "administrator")
public class Administrator {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String username;
    private String password;
    private String salt;

    public Administrator() {

    }

    public Administrator(String username, String password, String salt) {
        this.username = username;
        this.password = password;
        this.salt = salt;
    }
}
