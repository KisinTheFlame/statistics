package tech.kisin.statistics.service;

import org.springframework.stereotype.Service;
import tech.kisin.statistics.dao.VisitorCountRepository;
import tech.kisin.statistics.dao.VisitorRecordRepository;
import tech.kisin.statistics.dto.VisitorCountDTO;
import tech.kisin.statistics.dto.VisitorRecordDTO;
import tech.kisin.statistics.po.VisitorRecordPO;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DashboardService {

    private final VisitorCountRepository visitorCountRepository;
    private final VisitorRecordRepository visitorRecordRepository;

    public DashboardService(VisitorCountRepository visitorCountRepository, VisitorRecordRepository visitorRecordRepository) {
        this.visitorCountRepository = visitorCountRepository;
        this.visitorRecordRepository = visitorRecordRepository;
    }

    public List<VisitorCountDTO> getVisitorCountList() {
        return visitorCountRepository.getAllByOrderByIdentifier().stream().map(VisitorCountDTO::new).collect(Collectors.toList());
    }

    public List<VisitorRecordDTO> getVisitorRecordList(String filterIdentifier) {
        List<VisitorRecordPO> visitorRecordPOList;
        if (filterIdentifier == null) {
            visitorRecordPOList = visitorRecordRepository.getAllByOrderByVisitTimeDesc();
        } else {
            visitorRecordPOList = visitorRecordRepository.getAllByIdentifierOrderByVisitTimeDesc(filterIdentifier);
        }
        return visitorRecordPOList.stream().map(VisitorRecordDTO::new).collect(Collectors.toList());
    }
}
