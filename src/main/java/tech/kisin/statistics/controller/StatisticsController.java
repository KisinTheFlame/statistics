package tech.kisin.statistics.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import tech.kisin.statistics.dto.VisitorCountDTO;
import tech.kisin.statistics.dto.VisitorCountQueryDTO;
import tech.kisin.statistics.dto.VisitorRecordDTO;
import tech.kisin.statistics.service.VisitorService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
public class StatisticsController {
    private final VisitorService visitorService;

    public StatisticsController(VisitorService visitorService) {
        this.visitorService = visitorService;
    }

    @PostMapping("/get-visitor-count")
    public Integer getVisitorCount(
            HttpServletRequest request,
            HttpServletResponse response,
            @RequestBody VisitorCountQueryDTO visitorCountQuery
    ) {
        return visitorService.visit(request, response, visitorCountQuery.getIdentifier());
    }

    @PostMapping("/get-visitor-count-list")
    public List<VisitorCountDTO> getVisitorCountList() {
        return visitorService.getVisitorCountList();
    }

    @PostMapping("/get-visitor-record-list")
    public List<VisitorRecordDTO> getVisitorRecordList() {
        return visitorService.getVisitorRecordList(null);
    }

    @PostMapping("/get-visitor-record-list-filtered/{identifier}")
    public List<VisitorRecordDTO> getVisitorRecordListFiltered(@PathVariable("identifier") String identifier) {
        return visitorService.getVisitorRecordList(identifier);
    }
}
