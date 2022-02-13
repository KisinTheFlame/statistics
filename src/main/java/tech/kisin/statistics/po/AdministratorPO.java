package tech.kisin.statistics.po;

import tech.kisin.statistics.enums.AdministratorRole;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class AdministratorPO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String username;
    private String password;
    private String salt;
    private String role;

    public AdministratorPO(String username, String password, String salt, AdministratorRole role) {
        this.username = username;
        this.password = password;
        this.salt = salt;
        this.role = role.toString();
    }
}
