package tech.kisin.statistics.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tech.kisin.statistics.dto.LoginCertificateDTO;
import tech.kisin.statistics.result.Result;
import tech.kisin.statistics.service.AccountService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/register")
    public Result<Boolean> register(
            HttpServletRequest request,
            HttpServletResponse response,
            @RequestParam String superToken,
            @RequestBody LoginCertificateDTO loginCertificateDTO
    ) {
        return accountService.register(request, response, superToken, loginCertificateDTO);
    }

    @PostMapping("/login")
    public Result<Boolean> login(
            HttpServletRequest request,
            HttpServletResponse response,
            @RequestBody LoginCertificateDTO loginCertificateDTO
    ) {
        return accountService.login(request, response, loginCertificateDTO);
    }

    @PostMapping("/logout")
    public Result<Boolean> logout(
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        return accountService.logout(request, response);
    }
}
