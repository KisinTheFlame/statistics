package tech.kisin.statistics.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.kisin.statistics.po.VisitorCountPO;

import java.util.List;

public interface VisitorCountRepository extends JpaRepository<VisitorCountPO, Integer> {

    boolean existsByIdentifier(String identifier);

    VisitorCountPO getByIdentifier(String identifier);

    List<VisitorCountPO> getAllByOrderByIdentifier();
}
