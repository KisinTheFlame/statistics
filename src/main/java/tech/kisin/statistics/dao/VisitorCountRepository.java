package tech.kisin.statistics.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import tech.kisin.statistics.po.VisitorCountPO;

import javax.transaction.Transactional;
import java.util.List;

public interface VisitorCountRepository extends JpaRepository<VisitorCountPO, Integer> {

    boolean existsByIdentifier(String identifier);

    VisitorCountPO getByIdentifier(String identifier);

    List<VisitorCountPO> getAllByOrderByIdentifier();

    @Transactional
    @Modifying
    @Query(value = "update VisitorCountPO v set v.count = v.count + 1 where v.identifier = :identifier")
    void countUp(String identifier);
}
