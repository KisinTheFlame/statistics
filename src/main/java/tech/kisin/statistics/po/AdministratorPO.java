package tech.kisin.statistics.po;

import javax.persistence.*;

public class AdministratorPO {
    private Integer id;
    private final String username;
    private final String password;
    private final String salt;

    public AdministratorPO(String username, String password, String salt) {
        this.username = username;
        this.password = password;
        this.salt = salt;
    }
}
