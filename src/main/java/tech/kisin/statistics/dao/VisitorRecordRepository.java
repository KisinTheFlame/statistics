package tech.kisin.statistics.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.kisin.statistics.entity.VisitorRecord;

import java.util.List;

public interface VisitorRecordRepository extends JpaRepository<VisitorRecord, Integer> {
    List<VisitorRecord> getAllByOrderByVisitTimeDesc();

    List<VisitorRecord> getAllByIdentifierOrderByVisitTimeDesc(String identifier);
}
