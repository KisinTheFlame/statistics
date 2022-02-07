package tech.kisin.statistics.service;

import org.springframework.stereotype.Service;
import tech.kisin.statistics.dao.VisitorCountRepository;
import tech.kisin.statistics.pojo.VisitorCount;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static tech.kisin.statistics.utils.Utils.getCurrentTime;

@Service
public class VisitorCountService {

    private final String RECENTLY_VISIT_TIME_COOKIE_NAME = "recently-visit-time";
    private final Integer RECENTLY_VISIT_TIME_COOKIE_MAX_AGE = 5;

    private final VisitorCountRepository visitorCountRepository;

    public VisitorCountService(VisitorCountRepository visitorCountRepository) {
        this.visitorCountRepository = visitorCountRepository;
    }

    public Integer visit(HttpServletRequest request, HttpServletResponse response, String identifier) {
        if (isRecentlyVisited(request)) {
            return getVisitorCount(identifier, false);
        } else {
            Cookie recentlyVisitTimeCookie = new Cookie(RECENTLY_VISIT_TIME_COOKIE_NAME, URLEncoder.encode(getCurrentTime(), StandardCharsets.UTF_8));
            recentlyVisitTimeCookie.setMaxAge(RECENTLY_VISIT_TIME_COOKIE_MAX_AGE);
            response.addCookie(recentlyVisitTimeCookie);
            return getVisitorCount(identifier, true);
        }
    }

    private boolean isRecentlyVisited(HttpServletRequest request) {
        if (request.getCookies() == null) return false;
        Map<String, Cookie> cookieMap = new HashMap<>();
        Arrays.stream(request.getCookies()).forEach(cookie -> cookieMap.put(cookie.getName(), cookie));
        return cookieMap.containsKey(RECENTLY_VISIT_TIME_COOKIE_NAME);
    }

    private Integer getVisitorCount(String identifier, boolean toModify) {
        VisitorCount visitorCount;
        if (visitorCountRepository.existsByIdentifier(identifier)) {
            visitorCount = visitorCountRepository.getByIdentifier(identifier);
        } else {
            visitorCount = new VisitorCount(identifier, 0);
        }
        if (toModify) {
            visitorCount.setCount(visitorCount.getCount() + 1);
            visitorCountRepository.save(visitorCount);
        }
        return visitorCount.getCount();
    }
}
