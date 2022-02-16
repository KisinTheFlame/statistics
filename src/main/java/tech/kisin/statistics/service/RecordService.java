package tech.kisin.statistics.service;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;
import tech.kisin.statistics.dao.VisitorCountRepository;
import tech.kisin.statistics.dao.VisitorRecordRepository;
import tech.kisin.statistics.entity.VisitorCount;
import tech.kisin.statistics.entity.VisitorRecord;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.stream.Collectors;

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
                    .from(
                            RECENTLY_VISIT_TIME_COOKIE_NAME_PREFIX + identifier,
                            getCurrentTimeDashFormat()
                    )
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
        return Arrays
                .stream(request.getCookies())
                .parallel()
                .map(Cookie::getName)
                .collect(Collectors.toSet())
                .contains(RECENTLY_VISIT_TIME_COOKIE_NAME_PREFIX + identifier);
    }

    private Integer getVisitorCount(String identifier, boolean toModify) {
        VisitorCount visitorCount;
        if (visitorCountRepository.existsByIdentifier(identifier)) {
            visitorCount = visitorCountRepository.getByIdentifier(identifier);
        } else {
            visitorCount = new VisitorCount(identifier, 0);
            visitorCountRepository.save(visitorCount);
        }
        if (toModify) {
            visitorCountRepository.countUp(identifier);
            return visitorCount.getCount() + 1;
        } else {
            return visitorCount.getCount();
        }
    }

    private void saveVisitorRecord(HttpServletRequest request, String identifier) {
        VisitorRecord visitorRecord = new VisitorRecord(
                identifier,
                getCurrentTimeDatetimeFormat(),
                request.getHeader("x-forwarded-for")
        );
        visitorRecordRepository.save(visitorRecord);
    }
}
