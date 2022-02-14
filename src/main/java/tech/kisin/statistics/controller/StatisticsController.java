package tech.kisin.statistics.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import tech.kisin.statistics.dto.VisitDTO;
import tech.kisin.statistics.dto.VisitorCountDTO;
import tech.kisin.statistics.dto.VisitorRecordDTO;
import tech.kisin.statistics.result.Result;
import tech.kisin.statistics.result.ResultCode;
import tech.kisin.statistics.service.DashboardService;
import tech.kisin.statistics.service.RecordService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
public class StatisticsController {
    private final RecordService recordService;
    private final DashboardService dashboardService;

    public StatisticsController(RecordService recordService, DashboardService dashboardService) {
        this.recordService = recordService;
        this.dashboardService = dashboardService;
    }

    @PostMapping("/visit-and-get-count")
    public Result<Integer> visit(
            HttpServletRequest request,
            HttpServletResponse response,
            @RequestBody VisitDTO visit
    ) {
        return new Result<>(
                ResultCode.SUCCESS,
                recordService.visit(request, response, visit.getIdentifier())
        );
    }

    @PostMapping("/get-visitor-count-list")
    public Result<List<VisitorCountDTO>> getVisitorCountList() {
        return new Result<>(ResultCode.SUCCESS, dashboardService.getVisitorCountList());
    }

    @PostMapping("/get-visitor-record-list")
    public Result<List<VisitorRecordDTO>> getVisitorRecordList() {
        return new Result<>(ResultCode.SUCCESS, dashboardService.getVisitorRecordList(null));
    }

    @PostMapping("/get-visitor-record-list-filtered/{identifier}")
    public Result<List<VisitorRecordDTO>> getVisitorRecordListFiltered(@PathVariable("identifier") String identifier) {
        return new Result<>(ResultCode.SUCCESS, dashboardService.getVisitorRecordList(identifier));
    }
}
