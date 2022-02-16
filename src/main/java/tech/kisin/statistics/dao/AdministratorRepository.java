package tech.kisin.statistics.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.kisin.statistics.entity.Administrator;

public interface AdministratorRepository extends JpaRepository<Administrator, Integer> {
    boolean existsByUsername(String username);

    boolean existsByToken(String token);

    Administrator getByUsername(String Username);
}