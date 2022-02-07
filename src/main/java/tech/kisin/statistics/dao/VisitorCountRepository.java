package tech.kisin.statistics.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.kisin.statistics.pojo.VisitorCount;

public interface VisitorCountRepository extends JpaRepository<VisitorCount, Integer> {

    boolean existsByIdentifier(String identifier);

    VisitorCount getByIdentifier(String identifier);
}
