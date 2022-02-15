package tech.kisin.statistics.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import tech.kisin.statistics.entity.VisitorCount;

import javax.transaction.Transactional;
import java.util.List;

public interface VisitorCountRepository extends JpaRepository<VisitorCount, Integer> {

    boolean existsByIdentifier(String identifier);

    VisitorCount getByIdentifier(String identifier);

    List<VisitorCount> getAllByOrderByIdentifier();

    @Transactional
    @Modifying
    @Query(value = "update VisitorCount v set v.count = v.count + 1 where v.identifier = :identifier")
    void countUp(String identifier);
}
