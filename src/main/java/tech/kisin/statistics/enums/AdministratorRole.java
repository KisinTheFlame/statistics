package tech.kisin.statistics.enums;

public enum AdministratorRole {
    ROOT("Root"), NORMAL("Normal"), READONLY("Readonly");

    private String name;

    AdministratorRole(String name) {
        this.name = name;
    }
}
