package tech.kisin.statistics.service;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;
import tech.kisin.statistics.dao.VisitorCountRepository;
import tech.kisin.statistics.dao.VisitorRecordRepository;
import tech.kisin.statistics.po.VisitorCountPO;
import tech.kisin.statistics.po.VisitorRecordPO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

import static tech.kisin.statistics.util.TimeUtils.getCurrentTimeDashFormat;
import static tech.kisin.statistics.util.TimeUtils.getCurrentTimeDatetimeFormat;

@Service
public class RecordService {

    private final String RECENTLY_VISIT_TIME_COOKIE_NAME_PREFIX = "recently-visit-time-for-";

    private final VisitorCountRepository visitorCountRepository;
    private final VisitorRecordRepository visitorRecordRepository;

    public RecordService(VisitorCountRepository visitorCountRepository, VisitorRecordRepository visitorRecordRepository) {
        this.visitorCountRepository = visitorCountRepository;
        this.visitorRecordRepository = visitorRecordRepository;
    }

    public Integer visit(HttpServletRequest request, HttpServletResponse response, String identifier) {
        if (isRecentlyVisited(request, identifier)) {
            return getVisitorCount(identifier, false);
        } else {
            ResponseCookie cookie = ResponseCookie
                    .from(RECENTLY_VISIT_TIME_COOKIE_NAME_PREFIX + identifier, getCurrentTimeDashFormat())
                    .maxAge(60 * 5)
                    .path("/")
                    .secure(true)
                    .httpOnly(false)
                    .sameSite("None")
                    .build();
            response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
            response.addHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS, request.getRemoteHost());
            saveVisitorRecord(request, identifier);
            return getVisitorCount(identifier, true);
        }
    }

    private boolean isRecentlyVisited(HttpServletRequest request, String identifier) {
        if (request.getCookies() == null) return false;
        return Arrays.stream(request.getCookies()).parallel().reduce(false, (found, cookie) -> found || cookie.getName().equals(RECENTLY_VISIT_TIME_COOKIE_NAME_PREFIX + identifier), (found1, found2) -> found1 || found2);
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
        VisitorRecordPO visitorRecord = new VisitorRecordPO(identifier, getCurrentTimeDatetimeFormat(), request.getHeader("x-forwarded-for"));
        visitorRecordRepository.save(visitorRecord);
    }
}
