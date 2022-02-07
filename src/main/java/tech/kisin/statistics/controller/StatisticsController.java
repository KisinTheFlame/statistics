package tech.kisin.statistics.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import tech.kisin.statistics.service.VisitorCountService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class StatisticsController {
    private final VisitorCountService visitorCountService;

    public StatisticsController(VisitorCountService visitorCountService) {
        this.visitorCountService = visitorCountService;
    }

    @PostMapping("/get-visitor-count")
    public Integer getVisitorCount(HttpServletRequest request, HttpServletResponse response, @RequestBody String identifier) {
        return visitorCountService.visit(request, response, identifier);
    }
}
