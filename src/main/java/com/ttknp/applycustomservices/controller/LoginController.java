package com.ttknp.applycustomservices.controller;

import com.ttknp.responsecustomservice.constant.CommonStatus;
import com.ttknp.responsecustomservice.entity.ResponseObject;
import com.ttknp.security.custom.configs.jwt.JwtService;
import com.ttknp.security.custom.entities.LoginModel;
import com.ttknp.security.custom.entities.LoginRequest;
import com.ttknp.security.custom.entities.LoginResponse;
import com.ttknp.security.custom.helpers.auth.UsefulAuthHelper;
import com.ttknp.webcustomservice.annotation.CommonRestAPI;
import io.jsonwebtoken.JwtBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;


// By default /api/** it's security
@CommonRestAPI(configPath = "/api", configOrigins = "http://localhost:4200")
public class LoginController {

    private static final Logger log = LoggerFactory.getLogger(LoginController.class);
    private final JwtService jwtService;

    @Autowired
    public LoginController( JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @PostMapping(value = "/login")
    private ResponseEntity<ResponseObject<LoginResponse>> demoLogin(@RequestBody LoginRequest loginRequest) {
        LoginResponse loginResponse = new LoginResponse();
        getModels().forEach((LoginModel loginModelTemp) -> {
            // find by username
            if (loginModelTemp.getUsername().equals(loginRequest.getUsername())) {
                if ( UsefulAuthHelper.validatePasswordStringWithPasswordBCrypt(loginRequest.getPassword(), loginModelTemp.getPassword())) { // check password string with password bcrypt from database
                    log.debug("login : success");
                    JwtBuilder jwtBuilder = jwtService.generateToken(null, loginModelTemp); /// Generate token and set all details as claims,issue&expired token,... by LoginModel
                    loginResponse.setToken(jwtBuilder.compact());
                } else {
                    log.debug("login : failed");
                }
            }
        });
        return ResponseEntity
                .status((Short) CommonStatus.OK[0])
                .body(ResponseObject.builder()
                        .status((Short) CommonStatus.OK[0])
                        .info((String) CommonStatus.OK[1])
                        .data(loginResponse)
                        .build()
                );
    }


    private List<LoginModel> getModels() {
        List<LoginModel> models = new ArrayList<>();
        LoginModel loginModel = new LoginModel();
        LoginModel loginModel2 = new LoginModel();
        LoginModel loginModel3 = new LoginModel();
        LoginModel loginModel4 = new LoginModel();

        loginModel.setUsername("test");
        loginModel.setEmail("test@hotmail.com");
        loginModel.setRole("ROLE_ADMIN"); // ** required work for hasRole(...) need a prefix as ROLE_*
        loginModel.setCreateBy("ADMIN");
        loginModel.setPassword(UsefulAuthHelper.convertStringToBCryptString("1"));

        loginModel2.setUsername("test2");
        loginModel2.setEmail("test2@hotmail.com");
        loginModel2.setRole("ROLE_USER"); // ** required work for hasRole(...) need a prefix as ROLE_*
        loginModel2.setCreateBy("ADMIN");
        loginModel2.setPassword(UsefulAuthHelper.convertStringToBCryptString("1"));

        loginModel3.setUsername("test3");
        loginModel3.setEmail("test3@hotmail.com");
        loginModel3.setRole("admin"); // ** required work for hasAuthority(...)
        loginModel3.setCreateBy("ADMIN");
        loginModel3.setPassword(UsefulAuthHelper.convertStringToBCryptString("1"));

        loginModel4.setUsername("test4");
        loginModel4.setEmail("test4@hotmail.com");
        loginModel4.setRole("user"); // ** required work for hasAuthority(...)
        loginModel4.setCreateBy("ADMIN");
        loginModel4.setPassword(UsefulAuthHelper.convertStringToBCryptString("1"));

        models.add(loginModel);
        models.add(loginModel2);
        models.add(loginModel3);
        models.add(loginModel4);
        return models;
    }


}
