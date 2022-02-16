package tech.kisin.statistics.service;

import tech.kisin.statistics.dao.AdministratorRepository;
import tech.kisin.statistics.dto.LoginCertificateDTO;
import tech.kisin.statistics.entity.Administrator;
import tech.kisin.statistics.result.Result;
import tech.kisin.statistics.result.ResultCode;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static tech.kisin.statistics.util.PasswordUtils.generateHashedPassword;
import static tech.kisin.statistics.util.PasswordUtils.generateSalt;

public class LoginService {

    private final AdministratorRepository administratorRepository;

    public LoginService(AdministratorRepository administratorRepository) {
        this.administratorRepository = administratorRepository;
    }

    public Result<Boolean> register(
            HttpServletRequest request,
            HttpServletResponse response,
            LoginCertificateDTO loginCertificateDTO
    ) {
        if (administratorRepository.existsByUsername(loginCertificateDTO.getUsername())) {
            return new Result<>(ResultCode.USER_USERNAME_EXISTING, false);
        }
        String salt = generateSalt();
        administratorRepository.save(
                new Administrator(
                        loginCertificateDTO.getUsername(),
                        generateHashedPassword(loginCertificateDTO.getPassword(), salt),
                        salt
                )
        );
        return new Result<>(ResultCode.SUCCESS, true);
    }

    public boolean login(
            HttpServletRequest request,
            HttpServletResponse response,
            LoginCertificateDTO loginCertificateDTO
    ) {
        return true;
    }
}
