package com.example.session.controller;


import com.example.session.model.LoginRequest;
import com.example.session.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/account")
public class AccountApiController {

    private final UserService userService;

    @PostMapping("/login")
    public void login(
        @RequestBody
        LoginRequest loginRequest, //로그인할 때 받는 인자
        HttpSession httpSession //스프링에서 자동으로 해당 요청에 대한 세션을 만들어 주입.
    ){
        userService.login(loginRequest, httpSession);
    }
}
