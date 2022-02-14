package tech.kisin.statistics.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import tech.kisin.statistics.result.Result;
import tech.kisin.statistics.result.ResultCode;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class LoginController {
    @PostMapping("/register")
    public Result<Boolean> register(HttpServletRequest request, HttpServletResponse response) {
        return new Result<>(ResultCode.SUCCESS, true);
    }

    @PostMapping("/login")
    public Result<Boolean> login(HttpServletRequest request, HttpServletResponse response) {
        return new Result<>(ResultCode.SUCCESS, true);
    }
}
