package tech.kisin.statistics.po;

import javax.persistence.*;

@Entity
@Table(name = "administrator")
public class AdministratorPO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String username;
    private String password;
    private String salt;

    public AdministratorPO() {

    }

    public AdministratorPO(String username, String password, String salt) {
        this.username = username;
        this.password = password;
        this.salt = salt;
    }
}
