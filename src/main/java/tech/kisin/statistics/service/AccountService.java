package tech.kisin.statistics.service;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;
import tech.kisin.statistics.dao.AdministratorRepository;
import tech.kisin.statistics.dto.LoginCertificateDTO;
import tech.kisin.statistics.entity.Administrator;
import tech.kisin.statistics.result.Result;
import tech.kisin.statistics.result.ResultCode;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

import static tech.kisin.statistics.util.SecurityUtils.*;

@Service
public class AccountService {

    private final String TOKEN_COOKIE_NAME = "token";

    private final AdministratorRepository administratorRepository;

    public AccountService(AdministratorRepository administratorRepository) {
        this.administratorRepository = administratorRepository;
    }

    public Result<Boolean> register(
            HttpServletRequest request,
            HttpServletResponse response,
            LoginCertificateDTO certificate
    ) {
        if (
                request.getCookies() != null &&
                        Arrays
                                .stream(request.getCookies())
                                .map(Cookie::getName)
                                .anyMatch(name -> name.equals(TOKEN_COOKIE_NAME))
        ) {
            return new Result<>(ResultCode.USER_HAS_LOGGED_IN, false);
        }

        if (certificate.getUsername() == null || certificate.getUsername().equals("")) {
            return new Result<>(ResultCode.PARAM_IS_BLANK, false);
        }

        if (administratorRepository.existsByUsername(certificate.getUsername())) {
            return new Result<>(ResultCode.USER_USERNAME_EXISTING, false);
        }
        String salt = generateSalt();
        String token = generateToken();
        administratorRepository.save(
                new Administrator(
                        certificate.getUsername(),
                        generateHashedPassword(certificate.getPassword(), salt),
                        salt,
                        token
                )
        );

        addTokenIntoCookie(response, token);
        return new Result<>(ResultCode.SUCCESS, true);
    }

    public Result<Boolean> login(
            HttpServletRequest request,
            HttpServletResponse response,
            LoginCertificateDTO certificate
    ) {
        if (
                request.getCookies() != null &&
                        Arrays
                                .stream(request.getCookies())
                                .map(Cookie::getName)
                                .anyMatch(name -> name.equals(TOKEN_COOKIE_NAME))) {
            return new Result<>(ResultCode.USER_HAS_LOGGED_IN, false);
        }

        if (certificate.getUsername() == null || certificate.getUsername().equals("")) {
            return new Result<>(ResultCode.PARAM_IS_BLANK, false);
        }

        if (!administratorRepository.existsByUsername(certificate.getUsername())) {
            return new Result<>(ResultCode.USER_NONEXISTENT, false);
        }

        Administrator administrator = administratorRepository.getByUsername(certificate.getUsername());
        if (!administrator.getPassword().equals(generateHashedPassword(certificate.getPassword(), administrator.getSalt()))) {
            return new Result<>(ResultCode.USER_PASSWORD_INCORRECT, false);
        }

        addTokenIntoCookie(response, administrator.getToken());
        return new Result<>(ResultCode.SUCCESS, true);
    }

    private void addTokenIntoCookie(HttpServletResponse response, String token) {
        ResponseCookie cookie = ResponseCookie
                .from(TOKEN_COOKIE_NAME, token)
                .maxAge(-1)
                .path("/")
                .secure(true)
                .httpOnly(false)
                .build();
        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
    }
}
