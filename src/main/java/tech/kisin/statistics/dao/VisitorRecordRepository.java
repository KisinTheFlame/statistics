package tech.kisin.statistics.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.kisin.statistics.po.VisitorRecordPO;

import java.util.List;

public interface VisitorRecordRepository extends JpaRepository<VisitorRecordPO, Integer> {
    List<VisitorRecordPO> getAllByOrderByVisitTimeDesc();
    List<VisitorRecordPO> getAllByIdentifierOrderByVisitTimeDesc(String identifier);
}
