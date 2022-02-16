package tech.kisin.statistics.service;

import org.springframework.stereotype.Service;
import tech.kisin.statistics.dao.AdministratorRepository;
import tech.kisin.statistics.dao.VisitorCountRepository;
import tech.kisin.statistics.dao.VisitorRecordRepository;
import tech.kisin.statistics.dto.VisitorCountDTO;
import tech.kisin.statistics.dto.VisitorRecordDTO;
import tech.kisin.statistics.entity.VisitorRecord;
import tech.kisin.statistics.result.Result;
import tech.kisin.statistics.result.ResultCode;
import tech.kisin.statistics.util.SecurityUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DashboardService {

    private final VisitorCountRepository visitorCountRepository;
    private final VisitorRecordRepository visitorRecordRepository;
    private final AdministratorRepository administratorRepository;

    public DashboardService(
            VisitorCountRepository visitorCountRepository,
            VisitorRecordRepository visitorRecordRepository,
            AdministratorRepository administratorRepository
    ) {
        this.visitorCountRepository = visitorCountRepository;
        this.visitorRecordRepository = visitorRecordRepository;
        this.administratorRepository = administratorRepository;
    }

    public Result<List<VisitorCountDTO>> getVisitorCountList(HttpServletRequest request, HttpServletResponse response) {
        ResultCode authenticationFailure = isAuthenticated(request, response);
        if (authenticationFailure != null) return new Result<>(authenticationFailure, null);
        return new Result<>(
                ResultCode.SUCCESS,
                visitorCountRepository
                        .getAllByOrderByIdentifier()
                        .stream().map(VisitorCountDTO::new)
                        .collect(Collectors.toList())
        );
    }

    public Result<List<VisitorRecordDTO>> getVisitorRecordList(
            HttpServletRequest request,
            HttpServletResponse response,
            String filterIdentifier
    ) {
        ResultCode authenticationFailure = isAuthenticated(request, response);
        if (authenticationFailure != null) return new Result<>(authenticationFailure, null);
        List<VisitorRecord> visitorRecordList;
        if (filterIdentifier == null) {
            visitorRecordList = visitorRecordRepository.getAllByOrderByVisitTimeDesc();
        } else {
            visitorRecordList = visitorRecordRepository.getAllByIdentifierOrderByVisitTimeDesc(filterIdentifier);
        }
        return new Result<>(
                ResultCode.SUCCESS,
                visitorRecordList
                        .stream()
                        .map(VisitorRecordDTO::new)
                        .collect(Collectors.toList())
        );
    }

    private ResultCode isAuthenticated(HttpServletRequest request, HttpServletResponse response) {
        if (request.getCookies() == null) return ResultCode.USER_NOT_LOGGED_IN;
        for (Cookie cookie : request.getCookies()) {
            if (cookie.getName().equals(SecurityUtils.TOKEN_COOKIE_NAME)) {
                if (administratorRepository.existsByToken(cookie.getValue())) return null;
                SecurityUtils.addTokenIntoCookie(response, "", 0);
                return ResultCode.USER_INVALID_TOKEN;
            }
        }
        return ResultCode.USER_NOT_LOGGED_IN;
    }
}
