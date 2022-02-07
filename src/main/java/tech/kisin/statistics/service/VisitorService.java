package tech.kisin.statistics.service;

import org.springframework.stereotype.Service;
import tech.kisin.statistics.dao.VisitorCountRepository;
import tech.kisin.statistics.dao.VisitorRecordRepository;
import tech.kisin.statistics.dto.VisitorCountDTO;
import tech.kisin.statistics.dto.VisitorRecordDTO;
import tech.kisin.statistics.po.VisitorCountPO;
import tech.kisin.statistics.po.VisitorRecordPO;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static tech.kisin.statistics.utils.Utils.getCurrentTimeDashFormat;
import static tech.kisin.statistics.utils.Utils.getCurrentTimeDatetimeFormat;

@Service
public class VisitorService {

    private final String RECENTLY_VISIT_TIME_COOKIE_NAME_PREFIX = "recently-visit-time-for-";
    private final Integer RECENTLY_VISIT_TIME_COOKIE_MAX_AGE = 5;

    private final VisitorCountRepository visitorCountRepository;
    private final VisitorRecordRepository visitorRecordRepository;

    public VisitorService(VisitorCountRepository visitorCountRepository, VisitorRecordRepository visitorRecordRepository) {
        this.visitorCountRepository = visitorCountRepository;
        this.visitorRecordRepository = visitorRecordRepository;
    }

    public Integer visit(HttpServletRequest request, HttpServletResponse response, String identifier) {
        if (isRecentlyVisited(request, identifier)) {
            return getVisitorCount(identifier, false);
        } else {
            Cookie recentlyVisitTimeCookie = new Cookie(
                    RECENTLY_VISIT_TIME_COOKIE_NAME_PREFIX + identifier,
                    URLEncoder.encode(getCurrentTimeDashFormat(), StandardCharsets.UTF_8)
            );
            recentlyVisitTimeCookie.setMaxAge(RECENTLY_VISIT_TIME_COOKIE_MAX_AGE);
            response.addCookie(recentlyVisitTimeCookie);
            saveVisitorRecord(request, identifier);
            return getVisitorCount(identifier, true);
        }
    }

    public List<VisitorCountDTO> getVisitorCountList() {
        return visitorCountRepository.getAllByOrderByIdentifier().stream().map(VisitorCountDTO::new).collect(Collectors.toList());
    }

    public List<VisitorRecordDTO> getVisitorRecordList(String filterIdentifier) {
        List<VisitorRecordPO> visitorRecordPOList;
        if(filterIdentifier == null) {
            visitorRecordPOList = visitorRecordRepository.getAllByOrderByVisitTimeDesc();
        } else {
            visitorRecordPOList = visitorRecordRepository.getAllByIdentifierOrderByVisitTimeDesc(filterIdentifier);
        }
        return visitorRecordPOList.stream().map(VisitorRecordDTO::new).collect(Collectors.toList());
    }

    private boolean isRecentlyVisited(HttpServletRequest request, String identifier) {
        if (request.getCookies() == null) return false;
        return Arrays.stream(request.getCookies())
                .parallel()
                .reduce(
                        false,
                        (found, cookie) -> found || cookie.getName().equals(RECENTLY_VISIT_TIME_COOKIE_NAME_PREFIX + identifier),
                        (found1, found2) -> found1 || found2
                );
    }

    private Integer getVisitorCount(String identifier, boolean toModify) {
        VisitorCountPO visitorCount;
        if (visitorCountRepository.existsByIdentifier(identifier)) {
            visitorCount = visitorCountRepository.getByIdentifier(identifier);
        } else {
            visitorCount = new VisitorCountPO(identifier, 0);
        }
        if (toModify) {
            visitorCount.setCount(visitorCount.getCount() + 1);
            visitorCountRepository.save(visitorCount);
        }
        return visitorCount.getCount();
    }

    private void saveVisitorRecord(HttpServletRequest request, String identifier) {
        VisitorRecordPO visitorRecord = new VisitorRecordPO(identifier, getCurrentTimeDatetimeFormat(), request.getRemoteAddr());
        visitorRecordRepository.save(visitorRecord);
    }
}
