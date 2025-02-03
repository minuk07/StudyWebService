package com.example.cookie.controller;

import com.example.cookie.db.UserRepository;
import com.example.cookie.model.UserDto;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user")
public class UserApiController {

    private final UserRepository userRepository;

    @GetMapping("/me")
    public UserDto me(
            HttpServletRequest httpServletRequest,
            @CookieValue(name = "authorization-cookie", required = false)
            String authorizationCookie // 쿠키를 직접 받는 방법.
    ){
        log.info("authorizationCookie : {}", authorizationCookie);

        var optionalUserDto = userRepository.findById(authorizationCookie);

        return optionalUserDto.get(); //존재한다면 UserDto의 내용이 반환

        /*var cookies = httpServletRequest.getCookies(); //어떠한 쿠키들이 들어있는지 확인

        if(cookies != null){

            for(Cookie cookie : cookies){
                log.info("key : {}, value : {}", cookie.getName(), cookie.getValue()); //cookie는 key-value 형태
                //key : authorization-cookie, value : c4a15cc6-8478-4d52-819d-bc91ae769222
            }
        }*/
        //return null;
    }

    @GetMapping("/me2")
    public UserDto me2(
            @RequestHeader(name = "authorization", required = false) //header에 "authorization" 값이 있따면
            String authorizationHeader // 쿠키를 직접 받는 방법.
    ){
        log.info("authorizationCookie : {}", authorizationHeader);

        var optionalUserDto = userRepository.findById(authorizationHeader);

        return optionalUserDto.get(); //존재한다면 UserDto의 내용이 반환
    }
}
