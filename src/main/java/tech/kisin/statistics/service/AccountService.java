package tech.kisin.statistics.service;

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

    private final AdministratorRepository administratorRepository;

    public AccountService(AdministratorRepository administratorRepository) {
        this.administratorRepository = administratorRepository;
    }

    public Result<Boolean> register(
            HttpServletRequest request,
            HttpServletResponse response,
            String superToken,
            LoginCertificateDTO certificate
    ) {
        if (superToken == null || !superToken.equals(SUPER_TOKEN)) {
            return new Result<>(ResultCode.FAILURE, false);
        }

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

        addTokenIntoCookie(response, token, -1);
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
        if (
                !administrator.getPassword().equals(
                        generateHashedPassword(certificate.getPassword(), administrator.getSalt())
                )
        ) {
            return new Result<>(ResultCode.USER_PASSWORD_INCORRECT, false);
        }

        addTokenIntoCookie(response, administrator.getToken(), -1);
        return new Result<>(ResultCode.SUCCESS, true);
    }

    public Result<Boolean> logout(
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        if (request.getCookies() == null ||
                Arrays.stream(request.getCookies())
                        .map(Cookie::getName)
                        .noneMatch(name -> name.equals(TOKEN_COOKIE_NAME))
        ) {
            return new Result<>(ResultCode.USER_NOT_LOGGED_IN, false);
        }

        addTokenIntoCookie(response, "", 0);
        return new Result<>(ResultCode.SUCCESS, true);
    }

    public Result<Boolean> checkTokenValidity(
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        if (request.getCookies() == null) {
            return new Result<>(ResultCode.USER_NOT_LOGGED_IN, false);
        }
        for (Cookie cookie : request.getCookies()) {
            if (!cookie.getName().equals(TOKEN_COOKIE_NAME)) continue;

            if (administratorRepository.existsByToken(cookie.getValue())) {
                return new Result<>(ResultCode.SUCCESS, true);
            } else {
                addTokenIntoCookie(response, "", 0);
                return new Result<>(ResultCode.USER_INVALID_TOKEN, false);
            }

        }
        return new Result<>(ResultCode.USER_NOT_LOGGED_IN, false);
    }
}
